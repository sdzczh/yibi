package com.yibi.turnapi.commons.variables;

/**
 * @描述
 * @author administrator
 * @版本 V1.0.0
 * @日期 2017-6-6
 */
public class GlobalParams{

	/**
	 * 字符编码
	 */
	public static final String UTF8 = "UTF-8";

	/**
	 * 返回类型
	 */
	public static final String APPLICATION_JSON = "application/json";

	/**
	 * 返回格式
	 */
	public static final String CONTENT_TYPE_TEXT_JSON = "text/json";

	/**
	 * 向量
	 */
	public static final String PWD_KEY = "com.!@#ant#@!";

	/**
	 * DES
	 */
	public static final String DES="DES";

	/**
	 * 短信验证码获取地址
	 */
	public static final String SMS_VALIDATE_SERVER_URL="https://api.netease.im/sms/sendcode.action";

	/**
	 * 短信通知获取地址
	 */
	public static final String SMS_NOTICE_SERVER_URL="https://api.netease.im/sms/sendtemplate.action";

	/**
	 * 随机数
	 */
	public static final String[] ROUND_NUM={"1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

	/*启动参数版本号*/
	public static final int SYSTEM_PARAMS_VERSION = 3;

	//融云appkey
	public static final String RONG_CLOUD_APP_KEY="";
	//融云secret
	public static final String RONG_CLOUD_SECRET="";
	//文件上传地址
	public static final String FILE_UPLOAD_PATH="http://img.huolicoin.com/upload.php";
	//文件服务器域名
	public static final String FILE_URL="http://img.huolicoin.com/";
	//公告详情地址
	public static final String NOTICE_INFO_PATH="/notice/notice/%s.action";
	//火粒总量
	public static final int AMOUNT_HLC = 9100000;
	//火蚁总量
	public static final int AMOUNT_HYC = 36000000;
	//最小充值金额
	public static final double RECHARGE_MIN_AMOUNT = 0;
	//最小提现金额
	public static final double WITHDRAW_MIN_AMOUNT = 0;





	public static  final String TOKEN="token";
	public static  final String PARAM="params";
	public static  final String SIGN="sign";
	public static  final String SRCRETKEY = "key";
	public static  final String CURRENT_USER="user";

	public static final int TIMEOUTINTERVAL = 10000000;

	public static final String 	RSA_PRIVATE_KEY="MIIDlwIBADANBgkqhkiG9w0BAQEFAASCA4EwggN9AgEAAoHBALAP2hFONlnert+Q/Yjhya8UKJnpKw2uSrXJNeLov8BdznIWV6sc8vGtmwtr8wE1NEY473Va+hkElTO5CR4Wd5Va+X/vnHNpQRaYIGiHeSBELLtCfJSEh7Ljpx5FuU+Nd9XhMPUEgMgJxtIiJ8/bjxJOM9UFZKCOjcevDwhVaJUGG6I1IjnHSiWAOlMXFNGeqZUKaEwIk040oPI7oVTTXN1lpCKwLY7yyoVUrsrpUPYQHKXmvto0QjeEwv1BITumCQIDAQABAoHBAJuhp5ZcmNbgbZzawgNZ87p8C1fiMViqlQgUkTm/nKEjSva2oVB1CpxwZfm2ttx+d3MLqUEH5sRutKVAD7s1CQhRnvNkj9vFUu/yEdPUzVGkchYmM5HlhT6w1m2AHMshtSs3G289Z63QxAOVKf0jMwCrZLXdg6BvLzg2qrmMPHDuR4UgB1Xq++UEIaHcKc5y8/tKIxgiVnWWK4vUTzlUYCoufnV0knNzwWqyJxSSjEcUEfpljw9IGOJMFoWprPdh8QJhAP5NPK4agnw/8uFKvxJHGLHouFpcW1SYs6CiAD71VIC/eHWT4bBsR+5cTxvhbrSgDMApSITsPGwZAMF3y4QsYDRu+Qg9dvC6DUCBwipGh78ThA11MoN6or6FALZ8bekFFwJhALE82nW3UkKPQnLuSuAzpqHgAWvopTCD05RrziUZoZZXQDEjpSglP4z99PtMSw2c+lhFy6XtPUVtDbgcJe7BkJ0XXa7geyf+2NCSSxxcJjxgViQRR2GihRJr6gENwtHh3wJgJ2R/idjBbc3aKdwSTRqGUMjkBdtYqnodGTz/HMGUHX2Lg2stAs9DNUFHX+JD6+b/EqVSfoS5t2UIIPAVrpvRK04ldYlP62pmsx9mnHXggVDcm9kTwho1K54exiXwQLBxAmEAhbdHijA4m4bBtzwYuXW+bbo22Fa46hVK91suN8dSpb5F68zcuaEAhTrELSOuLCMWrKpbrl+CLnLMOu7hn3PhyuLqBxnNUKtwoEr+5/KN1ldIJgXzOLIIe5F3Bjx/kyE5AmBnooY+qbVIVx1IvuaxEn4FRAyRf3lkostgzjS1rVBaa9OKuhpFdcQBw6Ciih1IrzufLy5VMpsO4Kvc+W6hlWqihquqG+YSi1RCa+qcOwmeRFHzUsjEBxzBiJCD0CWNYDg=";

	public static  final int INACTIVE=0;
	public static  final int ACTIVE=1;
	public static  final int FORBIDDEN=2;
	public static  final int LOGOFF=3;
	public static  final int ACCOUNT_TYPE_C2C=0;//C2C账户
	public static  final int ACCOUNT_TYPE_SPOT=1;//现货账户
	public static  final int ACCOUNT_TYPE_DIG=2;//挖矿账户
	public static  final int ACCOUNT_TYPE_LEVERAGE=3;//杠杆账户

	public static  final int ROLE_TYPE_COMMON=0;//普通用户
	public static  final int ROLE_TYPE_MANAGE=1;//管理用户
	public static  final int ROLE_TYPE_RECHARGE_REWARD=2;//充值收益账户
	public static  final int ROLE_TYPE_WITHWARD_REWARD=3;//提现收益账户
	public static  final int ROLE_TYPE_DIG_REWARD=4;//挖矿收益账户
	public static  final int ROLE_TYPE_ORDER_REWARD=5;//交易收益账户
	public static  final int ROLE_TYPE_TRANSFER_REWARD=6;//转账收益账户

	public static  final int TABLE_BASE=10;


	public static  final int PAY_ALIPAY=0;//支付宝
	public static  final int PAY_WECHANT=1;//微信
	public static  final int PAY_BANK=2;//银行卡
	public static  final int PAY_COMMON=3;//普通方式
	public static  final int PAY_SPOT=4;//提现到现货账户

	public static  final int ORDER_STATE_UNTREATED=0;//正在处理
	public static  final int ORDER_STATE_TREATED=1;//已成功
	public static  final int ORDER_STATE_BACK=2;//已撤销
	public static  final int ORDER_STATE_FAIL=3;//已失败

	public static  final int TRANSFER_TYPE_OUT=0;//转出
	public static  final int TRANSFER_TYPE_IN=1;//转入

	public static final String 	PAY_ALIPAY_ACCOUNT="fai@huoli.pro";
	public static final String 	PAY_WECHANT_ACCOUNT="";
	public static final String 	PAY_BANK_ACCOUNT_NUM=" ";
	public static final String 	PAY_BANK_NAME=" ";
	public static final String 	PAY_BANK_ACCOUNT_NAME=" ";

	public static  final int NOTICE_NOTICE=0;//公告
	public static  final int NOTICE_ABOUT=1;//关于
	public static  final int NOTICE_HELP=2;//帮助
	public static  final int NOTICE_RATE=3;//费率
	public static  final int NOTICE_ALGORITHM=4;//算法
	public static  final int NOTICE_REGISTER_AGREEMENT=5;//注册协议


	public static  final int ORDER_TYPE_BUY=0;//买入
	public static  final int ORDER_TYPE_SALE=1;//卖出
	public static  final int ORDER_TYPE_ALL=-1;//全部
	public static  final int ORDER_ORDERTYPE_MARKET=0;//限价
	public static  final int ORDER_ORDERTYPE_LIMIT=1;//市价

	public static  final int COMMISSION_TYPE_PERFORM=0;//平台手续费
	public static  final int COMMISSION_TYPE_REFER=1;//推荐人手续费


	public static  final int REALNAME_STATE_NOT_EXIST=-1;//未进行实名认证
	public static  final int REALNAME_STATE_ING=0;//实名认证进行中
	public static  final int REALNAME_STATE_SUCCESS=1;//实名认证成功
	public static  final int REALNAME_STATE_FAIL=2;//实名认证失败
	public static  final int REALNAME_STATE_AGE_ILLEGAL=3;//年龄不合法
	public static  final int REALNAME_STATE_IDCARD_EXIST=4;//身份证号已存在

	public static  final int ACCOUNT_TRANSFER_TYPE_C2CTOSPOT=1;//C2C到现货
	public static  final int ACCOUNT_TRANSFER_TYPE_SPOTTOC2C=2;//现货到C2C

	public static  final int C2C_USER_TAKER=0;//普通角色
	public static  final int C2C_USER_MAKER=1;//商家角色

	/*C2C交易状态  0代付款 1待确认 2冻结 3已完成 4已取消 5超时取消*/
	public static  final int C2C_ORDER_STATE_PENDINGPAY=0;//代付款
	public static  final int C2C_ORDER_STATE_PENDINGRECEIPT=1;//代收款
	public static  final int C2C_ORDER_STATE_FROZEN=2;//冻结
	public static  final int C2C_ORDER_STATE_FINISHED=3;//完成
	public static  final int C2C_ORDER_STATE_CANCEL=4;//取消
	public static  final int C2C_ORDER_STATE_OVERTIME=5;//超时取消

	public static  final int COMMISSION_TYPE_LOGIN=0;//登录奖励
	public static  final int COMMISSION_TYPE_REALNAME=1;//实名奖励

	public static final int BUY_COINTYPE=2;//认购的币种类型
	public static final int EARN_COINTYPE=8;//认购返回用户钱包的币种类型DK
	public static final int REFER_EARN_COINTYPE=2;//认购返回推荐人用户钱包的币种类型USDT 10%;
	public static final int USER_RECHARGE=2;//认购收益的账户角色

	public static final int COMPUTER=3;//用户登录的设备

	public static final String FIRST_START_TIME="2018-04-08 12:00:00";
	public static final String FIRST_END_TIME="2018-04-12 12:00:00";
	public static final String SECOND_START_TIME="2018-04-12 12:00:00";
	public static final String SECOND_END_TIME="2018-04-14 12:00:00";
	public static final String THIRD_START_TIME="2018-04-14 12:00:00";
	public static final String THIRD_END_TIME="2018-04-16 12:00:00";

	public static final String FIRST_AMOUNT="coinorder:usdtAmount";
	public static final String SECOND_AMOUNT="coinorder:usdtAmount2";
	public static final String THIRD_AMOUNT="coinorder:usdtAmount3";

	public static final int F=100000;//第一期数量
	public static final int S=120000;//第二期数量
	public static final int T=140000;//第三期数量

	public static final int ON = 1;//开关开启
	public static final int OFF = 0;//开关关闭

	public static final int DIGCAL_REACORD_DEFAULT = 1;//算力流水默认查询
	public static final int DIGCAL_REACORD_DAY = 1;//算力流水每日查询


	public static final int SEX_MALE = 0;//男
	public static final int SEX_FAMALE = 1;//女

	public static  final int APPLY_STATE_WAITINT_PROCESS=0;//等待处理
	public static  final int APPLY_STATE_SUCCESS=1;//已成功
	public static  final int APPLY_STATE_FAIL=2;//已拒绝


	public static  final int BIND_ACCOUNT_RONGCLOUD=0;//融云

	public static final int GROUP_TYPE_ROOM = 0;//聊天室
	public static final int GROUP_TYPE_GROUP = 1;//群组

	public static final int GROUP_ROLE_OWNER = 0;//群主
	public static final int GROUP_ROLE_MANAGER = 1;//管理员
	public static final int GROUP_ROLE_MEMBER = 2;//成员

	public static final int RED_PACKET_SINGLE = 0;//单人红包
	public static final int RED_PACKET_GROUP = 1;//群组红包

	//杠杆安全风险率
	public static final double RISK_SAFE = 1.5;
	//杠杆警告风险率
	public static final double RISK_WORN = 1.3;
	//杠杆爆仓风险率
	public static final double RISK_BLAST = 1.1;

	//竞猜结算key
	public static final String GUESSINGKEY = "7UoIJve4cGPBG9eQIMSmqSRDWsJY33V5";





}