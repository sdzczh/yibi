package com.yibi.orderapi.biz.impl;

import com.yibi.common.utils.*;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SmsTemplateCode;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.exception.BanlanceNotEnoughException;
import com.yibi.core.service.*;
import com.yibi.extern.api.aliyun.smscode.SMSCodeUtil;
import com.yibi.orderapi.biz.OrderTakerBiz;
import com.yibi.orderapi.dto.OrderMakerDto;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/7/16 0016.
 */

@Service("orderTakerBiz")
@Transactional
public class OrderTakerBizImpl extends BaseBizImpl implements OrderTakerBiz {
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private CoinScaleService coinScaleService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private BindInfoService bindInfoService;
    @Autowired
    private OrderTakerService orderTakerService;
    @Autowired
    private OrderMakerService orderMakerService;
    @Autowired
    private RedisTemplate<String,String> redis;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private SMSCodeUtil smsCodeUtil;
    @Autowired
    private OrderSpotRecordService orderSpotRecordService;


    @Override
    @Transactional
    public String takerReleaseDeal(User user, Integer coinType, BigDecimal amount, Integer orderId, String password,Integer orderType) {

        CoinScale coinScale = coinScaleService.queryByCoin(coinType,CoinType.NONE);
        amount = BigDecimalUtils.roundDown(amount,coinScale ==null?4:coinScale.getC2cnumscale());
        /*功能开关校验*/
        CoinManage coinManage = coinManageService.queryByCoinType(coinType);
        if(coinManage!=null &&coinManage.getC2conoff()==GlobalParams.INACTIVE){
            return  Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }

        /*实名认证检查*/
        if(user.getIdstatus() == GlobalParams.INACTIVE){
            return Result.toResult(ResultCode.USER_NOT_REALNAME);
        }

        /*校验交易密码*/
        if(!StrUtils.isBlank(password)){
            String valiStr = validateOrderPassword(user, password);
            if(valiStr!=null){
                return valiStr;
            }
        }

        /*判断普通用户是否存在未成交订单*/
        /*Boolean checkOrderNum = checkOrderNum(user, coinType,orderType);
        if(checkOrderNum){
            return Result.toResult(ResultCode.ORDER_C2C_BUY_LIMIT);
        }*/

        if(orderType == GlobalParams.ORDER_TYPE_BUY && !countOfCancelOrderEnable(user.getId(),GlobalParams.C2C_USER_TAKER,null)){
            /*买入订单判断用户的当日取消次数*/
            return Result.toResult(ResultCode.ORDER_C2C_CANCEL_LIMIT);
        }

        List<BindInfo> info = bindInfoService.queryByUser(user.getId());
        if(orderType == GlobalParams.ORDER_TYPE_SALE && (info==null ||info.isEmpty())){
           /* 卖出订单判断是否存在绑定信息*/
            return Result.toResult(ResultCode.PAY_INFO_NOT_BIND);
        }


        Map<String , Object> res = new HashMap<>();
        ResultCode result = ResultCode.ORDER_NO_MATCH;
        //指定商家交易
        if(orderId!=null && orderId>0){
            OrderMaker maker = orderMakerService.selectByPrimaryKey(orderId);

			/*商家订单不为空，并且交易类型不一致的情况下进行成交匹配*/
            if(maker!=null&&maker.getType()!=orderType){
                result = makeDeal(user.getId(),  maker, amount,info,coinScale,coinManage,res);
                return Result.toResult(result,res);
            }

        }else{
			/*快速卖出*/
            return Result.toResult(ResultCode.PERMISSION_NO_OPEN);
        }

        return  Result.toResult(result,res);
    }

    /**
     * 交易匹配
     * @param takerUserId
     * @param maker
     * @param amount
     * @param info
     * @param res
     * @return
     * @throws BanlanceNotEnoughException
     */
    public ResultCode makeDeal(Integer takerUserId, OrderMaker maker, BigDecimal amount, List<BindInfo> info,CoinScale coinScale,CoinManage coinManage,  Map<String , Object> res) {
		/*用户状态判断*/
        User makerUser = userService.selectByPrimaryKey(maker.getUserid());
        if(makerUser.getState() !=GlobalParams.ACTIVE){
            return ResultCode.TO_USER_FORBIDDEN;
        }

		/*自己无法跟自己交易*/
        if(takerUserId.intValue() == maker.getUserid().intValue()){
            return ResultCode.ORDER_USER_ILLEGAL;
        }

		/*商家委托订单状态判断*/
        if(maker.getState()!=GlobalParams.ORDER_STATE_UNTREATED){
            return ResultCode.ORDER_STATE_HAS_DEAL;
        }
		/*商户接单判断*/
        if(!maker.getOrderflag()){
            return ResultCode.ORDER_STATE_HAS_STOP_RECEIPT;
        }

		/*数量判断*/
        if(amount.compareTo(maker.getRemain())==1){
            return ResultCode.AMOUNT_MAKER_NOT_ENOUGH;
        }
		/*成交额判断*/
        BigDecimal total = BigDecimalUtils.multiply(amount, maker.getPrice(),coinScale==null?4:coinScale.getC2ctotalamtscale());
        if(total.compareTo(maker.getTotalmin())==-1||total.compareTo(maker.getTotalmax())==1){
            return ResultCode.ORDER_C2C_TOTAL_ERROR;
        }

		/*商家委托是买入，即普通用户卖出时*/
        if(maker.getType() == GlobalParams.ORDER_TYPE_BUY){
			/*支付方式判断*/
            if(!payTypeEnable(maker.getPaytype(), info)){
                return ResultCode.PAY_INFO_NOT_BIND;
            }
        }


        //保存toker
        OrderTaker taker = createOrderTaker(takerUserId, maker, amount,total);
        orderTakerService.insert(taker);
        res.put("id", taker.getId());

        //maker剩余数量减去amount，冻结数量加上amount
        try {

        orderMakerService.updateOrderRemain(maker.getId(), BigDecimalUtils.plusMinus(amount),amount);
        }catch (BanlanceNotEnoughException e){
            e.printStackTrace();
            throw new RuntimeException();
        }

        //普通用户提交卖出订单的情况下，减少用户的可用余额
        if(maker.getType() == GlobalParams.ORDER_TYPE_BUY){
            accountService.updateAccountAndInsertFlow(takerUserId,GlobalParams.ACCOUNT_TYPE_C2C,maker.getCointype(),BigDecimalUtils.plusMinus(amount),amount,takerUserId,"法币交易，用户卖出",taker.getId());
        }

		/*订单加入到待付款订单队列中*/
        int interval = 30;
        Sysparams param1 = sysparamsService.getValByKey(SystemParams.ORDER_C2C_NOTPAY_INACTIVE_INTERVAL);
        if(param1!=null){
            interval = Integer.parseInt(param1.getKeyval());
        }
        addOverTimeQueue(taker,RedisKey.C2C_ORDERS_NOTPAY_KEY_NAME,RedisKey.C2C_ORDERS_NOTPAY,interval);

		/*如果商家是买入，则通知商家订单匹配成功*/
        if(maker.getType() == GlobalParams.ORDER_TYPE_BUY){
            Map<String, String> params = new HashMap<String, String>();
            params.put("orderNum", taker.getOrdernum());
            params.put("price", BigDecimalUtils.toString(taker.getPrice()));
            params.put("coinType", coinManage==null?"":coinManage.getCoinname());
            params.put("amount",  BigDecimalUtils.toString(taker.getAmount()));
            params.put("total", BigDecimalUtils.toString(taker.getTotal()));
            smsCodeUtil.sendSms(makerUser.getPhone(), SmsTemplateCode.SMS_C2C_MAKER_MATCH_SALER, params);
        }


        return ResultCode.SUCCESS;
    }

    /**
     * 将订单加入到超时队列中， （名称list保存订单队列的key集合，订单队列保存订单集合）
     * @param nameListKey nameList的key
     * @param queueOrderKey 订单队列的key
     * @param timeout 超时时间
     * @return void
     * @date 2018-3-7
     * @author lina
     */
    public void addOverTimeQueue(OrderTaker taker ,String nameListKey,String queueOrderKey,int timeout){
		/*从nameList的最右侧取出最新的queueKey,如果不存在则生成新的queueKey,并添加到nameList中*/
        String keyName = "";
        long keySize = RedisUtil.searchListSize(redis, nameListKey);
        if(keySize == 0){
            keyName = String.format(queueOrderKey, timeout);
            RedisUtil.addListRight(redis, nameListKey, keyName);
        }else{
            keyName = RedisUtil.searchIndexList(redis,nameListKey,keySize-1);
            String keyNameNew = String.format(queueOrderKey, timeout);
            if(!keyName.equals(keyNameNew)){
                keyName = keyNameNew;
                RedisUtil.addListRight(redis, nameListKey, keyName);
            }
        }
		/*订单加入订单队列中*/
        RedisUtil.addListRight(redis, keyName, taker);
    }

    public OrderTaker createOrderTaker(Integer userId,OrderMaker maker,BigDecimal amount,BigDecimal total){
        OrderTaker taker = new OrderTaker();
        taker.setUserid(userId);
        taker.setType(maker.getType()==GlobalParams.ORDER_TYPE_BUY?GlobalParams.ORDER_TYPE_SALE:GlobalParams.ORDER_TYPE_BUY);
        taker.setCointype(maker.getCointype());
        taker.setMakeruserid(maker.getUserid());
        taker.setMakerid(maker.getId());
        taker.setPrice(maker.getPrice());
        taker.setAmount(amount);
        taker.setTotal(total);
        taker.setOrdernum("TK"+userId+System.currentTimeMillis());

        taker.setRemark("");
        taker.setState(GlobalParams.ORDER_STATE_UNTREATED);

        String flagNum = StrUtils.getCode(6);
        while(orderTakerService.queryByFlagNum(flagNum)!=null){
            flagNum = StrUtils.getCode(6);
        }
        taker.setFlagnum(flagNum);
        int interval = 30;
        Sysparams param1 = sysparamsService.getValByKey(SystemParams.ORDER_C2C_NOTPAY_INACTIVE_INTERVAL);
        if(param1!=null){
            interval = Integer.parseInt(param1.getKeyval());
        }
        Calendar current = Calendar.getInstance();
        current.add(Calendar.MINUTE, interval);

        taker.setInactivetime(new Timestamp(current.getTimeInMillis()));

        return taker;
    }

    /**
     * 判断支付方式是否有效
     * @param payType
     * @param bindlist
     * @return int 1：有效  -1：未绑定支付宝  -2：未绑定微信  -3：未绑定银行卡
     * @date 2018-2-23
     * @author lina
     */
    public boolean payTypeEnable(Integer payType,List<BindInfo> bindlist){
        boolean alipay = false;
        boolean wechant = false;
        boolean bank = false;

        for(BindInfo info :bindlist){
            if(info.getType() == GlobalParams.PAY_ALIPAY){
                alipay = true;
            }else
            if(info.getType() == GlobalParams.PAY_WECHANT){
                wechant = true;
            }else if(info.getType() == GlobalParams.PAY_BANK){
                bank = true;
            }
        }

		/*如果商家支持支付宝，并且用户绑定支付宝，返回true*/
        if((payType&4)==4 && alipay ){
            return true;
        }

		/*如果商家支持微信，并且用户绑定微信，返回true*/
        if((payType&2)==2 && wechant){
            return true;
        }

		/*如果商家支持银行卡，并且用户绑定银行卡，返回true*/
        return (payType & 1) == 1 && bank;

    }

    /**
     * 当前交易类型是否存在未成交订单
     * @param user
     * @param coinType
     * @param orderType
     * @return
     */
    private Boolean checkOrderNum(User user, Integer coinType, Integer orderType) {
        Map<Object,Object> map = new HashMap<>();
        map.put("type",orderType);
        map.put("cointype",coinType);
        map.put("userid",user.getId());
        map.put("state",0);

        int count = orderTakerService.selectCount(map);
        return count!=0;

    }
    /**
     * 判断输入的数字是否正确
     * @param price
     * @param amount
     * @return boolean
     * @date 2018-2-23
     * @author lina
     */
    public boolean digitalEnable(BigDecimal price,BigDecimal amount){
        return !(price.compareTo(BigDecimal.ZERO) < 1 || amount.compareTo(BigDecimal.ZERO) < 1);

    }

    @Override
    public String queryOrderList(User user, Integer coinType,Integer orderType, Integer state, Integer userType, Integer page, Integer rows) {
        Map<String, Object> data = new HashMap<String, Object>();
        Integer pageInt = page==null?0:page;
        Integer rowsInt = rows==null?10:rows;

        List<?> list = orderTakerService.queryAppList(user.getId(), orderType, state, userType,coinType,pageInt,rowsInt);
        for(Object obj:list){
            Map<String, Object> map = (Map<String, Object>)obj;
            if(userType == GlobalParams.C2C_USER_MAKER){
                Integer type = Integer.parseInt(map.get("orderType").toString());
                map.put("orderType", Tools.reverseZeroOne(type));
            }

            BigDecimal amount = new BigDecimal(map.get("amount").toString());
            map.put("amount", BigDecimalUtils.toString(amount,coinType));

            BigDecimal price = new BigDecimal(map.get("price").toString());
            map.put("price", BigDecimalUtils.toString(price));

            BigDecimal total = new BigDecimal(map.get("total").toString());
            map.put("total", BigDecimalUtils.toString(total));  

            Timestamp time = (Timestamp) map.get("createTime");
            map.put("createTime", TimeStampUtils.toTimeString(time, "YYYY-MM-dd HH:mm:ss"));

            Timestamp inactiveTime = (Timestamp) map.get("inactiveTime");
            map.put("inactiveTime", TimeStampUtils.toTimeString(inactiveTime, "HH:mm:ss"));
        }
        data.put("list", list);
        return Result.toResult(ResultCode.SUCCESS, data);
    }

    @Override
    public String queryOrderInfo(User user, Integer orderId) {
        OrderTaker taker = orderTakerService.selectByPrimaryKey(orderId);
        Map<String, Object> map = new HashMap<>();

        map.put("coinType", taker.getCointype());
        map.put("state", taker.getState());
        map.put("price", BigDecimalUtils.toString(taker.getPrice()));
        map.put("amount", BigDecimalUtils.toString(taker.getAmount()));
        map.put("total", BigDecimalUtils.toString(taker.getTotal()));
        map.put("createTime", TimeStampUtils.toTimeString(taker.getCreatetime(), "YYYY-MM-dd HH:mm:ss"));
        map.put("updateTime", TimeStampUtils.toTimeString(taker.getUpdatetime(), "YYYY-MM-dd HH:mm:ss"));
        map.put("orderNum", taker.getOrdernum());
        map.put("referNum",taker.getFlagnum());
        long current = System.currentTimeMillis();
        if(taker.getInactivetime() == null||taker.getInactivetime().before(new Timestamp(current))){
            map.put("inactiveTimeInterval",0);
        }else{
            map.put("inactiveTimeInterval", TimeUnit.MILLISECONDS.toSeconds(taker.getInactivetime().getTime()-current));
        }
        map.put("inactiveTime",TimeStampUtils.toTimeString(taker.getInactivetime(), "YYYY-MM-dd HH:mm:ss"));

        Integer otherUserId = user.getId().intValue() == taker.getMakeruserid().intValue()?taker.getUserid():taker.getMakeruserid();
        User otherUser = userService.selectByPrimaryKey(otherUserId);
        map.put("orderType", user.getId().intValue() == taker.getMakeruserid().intValue()?Tools.reverseZeroOne(taker.getType()):taker.getType());
        map.put("otherPhone", otherUser == null?"":otherUser.getPhone());
        map.put("otherName", otherUser == null?"":otherUser.getUsername());
        map.put("otherHeadPath",otherUser.getHeadpath());
        map.put("otherNickName",otherUser.getNickname());

        Map<Integer, Object> payInfo = new HashMap<Integer, Object>();

        if(taker.getState()==0){		
			/*如果订单状态是代付款，查询卖家支持的支付方式*/
            int payType = orderMakerService.selectByPrimaryKey(taker.getMakerid()).getPaytype();// 支付方式为商家支持的支付方式
            List<BindInfo> info = null;
            if(taker.getType() == GlobalParams.ORDER_TYPE_SALE){
                info = bindInfoService.queryByUser(taker.getUserid());
            }else{
                info = bindInfoService.queryByUser(taker.getMakeruserid());
            }

            for(BindInfo bind:info){
                if(orderPayTypeEnable(payType,bind.getType())){
                    BindInfoModel infoM  = new BindInfoModel();
                    copyBinInfo(bind, infoM);
                    payInfo.put(bind.getType(), infoM);
                }
            }
        }else if(taker.getState()>0&&taker.getState()<4){
			/*如果订单状态是1待确认 2冻结 3已完成 ，查询买家选择的支付方式*/
            if(taker.getPayid()!=null){
                BindInfo info = bindInfoService.selectByPrimaryKey(taker.getPayid());
                if(info!=null){
                    BindInfoModel infoM  = new BindInfoModel();
                    copyBinInfo(info, infoM);
                    payInfo.put(infoM.getType(), infoM);
                }
            }
        }


        map.put("payInfo", payInfo);
        return Result.toResult(ResultCode.SUCCESS, map);
    }


    public void copyBinInfo(BindInfo source,BindInfoModel target){
        target.setId(source.getId());
        target.setAccount(source.getAccount());
        target.setBankName(source.getBankname());
        target.setBranchName(source.getBranchname());
        target.setImgurl(source.getImgurl());
        target.setName(source.getName());
        target.setType(source.getType());
    }
    /**
     * 商户订单是否支持当前支付方式
     * @param orderPayType 订单选择的支付方式
     * @param type  用户支付方式
     * @return boolean
     * @date 2018-3-19
     * @author lina
     */
    public boolean orderPayTypeEnable(int orderPayType,Integer type ){
        switch(type){
            case 0:return (orderPayType&4)==4;
            case 1:return (orderPayType&2)==2;
            case 2:return (orderPayType&1)==1;
            default : return false;
        }
    }
    @Override
    @Transactional
    public String cancelTakerOrder(User user, Integer orderId){

        OrderTaker taker = orderTakerService.selectByPrimaryKey(orderId);
		/*判断交易状态是不是待付款或冻结状态*/
        if(taker==null||(taker.getState() !=GlobalParams.C2C_ORDER_STATE_PENDINGPAY&&taker.getState() !=GlobalParams.C2C_ORDER_STATE_FROZEN)){
            return Result.toResult(ResultCode.ORDER_STATE_INACTIVE);
        }
		/*限制买家才能操作*/
        if((taker.getType() == GlobalParams.ORDER_TYPE_BUY&&user.getId().intValue() != taker.getUserid().intValue())
                ||(taker.getType() == GlobalParams.ORDER_TYPE_SALE&&user.getId().intValue() != taker.getMakeruserid().intValue())){
            return Result.toResult(ResultCode.OPERATOR_NOT_LIMIT);
        }
		//取消订单
		orderTakerService.cancelOrder(taker,user.getId(),GlobalParams.C2C_ORDER_STATE_CANCEL);

        return Result.toResult(ResultCode.SUCCESS);
    }


    @Override
    @Transactional
    public String confirmPayment(User user, Integer orderId, Integer payType , String password) {
        OrderTaker taker = orderTakerService.selectByPrimaryKey(orderId);
		/*判断交易状态是不是待付款*/
        if(taker ==null||(taker.getState() !=GlobalParams.C2C_ORDER_STATE_PENDINGPAY)){
            return Result.toResult(ResultCode.ORDER_STATE_INACTIVE);
        }
		/*限制买家才能操作*/
        if((taker.getType() == GlobalParams.ORDER_TYPE_BUY&&user.getId().intValue() != taker.getUserid().intValue())
                ||(taker.getType() == GlobalParams.ORDER_TYPE_SALE&&user.getId().intValue() != taker.getMakeruserid().intValue())){
            return Result.toResult(ResultCode.OPERATOR_NOT_LIMIT);
        }
		/*校验交易密码*/
        if(!StrUtils.isBlank(password)){
            String valiStr = validateOrderPassword(user, password);
            if(valiStr!=null){
                return valiStr;
            }
        }
		
		/*确认付款*/
        taker.setState(GlobalParams.C2C_ORDER_STATE_PENDINGRECEIPT);

		/*设置失效时间*/
        int interval = 720;
        Sysparams param1 = sysparamsService.getValByKey(SystemParams.ORDER_C2C_NOTCONFIRM_INACTIVE_INTERVAL);
        if(param1!=null){
            interval = Integer.parseInt(param1.getKeyval());
        }
        Calendar current = Calendar.getInstance();
        current.add(Calendar.MINUTE, interval);
        taker.setInactivetime(new Timestamp(current.getTimeInMillis()));

		/*设置支付方式*/
        Integer saleUserId = taker.getType() == GlobalParams.ORDER_TYPE_BUY?taker.getMakeruserid():taker.getUserid();
        BindInfo pay = bindInfoService.queryByUserAndType(saleUserId, payType);
        if(pay == null){
            return Result.toResult(ResultCode.PARAM_IS_INVALID);
        }
        taker.setPayid(pay.getId());
        orderTakerService.updateByPrimaryKey(taker);

		/*订单加入待确认队列*/
        addOverTimeQueue(taker,RedisKey.C2C_ORDERS_NOTCONFIRM_KEY_NAME,RedisKey.C2C_ORDERS_NOTCONFIRM,interval);

		/*短信通知卖家*/
        User saleUser = userService.selectByPrimaryKey(saleUserId);
        CoinManage coinManage = coinManageService.queryByCoinType(taker.getCointype());
        if(saleUser!=null){
            Map<String, String> params = new HashMap();
            params.put("orderNum", taker.getOrdernum());
            params.put("price", BigDecimalUtils.toString(taker.getPrice(),taker.getCointype()));
            params.put("coinType", coinManage ==null?"":coinManage.getCoinname());
            params.put("amount",  BigDecimalUtils.toString(taker.getAmount()));
            params.put("total", BigDecimalUtils.toString(taker.getTotal()));
            params.put("payType",getPayName(payType) );
            smsCodeUtil.sendSms(saleUser.getPhone(), SmsTemplateCode.SMS_C2C_PAY_NOTIFY, params);
        }
        return Result.toResult(ResultCode.SUCCESS);
    }

    /**
     * 根据id转换支付方式的名称
     * @param payType
     * @return String
     * @date 2018-3-8
     * @author lina
     */
    public String getPayName(Integer payType){
        if(payType==null){
            return "";
        }
        switch(payType){
            case 0: return "支付宝";
            case 1: return "微信";
            case 2: return "银行卡";
            default : return "";
        }

    }

    @Override
    @Transactional
    public String confirmReceipt(User user, Integer orderId, String password) {
        OrderTaker taker = orderTakerService.selectByPrimaryKey(orderId);
		/*判断交易状态是不是待付款*/
        if(taker == null||(taker.getState() !=GlobalParams.C2C_ORDER_STATE_PENDINGRECEIPT&&taker.getState() !=GlobalParams.C2C_ORDER_STATE_FROZEN)){
            return Result.toResult(ResultCode.ORDER_STATE_INACTIVE);
        }
		/*限制卖家才能操作*/
        if((taker.getType() == GlobalParams.ORDER_TYPE_BUY&&user.getId().intValue() != taker.getMakeruserid().intValue())
                ||(taker.getType() == GlobalParams.ORDER_TYPE_SALE&&user.getId().intValue() != taker.getUserid().intValue())){
            return Result.toResult(ResultCode.OPERATOR_NOT_LIMIT);
        }
		/*校验交易密码*/
        if(!StrUtils.isBlank(password)){
            String valiStr = validateOrderPassword(user, password);
            if(valiStr!=null){
                return valiStr;
            }
        }
		orderTakerService.confirmOrder(taker,user.getId());

        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    @Transactional
    public String orderAppeal(User user, Integer orderId,String remark) {
        OrderTaker taker = orderTakerService.selectByPrimaryKey(orderId);
		/*判断交易状态是不是代收款*/
        if(taker.getState() !=GlobalParams.C2C_ORDER_STATE_PENDINGRECEIPT){
            return Result.toResult(ResultCode.ORDER_STATE_INACTIVE);
        }
		/*限制卖家才能操作*/
        if((taker.getType() == GlobalParams.ORDER_TYPE_BUY&&user.getId().intValue() != taker.getMakeruserid().intValue())
                ||(taker.getType() == GlobalParams.ORDER_TYPE_SALE&&user.getId().intValue() != taker.getUserid().intValue())){
            return Result.toResult(ResultCode.OPERATOR_NOT_LIMIT);
        }
		/*冻结*/
        taker.setState(GlobalParams.C2C_ORDER_STATE_FROZEN);
        taker.setRemark(remark);
		
		/*设置失效时间*/
        taker.setInactivetime(null);
        orderTakerService.updateByPrimaryKey(taker);
		
		/*短信通知买家*/
        Integer buyUserId = taker.getType() == GlobalParams.ORDER_TYPE_BUY? taker.getUserid():taker.getMakeruserid();
        User buyUser = userService.selectByPrimaryKey(buyUserId);
        CoinManage coinManage = coinManageService.queryByCoinType(taker.getCointype());
        if(buyUser!=null){
            Map<String, String> params = new HashMap();
            params.put("createTime", TimeStampUtils.toTimeString(taker.getCreatetime()));
            params.put("orderNum", taker.getOrdernum());
            params.put("price", BigDecimalUtils.toString(taker.getPrice()));
            params.put("coinType", coinManage ==null?"":coinManage.getCoinname());
            params.put("amount",  BigDecimalUtils.toString(taker.getAmount()));
            params.put("total", BigDecimalUtils.toString(taker.getTotal()));
            smsCodeUtil.sendSms(buyUser.getPhone(), SmsTemplateCode.SMS_C2C_APPEAL_NOTIFY, params);
        }

        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String queryUserInfo(User user, String userPhone) {
        Map<String, Object> map = new HashMap();
        User otherUser = userService.selectByPhone(userPhone);

        int spotNum =orderSpotRecordService.queryUserCount(otherUser.getId());
        int c2cNum = orderTakerService.queryUserCount(otherUser.getId());
        map.put("totalNum",spotNum+c2cNum);
        map.put("spotNum",spotNum);
        map.put("c2cNum",c2cNum);
        map.put("headPath",otherUser.getHeadpath());
        map.put("nickName",otherUser.getNickname());
        map.put("phone",otherUser.getPhone());
        map.put("registerTime",TimeStampUtils.toDateString(otherUser.getCreatetime()));
        map.put("phoneAuthFlag",true);
        map.put("realNameAuthFlag",true);

        List<OrderMakerDto> buyListAc = new ArrayList<>();
        List<OrderMaker> buyList = orderMakerService.queryUserOrderList(otherUser.getId(),GlobalParams.ORDER_TYPE_SALE);
        convertMakerDto(buyList,buyListAc);
        map.put("buyList",buyListAc);


        List<OrderMakerDto> saleListAc = new ArrayList<>();
        List<OrderMaker> saleList = orderMakerService.queryUserOrderList(otherUser.getId(),GlobalParams.ORDER_TYPE_BUY);
        convertMakerDto(saleList,saleListAc);
        map.put("saleList",saleListAc);


        return Result.toResult(ResultCode.SUCCESS,map);
    }


    public void convertMakerDto(List<OrderMaker> buyList,List<OrderMakerDto> buyListAc){
        if(buyList!=null&&!buyList.isEmpty()){
            for(OrderMaker order :buyList){
                OrderMakerDto dto = new OrderMakerDto();
                dto.setId(order.getId());
                dto.setCoinType(order.getCointype());
                dto.setPayType(order.getPaytype());
                dto.setPrice(BigDecimalUtils.toString(order.getPrice()));
                dto.setAmount(BigDecimalUtils.toString(order.getRemain()));
                dto.setTotalMax(BigDecimalUtils.toString(order.getTotalmax()));
                dto.setTotalMin(BigDecimalUtils.toString(order.getTotalmin()));

                buyListAc.add(dto);
            }
        }
    }
}
