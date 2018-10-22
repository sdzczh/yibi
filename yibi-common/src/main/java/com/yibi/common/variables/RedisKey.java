package com.yibi.common.variables;

/**
 * @描述 Redis-Key值声明类<br>
 * @author administrator
 * @版本 v1.0.0
 * @日期 2017-10-13
 */
public class RedisKey {

	public static final String TEST="TEST";
	
	public static final String TOKEN = "coinorder:token:%s";
	
	public static final String SYSTEM_PARAM = "coinorder:systemParam:%s";
	
	public static final String TABLE_NAME = "coinorder:tableName:%s";
	
	public static final String BUY_ORDER_VERSION = "coinorder:order:%s:%s:buys:version"; /*买入订单列表版本号   ：计价币种：交易币种*/
	public static final String BUY_ORDER_LIST = "coinorder:order:%s:%s:buys:list"; /*买入订单列表   ：计价币种：交易币种*/
	
	public static final String SALE_ORDER_VERSION = "coinorder:order:%s:%s:sales:version"; /*卖出订单列表版本号   ：计价币种：交易币种*/
	public static final String SALE_ORDER_LIST = "coinorder:order:%s:%s:sales:list"; /*卖出订单列表   ：计价币种：交易币种*/
	public static final String LATEST_TRANS_PRICE = "coinorder:order:%s:%s:price";/*最新成交价*/
	public static final String ORDER_MANAGER = "coinorder:order:%s:%s:MANAGER"; /*交易管理*/
	public static final String ORDER_MANAGER_ONOFF = "ONOFF"; /*交易管理-开关*/
	public static final String ORDER_MANAGER_REFER_RATE = "referRate"; /*交易管理-推荐人费率*/
	public static final String ORDER_MANAGER_PERFORM_RATE = "performRate"; /*交易管理-平台费率*/
	
	public static final String ORDER_PASSWORD_ERROR_TIMES = "coinorder:orderPassword:%s:errorTimes"; /*交易密码错误次数限制*/
	public static final String ORDER_PASSWORD_ERROR_TIMESTAMP = "coinorder:orderPassword:%s:errorTimestamp"; /*错误次数累计时间范围*/
	public static final String ORDER_PASSWORD_LOCK_TIMESTAMP = "coinorder:orderPassword:%s:lockTimestamp"; /*锁定时长*/
	public static final String SMS_ERROR_TIMES = "coinorder:smsRecord:%s"; /*验证码错误次数*/
	
	/*银行列表*/
	public static final String BANK_LIST = "coinorder:bank";
	public static final String C2C_MANAGE = "coinorder:C2CManage:%s";
	
	//C2C最新成交价   %s:币种
	public static final String C2C_PRICE = "coinorder:C2CPrice:%s";
	
	//C2C超时订单列表名称
	public static final String C2C_ORDERS_NOTPAY_KEY_NAME = "coinorder:C2COrders:notPayName";
	public static final String C2C_ORDERS_NOTCONFIRM_KEY_NAME = "coinorder:C2COrders:notConfirmName";
	//C2C超时订单列表:超时分钟数
	public static final String C2C_ORDERS_NOTPAY = "coinorder:C2COrders:notPay:%s";
	public static final String C2C_ORDERS_NOTCONFIRM = "coinorder:C2COrders:notConfirm:%s";
	
	//撤销次数统计:用户id:用户角色:日期
	public static final String C2C_ORDERS_CANCEL = "coinorder:cancelCounts:%s:%s:%s";
	
	//k线数据  时间间隔类型（1:1min,2:5min,3:30min,4:1hour,5:1day）:计价币种:交易币种
	public static final String KLINEYB = "coinorder:kline:YB:%s:%s:%s";
	public static final String KLINEOK = "coinorder:kline:OK:%s:%s:%s";
	
	//全球现货行情最新的成交价 :计价币种:交易币种
	public static final String SPOT_NEWPRICE = "coinorder:spotNewPrice:OK:%s:%s";
	//交易记录列表  计价币种:交易币种
	public static final String ORDER_RECORD_LIST = "coinorder:orderRecords:%s:%s";
	
	//红包超时队列
	public static final String RED_PACKET_OUTTIME_QUEUE = "coinorder:redPacket:outTimeQueue";

	public static final String MARKET = "coinorder:market:%s:%s:%s";//行情 marketType(1一币2主流):计价币种:交易币种
	
}
