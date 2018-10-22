package com.yibi.core.service.impl;

import com.google.common.collect.Maps;
import com.yibi.common.utils.*;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SmsTemplateCode;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.dao.OrderTakerMapper;
import com.yibi.core.entity.*;
import com.yibi.core.exception.BanlanceNotEnoughException;
import com.yibi.core.service.*;
import com.yibi.extern.api.aliyun.smscode.SMSCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("orderTakerService")
public class OrderTakerServiceImpl implements OrderTakerService {
    @Autowired
    private SMSCodeUtil smsCodeUtil;
    @Resource
    private OrderTakerMapper orderTakerMapper;

    @Resource
    private OrderMakerService orderMakerService;
    @Resource
    private AccountService accountService;
    @Resource
    private SysparamsService sysparamsService;
    @Resource
    private RedisTemplate<String, String> redis;
    @Resource
    private UserService userService;
    @Resource
    private CoinManageService coinManageService;
    @Resource
    private DigcalRecordService digcalRecordService;

    private static final Logger logger = LoggerFactory.getLogger(OrderTakerServiceImpl.class);

    @Override
    public int insert(OrderTaker record) {
        return this.orderTakerMapper.insert(record);
    }

    @Override
    public int insertSelective(OrderTaker record) {
        return this.orderTakerMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrderTaker record) {
        return this.orderTakerMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderTaker record) {
        return this.orderTakerMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.orderTakerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public OrderTaker selectByPrimaryKey(Integer id) {
        return this.orderTakerMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OrderTaker> selectAll(Map<Object, Object> param) {
        return this.orderTakerMapper.selectAll(param);
    }

    @Override
    public List<OrderTaker> selectPaging(Map<Object, Object> param) {
        return this.orderTakerMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.orderTakerMapper.selectCount(param);
    }

    @Override
    public List<?> queryAppList(Integer userId, Integer orderType, Integer state, Integer userType, Integer coinType, Integer page, Integer rows) {
        Map<Object,Object> map = Maps.newHashMap();
        if(userType == GlobalParams.C2C_USER_TAKER){
            map.put("type",orderType);
            map.put("userid",userId);
        }else{
            map.put("type", Tools.reverseZeroOne(orderType));
            map.put("makeruserid",userId);
        }
        map.put("state",state);
        map.put("cointype",coinType);
        map.put("firstResult",page*rows);
        map.put("maxResult",rows);
        return this.orderTakerMapper.queryAppList(map);
    }

    @Override
    public int queryUserCount(Integer userId) {
        return this.orderTakerMapper.queryUserCount(userId);
    }

    @Override
    public List<Map<String, Object>> selectConditionPaging(Map<Object, Object> map) {
        return this.orderTakerMapper.selectConditionPaging(map);
    }

    @Override
    public int selectConditionCount(Map<Object, Object> map) {
        return this.orderTakerMapper.selectConditionCount(map);
    }

    @Override
    public void cancelOrder(OrderTaker taker ,Integer operId,Integer state) {
        taker.setState(state);
        taker.setInactivetime(null);
        this.updateByPrimaryKey(taker);

		/*卖出交易撤销时返回普通用户冻结数量*/
        if(taker.getType() == GlobalParams.ORDER_TYPE_SALE){
            accountService.updateAccountAndInsertFlow(taker.getUserid(),GlobalParams.ACCOUNT_TYPE_C2C,taker.getCointype(),taker.getAmount(),
                    BigDecimalUtils.plusMinus(taker.getAmount()),operId,"法币交易撤销",taker.getId());
        }

        //修改maker剩余数量
        orderMakerService.updateOrderRemain(taker.getMakerid(),taker.getAmount(),BigDecimalUtils.plusMinus(taker.getAmount()));


		/*累计取消次数*/
        int userRole = taker  .getType() == GlobalParams.ORDER_TYPE_BUY?GlobalParams.C2C_USER_TAKER:GlobalParams.C2C_USER_MAKER;
        Integer buyUserId = taker.getType() == GlobalParams.ORDER_TYPE_BUY? taker.getUserid():taker.getMakeruserid();
        int count = addCountsOfCancelOrder(buyUserId,userRole);

		/*如果是商家，且取消次数超过限制，则撤销所有未成交的买入交易*/
        if(userRole==GlobalParams.C2C_USER_MAKER&&!countOfCancelOrderEnable(taker.getMakeruserid(),userRole,count)){
            User depositUser = userService.getByRole(GlobalParams.ROLE_TYPE_PLATFORM);
            List<OrderMaker> makers = orderMakerService.queryByOrderTypeAndState(taker.getMakeruserid(), GlobalParams.ORDER_TYPE_BUY, GlobalParams.ORDER_STATE_UNTREATED);
            for(OrderMaker maker :makers){
                accountService.updateAccountAndInsertFlow(taker.getMakeruserid(),GlobalParams.ACCOUNT_TYPE_C2C,maker.getCointype(),
                        BigDecimal.ZERO,BigDecimalUtils.plusMinus(maker.getDeposit()),operId,"",maker.getId());

                if(depositUser!=null){
                    accountService.updateAccountAndInsertFlow(depositUser.getId(),GlobalParams.ACCOUNT_TYPE_C2C,maker.getCointype(),maker.getDeposit(),BigDecimal.ZERO,
                            operId,"法币交易保证金",maker.getId());

                }
                maker.setState(GlobalParams.ORDER_STATE_BACK);
                orderMakerService.updateByPrimaryKey(maker);            }
        }

		/*短信通知买家*/
        User buyUser = userService.selectByPrimaryKey(buyUserId);
        CoinManage coinManage = coinManageService.queryByCoinType(taker.getCointype());
        if(buyUser!=null){
            Map<String, String> params = new HashMap();
            params.put("createTime", TimeStampUtils.toTimeString(taker.getCreatetime()));
            params.put("orderNum", taker.getOrdernum());
            params.put("price", BigDecimalUtils.toString(taker.getPrice()));
            params.put("coinType", coinManage.getCoinname());
            params.put("amount",  BigDecimalUtils.toString(taker.getAmount()));
            params.put("total", BigDecimalUtils.toString(taker.getTotal()));
            smsCodeUtil.sendSms(buyUser.getPhone(), SmsTemplateCode.SMS_C2C_OVERTIME_NOTIYF, params);
        }
    }

    /**
     * 累计用户当天的取消次数
     * @param userId
     * @param userRole
     * @return void
     * @date 2018-2-28
     * @author lina
     */
    public int addCountsOfCancelOrder(Integer userId,Integer userRole){
        String today = DATE.getCurrentDateStr();
        String key = String.format(RedisKey.C2C_ORDERS_CANCEL, userId,userRole,today);

        String count = RedisUtil.searchString(redis, key);
        int nextCount = StrUtils.isBlank(count)?1:Integer.parseInt(count)+1;
        RedisUtil.addString(redis, key, TimeUnit.HOURS.toSeconds(24), nextCount+"");

        return nextCount;
    }


    /**
     * 判断买入取消次数是否在有效内
     * @param userId
     * @param userRole
     * @return boolean
     * @date 2018-2-28
     * @author lina
     */
    public boolean countOfCancelOrderEnable(Integer userId,int userRole,Integer count){
        String paramKey = userRole == GlobalParams.C2C_USER_TAKER? SystemParams.ORDER_C2C_CANCEL_LIMIT_TAKER:SystemParams.ORDER_C2C_CANCEL_LIMIT_MAKER;
        Sysparams param = sysparamsService.getValByKey(paramKey);
        int limit = param==null?0:Integer.parseInt(param.getKeyval());

        if(count==null){
            String countKey = String.format(RedisKey.C2C_ORDERS_CANCEL, userId,userRole,DATE.getCurrentDate());
            String countVal = RedisUtil.searchString(redis, countKey);
            count = StrUtils.isBlank(countVal)?0:Integer.parseInt(countVal);
        }
        return count <= limit;

    }

    @Override
    public void confirmOrder(OrderTaker taker,Integer operId) {
        /*修改订单状态*/
        taker.setState(GlobalParams.C2C_ORDER_STATE_FINISHED);
        taker.setInactivetime(null);
        this.updateByPrimaryKey(taker);

		/*如果订单是卖出，则增加MakerUser的可用余额，减少TakerUser的冻结金额*/
        try {
            if(taker.getType() ==GlobalParams.ORDER_TYPE_SALE){
                accountService.updateAccountAndInsertFlow(taker.getMakeruserid(),GlobalParams.ACCOUNT_TYPE_C2C,taker.getCointype(),taker.getAmount(),BigDecimal.ZERO,
                        operId,"法币交易卖出",taker.getId());
                accountService.updateAccountAndInsertFlow(taker.getUserid(),GlobalParams.ACCOUNT_TYPE_C2C,taker.getCointype(),BigDecimal.ZERO,BigDecimalUtils.plusMinus(taker.getAmount()),
                        operId,"法币交易买入",taker.getId());
            }else{
                /*如果订单是买入，则增加TakerUser的可用余额，减少MakerUser的冻结金额*/
                accountService.updateAccountAndInsertFlow(taker.getUserid(),GlobalParams.ACCOUNT_TYPE_C2C,taker.getCointype(),taker.getAmount(),BigDecimal.ZERO,
                        operId,"法币交易买入",taker.getId());
                accountService.updateAccountAndInsertFlow(taker.getMakeruserid(),GlobalParams.ACCOUNT_TYPE_C2C,taker.getCointype(),BigDecimal.ZERO,BigDecimalUtils.plusMinus(taker.getAmount()),
                        operId,"法币交易卖出",taker.getId());
            }

            //减少商家委托单的冻结数量
            OrderMaker maker = orderMakerService.selectByPrimaryKey(taker.getMakerid());
            maker.setFrozen(BigDecimalUtils.subtract(maker.getFrozen(), taker.getAmount()));

            /*如果商家的剩余数量为0，且冻结数量也为0，则商家委托单状态改为已完成*/
            if(maker.getFrozen().compareTo(BigDecimal.ZERO)==0 && maker.getRemain().compareTo(BigDecimal.ZERO)==0){
                maker.setState(GlobalParams.ORDER_STATE_TREATED);
                accountService.updateAccountAndInsertFlow(maker.getUserid(),GlobalParams.ACCOUNT_TYPE_C2C,maker.getCointype(),maker.getDeposit(),BigDecimalUtils.plusMinus(maker.getDeposit()),
                        operId,"法币交易保证金",maker.getId());
            }
            orderMakerService.updateByPrimaryKey(maker);
        } catch (BanlanceNotEnoughException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        //更新最新的成交价
        String key = String.format(RedisKey.C2C_PRICE, taker.getCointype());
        RedisUtil.addString(redis, key, BigDecimalUtils.toString(taker.getPrice()));

        //   增加交易魂力
        digcalRecordService.insertOrderCalculForce(taker.getUserid());
        digcalRecordService.insertOrderCalculForce(taker.getMakeruserid());

		/*短信通知买家*/
        Integer buyUserId = taker.getType() == GlobalParams.ORDER_TYPE_BUY?taker.getUserid():taker.getMakeruserid();
        CoinManage coinManage = coinManageService.queryByCoinType(taker.getCointype());
        User buyUser = userService.selectByPrimaryKey(buyUserId);
        if(buyUser!=null){
            Map<String, String> params = new HashMap();
            params.put("orderNum", taker.getOrdernum());
            params.put("price", BigDecimalUtils.toString(taker.getPrice()));
            params.put("coinType", coinManage==null?"":coinManage.getCoinname() );
            params.put("amount",  BigDecimalUtils.toString(taker.getAmount()));
            params.put("total", BigDecimalUtils.toString(taker.getTotal()));
            smsCodeUtil.sendSms(buyUser.getPhone(), SmsTemplateCode.SMS_C2C_RECEIPT_NOTIFY, params);
        }
    }

    @Override
    public OrderTaker queryByFlagNum(String flagNum) {
        Map<Object, Object> params = new HashMap();
        params.put("flagnum", flagNum);
        List<OrderTaker> list = this.selectAll(params);
        return list ==null || list.isEmpty()? null:list.get(0);
    }
}