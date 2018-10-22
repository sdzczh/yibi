package com.yibi.orderapi.biz.impl;

import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.exception.BanlanceNotEnoughException;
import com.yibi.core.service.*;
import com.yibi.orderapi.biz.OrderMakerBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/14 0014.
 */
@Service
@Transactional
public class OrderMakerBizImpl extends BaseBizImpl implements OrderMakerBiz {
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private BindInfoService bindInfoService;
    @Autowired
    private OrderMakerService orderMakerService;
    @Autowired
    private CoinScaleService coinScaleService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private BaseService baseService;
    @Override
    public String makerReleaseDeal(User user, Integer coinType, BigDecimal price, BigDecimal amount, Integer orderType, BigDecimal totalMin, BigDecimal totalMax, Integer payType, String password) {

        CoinScale coinScale = coinScaleService.queryByCoin(coinType,CoinType.NONE);
        price = BigDecimalUtils.roundDown(price,coinScale==null?4:coinScale.getC2cpricescale());
        amount = BigDecimalUtils.roundDown(amount,coinScale==null?4:coinScale.getC2cnumscale());
        totalMin = BigDecimalUtils.roundDown(totalMin,coinScale==null?4:coinScale.getC2ctotalamtscale());
        totalMax = BigDecimalUtils.roundDown(totalMax,coinScale==null?4:coinScale.getC2ctotalamtscale());
        CoinManage coinManage = coinManageService.queryByCoinType(coinType);
        /*功能开关校验*/
        if(coinManage!=null && coinManage.getC2conoff() == GlobalParams.INACTIVE){
            return  Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }

		/*输入数字校验*/
        if(!digitalEnable(price, amount, totalMin, totalMax)){
            return Result.toResult(ResultCode.AMOUNT_ERROR);
        }

        Sysparams minAmt = sysparamsService.getValByKey(SystemParams.ORDER_C2C_MAKER_MINTOTAL);
        BigDecimal total = BigDecimalUtils.multiply(amount, price,2);
        if(minAmt != null && total.compareTo(new BigDecimal(minAmt.getKeyval())) == -1){
            return Result.toResultFormat(ResultCode.ORDER_C2C_TOTAL_MIN_LIMIT, minAmt.getKeyval());
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

        /*判断商家用户当前卖出委托订单数量*/
        Boolean checkOrderNum = checkOrderNum(user, coinType,orderType);
        if(checkOrderNum){
            return Result.toResult(ResultCode.ORDER_C2C_MARKER_SALE_LIMIT);
        }
		/*支付方式验证*/
        if(orderType == GlobalParams.ORDER_TYPE_SALE){
            int payVal = payTypeEnable(payType, user.getId());
            if(payVal == -1){
                return Result.toResult(ResultCode.PAY_ALIPAY_NOT_BIND);
            }
            if(payVal == -2){
                return Result.toResult(ResultCode.PAY_WECHANT_NOT_BIND);
            }

            if(payVal == -3){
                return Result.toResult(ResultCode.PAY_BANK_NOT_BIND);
            }


        }else{
			/*判断商户的当日取消次数*/
            if(!countOfCancelOrderEnable(user.getId(),GlobalParams.C2C_USER_MAKER,null)){
                return Result.toResult(ResultCode.ORDER_C2C_CANCEL_LIMIT);
            }

        }
        BigDecimal deposit = coinManage==null?BigDecimal.ZERO:coinManage.getC2corderdeposit();

		/*保存委托单记录*/
        OrderMaker maker = new OrderMaker();
        maker.setUserid(user.getId());
        maker.setCointype(coinType);
        maker.setType(orderType);
        maker.setPrice(price);
        maker.setAmount(amount);
        maker.setTotalmin(totalMin);
        maker.setTotalmax(totalMax);
        maker.setRemain(amount);
        maker.setPaytype(payType);
        maker.setOrderflag(false);
        maker.setState(GlobalParams.ORDER_STATE_UNTREATED);
        maker.setFrozen(BigDecimal.ZERO);
        maker.setDeposit(deposit);
        maker.setOrdernum("MK"+user.getId()+System.currentTimeMillis());
        orderMakerService.insert(maker);

        //如果卖出，则账户更新提交数量加保证金，如果是买入，则账户更新保证金
        BigDecimal updateAmount = orderType == GlobalParams.ORDER_TYPE_SALE? BigDecimalUtils.add(amount, deposit) : deposit;

        //更新后账户余额
        accountService.updateAccountAndInsertFlow(user.getId(),GlobalParams.ACCOUNT_TYPE_C2C,coinType,BigDecimalUtils.plusMinus(updateAmount),updateAmount,user.getId(),"法币交易委托",maker.getId());

        return Result.toResult(ResultCode.SUCCESS);
    }


    private Boolean checkOrderNum(User user, Integer coinType, Integer orderType) {
        Map<Object,Object> map = new HashMap<>();
        map.put("type",orderType);
        map.put("cointype",coinType);
        map.put("userid",user.getId());
        map.put("state",0);

        int count = orderMakerService.selectCount(map);
        return count!=0;
    }
    /**
     * 判断输入的数字是否正确
     * @param price
     * @param amount
     * @param totalMin
     * @param totalMax
     * @return boolean
     * @date 2018-2-23
     * @author lina
     */
    public boolean digitalEnable(BigDecimal price,BigDecimal amount,BigDecimal totalMin,BigDecimal totalMax){

        if(price.compareTo(BigDecimal.ZERO)<1||amount.compareTo(BigDecimal.ZERO)<1||totalMin.compareTo(BigDecimal.ZERO)<1||totalMax.compareTo(BigDecimal.ZERO)<1){
            return false;
        }
        BigDecimal total = BigDecimalUtils.multiply(price,amount);
        if(total.compareTo(totalMin)==-1){
            return false;
        }
        return totalMin.compareTo(totalMax) <= -1;

    }

    /**
     * 判断支付方式是否有效
     * @param payType
     * @param userId
     * @return int 1：有效  -1：未绑定支付宝  -2：未绑定微信  -3：未绑定银行卡
     * @date 2018-2-23
     * @author lina
     */
    public int payTypeEnable(Integer payType,Integer userId){
        boolean alipay = false;
        boolean wechant = false;
        boolean bank = false;

        List<BindInfo> bindlist = bindInfoService.queryByUser(userId);
        for(BindInfo info :bindlist){
            if(info.getType() == GlobalParams.PAY_ALIPAY){
                alipay = true;
            }else if(info.getType() == GlobalParams.PAY_WECHANT){
                wechant = true;
            }else if(info.getType() == GlobalParams.PAY_BANK){
                bank = true;
            }
        }

		/*选择支付宝方式，但未绑定支付宝*/
        if((payType&4)==4 && !alipay ){
            return -1;
        }

		/*选择微信方式，但未绑定微信*/
        if((payType&2)==2 && !wechant){
            return -2;
        }

		/*选择银行卡方式，但未绑定银行卡*/
        if((payType&1)==1 && !bank){
            return -3;
        }

        return 1;
    }

    @Override
    public String queryOrderList(User user,Integer orderType, Integer coinType, Integer state, Integer page, Integer rows) {
        Map<String, Object> data = new HashMap();
        Integer pageInt = page==null?0:page;
        Integer rowsInt = rows==null?10:rows;
        List<?> list = orderMakerService.queryAppList(user.getId(),orderType, coinType, state, pageInt, rowsInt);
        for(Object obj:list){
            Map<String, Object> map = (Map<String, Object>)obj;
            BigDecimal amount = new BigDecimal(map.get("amount").toString());
            map.put("amount", BigDecimalUtils.toString(amount));

            BigDecimal remain = new BigDecimal(map.get("remain").toString());
            map.put("remain", BigDecimalUtils.toString(remain));

            BigDecimal price = new BigDecimal(map.get("price").toString());
            map.put("price", BigDecimalUtils.toString(price));

            map.put("total", BigDecimalUtils.toString(BigDecimalUtils.multiply(price,amount,2)));

            BigDecimal totalMin = new BigDecimal(map.get("totalMin").toString());
            map.put("totalMin", BigDecimalUtils.toString(totalMin));

            BigDecimal totalMax = new BigDecimal(map.get("totalMax").toString());
            map.put("totalMax", BigDecimalUtils.toString(totalMax));

            Integer orderFlag = Integer.parseInt(map.get("orderFlag").toString());
            map.put("orderFlag", orderFlag==GlobalParams.ACTIVE?true:false);
        }
        data.put("list", list);
        return Result.toResult(ResultCode.SUCCESS, data);
    }

    @Override
    public String queryavailBalanceAndPrice(User user, Integer coinType) {
        CoinManage coinManage = coinManageService.queryByCoinType(coinType);
        String val= sysparamsService.getValStringByKey(SystemParams.ORDER_C2C_MAKER_MINTOTAL);
        Map<String , Object> map = new HashMap<>();
        map.put("latestPrice", BigDecimalUtils.toString(baseService.getC2CLatestPrice(coinType)));
        map.put("availBalance", BigDecimalUtils.toString(accountService.queryAvailBalance(user.getId(),coinType,GlobalParams.ACCOUNT_TYPE_C2C)));
        map.put("deposit",coinManage==null?"0":BigDecimalUtils.toString(coinManage.getC2corderdeposit()));
        map.put("minDealAmt", StringUtils.isBlank(val)?"0":val);
        map.put("platFee","0");
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String cancelOrder(User user, Integer orderId, String password) {
        OrderMaker maker = orderMakerService.selectByPrimaryKey(orderId);
		/*委托单状态判断*/
        if(maker == null ||maker.getState()!=GlobalParams.ORDER_STATE_UNTREATED){
            return Result.toResult(ResultCode.ORDER_STATE_INACTIVE);
        }
        if(maker.getUserid().intValue() != user.getId().intValue()){
            return Result.toResult(ResultCode.OPERATOR_NOT_LIMIT);
        }
		/*当冻结数量大于0时，委托单存在未处理的订单*/
        if(maker.getFrozen().compareTo(BigDecimal.ZERO)==1){
            return Result.toResult(ResultCode.ORDER_UNFINISHED_EXIST);
        }
		
		/*校验交易密码*/
        if(!StrUtils.isBlank(password)){
            String valiStr = validateOrderPassword(user, password);
            if(valiStr!=null){
                return valiStr;
            }
        }
		
		/*更新订单状态*/
        maker.setState(GlobalParams.ORDER_STATE_BACK);
        maker.setOrderflag(false);
        orderMakerService.updateByPrimaryKey(maker);

        //如果卖出，则账户返回剩余数量加保证金，如果是买入，则账户返回 保证金
        BigDecimal updateAmount = maker.getType() == GlobalParams.ORDER_TYPE_SALE?BigDecimalUtils.add(maker.getRemain(), maker.getDeposit()) :  maker.getDeposit();

        try {
            accountService.updateAccountAndInsertFlow(user.getId(),GlobalParams.ACCOUNT_TYPE_C2C,maker.getCointype(),updateAmount,BigDecimalUtils.plusMinus( updateAmount),user.getId(),"法币交易委托单撤销",maker.getId());
        } catch (BanlanceNotEnoughException e) {
            e.printStackTrace();
            throw new RuntimeException("法币交易委托撤销失败");
        }

        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String receiptOrder(User user, Integer orderId) {
        OrderMaker maker = orderMakerService.selectByPrimaryKey(orderId);
        Map<String, Object> map = new HashMap();
		/*委托单状态判断*/
        if(maker == null ||maker.getState()!=GlobalParams.ORDER_STATE_UNTREATED){
            return Result.toResult(ResultCode.ORDER_STATE_INACTIVE);
        }
        if(maker.getUserid().intValue() != user.getId().intValue()){
            return Result.toResult(ResultCode.OPERATOR_NOT_LIMIT);
        }

		/*更新订单状态*/
        maker.setOrderflag(!maker.getOrderflag());
        orderMakerService.updateByPrimaryKey(maker);
        map.put("orderFlag", maker.getOrderflag());
        return Result.toResult(ResultCode.SUCCESS,map);
    }

    @Override
    public String queryOrderList(Integer coinType, Integer orderType, Integer page, Integer rows) {
        Map<String, Object> data = new HashMap();
        Integer pageInt = page==null?0:page;
        Integer rowsInt = rows==null?10:rows;

        List<?> list = orderMakerService.queryByType( coinType, orderType, pageInt, rowsInt);
        for(Object obj:list){
            Map<String, Object> map = (Map<String, Object>)obj;

            BigDecimal remain = new BigDecimal(map.get("remain").toString());
            map.put("remain", BigDecimalUtils.toString(remain));

            BigDecimal price = new BigDecimal(map.get("price").toString());
            map.put("price", BigDecimalUtils.toString(price));

            BigDecimal totalMin = new BigDecimal(map.get("totalMin").toString());
            map.put("totalMin", BigDecimalUtils.toString(totalMin));

            BigDecimal totalMax = new BigDecimal(map.get("totalMax").toString());
            map.put("totalMax", BigDecimalUtils.toString(totalMax));

        }
        data.put("list", list);
        return Result.toResult(ResultCode.SUCCESS, data);
    }
}
