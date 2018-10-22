package com.yibi.turnapi.commons.variables;


public class SystemParams {
	//记录请求地址开关
	public static final String RECORDREQUEST_ONOFF = "RECORDREQUEST_ONOFF";
	
	//短信开关
	public static final String SMS_ONOFF = "SMS_ONOFF";
	//短信次数限制
	public static final String SMS_COUNTS_LIMIT = "SMS_COUNTS_LIMIT"; 
	//短信时间限制   （分钟）
	public static final String SMS_TIME_LIMIT = "SMS_TIME_LIMIT";
	
	public static final String POPUP_NOTICE_ONOFF = "POPUP_NOTICE_ONOFF"; //弹窗通知开关
	public static final String POPUP_NOTICE_IMG = "POPUP_NOTICE_IMG"; //弹窗通知图片
	public static final String POPUP_NOTICE_LINK_URL = "POPUP_NOTICE_URL"; //弹窗通知跳转地址
	public static final String POPUP_NOTICE_LINK_TITLE = "POPUP_NOTICE_TIILE"; //弹窗通知跳转标题
	
	//注册功能开关
	public static final String REGIST_ONOFF = "REGIST_ONOFF"; 
	//https开关
	public static final String HTTPS_ONOFF = "HTTPS_ONOFF"; 
	//转账开关
	public static final String TRANSFER_ONOFF = "TRANSFER_ONOFF"; 
	
	/*充值开关*/
	public static final String RECHARGE_ALIPAY_ONOFF = "RECHARGE_ALIPAY_ONOFF";//支付宝充值功能开关
	public static final String RECHARGE_WECHANT_ONOFF = "RECHARGE_WECHANT_ONOFF";//微信充值功能开关
	public static final String RECHARGE_BANK_ONOFF = "RECHARGE_BANK_ONOFF";//银行卡充值功能开关
	public static final String RECHARGE_COMMON_ONOFF = "RECHARGE_COMMON_ONOFF";//普通充值开关
	
	/*费率*/
	public static final String RECHARGE_ALIPAY_RATE = "RECHARGE_ALIPAY_RATE";//支付宝充值费率
	public static final String RECHARGE_WECHANT_RATE = "RECHARGE_WECHANT_RATE";//微信充值费率
	public static final String RECHARGE_BANK_RATE = "RECHARGE_BANK_RATE";//银行卡充值费率
	public static final String RECHARGE_COMMON_RATE = "RECHARGE_COMMON_RATE";//普通充值费率
	public static final String TRANSFER_RATE = "TRANSFER_RATE";//转账充值费率
	
	
	/*提现开关*/
	public static final String WITHDRAW_ALIPAY_ONOFF = "WITHDRAW_ALIPAY_ONOFF";//支付宝提现功能开关
	public static final String WITHDRAW_WECHANT_ONOFF = "WITHDRAW_WECHANT_ONOFF";//微信提现功能开关
	public static final String WITHDRAW_BANK_ONOFF = "WITHDRAW_BANK_ONOFF";//银行卡提现功能开关
	public static final String WITHDRAW_COMMON_ONOFF = "WITHDRAW_COMMON_ONOFF";//普通提现开关
	
	/*提现费率*/
	public static final String WITHDRAW_ALIPAY_RATE = "WITHDRAW_ALIPAY_RATE";//支付宝提现费率
	public static final String WITHDRAW_WECHANT_RATE = "WITHDRAW_WECHANT_RATE";//微信提现费率
	public static final String WITHDRAW_BANK_RATE = "WITHDRAW_BANK_RATE";//银行卡提现费率
	public static final String WITHDRAW_COMMON_RATE = "WITHDRAW_COMMON_RATE";//普通提现费率
	
	
	public static final String WITHDRAW_COUNT_LIMIT = "WITHDRAW_COUNT_LIMIT";//日提现次数限制
	public static final String WITHDRAW_SUM_LIMIT = "WITHDRAW_SUM_LIMIT";//日提现金额限制
	
	
	
	public static final String ORDERPWD_LOCK_INTERVAL = "ORDERPWD_LOCK_INTERVAL";//交易密码锁定时间(分钟)
	public static final String ORDERPWD_ERROR_INTERVAL = "ORDERPWD_ERROR_INTERVAL";//交易密码错误时间(分钟)
	public static final String ORDERPWD_ERROR_TIMES = "ORDERPWD_ERROR_TIMES";//交易密码错误次数
	
	public static final String DIG_HYC_PAY_ONOFF = "DIG_RECHARGE_ONOFF";//火蚁挖矿支付开关
	public static final String DIG_HYC_PAY_AMOUNT = "DIG_HYC_PAY_AMOUNT";//火蚁挖矿支付金额
	public static final String DIG_HYC_PAY_ACTIVEIIME = "DIG_HYC_PAY_ACTIVEIIME";//火蚁挖矿支付有效时间
	public static final String DIG_HYC_PAY_RATE = "DIG_HYC_PAY_RATE";//火蚁挖矿支付佣金费率

	
	public static final String SYSTEM_URL = "SYSTEM_URL";//系统地址
	
	public static final String MARKET_ORDER_SIZE_MAX = "MARKET_ORDER_SIZE_MAX";//行情最大档位
	
	public static final String DIG_ONOFF = "DIG_ONOFF_%s";//挖矿开关
	public static final String REAL_NAME_ONOFF = "REAL_NAME_ONOFF";//实名认证开关
	public static final String IDCARD_VALIDATE_TIMES_LIMIT = "IDCARD_VALIDATE_TIMES_LIMIT";//身份证验证次数限制
	
	public static final String ORDER_C2C_NOTPAY_INACTIVE_INTERVAL = "ORDER_C2C_NOTPAY_INACTIVE_INTERVAL";//C2C代付款的失效时间(单位：分钟)
	public static final String ORDER_C2C_NOTCONFIRM_INACTIVE_INTERVAL = "ORDER_C2C_NOTCONFIRM_INACTIVE_INTERVAL";//C2C待确认的失效时间(单位：分钟)
	
	public static final String ORDER_C2C_CANCEL_LIMIT_TAKER = "ORDER_C2C_CANCEL_LIMIT_TAKER";//普通用户取消订单次数限制
	public static final String ORDER_C2C_CANCEL_LIMIT_MAKER = "ORDER_C2C_CANCEL_LIMIT_MAKER";//商家取消订单次数限制
	
	/*APP启动参数配置*/
	public static final String APP_CONFIG_VERSION = "APP_CONFIG_VERSION";//版本号
	public static final String APP_CONFIG_CONTACTUS_URL = "APP_CONFIG_CONTACTUS_URL";//在线客服（联系我们）
	public static final String APP_CONFIG_AGREENMENT_URL = "APP_CONFIG_AGREENMENT_URL";//注册协议
	public static final String APP_CONFIG_RATEDETAILS_URL = "APP_CONFIG_RATEDETAILS_URL";//费率
	public static final String APP_CONFIG_HELPDOC_URL = "APP_CONFIG_HELPDOC_URL";//c2c帮助文档
	public static final String APP_CONFIG_EXCHANGERATE_URL = "APP_CONFIG_EXCHANGERATE_URL";//充值提现帮助文档
	public static final String APP_CONFIG_NOTLOGGED_SHARE_URL = "APP_CONFIG_NOTLOGGED_SHARE_URL";//未登录分享地址
	public static final String APP_CONFIG_LOGGED_SHARE_URL = "APP_CONFIG_LOGGED_SHARE_URL";//已登录分享地址
	public static final String APP_CONFIG_INVITE_URL = "APP_CONFIG_INVITE_URL";//要求返佣地址
	public static final String APP_CONFIG_SHARE_TITLE = "APP_CONFIG_SHARE_TITLE";//分享标题
	public static final String APP_CONFIG_SHARE_DES = "APP_CONFIG_SHARE_DES";//分享描述
	public static final String APP_CONFIG_HTTPS_FLAG = "APP_CONFIG_HTTPS_FLAG";//HTTPS开关
	public static final String APP_CONFIG_SPOTCOIN_PAIR = "APP_CONFIG_SPOTCOIN_PAIR";//现货交易币种选择（json串）
	public static final String APP_CONFIG_SPOTQUERY_COINPAIR = "APP_CONFIG_SPOTQUERY_COINPAIR";//现货委托查询币种选择（json串）
	public static final String APP_CONFIG_ORDERCOUNT = "APP_CONFIG_ORDERCOUNT";//现货交易档位（json串）
	public static final String APP_CONFIG_C2CCOIN = "APP_CONFIG_C2CCOIN";//c2c交易币种（json串）
	public static final String APP_CONFIG_RECHANDWITH_COIN = "APP_CONFIG_RECHANDWITH_COIN";//充值期限币种（json串）
	public static final String APP_CONFIG_GUIDES_URL = "APP_CONFIG_GUIDES_URL";//挖矿秘籍url
	public static final String APP_CONFIG_MIMEINFO_URL = "APP_CONFIG_MIMEINFO_URL";//矿区介绍url
	
	public static final String ORDER_C2C_MAKER_MINTOTAL = "ORDER_C2C_MAKER_MINTOTAL";//C2C商家最低交易额
	
	public static final String COMMISSION_LOGIN_ONOFF = "COMMISSION_LOGIN_ONOFF";//登录奖励开关
	public static final String COMMISSION_LOGIN_AMOUNT = "COMMISSION_LOGIN_AMOUNT";//登录奖励金额
	public static final String COMMISSION_REALNAME_ONOFF = "COMMISSION_REALNAME_ONOFF";//实名奖励开关
	public static final String COMMISSION_REALNAME_AMOUNT_USER = "COMMISSION_REALNAME_AMOUNT_USER";//实名奖励金额-用户
	public static final String COMMISSION_REALNAME_AMOUNT_REFER = "COMMISSION_REALNAME_AMOUNT_REFER";//实名奖励金额-推荐人
	
	public static final String ORDER_FORCE_PER = "ORDER_FORCE_PER";//每次交易增加的算力
	public static final String ORDER_FORCE_TOTAL = "ORDER_FORCE_TOTAL";//交易算力上限

	public static final String CALCULATE_FORCE_ONE = "CALCULATE_FORCE_ONE";//每日登录奖励
	public static final String CALCULATE_FORCE_TEN = "CALCULATE_FORCE_TEN";//连续10天登录奖励
	public static final String CALCULATE_FORCE_MONTH = "CALCULATE_FORCE_MONTH";//连续30天登录奖励
	public static final String CALCULATE_FORCE_REGISTER = "CALCULATE_FORCE_REGISTER";//注册用户奖励
	public static final String CALCULATE_FORCE_REALNAME = "CALCULATE_FORCE_REALNAME";//实名奖励
	public static final String CALCULATE_FORCE_INVITE = "CALCULATE_FORCE_INVITE";//邀请实名奖励
	public static final String CALCULATE_FORCE_INSTRUCTION_URL = "CALCULATE_FORCE_INSTRUCTION_URL";//算力说明文档地址
	public static final String CALCULATE_FORCE_BETIN = "CALCULATE_FORCE_BETIN";//世界杯竞猜投注1000dk以上
	
	public static final String WATCH_PUBLICNUMBER = "WATCH_PUBLICNUMBER";//微信公众号增加算力
	public static final String JOIN_QQGROUP = "JOIN_QQGROUP";//加入Q群增加算力
	public static final String JOIN_WECHAT = "JOIN_WECHAT";//加入微信群增加算力
	
	public static final String SHARE_DAY_QQ = "SHARE_DAY_QQ";//每日分享QQ
	public static final String SHARE_DAY_WECHAT = "SHARE_DAY_WECHAT";//每日分享微信
	public static final String SHARE_DAY_QZONE = "SHARE_DAY_QZONE";//每日分享空间
	public static final String SHARE_DAY_CIRCLE = "SHARE_DAY_CIRCLE";//每日分享朋友圈

	public static final String WORLDCUP_SP_RATE = "WORLDCUP_SP_RATE";//世界杯竞猜平台收益率
	public static final String WORLDCUP_SP_MAX = "WORLDCUP_SP_MAX";//世界杯竞猜赔率上限
}
