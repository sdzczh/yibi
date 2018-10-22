package com.yibi.orderapi.biz.impl;

import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.DATE;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.TimeStampUtils;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import com.yibi.orderapi.biz.ChatTransferBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/12 0012.
 */
@Service
@Transactional
public class ChatTransferBizImpl extends BaseBizImpl implements ChatTransferBiz {
    
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private ChatTransferService chatTransferService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CoinScaleService coinScaleService;
    @Autowired
    private BaseService baseService;
    @Override
    public String sendFransfer(User user, String phone, Integer accountType, Integer coinType, BigDecimal amount, String note, String password) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        CoinScale coinScale = coinScaleService.queryByCoin(coinType, CoinType.NONE);

		/*判断功能是否关闭*/
        Sysparams systemParam = sysparamsService.getValByKey(SystemParams.TRANSFER_ONOFF);
        if(systemParam!=null&&systemParam.getKeyval().equals("-1")){
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }

		/*输入金额校验*/
        if(amount.compareTo(BigDecimal.ZERO)<1){
            return Result.toResult(ResultCode.AMOUNT_ERROR);
        }

		/*实名认证判断*/
        if(user.getIdstatus()== GlobalParams.INACTIVE){
            return  Result.toResult(ResultCode.USER_NOT_REALNAME);
        }

		/*校验交易密码*/
        if(!StrUtils.isBlank(password)){
            String valiStr = validateOrderPassword(user, password);
            if(valiStr!=null){
                return valiStr;
            }
        }

		/*日限额判断*/
        BigDecimal dayAmt = chatTransferService.querySumSendDay(user.getId(), DATE.getCurrentDateStr());
        CoinManage coinManage = coinManageService.queryByCoinType(coinType);
        BigDecimal dayAmtTotal = BigDecimalUtils.add(dayAmt,amount);
        if(coinManage!=null && dayAmtTotal.compareTo(coinManage.getTransfermaxamtday())==1){
            return Result.toResultFormat(ResultCode.TAKL_TRANSFER_AMOUNT_MAXERROR, BigDecimalUtils.toString(coinManage.getTransfermaxamtday())+coinManage.getCoinname());
        }

		/*转入账户验证*/
        if(phone.equals(user.getPhone())){
            return  Result.toResult(ResultCode.TO_USER_NOT_ONESELF);
        }
        User toUser = userService.selectByPhone(phone);
        if(toUser==null){
            return  Result.toResult(ResultCode.TO_USER_NOT_EXIST);
        }
        if(toUser.getState()==GlobalParams.FORBIDDEN){
            return  Result.toResult(ResultCode.TO_USER_FORBIDDEN);
        }
        if(toUser.getState()==GlobalParams.LOGOFF){
            return  Result.toResult(ResultCode.TO_USER_LOGOFF);
        }
        if(toUser.getIdstatus()==GlobalParams.INACTIVE){
            return  Result.toResult(ResultCode.TO_USER_NOT_REALNAME);
        }

		/*费率计算*/
        BigDecimal rate = new BigDecimal(0);
        Sysparams rateParam = sysparamsService.getValByKey(SystemParams.TRANSFER_RATE);
        if(rateParam!=null){
            rate = new BigDecimal(rateParam.getKeyval());
        }

        BigDecimal fee = coinScale==null?BigDecimal.ZERO:BigDecimalUtils.multiply(amount, rate,coinScale.getCalculscale());
        BigDecimal remain = BigDecimalUtils.subtract(amount, fee);

		/*记录转账记录*/
        ChatTransfer transfer = new ChatTransfer();
        transfer.setFromuserid(user.getId());
        transfer.setTouserid(toUser.getId());
        transfer.setCointype(coinType);
        transfer.setAmount(amount);
        transfer.setFee(fee);
        transfer.setRemain(remain);
        transfer.setAmtofcny(BigDecimalUtils.multiply(amount, baseService.getPriceOfCNY(coinType)));
        transfer.setNote(note);
        chatTransferService.insert(transfer);


        /*更新转出账并保存流水*/
        accountService.updateAccountAndInsertFlow(user.getId(),accountType,coinType,BigDecimalUtils.plusMinus(amount),BigDecimal.ZERO,user.getId(),"转账",transfer.getId());
		/*更新服务费账户并保存流水*/
        User feeUser = userService.getByRole(GlobalParams.ROLE_TYPE_PLATFORM);
        if(feeUser!=null){
        accountService.updateAccountAndInsertFlow(feeUser.getId(),accountType,coinType,fee,BigDecimal.ZERO,user.getId(),"转账手续费",transfer.getId());
        }
        /*更新转入账并保存流水*/
        accountService.updateAccountAndInsertFlow(toUser.getId(),accountType,coinType,remain,BigDecimal.ZERO,user.getId(),"转账",transfer.getId());

        map.put("id", transfer.getId());
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String queryTransferDetail(User user, Integer id) {

        Map<String, Object> map = chatTransferService.queryByIdAndUserId(id, user.getId());
        if(map == null){
            return Result.toResult(ResultCode.RESULE_DATA_NONE);
        }

        Integer coinType = (Integer) map.get("coinType");
        BigDecimal amount = new BigDecimal(map.get("amount").toString());
        BigDecimal amtOfCny = new BigDecimal(map.get("amtOfCny").toString());
        map.put("amount", BigDecimalUtils.toString(amount,coinType.intValue()));
        map.put("amtOfCny", BigDecimalUtils.toString(amtOfCny,2));
        map.put("createTime", TimeStampUtils.toTimeString((Timestamp)map.get("createTime")));
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String queryTransferList(User user, Integer type, Integer page, Integer rows) {
        Map<String, Object> map = new HashMap();
        List<?> list = new ArrayList();
        page = page ==null?0:page;
        rows = rows ==null?10:rows;
        BigDecimal sumAmtOfCny = BigDecimal.ZERO;
        if(type == 0){
			/*转入记录*/
            list = chatTransferService.queryReciveList(user.getId(),page,rows) ;
            sumAmtOfCny = chatTransferService.querySumRecive(user.getId());
        }else if(type == 1){
			/*转出记录*/
            list = chatTransferService.querySendList(user.getId(),page,rows);
            sumAmtOfCny = chatTransferService.querySumSend(user.getId());
        }else{
            Result.toResult(ResultCode.PARAM_IS_INVALID);
        }

        if(list!=null){
            for(Object obj :list){
                Map<String, Object> data = (Map<String, Object>)obj;
                Integer coinType = Integer.parseInt(data.get("coinType").toString());
                BigDecimal amount = new BigDecimal(data.get("amount").toString());
                BigDecimal amtOfCny = new BigDecimal(data.get("amtOfCny").toString());
                data.put("amount", BigDecimalUtils.toString(amount,coinType));
                data.put("amtOfCny", BigDecimalUtils.toString(amtOfCny,2));
                data.put("createTime", TimeStampUtils.toTimeString((Timestamp)data.get("createTime")));
            }
        }
        map.put("list", list);
        map.put("sumAmtOfCny", BigDecimalUtils.toString(sumAmtOfCny,2   ));
        return Result.toResult(ResultCode.SUCCESS,map);
    }
}
