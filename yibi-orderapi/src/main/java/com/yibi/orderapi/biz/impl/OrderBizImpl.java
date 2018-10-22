package com.yibi.orderapi.biz.impl;

import com.google.common.eventbus.EventBus;
import com.yibi.common.model.PageModel;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.entity.*;
import com.yibi.core.exception.BanlanceNotEnoughException;
import com.yibi.core.service.*;
import com.yibi.orderapi.biz.OrderBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import com.yibi.orderapi.event.AddCalcForceListenerBean;
import com.yibi.orderapi.event.AfterOrderListenerBean;
import com.yibi.orderapi.event.CancelOrderListenerBean;
import com.yibi.orderapi.event.DealDigListenerBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
@Slf4j
public class OrderBizImpl extends BaseBizImpl implements OrderBiz {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CoinScaleService coinScaleService;
    @Autowired
    private OrderManageService orderManageService;
    @Autowired
    private OrderSpotService orderSpotService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderSpotRecordService orderSpotRecordService;
    @Autowired
    private CommissionRecordService commissionRecordService;
    @Autowired
    private DealDigRecordService dealDigRecordService;
    @Resource
    private EventBus orderEventBus;
    @Autowired
    private RedisTemplate<String, String> redis;

    /**
     * 获取交易界面场景信息
     *
     * @param levFlag 杠杆交易标识 1:杠杆交易 0：现货交易
     * @return
     */
    @Override
    public String mainpageInfo(User user, Integer orderCoin, Integer unitCoin, Integer levFlag) {
        Map<String, Object> map = new HashMap<String, Object>();
        String order = "";
        String unit = "";
        String rmbRate = "";
        //获取计价币种的cny汇率
        BigDecimal rate = getPriceOfCNY(unitCoin);
        rmbRate = rate.stripTrailingZeros().toPlainString();
        map.put("rmbRate", rmbRate);
        CoinScale coinScale = coinScaleService.queryByCoin(orderCoin, unitCoin);
        int unitScale = 2;
        int orderScale = 4;
        if (coinScale != null) {
            unitScale = coinScale.getAvailofspotunitscale();
            orderScale = coinScale.getAvailofspotorderscale();
        }
        //如果是现货交易
        if (levFlag == 0) {
            BigDecimal orderAvail = accountService.queryAvailBalance(user.getId(), orderCoin, GlobalParams.ACCOUNT_TYPE_SPOT);
            BigDecimal unitAvail = accountService.queryAvailBalance(user.getId(), unitCoin, GlobalParams.ACCOUNT_TYPE_SPOT);
            order = BigDecimalUtils.toString(orderAvail, orderScale);
            unit = BigDecimalUtils.toString(unitAvail, unitScale);
        }
        //TODO 如果是杠杆交易
        if (levFlag == 1) {

        }
        map.put("order", order);
        map.put("unit", unit);
        map.put("priceScale", coinScale == null ? 0 : coinScale.getOrderamtpricescale());
        map.put("amountScale", coinScale == null ? 0 : coinScale.getOrderamtamountscale());
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String limitPriceBuy(User user, Integer orderCoin, Integer unitCoin, Integer levFlag, String price, String amount, String password) {
        Map<Object, Object> manageParams = new HashMap<Object, Object>();
        manageParams.put("ordercointype", orderCoin);
        manageParams.put("unitcointype", unitCoin);
        OrderManage manage = null;
        List<OrderManage> orderManageList = orderManageService.selectAll(manageParams);
        if (orderManageList != null && !orderManageList.isEmpty()) {
            manage = orderManageList.get(0);
        }
        /*---------------------------------------------订单合法验证-----------------------------------------------------*/
        String result = validateOrder(user, orderCoin, unitCoin, password, manage);
        if (result != null) {
            return result;
        }
        CoinScale coinScale = coinScaleService.queryByCoin(orderCoin, unitCoin);
        int priceScale = 2;//价格小数位数
        int amountScale = 4;//数量小数位数
        BigDecimal minAmount = new BigDecimal(0.0001);//最小交易量
        BigDecimal minTotal = new BigDecimal(0.01);//最小交易额
        if (coinScale != null) {
            priceScale = coinScale.getOrderamtpricescale();
            amountScale = coinScale.getOrderamtamountscale();
            minAmount = coinScale.getMinspottransnum();
                minTotal = coinScale.getMinspottransamt();
        }
        //格式化价格小数位数
        BigDecimal priceBd = BigDecimalUtils.roundDown(new BigDecimal(price), priceScale);
        //格式化数量小数位数
        BigDecimal amountBd = BigDecimalUtils.roundDown(new BigDecimal(amount), amountScale);
        //判断数量是否大于最低交易数量
        if (amountBd.compareTo(minAmount) < 0) {
            return Result.toResultFormat(ResultCode.ORDER_SPOT_AMOUNT_LESS_THAN_MIN, BigDecimalUtils.toString(minAmount));
        }
        //验证成交总额是否小于最小成交额
        BigDecimal totalAmount = priceBd.multiply(amountBd);
        if (totalAmount.compareTo(minTotal) < 0) {
            return Result.toResultFormat(ResultCode.ORDER_SPOT_PRICE_LESS_THAN_MIN, BigDecimalUtils.toString(minTotal));
        }
        //判断账户余额是否足够
        //杠杆
        if (levFlag == 1) {
            //TODO 判断杠杆账户余额是否充足
        }
        //普通现货
        if (levFlag == 0) {
            Map<Object, Object> accountParams = new HashMap<Object, Object>();
            accountParams.put("userid", user.getId());
            accountParams.put("accounttype", GlobalParams.ACCOUNT_TYPE_SPOT);
            accountParams.put("cointype", unitCoin);
            List<Account> listAccount = accountService.selectAll(accountParams);
            if (listAccount == null || listAccount.isEmpty()) {
                return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
            }
            Account account = listAccount.get(0);
            if (account.getAvailbalance().compareTo(priceBd.multiply(amountBd)) < 0) {
                return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
            }
        }
        /*---------------------------------------------创建新订单-----------------------------------------------------*/
        OrderSpot buyOrder = new OrderSpot();
        buyOrder.setUserid(user.getId());
        buyOrder.setAmount(amountBd);
        buyOrder.setAverage(new BigDecimal(0));
        buyOrder.setLevflag(levFlag);
        buyOrder.setOrdercointype(orderCoin);
        buyOrder.setOrdernum("O" + user.getId() + System.currentTimeMillis());
        buyOrder.setOrdertype(GlobalParams.ORDER_ORDERTYPE_LIMIT);
        buyOrder.setPrice(priceBd);
        buyOrder.setRemain(amountBd);
        buyOrder.setState(GlobalParams.ORDER_STATE_UNTREATED);
        buyOrder.setTotal(new BigDecimal(0));
        buyOrder.setType(GlobalParams.ORDER_TYPE_BUY);
        buyOrder.setUnitcointype(unitCoin);
        buyOrder.setDealAmount(new BigDecimal(0));
        orderSpotService.insertSelective(buyOrder);
                /*---------------------------------------------匹配订单-----------------------------------------------------*/
        Map<Object, Object> matchingParams = new HashMap<Object, Object>();
        matchingParams.put("ordercointype", orderCoin);
        matchingParams.put("unitcointype", unitCoin);
        matchingParams.put("type", GlobalParams.ORDER_TYPE_SALE);
        matchingParams.put("price", priceBd);
        List<OrderSpot> matchingList = orderSpotService.selectAllMatching(matchingParams);
        List<OrderSpotRecord> recordList = new ArrayList<OrderSpotRecord>();


        for (OrderSpot matchingOrder : matchingList) {
            //验证匹配订单的挂单用户合法
            User matchingUser = userService.selectByPrimaryKey(matchingOrder.getUserid());
            if (matchingUser.getState() != GlobalParams.ACTIVE) {
                //取消订单
                CancelOrderListenerBean cancelOrderListenerBean = new CancelOrderListenerBean();
                cancelOrderListenerBean.setFlowOperType("交易挂单用户已停用，订单自动撤销");
                cancelOrderListenerBean.setOrderSpot(matchingOrder);
                orderEventBus.post(cancelOrderListenerBean);
                continue;
            }
            boolean complete = orderDealLimit(buyOrder, matchingOrder, GlobalParams.ORDER_TYPE_BUY, coinScale, manage, recordList);
            //判断卖单量和价格是否小于最小值
            validateCoinScale(matchingOrder, coinScale);
            if (complete) {
                buyOrder.setState(GlobalParams.ORDER_STATE_TREATED);
                break;
            }
        }
        /*---------------------------------------------更新订单-----------------------------------------------------*/
        if (buyOrder.getDealAmount().compareTo(BigDecimal.ZERO) > 0) {
            orderSpotService.updateByPrimaryKeySelective(buyOrder);
            //插入钱包和流水
            if (buyOrder.getLevflag() == 1) {
                //TODO 杠杆
            }
            if (buyOrder.getLevflag() == 0) {
                //普通交易
                accountService.updateAccountAndInsertFlow(buyOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, buyOrder.getOrdercointype(), buyOrder.getDealAmount(), new BigDecimal(0), buyOrder.getUserid(), "币币交易买入", buyOrder.getId());
            }
            validateCoinScale(buyOrder, coinScale);
            //增加魂力
            addOrderCalcul(buyOrder.getUserid());
        }
        BigDecimal minusTotal = BigDecimalUtils.multiply(buyOrder.getRemain(),buyOrder.getPrice(),coinScale.getOrderamtpricescale()).add(buyOrder.getTotal());
        accountService.updateAccountAndInsertFlow(user.getId(), GlobalParams.ACCOUNT_TYPE_SPOT, unitCoin, BigDecimalUtils.plusMinus(minusTotal), BigDecimal.ZERO, user.getId(), "币币交易买入", buyOrder.getId());

        //行情和交易深度更新
        doAfterOrder(orderCoin, unitCoin, recordList);
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String limitPriceSale(User user, Integer orderCoin, Integer unitCoin, Integer levFlag, String price, String amount, String password) {
        Map<Object, Object> manageParams = new HashMap<Object, Object>();
        manageParams.put("ordercointype", orderCoin);
        manageParams.put("unitcointype", unitCoin);
        OrderManage manage = null;
        List<OrderManage> orderManageList = orderManageService.selectAll(manageParams);
        if (orderManageList != null && !orderManageList.isEmpty()) {
            manage = orderManageList.get(0);
        }
        /*---------------------------------------------订单合法验证-----------------------------------------------------*/
        String result = validateOrder(user, orderCoin, unitCoin, password, manage);
        if (result != null) {
            return result;
        }
        CoinScale coinScale = coinScaleService.queryByCoin(orderCoin, unitCoin);
        int priceScale = 2;//价格小数位数
        int amountScale = 4;//数量小数位数
        BigDecimal minAmount = new BigDecimal(0.0001);//最小交易量
        BigDecimal minTotal = new BigDecimal(0.01);//最小交易额
        if (coinScale != null) {
            priceScale = coinScale.getOrderamtpricescale();
            amountScale = coinScale.getOrderamtamountscale();
            minAmount = coinScale.getMinspottransnum();
            minTotal = coinScale.getMinspottransamt();
        }
        //格式化价格小数位数
        BigDecimal priceBd = BigDecimalUtils.roundDown(new BigDecimal(price), priceScale);
        //格式化数量小数位数
        BigDecimal amountBd = BigDecimalUtils.roundDown(new BigDecimal(amount), amountScale);
        //判断数量是否大于最低交易数量
        if (amountBd.compareTo(minAmount) < 0) {
            return Result.toResultFormat(ResultCode.ORDER_SPOT_AMOUNT_LESS_THAN_MIN, BigDecimalUtils.toString(minAmount));
        }
        //验证成交总额是否小于最小成交额
        BigDecimal totalAmount = priceBd.multiply(amountBd);
        if (totalAmount.compareTo(minTotal) < 0) {
            return Result.toResultFormat(ResultCode.ORDER_SPOT_PRICE_LESS_THAN_MIN, BigDecimalUtils.toString(minTotal));
        }
        //判断账户余额是否足够
        //杠杆
        if (levFlag == 1) {
            //TODO 判断杠杆账户余额是否充足
        }
        //普通现货
        if (levFlag == 0) {
            Map<Object, Object> accountParams = new HashMap<Object, Object>();
            accountParams.put("userid", user.getId());
            accountParams.put("accounttype", GlobalParams.ACCOUNT_TYPE_SPOT);
            accountParams.put("cointype", orderCoin);
            List<Account> listAccount = accountService.selectAll(accountParams);
            if (listAccount == null || listAccount.isEmpty()) {
                return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
            }
            Account account = listAccount.get(0);
            if (account.getAvailbalance().compareTo(amountBd) < 0) {
                return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
            }
        }
        /*---------------------------------------------创建新订单-----------------------------------------------------*/
        OrderSpot saleOrder = new OrderSpot();
        saleOrder.setUserid(user.getId());
        saleOrder.setAmount(amountBd);
        saleOrder.setAverage(new BigDecimal(0));
        saleOrder.setLevflag(levFlag);
        saleOrder.setOrdercointype(orderCoin);
        saleOrder.setOrdernum("O" + user.getId() + System.currentTimeMillis());
        saleOrder.setOrdertype(GlobalParams.ORDER_ORDERTYPE_LIMIT);
        saleOrder.setPrice(priceBd);
        saleOrder.setRemain(amountBd);
        saleOrder.setState(GlobalParams.ORDER_STATE_UNTREATED);
        saleOrder.setTotal(new BigDecimal(0));
        saleOrder.setType(GlobalParams.ORDER_TYPE_SALE);
        saleOrder.setUnitcointype(unitCoin);
        saleOrder.setDealAmount(new BigDecimal(0));
        orderSpotService.insertSelective(saleOrder);
        accountService.updateAccountAndInsertFlow(user.getId(), GlobalParams.ACCOUNT_TYPE_SPOT, orderCoin, BigDecimalUtils.plusMinus(amountBd), BigDecimal.ZERO, user.getId(), "币币交易卖出", saleOrder.getId());
        /*---------------------------------------------匹配订单-----------------------------------------------------*/
        Map<Object, Object> matchingParams = new HashMap<Object, Object>();
        matchingParams.put("ordercointype", orderCoin);
        matchingParams.put("unitcointype", unitCoin);
        matchingParams.put("type", GlobalParams.ORDER_TYPE_BUY);
        matchingParams.put("price", priceBd);
        List<OrderSpot> matchingList = orderSpotService.selectAllMatching(matchingParams);
        List<OrderSpotRecord> recordList = new ArrayList<OrderSpotRecord>();
        for (OrderSpot matchingOrder : matchingList) {
            //验证匹配订单的挂单用户合法
            User matchingUser = userService.selectByPrimaryKey(matchingOrder.getUserid());
            if (matchingUser.getState() != GlobalParams.ACTIVE) {
                //取消订单
                CancelOrderListenerBean cancelOrderListenerBean = new CancelOrderListenerBean();
                cancelOrderListenerBean.setFlowOperType("交易挂单用户已停用，订单自动撤销");
                cancelOrderListenerBean.setOrderSpot(matchingOrder);
                orderEventBus.post(cancelOrderListenerBean);
                continue;
            }
            boolean complete = orderDealLimit(matchingOrder, saleOrder, GlobalParams.ORDER_TYPE_SALE, coinScale, manage, recordList);
            //判断买单量和价格是否小于最小值
            validateCoinScale(matchingOrder, coinScale);
            if (complete) {
                saleOrder.setState(GlobalParams.ORDER_STATE_TREATED);
                break;
            }
        }
        /*---------------------------------------------更新订单-----------------------------------------------------*/
        if (saleOrder.getDealAmount().compareTo(BigDecimal.ZERO) > 0) {
            orderSpotService.updateByPrimaryKeySelective(saleOrder);
            //插入钱包和流水
            if (saleOrder.getLevflag() == 1) {
                //TODO 杠杆
            }
            if (saleOrder.getLevflag() == 0) {
                //普通交易
                accountService.updateAccountAndInsertFlow(saleOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, saleOrder.getUnitcointype(), saleOrder.getDealAmount(), new BigDecimal(0), saleOrder.getUserid(), "币币交易", saleOrder.getId());
            }
            validateCoinScale(saleOrder, coinScale);
            //增加魂力
            addOrderCalcul(saleOrder.getUserid());
        }
        //行情和交易深度更新
        doAfterOrder(orderCoin, unitCoin, recordList);
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String marketPriceBuy(User user, Integer orderCoin, Integer unitCoin, Integer levFlag, String total, String password) {
        Map<Object, Object> manageParams = new HashMap<Object, Object>();
        manageParams.put("ordercointype", orderCoin);
        manageParams.put("unitcointype", unitCoin);
        OrderManage manage = null;
        List<OrderManage> orderManageList = orderManageService.selectAll(manageParams);
        if (orderManageList != null && !orderManageList.isEmpty()) {
            manage = orderManageList.get(0);
        }
        /*---------------------------------------------订单合法验证-----------------------------------------------------*/
        String result = validateOrder(user, orderCoin, unitCoin, password, manage);
        if (result != null) {
            return result;
        }
        CoinScale coinScale = coinScaleService.queryByCoin(orderCoin, unitCoin);
        int priceScale = 2;//价格小数位数
        int amountScale = 4;//数量小数位数
        BigDecimal minAmount = new BigDecimal(0.0001);//最小交易量
        BigDecimal minTotal = new BigDecimal(0.01);//最小交易额
        if (coinScale != null) {
            priceScale = coinScale.getOrderamtpricescale();
            amountScale = coinScale.getOrderamtamountscale();
            minAmount = coinScale.getMinspottransnum();
            minTotal = coinScale.getMarketbuyminamt();
        }
        //验证成交总额是否大于最小成交额
        BigDecimal totalAmount = new BigDecimal(total);
        totalAmount = BigDecimalUtils.roundDown(totalAmount, priceScale);
        if (totalAmount.compareTo(minTotal) < 0) {
            return Result.toResultFormat(ResultCode.ORDER_SPOT_PRICE_LESS_THAN_MIN, BigDecimalUtils.toString(minTotal));
        }
        //判断账户余额是否足够
        //杠杆
        if (levFlag == 1) {
            //TODO 判断杠杆账户余额是否充足
        }
        //普通现货
        if (levFlag == 0) {
            Map<Object, Object> accountParams = new HashMap<Object, Object>();
            accountParams.put("userid", user.getId());
            accountParams.put("accounttype", GlobalParams.ACCOUNT_TYPE_SPOT);
            accountParams.put("cointype", unitCoin);
            List<Account> listAccount = accountService.selectAll(accountParams);
            if (listAccount == null || listAccount.isEmpty()) {
                return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
            }
            Account account = listAccount.get(0);
            if (account.getAvailbalance().compareTo(totalAmount) < 0) {
                return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
            }
        }
        /*---------------------------------------------创建新订单-----------------------------------------------------*/
        OrderSpot buyOrder = new OrderSpot();
        buyOrder.setUserid(user.getId());
        buyOrder.setAmount(new BigDecimal(0));
        buyOrder.setAverage(new BigDecimal(0));
        buyOrder.setLevflag(levFlag);
        buyOrder.setOrdercointype(orderCoin);
        buyOrder.setOrdernum("O" + user.getId() + System.currentTimeMillis());
        buyOrder.setOrdertype(GlobalParams.ORDER_ORDERTYPE_MARKET);
        buyOrder.setPrice(totalAmount);
        buyOrder.setRemain(totalAmount);
        buyOrder.setState(GlobalParams.ORDER_STATE_UNTREATED);
        buyOrder.setTotal(new BigDecimal(0));
        buyOrder.setType(GlobalParams.ORDER_TYPE_BUY);
        buyOrder.setUnitcointype(unitCoin);
        buyOrder.setDealAmount(new BigDecimal(0));
        orderSpotService.insertSelective(buyOrder);
        accountService.updateAccountAndInsertFlow(user.getId(), GlobalParams.ACCOUNT_TYPE_SPOT, unitCoin, BigDecimalUtils.plusMinus(totalAmount), BigDecimal.ZERO, user.getId(), "币币交易买入", buyOrder.getId());
        /*---------------------------------------------匹配订单-----------------------------------------------------*/
        Map<Object, Object> matchingParams = new HashMap<Object, Object>();
        matchingParams.put("ordercointype", orderCoin);
        matchingParams.put("unitcointype", unitCoin);
        matchingParams.put("type", GlobalParams.ORDER_TYPE_SALE);
        List<OrderSpot> matchingList = orderSpotService.selectAllMatching(matchingParams);
        List<OrderSpotRecord> recordList = new ArrayList<OrderSpotRecord>();
        for (OrderSpot matchingOrder : matchingList) {
            //验证匹配订单的挂单用户合法
            User matchingUser = userService.selectByPrimaryKey(matchingOrder.getUserid());
            if (matchingUser.getState() != GlobalParams.ACTIVE) {
                //取消订单
                CancelOrderListenerBean cancelOrderListenerBean = new CancelOrderListenerBean();
                cancelOrderListenerBean.setFlowOperType("交易挂单用户已停用，订单自动撤销");
                cancelOrderListenerBean.setOrderSpot(matchingOrder);
                orderEventBus.post(cancelOrderListenerBean);
                continue;
            }
            boolean complete = orderDealMarket(buyOrder, matchingOrder, GlobalParams.ORDER_TYPE_BUY, coinScale, manage, recordList);
            //判断卖单量和价格是否小于最小值
            validateCoinScale(matchingOrder, coinScale);
            if (complete) {
                break;
            }
        }
        /*---------------------------------------------更新订单-----------------------------------------------------*/
        if (buyOrder.getDealAmount().compareTo(BigDecimal.ZERO) > 0) {
            buyOrder.setState(GlobalParams.ORDER_STATE_TREATED);
            orderSpotService.updateByPrimaryKeySelective(buyOrder);
            //插入钱包和流水
            if (buyOrder.getLevflag() == 1) {
                //TODO 杠杆
            }
            if (buyOrder.getLevflag() == 0) {
                //普通交易
                accountService.updateAccountAndInsertFlow(buyOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, buyOrder.getOrdercointype(), buyOrder.getDealAmount(), new BigDecimal(0), buyOrder.getUserid(), "币币交易", buyOrder.getId());
            }
            //增加魂力
            addOrderCalcul(buyOrder.getUserid());
            rebackRemain(buyOrder, "市价买入退款");
        } else {
            //如果没有成交 返回交易失败
            buyOrder.setState(GlobalParams.ORDER_STATE_FAIL);
            buyOrder.setRemain(BigDecimal.ZERO);
            orderSpotService.updateByPrimaryKeySelective(buyOrder);
            accountService.updateAccountAndInsertFlow(user.getId(), GlobalParams.ACCOUNT_TYPE_SPOT, unitCoin, totalAmount, BigDecimal.ZERO, user.getId(), "币币交易买入失败", buyOrder.getId());


            /*CancelOrderListenerBean cancelOrderListenerBean = new CancelOrderListenerBean();
            cancelOrderListenerBean.setFlowOperType("未匹配到订单，自动撤销");
            cancelOrderListenerBean.setOrderSpot(buyOrder);
            orderEventBus.post(cancelOrderListenerBean);*/
            return Result.toResult(ResultCode.ORDER_SPOT_FAIL);
        }
        //行情和交易深度更新
        doAfterOrder(orderCoin, unitCoin, recordList);
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String marketPriceSale(User user, Integer orderCoin, Integer unitCoin, Integer levFlag, String amount, String password) {
        Map<Object, Object> manageParams = new HashMap<Object, Object>();
        manageParams.put("ordercointype", orderCoin);
        manageParams.put("unitcointype", unitCoin);
        OrderManage manage = null;
        List<OrderManage> orderManageList = orderManageService.selectAll(manageParams);
        if (orderManageList != null && !orderManageList.isEmpty()) {
            manage = orderManageList.get(0);
        }
        /*---------------------------------------------订单合法验证-----------------------------------------------------*/
        String result = validateOrder(user, orderCoin, unitCoin, password, manage);
        if (result != null) {
            return result;
        }
        CoinScale coinScale = coinScaleService.queryByCoin(orderCoin, unitCoin);
        int priceScale = 2;//价格小数位数
        int amountScale = 4;//数量小数位数
        BigDecimal minAmount = new BigDecimal(0.0001);//最小交易量
        BigDecimal minTotal = new BigDecimal(0.01);//最小交易额
        if (coinScale != null) {
            priceScale = coinScale.getOrderamtpricescale();
            amountScale = coinScale.getOrderamtamountscale();
            minAmount = coinScale.getMarketsaleminnum();
            minTotal = coinScale.getMinspottransamt();
        }
        //验证成交总量是否大于最小成交量
        BigDecimal totalAmount = new BigDecimal(amount);
        totalAmount = BigDecimalUtils.roundDown(totalAmount, amountScale);
        if (totalAmount.compareTo(minAmount) < 0) {
            return Result.toResultFormat(ResultCode.ORDER_SPOT_AMOUNT_LESS_THAN_MIN, BigDecimalUtils.toString(minAmount));
        }
        //判断账户余额是否足够
        //杠杆
        if (levFlag == 1) {
            //TODO 判断杠杆账户余额是否充足
        }
        //普通现货
        if (levFlag == 0) {
            Map<Object, Object> accountParams = new HashMap<Object, Object>();
            accountParams.put("userid", user.getId());
            accountParams.put("accounttype", GlobalParams.ACCOUNT_TYPE_SPOT);
            accountParams.put("cointype", orderCoin);
            List<Account> listAccount = accountService.selectAll(accountParams);
            if (listAccount == null || listAccount.isEmpty()) {
                return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
            }
            Account account = listAccount.get(0);
            if (account.getAvailbalance().compareTo(totalAmount) < 0) {
                return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
            }
        }
        /*---------------------------------------------创建新订单-----------------------------------------------------*/
        OrderSpot saleOrder = new OrderSpot();
        saleOrder.setUserid(user.getId());
        saleOrder.setAmount(totalAmount);
        saleOrder.setAverage(new BigDecimal(0));
        saleOrder.setLevflag(levFlag);
        saleOrder.setOrdercointype(orderCoin);
        saleOrder.setOrdernum("O" + user.getId() + System.currentTimeMillis());
        saleOrder.setOrdertype(GlobalParams.ORDER_ORDERTYPE_MARKET);
        saleOrder.setPrice(new BigDecimal(0));
        saleOrder.setRemain(totalAmount);
        saleOrder.setState(GlobalParams.ORDER_STATE_UNTREATED);
        saleOrder.setTotal(new BigDecimal(0));
        saleOrder.setType(GlobalParams.ORDER_TYPE_SALE);
        saleOrder.setUnitcointype(unitCoin);
        saleOrder.setDealAmount(new BigDecimal(0));
        orderSpotService.insertSelective(saleOrder);
        accountService.updateAccountAndInsertFlow(user.getId(), GlobalParams.ACCOUNT_TYPE_SPOT, orderCoin, BigDecimalUtils.plusMinus(totalAmount), BigDecimal.ZERO, user.getId(), "币币交易卖出", saleOrder.getId());
        /*---------------------------------------------匹配订单-----------------------------------------------------*/
        Map<Object, Object> matchingParams = new HashMap<Object, Object>();
        matchingParams.put("ordercointype", orderCoin);
        matchingParams.put("unitcointype", unitCoin);
        matchingParams.put("type", GlobalParams.ORDER_TYPE_BUY);
        List<OrderSpot> matchingList = orderSpotService.selectAllMatching(matchingParams);
        List<OrderSpotRecord> recordList = new ArrayList<OrderSpotRecord>();
        for (OrderSpot matchingOrder : matchingList) {
            //验证匹配订单的挂单用户合法
            User matchingUser = userService.selectByPrimaryKey(matchingOrder.getUserid());
            if (matchingUser.getState() != GlobalParams.ACTIVE) {
                //取消订单
                CancelOrderListenerBean cancelOrderListenerBean = new CancelOrderListenerBean();
                cancelOrderListenerBean.setFlowOperType("交易挂单用户已停用，订单自动撤销");
                cancelOrderListenerBean.setOrderSpot(matchingOrder);
                orderEventBus.post(cancelOrderListenerBean);
                continue;
            }
            boolean complete = orderDealMarket(matchingOrder, saleOrder, GlobalParams.ORDER_TYPE_SALE, coinScale, manage, recordList);
            //判断卖单量和价格是否小于最小值
            validateCoinScale(matchingOrder, coinScale);
            if (complete) {
                break;
            }
        }
        /*---------------------------------------------更新订单-----------------------------------------------------*/
        if (saleOrder.getDealAmount().compareTo(BigDecimal.ZERO) > 0) {
            saleOrder.setState(GlobalParams.ORDER_STATE_TREATED);
            orderSpotService.updateByPrimaryKeySelective(saleOrder);
            //插入钱包和流水
            if (saleOrder.getLevflag() == 1) {
                //TODO 杠杆
            }
            if (saleOrder.getLevflag() == 0) {
                //普通交易
                accountService.updateAccountAndInsertFlow(saleOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, saleOrder.getUnitcointype(), saleOrder.getDealAmount(), new BigDecimal(0), saleOrder.getUserid(), "币币交易", saleOrder.getId());
            }
            //增加魂力
            addOrderCalcul(saleOrder.getUserid());
            rebackRemain(saleOrder, "市价卖出退款");
        } else {
            //如果没有成交 返回交易失败
            saleOrder.setState(GlobalParams.ORDER_STATE_FAIL);
            orderSpotService.updateByPrimaryKeySelective(saleOrder);
            accountService.updateAccountAndInsertFlow(user.getId(), GlobalParams.ACCOUNT_TYPE_SPOT, orderCoin, totalAmount, BigDecimal.ZERO, user.getId(), "币币交易卖出失败", saleOrder.getId());

            /*
            CancelOrderListenerBean cancelOrderListenerBean = new CancelOrderListenerBean();
            cancelOrderListenerBean.setFlowOperType("未匹配到订单，自动撤销");
            cancelOrderListenerBean.setOrderSpot(saleOrder);
            orderEventBus.post(cancelOrderListenerBean);*/
            return Result.toResult(ResultCode.ORDER_SPOT_FAIL);
        }
        //行情和交易深度更新
        doAfterOrder(orderCoin, unitCoin, recordList);
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String orderRecord(User user, Integer orderCoin, Integer unitCoin, Integer type, PageModel pageModel) {
        CoinScale coinScale = coinScaleService.queryByCoin(orderCoin, unitCoin);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userid", user.getId());
        params.put("ordercointype", orderCoin);
        params.put("unitcointype", unitCoin);
        if (type == 0) {
            //当前委托
            params.put("state", GlobalParams.ORDER_STATE_UNTREATED);
        } else {
            params.put("state", -1);
        }
        params.put("firstResult", pageModel.getFirstResult());
        params.put("maxResult", pageModel.getMaxResult());
        List<OrderSpot> list = orderSpotService.selectOrderRecordPaging(params);
        int count = orderSpotService.selectOrderRecordCount(params);
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        for (OrderSpot orderSpot : list) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("id", orderSpot.getId());
            resultMap.put("type", orderSpot.getType());
            resultMap.put("createTime", sdf.format(orderSpot.getCreatetime()));
            resultMap.put("price", BigDecimalUtils.toString(orderSpot.getPrice(), coinScale.getOrderamtpricescale()));
            resultMap.put("amount", BigDecimalUtils.toString(orderSpot.getAmount(), coinScale.getOrderamtamountscale()));
            resultMap.put("dealAmount", BigDecimalUtils.toString(orderSpot.getAmount().subtract(orderSpot.getRemain()), coinScale.getOrderamtamountscale()));
            resultMap.put("state", orderSpot.getState());
            resultMap.put("orderCoinType", orderSpot.getOrdercointype());
            resultMap.put("orderType", orderSpot.getOrdertype());
            resultMap.put("unitCoinType", orderSpot.getUnitcointype());
            resultList.add(resultMap);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", resultList);
        map.put("count", count);
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String orderCancel(Integer id) {
        OrderSpot orderSpot = orderSpotService.selectByPrimaryKey(id);
        if (orderSpot.getState() == GlobalParams.ORDER_STATE_UNTREATED) {
            log.info("撤销现货订单--->" + orderSpot.toString());
            Integer cancelCoinType = null;
            BigDecimal amount;
            if (orderSpot.getType() == GlobalParams.ORDER_TYPE_BUY) {
                CoinScale scale = coinScaleService.queryByCoin(orderSpot.getOrdercointype(),orderSpot.getUnitcointype());
                cancelCoinType = orderSpot.getUnitcointype();
                amount = BigDecimalUtils.multiply(orderSpot.getRemain(),orderSpot.getPrice(),scale.getOrderamtpricescale());
            } else {
                amount = orderSpot.getRemain();
                cancelCoinType = orderSpot.getOrdercointype();
            }
            //orderSpot.setRemain(new BigDecimal(0));
            orderSpot.setState(GlobalParams.ORDER_STATE_BACK);
            orderSpotService.updateByPrimaryKeySelective(orderSpot);
            if (orderSpot.getLevflag() == 1) {
                //TODO 如果是杠杆账户挂单,退款到杠杆账户
            }
            if (orderSpot.getLevflag() == 0) {
                //如果是现货账户挂单，退款到现货账户
               accountService.updateAccountAndInsertFlow(orderSpot.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, cancelCoinType, amount, new BigDecimal(0), orderSpot.getUserid(), "币币交易撤销", orderSpot.getId());

            }
            doAfterOrder(orderSpot.getOrdercointype(), orderSpot.getUnitcointype(), new ArrayList<OrderSpotRecord>());
            return Result.toResult(ResultCode.SUCCESS);
        }
        return Result.toResult(ResultCode.ORDER_STATE_INACTIVE);
    }

    @Override
    public String orderDetail(Integer id) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        OrderSpot orderSpot = orderSpotService.selectByPrimaryKey(id);
        CoinScale coinScale = coinScaleService.queryByCoin(orderSpot.getOrdercointype(), orderSpot.getUnitcointype());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", orderSpot.getId());
        map.put("orderCoinType", orderSpot.getOrdercointype());
        map.put("unitCoinType", orderSpot.getUnitcointype());
        map.put("type", orderSpot.getType());
        map.put("createTime", sdf.format(orderSpot.getCreatetime()));
        map.put("price", BigDecimalUtils.toString(orderSpot.getPrice(), coinScale.getOrderamtpricescale()));
        map.put("average", BigDecimalUtils.toString(orderSpot.getAverage(), coinScale.getOrderamtpricescale()));
        map.put("amount", BigDecimalUtils.toString(orderSpot.getAmount(), coinScale.getOrderamtamountscale()));
        map.put("dealAmount", BigDecimalUtils.toString(orderSpot.getAmount().subtract(orderSpot.getRemain()), coinScale.getOrderamtamountscale()));
        map.put("total", BigDecimalUtils.toString(orderSpot.getTotal(), coinScale.getOrderamtpricescale()));
        BigDecimal commAmount = commissionRecordService.selectSumAmountByOrderid(id);
        if (orderSpot.getType() == GlobalParams.ORDER_TYPE_BUY) {
            map.put("fee", BigDecimalUtils.toString(commAmount, coinScale.getOrderamtamountscale()));
            map.put("feeCoinType", orderSpot.getOrdercointype());
        } else {
            map.put("fee", BigDecimalUtils.toString(commAmount, coinScale.getOrderamtpricescale()));
            map.put("feeCoinType", orderSpot.getUnitcointype());
        }
        map.put("orderType", orderSpot.getOrdertype());
        map.put("state", orderSpot.getState());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderid", id);
        params.put("type", orderSpot.getType());
        List<OrderSpotRecord> list = orderSpotRecordService.queryOrderRecord(id,orderSpot.getType());
        List<Map<String, Object>> listMatching = new ArrayList<Map<String, Object>>();
        for (OrderSpotRecord order : list) {
            Map<String, Object> orderMap = new HashMap<String, Object>();
            orderMap.put("price", BigDecimalUtils.toString(order.getPrice()));
            orderMap.put("amount", BigDecimalUtils.toString(order.getAmount()));
            orderMap.put("total", BigDecimalUtils.toString(order.getTotal()));
            orderMap.put("createTime", sdf.format(order.getCreatetime()));
            orderMap.put("orderCoinType", order.getOrdercointype());
            orderMap.put("unitCoinType", order.getUnitcointype());
            listMatching.add(orderMap);
        }
        map.put("list", listMatching);
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String dealDigRecordList(User user, Integer page, Integer rows) {
        DealDigRecordModel ddrm = dealDigRecordService.queryProfit(user.getId());
        Map<String, Object> data = new HashMap<>();
        List<DealDigRecordModel> drList = new LinkedList<>();
        Integer coinScale = coinScaleService.queryByCoin(CoinType.DK, -1).getCalculscale();
        if(ddrm == null){
            data.put("today",BigDecimalUtils.toStringInZERO(new BigDecimal(0), coinScale));
            data.put("yesterday",BigDecimalUtils.toStringInZERO(new BigDecimal(0), coinScale));
            data.put("total",BigDecimalUtils.toStringInZERO(new BigDecimal(0) , coinScale));
        }else {
            data.put("today", BigDecimalUtils.toStringInZERO(ddrm.getToday() == null ? new BigDecimal(0) : ddrm.getToday(), coinScale));
            data.put("yesterday", BigDecimalUtils.toStringInZERO(ddrm.getYesterday() == null ? new BigDecimal(0) : ddrm.getYesterday(), coinScale));
            data.put("total", BigDecimalUtils.toStringInZERO(ddrm.getTotal() == null ? new BigDecimal(0) : ddrm.getTotal(), coinScale));
        }
        Map map = new HashMap();
        map.put("userid", user.getId());
        map.put("firstResult", page);
        map.put("maxResult", rows);
        List<DealDigRecord> list = dealDigRecordService.selectPaging(map);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        for(DealDigRecord dr : list){
            DealDigRecordModel ddr = new DealDigRecordModel();
            ddr.setAmount(BigDecimalUtils.toStringInZERO(dr.getAmount(), coinScale));
            ddr.setCreatetime(sdf.format(dr.getCreatetime()));
            ddr.setOperType(dr.getOpertype());
            drList.add(ddr);
        }
        data.put("list", drList);
        return Result.toResult(ResultCode.SUCCESS, data);
    }

    @Override
    public String checkRealNameAndOrderPassWord(User user) {
        /*实名认证判断*/
        if (user.getIdstatus() == 0) {
            return Result.toResult(ResultCode.USER_NOT_REALNAME);
        }
        /*是否设置交易密码*/
        if(StrUtils.isBlank(user.getOrderpwd())){
            return Result.toResult(ResultCode.ORDERPWD_NOT_EXISITED);
        }
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String minKLine(Integer orderCoin, Integer unitCoin) {
        Map<String, Object> data = new HashMap<>();
        String key = "coinorder:kline:YB:1:" + orderCoin + ":" + unitCoin;
        List<String> list = RedisUtil.searchList(redis, key, 0, 60);
        data.put("Kline", list);
        return Result.toResult(ResultCode.SUCCESS, data);
    }

    /**
     * 交易订单验证
     *
     * @return
     */
    private String validateOrder(User user, Integer orderCoin, Integer unitCoin, String password, OrderManage orderManage) {
        /*判断功能是否关闭*/
        if (orderManage == null || orderManage.getOnoff() == GlobalParams.INACTIVE) {
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }
        /*实名认证检查*/
        if (user.getIdstatus() == GlobalParams.INACTIVE) {
            return Result.toResult(ResultCode.USER_NOT_REALNAME);
        }
        /*交易密码验证*/
        String result = validateOrderPassword(user, password);
        if (result != null) {
            return result;
        }
        return null;

    }

    /**
     * 限价交易匹配
     *
     * @param buyOrder
     * @param saleOrder
     * @param type
     * @param coinScale
     * @param manage
     * @return
     */
    private boolean orderDealLimit(OrderSpot buyOrder, OrderSpot saleOrder, Integer type, CoinScale coinScale, OrderManage manage, List<OrderSpotRecord> list) {
        boolean complete = false;
        BigDecimal price = null;
        BigDecimal dealAmount = null;
        BigDecimal total = null;
        /*-----------------------------------------------买入-------------------------------------------------------------*/
        if (type == GlobalParams.ORDER_TYPE_BUY) {
            price = saleOrder.getPrice();
            //卖单量小于买单量
            if (buyOrder.getRemain().compareTo(saleOrder.getRemain()) > 0) {
                complete = false;
                //成交量
                dealAmount = saleOrder.getRemain();
                //成交总额
                total = BigDecimalUtils.multiply(dealAmount,price,coinScale.getOrderamtpricescale());
                if (total.compareTo(coinScale.getMinspottransamt()) < 0) {
                    //取消订单
                    CancelOrderListenerBean cancelOrderListenerBean = new CancelOrderListenerBean();
                    cancelOrderListenerBean.setFlowOperType("交易挂单小于最小成交额，订单自动撤销");
                    cancelOrderListenerBean.setOrderSpot(saleOrder);
                    orderEventBus.post(cancelOrderListenerBean);
                    return complete;
                }
                //剩余量
                BigDecimal buyRemain = buyOrder.getRemain().subtract(dealAmount);

                //添加成交记录
                OrderSpotRecord record = new OrderSpotRecord();
                record.setAmount(dealAmount);
                record.setBuyid(buyOrder.getId());
                record.setBuyuserid(buyOrder.getUserid());
                record.setOrdercointype(buyOrder.getOrdercointype());
                record.setPrice(price);
                record.setSaleid(saleOrder.getId());
                record.setSaleuserid(saleOrder.getUserid());
                record.setTotal(total);
                record.setUnitcointype(buyOrder.getUnitcointype());
                record.setCreatetime(new Timestamp(System.currentTimeMillis()));
                orderSpotRecordService.insertSelective(record);
                list.add(record);


                buyOrder.setRemain(buyRemain);
                buyOrder.setTotal(buyOrder.getTotal().add(total));
                buyOrder.setAverage(buyOrder.getTotal().divide(buyOrder.getAmount().subtract(buyOrder.getRemain()), coinScale.getOrderamtpricescale()));
                saleOrder.setRemain(new BigDecimal(0));
                saleOrder.setTotal(saleOrder.getTotal().add(total));
                saleOrder.setAverage(saleOrder.getTotal().divide(saleOrder.getAmount().subtract(saleOrder.getRemain()), coinScale.getOrderamtpricescale()));
                saleOrder.setState(GlobalParams.ORDER_STATE_TREATED);
                orderSpotService.updateByPrimaryKeySelective(saleOrder);
                BigDecimal availIncrement = calcFee(manage, total, saleOrder, record.getId(), GlobalParams.ORDER_ORDERTYPE_LIMIT);
                buyOrder.setDealAmount(buyOrder.getDealAmount().add(calcFee(manage, dealAmount, buyOrder, record.getId(), GlobalParams.ORDER_ORDERTYPE_LIMIT)));
                //增加卖家金额和流水
                if (saleOrder.getLevflag() == 1) {
                    //TODO 杠杆
                }
                if (saleOrder.getLevflag() == 0) {
                    //普通现货
                    accountService.updateAccountAndInsertFlow(saleOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, saleOrder.getUnitcointype(), availIncrement, new BigDecimal(0), saleOrder.getUserid(), "币币交易成交", record.getId());
                }
            }
            //卖单量大于等于买单量
            else {
                complete = true;
                //成交量
                dealAmount = buyOrder.getRemain();
                //成交总额
                total = dealAmount.multiply(price);
                if (total.compareTo(coinScale.getMinspottransamt()) < 0) {
                    return complete;
                }
                //添加成交记录
                OrderSpotRecord record = new OrderSpotRecord();
                record.setAmount(dealAmount);
                record.setBuyid(buyOrder.getId());
                record.setBuyuserid(buyOrder.getUserid());
                record.setOrdercointype(buyOrder.getOrdercointype());
                record.setPrice(price);
                record.setSaleid(saleOrder.getId());
                record.setSaleuserid(saleOrder.getUserid());
                record.setTotal(total);
                record.setUnitcointype(buyOrder.getUnitcointype());
                record.setCreatetime(new Timestamp(System.currentTimeMillis()));
                orderSpotRecordService.insertSelective(record);
                list.add(record);
//                //交易挖矿
//                doDealDig(record, GlobalParams.ORDER_ORDERTYPE_LIMIT);
                //剩余量
                buyOrder.setRemain(new BigDecimal(0));
                buyOrder.setTotal(buyOrder.getTotal().add(total));
                buyOrder.setAverage(buyOrder.getTotal().divide(buyOrder.getAmount().subtract(buyOrder.getRemain()), coinScale.getOrderamtpricescale()));
                BigDecimal saleRemain = saleOrder.getRemain().subtract(dealAmount);
                saleOrder.setRemain(saleRemain);
                saleOrder.setTotal(saleOrder.getTotal().add(total));
                saleOrder.setAverage(saleOrder.getTotal().divide(saleOrder.getAmount().subtract(saleOrder.getRemain()), coinScale.getOrderamtpricescale()));
                if (saleRemain.compareTo(BigDecimal.ZERO) == 0) {
                    saleOrder.setState(GlobalParams.ORDER_STATE_TREATED);
                }
                orderSpotService.updateByPrimaryKeySelective(saleOrder);
                //手续费扣除
                BigDecimal availIncrement = calcFee(manage, total, saleOrder, record.getId(), GlobalParams.ORDER_ORDERTYPE_LIMIT);
                buyOrder.setDealAmount(buyOrder.getDealAmount().add(calcFee(manage, dealAmount, buyOrder, record.getId(), GlobalParams.ORDER_ORDERTYPE_LIMIT)));
                //增加卖家金额和流水
                if (saleOrder.getLevflag() == 1) {
                    //TODO 杠杆
                }
                if (saleOrder.getLevflag() == 0) {
                    //普通现货
                    accountService.updateAccountAndInsertFlow(saleOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, saleOrder.getUnitcointype(), availIncrement, new BigDecimal(0), saleOrder.getUserid(), "币币交易成交", record.getId());
                }
            }
            //增加魂力
            addOrderCalcul(saleOrder.getUserid());
        }
        /*-----------------------------------------------卖出-------------------------------------------------------------*/
        else {
            price = buyOrder.getPrice();
            //卖单量小于买单量
            if (buyOrder.getRemain().compareTo(saleOrder.getRemain()) >= 0) {
                complete = true;
                //成交量
                dealAmount = saleOrder.getRemain();
                //成交总额
                total = dealAmount.multiply(price);
                if (total.compareTo(coinScale.getMinspottransamt()) < 0) {
                    return complete;
                }
                //剩余量
                BigDecimal buyRemain = buyOrder.getRemain().subtract(dealAmount);

                //添加成交记录
                OrderSpotRecord record = new OrderSpotRecord();
                record.setAmount(dealAmount);
                record.setBuyid(buyOrder.getId());
                record.setBuyuserid(buyOrder.getUserid());
                record.setOrdercointype(buyOrder.getOrdercointype());
                record.setPrice(price);
                record.setSaleid(saleOrder.getId());
                record.setSaleuserid(saleOrder.getUserid());
                record.setTotal(total);
                record.setUnitcointype(buyOrder.getUnitcointype());
                record.setCreatetime(new Timestamp(System.currentTimeMillis()));
                orderSpotRecordService.insertSelective(record);
                list.add(record);
                //交易挖矿
//                doDealDig(record, GlobalParams.ORDER_ORDERTYPE_LIMIT);
                if(buyRemain.compareTo(BigDecimal.ZERO)==0){
                    buyOrder.setState(GlobalParams.ORDER_STATE_TREATED);
                }
                buyOrder.setRemain(buyRemain);
                buyOrder.setTotal(buyOrder.getTotal().add(total));
                buyOrder.setAverage(buyOrder.getTotal().divide(buyOrder.getAmount().subtract(buyOrder.getRemain()), coinScale.getOrderamtpricescale()));
                orderSpotService.updateByPrimaryKeySelective(buyOrder);
                BigDecimal availIncrement = calcFee(manage, dealAmount, buyOrder, record.getId(), GlobalParams.ORDER_ORDERTYPE_LIMIT);
                saleOrder.setDealAmount(saleOrder.getDealAmount().add(calcFee(manage, total, saleOrder, record.getId(), GlobalParams.ORDER_ORDERTYPE_LIMIT)));
                //增加卖家金额和流水
                if (buyOrder.getLevflag() == 1) {
                    //TODO 杠杆
                }
                if (buyOrder.getLevflag() == 0) {
                    //普通现货
                    accountService.updateAccountAndInsertFlow(buyOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, buyOrder.getOrdercointype(), availIncrement, new BigDecimal(0), buyOrder.getUserid(), "币币交易成交", record.getId());
                }
                saleOrder.setRemain(new BigDecimal(0));
                saleOrder.setTotal(saleOrder.getTotal().add(total));
                saleOrder.setAverage(saleOrder.getTotal().divide(saleOrder.getAmount().subtract(saleOrder.getRemain()), coinScale.getOrderamtpricescale()));
            }
            //卖单量大于等于买单量
            else {
                complete = false;
                //成交量
                dealAmount = buyOrder.getRemain();
                //成交总额
                total = dealAmount.multiply(price);
                if (total.compareTo(coinScale.getMinspottransamt()) < 0) {
                    //取消订单
                    CancelOrderListenerBean cancelOrderListenerBean = new CancelOrderListenerBean();
                    cancelOrderListenerBean.setFlowOperType("交易挂单小于最小成交额，订单自动撤销");
                    cancelOrderListenerBean.setOrderSpot(buyOrder);
                    orderEventBus.post(cancelOrderListenerBean);
                    return complete;
                }
                //添加成交记录
                OrderSpotRecord record = new OrderSpotRecord();
                record.setAmount(dealAmount);
                record.setBuyid(buyOrder.getId());
                record.setBuyuserid(buyOrder.getUserid());
                record.setOrdercointype(buyOrder.getOrdercointype());
                record.setPrice(price);
                record.setSaleid(saleOrder.getId());
                record.setSaleuserid(saleOrder.getUserid());
                record.setTotal(total);
                record.setUnitcointype(buyOrder.getUnitcointype());
                record.setCreatetime(new Timestamp(System.currentTimeMillis()));
                orderSpotRecordService.insertSelective(record);
                list.add(record);
                //交易挖矿
//                doDealDig(record, GlobalParams.ORDER_ORDERTYPE_LIMIT);
                //剩余量
                buyOrder.setRemain(new BigDecimal(0));
                buyOrder.setTotal(buyOrder.getTotal().add(total));
                buyOrder.setAverage(buyOrder.getTotal().divide(buyOrder.getAmount().subtract(buyOrder.getRemain()), coinScale.getOrderamtpricescale()));
                buyOrder.setState(GlobalParams.ORDER_STATE_TREATED);
                orderSpotService.updateByPrimaryKeySelective(buyOrder);
                BigDecimal availIncrement = calcFee(manage, dealAmount, buyOrder, record.getId(), GlobalParams.ORDER_ORDERTYPE_LIMIT);
                saleOrder.setDealAmount(saleOrder.getDealAmount().add(calcFee(manage, total, saleOrder, record.getId(), GlobalParams.ORDER_ORDERTYPE_LIMIT)));
                //增加买家金额和流水
                if (buyOrder.getLevflag() == 1) {
                    //TODO 杠杆
                }
                if (buyOrder.getLevflag() == 0) {
                    //普通现货
                    accountService.updateAccountAndInsertFlow(buyOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, buyOrder.getOrdercointype(), availIncrement, new BigDecimal(0), buyOrder.getUserid(), "币币交易成交", record.getId());
                }
                BigDecimal saleRemain = saleOrder.getRemain().subtract(dealAmount);
                saleOrder.setRemain(saleRemain);
                saleOrder.setTotal(saleOrder.getTotal().add(total));
                saleOrder.setAverage(saleOrder.getTotal().divide(saleOrder.getAmount().subtract(saleOrder.getRemain()), coinScale.getOrderamtpricescale()));

            }
            //增加魂力
            addOrderCalcul(buyOrder.getUserid());
        }
        return complete;
    }

    /**
     * 市价交易匹配
     *
     * @param buyOrder
     * @param saleOrder
     * @param type
     * @param coinScale
     * @param manage
     * @return
     */
    private boolean orderDealMarket(OrderSpot buyOrder, OrderSpot saleOrder, Integer type, CoinScale coinScale, OrderManage manage, List<OrderSpotRecord> list) {
        boolean complete = false;
        BigDecimal price = null;
        BigDecimal dealAmount = null;
        BigDecimal total = null;
        /*-----------------------------------------------买入-------------------------------------------------------------*/
        if (type == GlobalParams.ORDER_TYPE_BUY) {
            price = saleOrder.getPrice();
            total = price.multiply(saleOrder.getRemain());
            //卖单总额小于买单总额
            if (buyOrder.getRemain().compareTo(total) > 0) {
                complete = false;
                //成交量
                dealAmount = saleOrder.getRemain();
                if (total.compareTo(coinScale.getMinspottransamt()) < 0) {
                    //取消订单
                    CancelOrderListenerBean cancelOrderListenerBean = new CancelOrderListenerBean();
                    cancelOrderListenerBean.setFlowOperType("交易挂单小于最小成交额，订单自动撤销");
                    cancelOrderListenerBean.setOrderSpot(saleOrder);
                    orderEventBus.post(cancelOrderListenerBean);
                    return complete;
                }
                //剩余量
                BigDecimal buyRemain = buyOrder.getRemain().subtract(total);

                //添加成交记录
                OrderSpotRecord record = new OrderSpotRecord();
                record.setAmount(dealAmount);
                record.setBuyid(buyOrder.getId());
                record.setBuyuserid(buyOrder.getUserid());
                record.setOrdercointype(buyOrder.getOrdercointype());
                record.setPrice(price);
                record.setSaleid(saleOrder.getId());
                record.setSaleuserid(saleOrder.getUserid());
                record.setTotal(total);
                record.setUnitcointype(buyOrder.getUnitcointype());
                record.setCreatetime(new Timestamp(System.currentTimeMillis()));
                orderSpotRecordService.insertSelective(record);
                list.add(record);
                //交易挖矿
//                doDealDig(record, GlobalParams.ORDER_ORDERTYPE_MARKET);

                buyOrder.setAmount(buyOrder.getAmount().add(dealAmount));
                buyOrder.setRemain(buyRemain);
                buyOrder.setTotal(buyOrder.getTotal().add(total));
                buyOrder.setAverage(buyOrder.getTotal().divide(buyOrder.getAmount(), coinScale.getOrderamtpricescale()));
                saleOrder.setRemain(new BigDecimal(0));
                saleOrder.setTotal(saleOrder.getTotal().add(total));
                saleOrder.setAverage(saleOrder.getTotal().divide(saleOrder.getAmount().subtract(saleOrder.getRemain()), coinScale.getOrderamtpricescale()));
                saleOrder.setState(GlobalParams.ORDER_STATE_TREATED);
                orderSpotService.updateByPrimaryKeySelective(saleOrder);
                BigDecimal availIncrement = calcFee(manage, total, saleOrder, record.getId(), GlobalParams.ORDER_ORDERTYPE_MARKET);
                buyOrder.setDealAmount(buyOrder.getDealAmount().add(calcFee(manage, dealAmount, buyOrder, record.getId(), GlobalParams.ORDER_ORDERTYPE_MARKET)));
                //增加卖家金额和流水
                if (saleOrder.getLevflag() == 1) {
                    //TODO 杠杆
                }
                if (saleOrder.getLevflag() == 0) {
                    //普通现货
                    accountService.updateAccountAndInsertFlow(saleOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, saleOrder.getUnitcointype(), availIncrement, new BigDecimal(0), saleOrder.getUserid(), "币币交易成交", record.getId());
                }
            }
            //卖单量大于买单量
            else {
                complete = true;
                //成交量
                dealAmount = buyOrder.getRemain().divide(price, coinScale.getOrderamtamountscale(), BigDecimal.ROUND_DOWN);
                //成交总额
                total = dealAmount.multiply(price);
                if (total.compareTo(coinScale.getMinspottransamt()) < 0) {
                    return complete;
                }
                //添加成交记录
                OrderSpotRecord record = new OrderSpotRecord();
                record.setAmount(dealAmount);
                record.setBuyid(buyOrder.getId());
                record.setBuyuserid(buyOrder.getUserid());
                record.setOrdercointype(buyOrder.getOrdercointype());
                record.setPrice(price);
                record.setSaleid(saleOrder.getId());
                record.setSaleuserid(saleOrder.getUserid());
                record.setTotal(total);
                record.setUnitcointype(buyOrder.getUnitcointype());
                record.setCreatetime(new Timestamp(System.currentTimeMillis()));
                orderSpotRecordService.insertSelective(record);
                list.add(record);
                //交易挖矿
//                doDealDig(record, GlobalParams.ORDER_ORDERTYPE_MARKET);
                //剩余量
                buyOrder.setAmount(buyOrder.getAmount().add(dealAmount));
                buyOrder.setRemain(buyOrder.getRemain().subtract(total));
                buyOrder.setTotal(buyOrder.getTotal().add(total));
                buyOrder.setAverage(buyOrder.getTotal().divide(buyOrder.getAmount(), coinScale.getOrderamtpricescale()));
                BigDecimal saleRemain = saleOrder.getRemain().subtract(dealAmount);
                saleOrder.setTotal(saleOrder.getTotal().add(total));
                if (saleOrder.getRemain().compareTo(dealAmount) == 0) {
                    saleOrder.setState(GlobalParams.ORDER_STATE_TREATED);
                }
                saleOrder.setRemain(saleRemain);
                saleOrder.setAverage(saleOrder.getTotal().divide(saleOrder.getAmount().subtract(saleOrder.getRemain()), coinScale.getOrderamtpricescale()));

                orderSpotService.updateByPrimaryKeySelective(saleOrder);
                //手续费扣除
                BigDecimal availIncrement = calcFee(manage, total, saleOrder, record.getId(), GlobalParams.ORDER_ORDERTYPE_MARKET);
                buyOrder.setDealAmount(buyOrder.getDealAmount().add(calcFee(manage, dealAmount, buyOrder, record.getId(), GlobalParams.ORDER_ORDERTYPE_MARKET)));
                //增加卖家金额和流水
                if (saleOrder.getLevflag() == 1) {
                    //TODO 杠杆
                }
                if (saleOrder.getLevflag() == 0) {
                    //普通现货
                    accountService.updateAccountAndInsertFlow(saleOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, saleOrder.getUnitcointype(), availIncrement, new BigDecimal(0), saleOrder.getUserid(), "币币交易成交", record.getId());
                }
            }
            //增加魂力
            addOrderCalcul(saleOrder.getUserid());
        }
        /*-----------------------------------------------卖出-------------------------------------------------------------*/
        else {
            price = buyOrder.getPrice();
            //卖单量小于买单量
            if (buyOrder.getRemain().compareTo(saleOrder.getRemain()) > 0) {
                complete = true;
                //成交量
                dealAmount = saleOrder.getRemain();
                //成交总额
                total = dealAmount.multiply(price);
                if (total.compareTo(coinScale.getMinspottransamt()) < 0) {
                    return complete;
                }
                //剩余量
                BigDecimal buyRemain = buyOrder.getRemain().subtract(dealAmount);

                //添加成交记录
                OrderSpotRecord record = new OrderSpotRecord();
                record.setAmount(dealAmount);
                record.setBuyid(buyOrder.getId());
                record.setBuyuserid(buyOrder.getUserid());
                record.setOrdercointype(buyOrder.getOrdercointype());
                record.setPrice(price);
                record.setSaleid(saleOrder.getId());
                record.setSaleuserid(saleOrder.getUserid());
                record.setTotal(total);
                record.setUnitcointype(buyOrder.getUnitcointype());
                record.setCreatetime(new Timestamp(System.currentTimeMillis()));
                orderSpotRecordService.insertSelective(record);
                list.add(record);
                //交易挖矿
//                doDealDig(record, GlobalParams.ORDER_ORDERTYPE_MARKET);

                buyOrder.setRemain(buyRemain);
                buyOrder.setTotal(buyOrder.getTotal().add(total));
                buyOrder.setAverage(buyOrder.getTotal().divide(buyOrder.getAmount().subtract(buyOrder.getRemain()), coinScale.getOrderamtpricescale()));
                orderSpotService.updateByPrimaryKeySelective(buyOrder);
                BigDecimal availIncrement = calcFee(manage, dealAmount, buyOrder, record.getId(), GlobalParams.ORDER_ORDERTYPE_MARKET);
                saleOrder.setDealAmount(saleOrder.getDealAmount().add(calcFee(manage, total, saleOrder, record.getId(), GlobalParams.ORDER_ORDERTYPE_MARKET)));
                //增加买家金额和流水
                if (buyOrder.getLevflag() == 1) {
                    //TODO 杠杆
                }
                if (buyOrder.getLevflag() == 0) {
                    //普通现货
                    accountService.updateAccountAndInsertFlow(buyOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, buyOrder.getOrdercointype(), availIncrement, new BigDecimal(0), buyOrder.getUserid(), "币币交易成交", record.getId());
                }
                saleOrder.setRemain(new BigDecimal(0));
                saleOrder.setTotal(saleOrder.getTotal().add(total));
                saleOrder.setAverage(saleOrder.getTotal().divide(saleOrder.getAmount().subtract(saleOrder.getRemain()), coinScale.getOrderamtpricescale()));
            }
            //卖单量大于等于买单量
            else {
                complete = false;
                //成交量
                dealAmount = buyOrder.getRemain();
                //成交总额
                total = dealAmount.multiply(price);
                if (total.compareTo(coinScale.getMinspottransamt()) < 0) {
                    //取消订单
                    CancelOrderListenerBean cancelOrderListenerBean = new CancelOrderListenerBean();
                    cancelOrderListenerBean.setFlowOperType("交易挂单小于最小成交额，订单自动撤销");
                    cancelOrderListenerBean.setOrderSpot(buyOrder);
                    orderEventBus.post(cancelOrderListenerBean);
                    return complete;
                }
                //添加成交记录
                OrderSpotRecord record = new OrderSpotRecord();
                record.setAmount(dealAmount);
                record.setBuyid(buyOrder.getId());
                record.setBuyuserid(buyOrder.getUserid());
                record.setOrdercointype(buyOrder.getOrdercointype());
                record.setPrice(price);
                record.setSaleid(saleOrder.getId());
                record.setSaleuserid(saleOrder.getUserid());
                record.setTotal(total);
                record.setUnitcointype(buyOrder.getUnitcointype());
                record.setCreatetime(new Timestamp(System.currentTimeMillis()));
                orderSpotRecordService.insertSelective(record);
                list.add(record);
                //交易挖矿
//                doDealDig(record, GlobalParams.ORDER_ORDERTYPE_MARKET);
                //剩余量
                buyOrder.setRemain(new BigDecimal(0));
                buyOrder.setTotal(buyOrder.getTotal().add(total));
                buyOrder.setAverage(buyOrder.getTotal().divide(buyOrder.getAmount().subtract(buyOrder.getRemain()), coinScale.getOrderamtpricescale()));
                buyOrder.setState(GlobalParams.ORDER_STATE_TREATED);
                orderSpotService.updateByPrimaryKeySelective(buyOrder);
                BigDecimal availIncrement = calcFee(manage, dealAmount, buyOrder, record.getId(), GlobalParams.ORDER_ORDERTYPE_MARKET);
                saleOrder.setDealAmount(saleOrder.getDealAmount().add(calcFee(manage, total, saleOrder, record.getId(), GlobalParams.ORDER_ORDERTYPE_MARKET)));
                //增加买家金额和流水
                if (buyOrder.getLevflag() == 1) {
                    //TODO 杠杆
                }
                if (buyOrder.getLevflag() == 0) {
                    //普通现货
                    accountService.updateAccountAndInsertFlow(buyOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, buyOrder.getOrdercointype(), availIncrement, new BigDecimal(0), buyOrder.getUserid(), "币币交易成交", record.getId());
                }
                BigDecimal saleRemain = saleOrder.getRemain().subtract(dealAmount);
                saleOrder.setRemain(saleRemain);
                saleOrder.setTotal(saleOrder.getTotal().add(total));
                saleOrder.setAverage(saleOrder.getTotal().divide(saleOrder.getAmount().subtract(saleOrder.getRemain()), coinScale.getOrderamtpricescale()));

            }
            //增加魂力
            addOrderCalcul(buyOrder.getUserid());
        }
        return complete;
    }

    /**
     * 计算手续费
     *
     * @param manage
     * @param amount
     * @param order
     * @param id
     * @return
     */
    private BigDecimal calcFee(OrderManage manage, BigDecimal amount, OrderSpot order, Integer id, Integer orderType) {
        if(orderType == GlobalParams.ORDER_ORDERTYPE_LIMIT) {
            if (manage.getPerformrate().compareTo(BigDecimal.ZERO) < 1 && manage.getReferrate().compareTo(BigDecimal.ZERO) < 1) {
                return amount;
            }
        }else{
            if (manage.getMarketpPerformRate().compareTo(BigDecimal.ZERO) < 1 && manage.getMarketReferRate().compareTo(BigDecimal.ZERO) < 1) {
                return amount;
            }
        }
        //手续费币种
        int commCoinType = order.getType() == GlobalParams.ORDER_TYPE_BUY ? order.getOrdercointype() : order.getUnitcointype();

        /*计算手续费*/
        BigDecimal feeOfPerform = new BigDecimal(0);
        BigDecimal feeOfReference = new BigDecimal(0);
        User user = userService.selectByPrimaryKey(order.getUserid());
        User refUser = null;
        if (user.getReferenceid() != null) {
            refUser = userService.selectByPrimaryKey(user.getReferenceid());
        }
        if (user.getReferenceid() != null && user.getReferenceid() > 0 && refUser != null && refUser.getLogintime() != null) {
            if(orderType == GlobalParams.ORDER_ORDERTYPE_LIMIT) {
                feeOfPerform = BigDecimalUtils.multiply(amount, manage.getPerformrate());
                feeOfReference = BigDecimalUtils.multiply(amount, manage.getReferrate());
            }else{
                feeOfPerform = BigDecimalUtils.multiply(amount, manage.getMarketpPerformRate());
                feeOfReference = BigDecimalUtils.multiply(amount, manage.getMarketReferRate());
            }
        } else {
            if(orderType == GlobalParams.ORDER_ORDERTYPE_LIMIT) {
                feeOfPerform = BigDecimalUtils.multiply(amount, manage.getPerformrate().add(manage.getReferrate()));
            }else{
                feeOfPerform = BigDecimalUtils.multiply(amount, manage.getMarketpPerformRate().add(manage.getMarketReferRate()));
            }
        }

        log.info("feeOfPerform" + feeOfPerform.toString());
        log.info("feeOfReference" + feeOfReference.toString());
        if (feeOfPerform.compareTo(BigDecimal.ZERO) == 1) {
            /*保存平台手续费记录*/
            CommissionRecord comm = new CommissionRecord();
            comm.setUserid(user.getId());
            comm.setCommamount(feeOfPerform);
            comm.setCommcointype(commCoinType);
            comm.setOrderamount(amount);
            comm.setOrdercointype(order.getOrdercointype());
            comm.setType(GlobalParams.COMMISSION_TYPE_PERFORM);
            comm.setReferenceid(GlobalParams.SYSTEM_OPERID);
            comm.setOrderid(order.getId());
            commissionRecordService.insertSelective(comm);

            User platUser = userService.getByRole(GlobalParams.ROLE_TYPE_PLATFORM);
            if(platUser!=null){
                /*更新平台钱包并保存流水*/
                accountService.updateAccountAndInsertFlow(platUser.getId(), GlobalParams.ACCOUNT_TYPE_SPOT, commCoinType, feeOfPerform, new BigDecimal(0), GlobalParams.SYSTEM_OPERID, "平台手续费奖励", comm.getId());
            }
            //交易挖矿
            OrderSpotRecord record = orderSpotRecordService.selectByPrimaryKey(id);
            doDealDig(comm, manage, record, orderType);
        }

        if (feeOfReference.compareTo(BigDecimal.ZERO) == 1) {
            /*保存推荐人续费记录*/
            User referUser = userService.selectByPrimaryKey(user.getReferenceid());
            CommissionRecord referComm = new CommissionRecord();
            referComm.setUserid(user.getId());
            referComm.setCommamount(feeOfReference);
            referComm.setCommcointype(commCoinType);
            referComm.setOrderamount(amount);
            referComm.setOrdercointype(order.getOrdercointype());
            referComm.setType(GlobalParams.COMMISSION_TYPE_REFER);
            referComm.setReferenceid(referUser.getId());
            referComm.setOrderid(order.getId());
            commissionRecordService.insertSelective(referComm);

            /*更新推荐人钱包并保存流水*/
            accountService.updateAccountAndInsertFlow(referUser.getId(), GlobalParams.ACCOUNT_TYPE_SPOT, commCoinType, feeOfReference, new BigDecimal(0), GlobalParams.SYSTEM_OPERID, "推荐人手续费奖励", referComm.getId());
        }
        return BigDecimalUtils.subtract(amount, BigDecimalUtils.add(feeOfPerform, feeOfReference));
    }

    /**
     * 验证订单数量、金额是否满足最小值
     *
     * @param orderSpot
     * @param coinScale
     */
    void validateCoinScale(OrderSpot orderSpot, CoinScale coinScale) {
        if (orderSpot.getState() == GlobalParams.ORDER_STATE_UNTREATED) {
            if (orderSpot.getRemain().compareTo(coinScale.getMinspottransnum()) < 0) {
                //取消订单
                CancelOrderListenerBean cancelOrderListenerBean = new CancelOrderListenerBean();
                cancelOrderListenerBean.setFlowOperType("剩余量小于最小交易量，订单自动撤销");
                cancelOrderListenerBean.setOrderSpot(orderSpot);
                orderEventBus.post(cancelOrderListenerBean);
                return;
            }
            if (orderSpot.getRemain().multiply(orderSpot.getPrice()).compareTo(coinScale.getMinspottransamt()) < 0) {
                //取消订单
                CancelOrderListenerBean cancelOrderListenerBean = new CancelOrderListenerBean();
                cancelOrderListenerBean.setFlowOperType("剩余交易金额小于最小交易金额，订单自动撤销");
                cancelOrderListenerBean.setOrderSpot(orderSpot);
                orderEventBus.post(cancelOrderListenerBean);
            }
        }
    }

    /**
     * 增加交易魂力
     *
     * @param userid
     */
    void addOrderCalcul(Integer userid) {
        AddCalcForceListenerBean addCalcForceListenerBean = new AddCalcForceListenerBean();
        addCalcForceListenerBean.setUserId(userid);
        orderEventBus.post(addCalcForceListenerBean);
    }

    /**
     * 市价交易退款
     *
     * @param orderSpot
     */
    void rebackRemain(OrderSpot orderSpot, String remark) {
        if (orderSpot.getRemain().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal amount = orderSpot.getRemain();
            Integer cancelCoinType = null;
            if (orderSpot.getType() == GlobalParams.ORDER_TYPE_BUY) {
                cancelCoinType = orderSpot.getUnitcointype();
                orderSpot.setRemain(new BigDecimal(0));
            } else {
                cancelCoinType = orderSpot.getOrdercointype();

            }

            orderSpotService.updateByPrimaryKeySelective(orderSpot);
            if (orderSpot.getLevflag() == 1) {
                //TODO 如果是杠杆账户挂单,退款到杠杆账户
            }
            if (orderSpot.getLevflag() == 0) {
                //如果是现货账户挂单，退款到现货账户
                try {
                    accountService.updateAccountAndInsertFlow(orderSpot.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, cancelCoinType, amount, new BigDecimal(0), GlobalParams.SYSTEM_OPERID, remark, orderSpot.getId());
                } catch (BanlanceNotEnoughException e) {
                    log.error("撤销订单失败--->" + e.getMessage());
                }
            }
        }
    }

    /**
     * TODO 推送行情、交易深度
     */
    void doAfterOrder(Integer orderCoin, Integer unitCoin, List<OrderSpotRecord> list) {
        AfterOrderListenerBean afterOrderListenerBean = new AfterOrderListenerBean();
        afterOrderListenerBean.setOrderCoin(orderCoin);
        afterOrderListenerBean.setUnitCoin(unitCoin);
        afterOrderListenerBean.setRecordList(list);
        orderEventBus.post(afterOrderListenerBean);
        log.info("更新交易socket：{}",afterOrderListenerBean.toString());
    }

    /**
     * 交易挖矿
     */
    void doDealDig(CommissionRecord commissionRecord, OrderManage manage, OrderSpotRecord record, Integer type) {
        DealDigListenerBean dealDigListenerBean = new DealDigListenerBean();
        dealDigListenerBean.setOrderType(type);
        dealDigListenerBean.setManage(manage);
        dealDigListenerBean.setRecord(record);
        dealDigListenerBean.setCommissionRecord(commissionRecord);
        orderEventBus.post(dealDigListenerBean);
    }
}
