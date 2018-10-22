package com.yibi.core.constants;


public class SystemParams {
	//记录请求地址开关
	public static final String RECORDREQUEST_ONOFF = "RECORDREQUEST_ONOFF";
	
	//短信开关
	public static final String SMS_ONOFF = "SMS_ONOFF";

	
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
	public static final String TRANSFER_RATE = "TRANSFER_RATE";//转账充值费率


	//短信次数限制
	public static final String SMS_COUNTS_LIMIT = "SMS_COUNTS_LIMIT";
	//短信时间限制   （分钟）
	public static final String SMS_TIME_LIMIT = "SMS_TIME_LIMIT";

	
	
	
	public static final String ORDERPWD_LOCK_INTERVAL = "ORDERPWD_LOCK_INTERVAL";//交易密码锁定时间(分钟)
	public static final String ORDERPWD_ERROR_INTERVAL = "ORDERPWD_ERROR_INTERVAL";//交易密码错误时间(分钟)
	public static final String ORDERPWD_ERROR_TIMES = "ORDERPWD_ERROR_TIMES";//交易密码错误次数

	
	public static final String SYSTEM_URL = "SYSTEM_URL";//系统地址
	
	public static final String MARKET_ORDER_SIZE_MAX = "MARKET_ORDER_SIZE_MAX";//行情最大档位

	public static final String REAL_NAME_ONOFF = "REAL_NAME_ONOFF";//实名认证开关
	public static final String IDCARD_VALIDATE_TIMES_LIMIT = "IDCARD_VALIDATE_TIMES_LIMIT";//身份证验证次数限制
	
	public static final String ORDER_C2C_NOTPAY_INACTIVE_INTERVAL = "ORDER_C2C_NOTPAY_INACTIVE_INTERVAL";//C2C代付款的失效时间(单位：分钟)
	public static final String ORDER_C2C_NOTCONFIRM_INACTIVE_INTERVAL = "ORDER_C2C_NOTCONFIRM_INACTIVE_INTERVAL";//C2C待确认的失效时间(单位：分钟)

	/*APP启动参数配置*/
	public static final String APP_CONFIG_VERSION = "APP_CONFIG_VERSION";//版本号
	public static final String APP_CONFIG_AGREENMENT_URL = "APP_CONFIG_AGREENMENT_URL";//注册协议
	public static final String APP_CONFIG_RATEDETAILS_URL = "APP_CONFIG_RATEDETAILS_URL";//费率
	public static final String APP_CONFIG_RECHARGEDOC_URL = "APP_CONFIG_EXCHANGERATE_URL";//充值帮助文档
	public static final String APP_CONFIG_WITHDRAWDOC_URL = "APP_CONFIG_EXCHANGERATE_URL";//提现帮助文档
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
	public static final String APP_CONFIG_GUIDES_URL = "APP_CONFIG_GUIDES_URL";//挖矿秘籍url
	public static final String APP_CONFIG_MIMEINFO_URL = "APP_CONFIG_MIMEINFO_URL";//矿区介绍url
	public static final String APP_CONFIG_DEALDIGDOC_URL = "APP_CONFIG_DEALDIGDOC_URL";//交易挖矿介绍url
	public static final String APP_CONFIG_REC_PACKET_COIN = "APP_CONFIG_REC_PACKET_COIN";//红包币种（json串）
	public static final String APP_CONFIG_TALK_TRANSFER_COIN = "APP_CONFIG_TALK_TRANSFER_COIN";//聊天转账币种（json串）
	public static final String APP_CONFIG_YUBIBAO_COIN = "APP_CONFIG_YUBIBAO_COIN";//余币宝币种（json串）
	public static final String APP_CONFIG_YUBIBAO_HELP_DOC = "APP_CONFIG_YUBIBAO_HELP_DOC";//余币宝帮助

	public static final String ORDER_C2C_MAKER_MINTOTAL = "ORDER_C2C_MAKER_MINTOTAL";//C2C商家最低交易额

	public static final String COMMISSION_REALNAME_ONOFF = "COMMISSION_REALNAME_ONOFF";//实名奖励开关
	public static final String COMMISSION_REALNAME_AMOUNT_USER = "COMMISSION_REALNAME_AMOUNT_USER";//实名奖励金额-用户
	public static final String COMMISSION_REALNAME_AMOUNT_REFER = "COMMISSION_REALNAME_AMOUNT_REFER";//实名奖励金额-推荐人
	public static final String COMMISSION_REALNAME_COIN = "COMMISSION_REALNAME_COIN";//实名奖励币种 josn
	public static final String COMMISSION_REALNAME_COIN_AMOUNT_USER = "COMMISSION_REALNAME_COIN_AMOUNT_USER";//实名奖励币种对应奖励数量 json
	public static final String COMMISSION_REALNAME_COIN_AMOUNT_REFER = "COMMISSION_REALNAME_COIN_AMOUNT_REFER";//实名奖励币种对应奖励数量 json

	public static final String CALCULATE_FORCE_ONE = "CALCULATE_FORCE_ONE";//每日登录奖励 -- 魂力
	public static final String CALCULATE_FORCE_TEN = "CALCULATE_FORCE_TEN";//连续10天登录奖励 -- 魂力
	public static final String CALCULATE_FORCE_MONTH = "CALCULATE_FORCE_MONTH";//连续30天登录奖励 -- 魂力
	public static final String CALCULATE_FORCE_REGISTER = "CALCULATE_FORCE_REGISTER";//注册用户奖励  -- 魂力
	public static final String CALCULATE_FORCE_REALNAME = "CALCULATE_FORCE_REALNAME";//实名奖励 -- 魂力
	public static final String CALCULATE_FORCE_INVITE = "CALCULATE_FORCE_INVITE";//邀请实名奖励 -- 魂力
	public static final String CALCULATE_FORCE_INSTRUCTION_URL = "CALCULATE_FORCE_INSTRUCTION_URL";//算力说明文档地址
	
	public static final String WATCH_PUBLICNUMBER = "WATCH_PUBLICNUMBER";//微信公众号增加算力
	public static final String JOIN_QQGROUP = "JOIN_QQGROUP";//加入Q群增加算力
	public static final String JOIN_WECHAT = "JOIN_WECHAT";//加入微信群增加算力
	
	public static final String SHARE_DAY_QQ = "SHARE_DAY_QQ";//每日分享QQ
	public static final String SHARE_DAY_WECHAT = "SHARE_DAY_WECHAT";//每日分享微信
	public static final String SHARE_DAY_QZONE = "SHARE_DAY_QZONE";//每日分享空间
	public static final String SHARE_DAY_CIRCLE = "SHARE_DAY_CIRCLE";//每日分享朋友圈
	
	public static final String APP_DOWNLAOD_URL = "APP_DOWNLAOD_URL";//app下载地址
	public static final String APP_SHARE_URL = "APP_SHARE_URL";//app分享地址
	
	public static final String GET_ADDRESS_DK = "GET_ADDRESS_DK";//生成DK钱包地址
	public static final String TRANSFER_ADDRESS_DK = "TRANSFER_ADDRESS_DK";//DK转账
	
	public static final String TALK_RED_PACKET_ONOFF = "TALK_RED_PACKET_ONOFF";//红包开关

	public static final String DEFAULT_HEAD_IMG_URL = "DEFAULT_HEAD_IMG_URL";//默认头像地址
	
	public static final String WORLDCUP_URL = "WORLDCUP_URL";//世界杯竞猜URL

	public static final String WEBSITE_URL = "WEBSITE_URL";//官网URL

	public static final String CHAIN_COIN = "CHAIN_COIN";//充值轮询钱包币种编码
	public static final String TRANS_TO_MAIN_COIN = "TRANS_TO_MAIN_COIN";//需把余额转账至主账户的币种对
	public static final String NET_INDEX_URL = "NET_INDEX_URL";//官网

	public static final String YIBI_SPIRIT_ID = "YIBI_SPIRIT_ID";//一币精灵ID

	public static final String SYSTEM_PUSH_DIG = "SYSTEM_PUSH_DIG";//收矿提醒
	public static final String SYSTEM_PUSH_MISSION = "SYSTEM_PUSH_MISSION";//任务提醒

	public static final String ORDER_C2C_CANCEL_LIMIT_TAKER = "ORDER_C2C_CANCEL_LIMIT_TAKER";//普通用户取消订单次数限制
	public static final String ORDER_C2C_CANCEL_LIMIT_MAKER = "ORDER_C2C_CANCEL_LIMIT_MAKER";//商家取消订单次数限制
	public static final String NOTSIGN_CALCUL_FORCE_RATE = "NOTSIGN_CALCUL_FORCE_RATE";//未连续签到，减去算力/天数 的比例

	public static final String ORDER_FORCE_PER = "ORDER_FORCE_PER";//每次交易增加的算力
	public static final String ORDER_FORCE_TOTAL = "ORDER_FORCE_TOTAL";//交易算力上限
	public static final String ORDER_FORCE_MINUS = "ORDER_FORCE_MINUS";//每次减少的交易算力

	public static final String ACTIVITY_URL = "ACTIVITY_URL";//活动URL

	public static final String RECHARGE_ONOFF_TOTAL = "RECHARGE_ONOFF_TOTAL";//钱包充值开关

	public static final String RECHARGE_COINTYPES = "RECHARGE_COINTYPES";//后台可以充值的币种

	public static final String ORDER_PASSWORD_DEFAULT="ORDER_PASSWORD_DEFAULT";

	public static final String ROBOT_USDT_PRICE="ROBOT_USDT_PRICE";

	public static final String USER_DEFAULT_PASSWORD="USER_DEFAULT_PASSWORD";

	public static final String GROUP_TEMPLATE_NAME="GROUP_TEMPLATE_NAME";

	public static final String GROUP_TEMPLATE_DECRIPTION="GROUP_TEMPLATE_DECRIPTION";

	public static final String GROUP_TEMPLATE_IMGURL="GROUP_TEMPLATE_IMGURL";



}
