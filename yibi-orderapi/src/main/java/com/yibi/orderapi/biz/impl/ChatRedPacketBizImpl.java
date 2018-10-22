package com.yibi.orderapi.biz.impl;

import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.TimeStampUtils;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.ChatRedpacket;
import com.yibi.core.entity.ChatRedpacketRecive;
import com.yibi.core.entity.CoinManage;
import com.yibi.core.entity.User;
import com.yibi.core.exception.BanlanceNotEnoughException;
import com.yibi.core.service.*;
import com.yibi.orderapi.Constants;
import com.yibi.orderapi.biz.ChatRedPacketBiz;
import com.yibi.orderapi.dto.RedPacketDto;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Administrator on 2018/7/11 0011.
 */
@Service
@Transactional
public class ChatRedPacketBizImpl extends BaseBizImpl implements ChatRedPacketBiz {

    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatRedpacketService chatRedpacketService;
    @Autowired
    private ChatRedpacketReciveService chatRedpacketReciveService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RedisTemplate<String,String> redis;
    @Autowired
    private BaseService baseService;


    @Override
    public String sendRedPacket(User user, Integer accountType, Integer coinType, BigDecimal amount, Integer num, String note, String password, String phone) {
        /*功能开关校验*/
        String onoff = sysparamsService.getValStringByKey(SystemParams.TALK_RED_PACKET_ONOFF);
        if("-1".equals(onoff)){
            return  Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }
		/*转入账户验证*/
        if(phone.equals(user.getPhone())){
            return  Result.toResult(ResultCode.TAKL_REDPACKET_NOT_SELF);
        }

		/*金额最小值判断*/
        if(amount.compareTo(BigDecimal.ZERO)<1){
            return  Result.toResult(ResultCode.TAKL_REDPACKET_AMOUNT_MINERROR);
        }

		/*金额最大值判断*/
        CoinManage coinManage = coinManageService.queryByCoinType(coinType);
        if(coinManage!=null && amount.compareTo(coinManage.getRedpacketmaxamtsingle())==1){
            return Result.toResultFormat(ResultCode.TAKL_REDPACKET_AMOUNT_MAXERROR, BigDecimalUtils.toString(coinManage.getRedpacketmaxamtsingle()));
        }

		/*实名认证检查*/
        if(user.getIdstatus()== GlobalParams.INACTIVE){
            return Result.toResult(ResultCode.USER_NOT_REALNAME);
        }
        User reviceUser = userService.selectByPhone(phone);
        if(reviceUser==null){
            return  Result.toResult(ResultCode.TO_USER_NOT_EXIST);
        }
        if(reviceUser.getState()==GlobalParams.FORBIDDEN){
            return  Result.toResult(ResultCode.TO_USER_FORBIDDEN);
        }
        if(reviceUser.getState()==GlobalParams.LOGOFF){
            return  Result.toResult(ResultCode.TO_USER_LOGOFF);
        }
        if(reviceUser.getIdstatus()==GlobalParams.INACTIVE){
            return  Result.toResult(ResultCode.TO_USER_NOT_REALNAME);
        }

		/*校验交易密码*/
        if(!StrUtils.isBlank(password)){
            String valiStr = validateOrderPassword(user, password);
            if(valiStr!=null){
                return valiStr;
            }
        }

		/*保存红包记录*/
        BigDecimal amtOfCny = BigDecimalUtils.multiply(baseService.getPriceOfCNY(coinType), amount);
        ChatRedpacket red = createRedPacket(user, accountType, coinType, amount, num, note,amtOfCny);

        createRedPacketRecive(red, reviceUser, coinType, amount, amtOfCny);

		/*更新账户并记录流水*/
        accountService.updateAccountAndInsertFlow(user.getId(),GlobalParams.ACCOUNT_TYPE_SPOT,coinType,BigDecimalUtils.plusMinus(amount),BigDecimal.ZERO,user.getId(),"红包",red.getId());


        Map<String, Object> map = new HashMap();
        map.put("id", red.getId());

		/*加入超时队列*/
        RedisUtil.addListRight(redis, RedisKey.RED_PACKET_OUTTIME_QUEUE, red);

        return Result.toResult(ResultCode.SUCCESS,map);
    }

    @Override
    public String queryRedPacketDetail(User user, Integer id) {
        ChatRedpacket red = chatRedpacketService.selectByPrimaryKey(id);
        if(red == null ){
            Result.toResult(ResultCode.TAKL_REDPACKET_NOT_EXIST);
        }

		/*设置红包接收方信息*/
        List<?> revices = chatRedpacketReciveService.queryRedPacketRecives(user.getId(), id);
        for(Object rec :revices){
            Map<String, Object> map = (Map<String, Object>)rec;
            map.put("amount",BigDecimalUtils.toString((BigDecimal)map.get("amount")));
            map.put("amtOfCny",BigDecimalUtils.toString((BigDecimal)map.get("amtOfCny"),2));
            map.put("reciveTime", TimeStampUtils.toTimeString((Timestamp)map.get("reciveTime")));
        }
        User sendUser = red.getSenduserid() == user.getId()?user:userService.selectByPrimaryKey(red.getSenduserid());

        return Result.toResult(ResultCode.SUCCESS, getRedPacketInfo(sendUser,red,revices));
    }

    @Override
    public String reciveRedPacket(User user, Integer id) {

        ChatRedpacket red = chatRedpacketService.selectByPrimaryKey(id);
        if(red == null ){
            Result.toResult(ResultCode.TAKL_REDPACKET_NOT_EXIST);
        }
        if(red.getState() == GlobalParams.ACTIVE){
            Result.toResult(ResultCode.TAKL_REDPACKET_FINISHED);
        }

		/*单个红包领取*/
        if(red.getType() == GlobalParams.RED_PACKET_SINGLE ){
            ChatRedpacketRecive recive = chatRedpacketReciveService.getReciveByPacketId(id, user.getId());
            if(recive == null || recive.getUserid().intValue() != user.getId()){
                return Result.toResult(ResultCode.TAKL_REDPACKET_ERROR);
            }

			/*更新领取时间*/
            recive.setRecivetime(new Timestamp(System.currentTimeMillis()));
            chatRedpacketReciveService.updateByPrimaryKey(recive);

			/*更新红包状态*/
            red.setState(GlobalParams.ACTIVE);
            red.setRemainamt(BigDecimal.ZERO);
            red.setRemainnum(0);
            chatRedpacketService.updateByPrimaryKey(red);

			/*更新账户余额*/
            try {
                accountService.updateAccountAndInsertFlow(user.getId(),GlobalParams.ACCOUNT_TYPE_SPOT,red.getCointype(),recive.getAmount(),BigDecimal.ZERO,user.getId(),"红包",red.getId());
            } catch (BanlanceNotEnoughException e) {
                e.printStackTrace();
            }


			/*获取红包详情*/
            List reciveList = new ArrayList();
            Map recv = new HashMap<String,Object>();
            recv.put("name",user.getNickname());
            recv.put("headPath",user.getHeadpath());
            recv.put("amount",BigDecimalUtils.toString(recive.getAmount()));
            recv.put("amtOfCny",BigDecimalUtils.toString(recive.getAmtofcny(), 2));
            recv.put("besthand",0);
            recv.put("reciveTime",TimeStampUtils.toTimeString(recive.getUpdatetime()));
            recv.put("coinType",red.getCointype());
            reciveList.add(recv);
            User sendUser = userService.selectByPrimaryKey(red.getSenduserid());
            return Result.toResult(ResultCode.SUCCESS, getRedPacketInfo(sendUser,red,reciveList));
        }

        return Result.toResult(ResultCode.PERMISSION_NO_OPEN);
    }

    @Override
    public String queryRedPacketList(User user, Integer type, Integer page, Integer rows) {
        HashMap map = new HashMap();
        List<?> list = new ArrayList();
        page = page ==null?0:page;
        rows = rows ==null?10:rows;
        BigDecimal sumAmtOfCny = BigDecimal.ZERO;
        if(type == 0){
			/*收到的红包*/
            list = chatRedpacketReciveService.queryReciveList(user.getId(),page,rows) ;
            sumAmtOfCny = chatRedpacketReciveService.querySumRecive(user.getId());
        }else if(type == 1){
			/*发送的红包*/
            list = chatRedpacketService.querySendList(user.getId(),page,rows);
            sumAmtOfCny = chatRedpacketService.querySumSend(user.getId());
        }else{
            Result.toResult(ResultCode.PARAM_IS_INVALID);
        }

        if(list!=null){
            for(Object obj :list){
                Map<String, Object> data = (Map<String, Object>)obj;
                Integer coinType = Integer.parseInt(data.get("coinType").toString());
                BigDecimal amount = new BigDecimal(data.get("amount").toString());
                BigDecimal amtOfCny = new BigDecimal(data.get("amtOfCny").toString());
                data.put("amount", BigDecimalUtils.toString(amount));
                data.put("amtOfCny", BigDecimalUtils.toString(amtOfCny,2));
                data.put("createTime", TimeStampUtils.toTimeString((Timestamp)data.get("createTime")));
            }
        }
        map.put("list", list);
        map.put("sumAmtOfCny", BigDecimalUtils.toString(sumAmtOfCny,2));
        return Result.toResult(ResultCode.SUCCESS,map);
    }


    /**
     * 返回红包详情信息
     * @param sendUser
     * @param red
     * @param recives
     * @return RedPacketModel
     * @date 2018-5-21
     * @author lina
     */
    public RedPacketDto getRedPacketInfo(User sendUser, ChatRedpacket red, List<?> recives){
        //设置红包详情
        RedPacketDto rpm = new RedPacketDto();
        rpm.setCoinType(red.getCointype());
        rpm.setAmount(BigDecimalUtils.toString(red.getAmount()));
        rpm.setNum(red.getNum());
        rpm.setRemainAmt(BigDecimalUtils.toString(red.getRemainamt()));
        rpm.setRemainNum(red.getRemainnum());
        rpm.setAmtOfCny(BigDecimalUtils.toString(red.getAmtofcny(),2));
        rpm.setState(red.getState());
        rpm.setNote(red.getNote());
        rpm.setInactiveTime(TimeStampUtils.toTimeString( red.getInactivetime()));
        rpm.setName(sendUser.getNickname());
        rpm.setHeadPath(sendUser.getHeadpath());
        rpm.setReciveList(recives);

        return rpm;
    }


    public ChatRedpacket createRedPacket(User user, Integer accountType,
                                         Integer coinType, BigDecimal amount,Integer num, String note,BigDecimal amtOfCny) {

        Calendar current = Calendar.getInstance();
        current.add(Calendar.HOUR, Constants.RED_PACKET_TINMOUT);
        ChatRedpacket red = new ChatRedpacket();
        red.setSenduserid(user.getId());
        red.setAccounttype(accountType);
        red.setType(num > 1 ? GlobalParams.RED_PACKET_GROUP : GlobalParams.RED_PACKET_SINGLE);
        red.setCointype(coinType);
        red.setAmount(amount);
        red.setNum(num);
        red.setAmtofcny(amtOfCny);
        red.setRemainamt(amount);
        red.setRemainnum(num);
        red.setState(GlobalParams.INACTIVE);//未领取
        red.setInactivetime(new Timestamp(current.getTimeInMillis()));
        red.setNote(note);
        chatRedpacketService.insert(red);


        return red;
    }

    /**
     * 保存红包接收方
     * @param sendPacket
     * @param reviceUser
     * @param coinType
     * @param amount
     * @param amtOfCny
     * @return TalkRedpacketRecive
     * @date 2018-5-21
     * @author lina
     */
    public ChatRedpacketRecive createRedPacketRecive(ChatRedpacket sendPacket,User reviceUser,
                                                     Integer coinType, BigDecimal amount,BigDecimal amtOfCny){

        ChatRedpacketRecive red = new ChatRedpacketRecive();
        red.setUserid(reviceUser.getId());
        red.setAmount(amount);
        red.setAmtofcny(amtOfCny);
        red.setCointype(coinType);
        red.setRedpacid(sendPacket.getId());

        chatRedpacketReciveService.insert(red);
        return red;
    }
}
