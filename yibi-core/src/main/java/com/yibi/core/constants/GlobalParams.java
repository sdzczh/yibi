package com.yibi.core.constants;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class GlobalParams {

    public static final int INACTIVE = 0;
    public static final int ACTIVE = 1;
    public static final int FORBIDDEN = 2;
    public static final int LOGOFF = 3;

    public static final int ACCOUNT_TYPE_C2C = 0;//C2C账户
    public static final int ACCOUNT_TYPE_SPOT = 1;//现货账户
    public static final int ACCOUNT_TYPE_DIG = 2;//挖矿账户
    public static final int ACCOUNT_TYPE_LEVERAGE = 3;//杠杆账户
    public static final int ACCOUNT_TYPE_YUBI = 4;//余币宝账户
    public static final int ACCOUNT_TYPE_GAME=5;//dkbaby游戏账户

    public static final int ROLE_TYPE_COMMON = 0;//普通用户
    public static final int ROLE_TYPE_PLATFORM = 1;//平台用户
    public static final int ROLE_TYPE_YIBIELVE = 2;//一笔精灵
    public static final int ROLE_TYPE_ROBOT = 3;//机器人任务执行用户

    public static final int ROLE_TYPE_PARTNER = 1;//合伙人
    public static final int ROLE_TYPE_COMMONUSER = 2;//普通用户

    public static final int PAY_COMMON = 0;//提现到pc钱包
    public static final int PAY_SPOT = 1;//提现到现货账户

    public static final int ORDER_STATE_UNTREATED = 0;//正在处理
    public static final int ORDER_STATE_TREATED = 1;//已成功
    public static final int ORDER_STATE_BACK = 2;//已撤销
    public static final int ORDER_STATE_FAIL = 3;//已失败
    public static  final int PAY_ALIPAY=0;//支付宝
    public static  final int PAY_WECHANT=1;//微信
    public static  final int PAY_BANK=2;//银行卡
    public static final int TRANSFER_TYPE_IN = 0;//转入
    public static final int TRANSFER_TYPE_OUT = 1;//转出

    public static final String PAY_ALIPAY_ACCOUNT = "fai@huoli.pro";
    public static final String PAY_WECHANT_ACCOUNT = "";
    public static final String PAY_BANK_ACCOUNT_NUM = " ";
    public static final String PAY_BANK_NAME = " ";
    public static final String PAY_BANK_ACCOUNT_NAME = " ";

    public static final int NOTICE_NOTICE = 0;//公告
    public static final int NOTICE_ABOUT = 1;//关于
    public static final int NOTICE_HELP = 2;//帮助
    public static final int NOTICE_RATE = 3;//费率
    public static final int NOTICE_ALGORITHM = 4;//算法
    public static final int NOTICE_REGISTER_AGREEMENT = 5;//注册协议


    public static final int ORDER_TYPE_BUY = 0;//买入
    public static final int ORDER_TYPE_SALE = 1;//卖出
    public static final int ORDER_TYPE_ALL = -1;//全部
    public static final int ORDER_ORDERTYPE_MARKET = 0;//市价
    public static final int ORDER_ORDERTYPE_LIMIT = 1;//限价

    public static final int COMMISSION_TYPE_PERFORM = 0;//平台手续费
    public static final int COMMISSION_TYPE_REFER = 1;//推荐人手续费


    public static final int REALNAME_STATE_NOT_EXIST = -1;//未进行实名认证
    public static final int REALNAME_STATE_ING = 0;//实名认证进行中
    public static final int REALNAME_STATE_SUCCESS = 1;//实名认证成功
    public static final int REALNAME_STATE_FAIL = 2;//实名认证失败
    public static final int REALNAME_STATE_AGE_ILLEGAL = 3;//年龄不合法
    public static final int REALNAME_STATE_IDCARD_EXIST = 4;//身份证号已存在

    public static final int ACCOUNT_TRANSFER_TYPE_C2CTOSPOT = 1;//C2C到现货
    public static final int ACCOUNT_TRANSFER_TYPE_SPOTTOC2C = 2;//现货到C2C

    public static final int C2C_USER_TAKER = 0;//普通角色
    public static final int C2C_USER_MAKER = 1;//商家角色

    /*C2C交易状态  0代付款 1待确认 2冻结 3已完成 4已取消 5超时取消*/
    public static final int C2C_ORDER_STATE_PENDINGPAY = 0;//代付款
    public static final int C2C_ORDER_STATE_PENDINGRECEIPT = 1;//代收款
    public static final int C2C_ORDER_STATE_FROZEN = 2;//冻结
    public static final int C2C_ORDER_STATE_FINISHED = 3;//完成
    public static final int C2C_ORDER_STATE_CANCEL = 4;//取消
    public static final int C2C_ORDER_STATE_OVERTIME = 5;//超时取消

    public static final int COMMISSION_TYPE_LOGIN = 0;//登录奖励
    public static final int COMMISSION_TYPE_REALNAME = 1;//实名奖励

    public static final int BUY_COINTYPE = 2;//认购的币种类型
    public static final int EARN_COINTYPE = 8;//认购返回用户钱包的币种类型DK
    public static final int REFER_EARN_COINTYPE = 2;//认购返回推荐人用户钱包的币种类型USDT 10%;
    public static final int USER_RECHARGE = 2;//认购收益的账户角色


    public static final int ON = 1;//开关开启
    public static final int OFF = 0;//开关关闭

    public static final int DIGCAL_REACORD_DEFAULT = 1;//算力流水默认查询
    public static final int DIGCAL_REACORD_DAY = 1;//算力流水每日查询


    public static final int SEX_MALE = 0;//男
    public static final int SEX_FAMALE = 1;//女

    public static final int APPLY_STATE_WAITINT_PROCESS = 0;//等待处理
    public static final int APPLY_STATE_SUCCESS = 1;//已成功
    public static final int APPLY_STATE_FAIL = 2;//已拒绝


    public static final int BIND_ACCOUNT_RONGCLOUD = 0;//融云

    public static final int GROUP_TYPE_ROOM = 0;//聊天室
    public static final int GROUP_TYPE_GROUP = 1;//群组
    public static final int GROUP_MAX_NUMBER = 2950;

    public static final int GROUP_ROLE_OWNER = 0;//群主
    public static final int GROUP_ROLE_MANAGER = 1;//管理员
    public static final int GROUP_ROLE_MEMBER = 2;//成员
   // public static final String GROUP_TEMPLATE_NAME = "一币生态社区群";
    public static final String GROUP_TEMPLATE_ID = "yibiCommunity";
    public static final String GROUP_OWNER_PHONE = "17616553029";
    public static final int GROUP_USER_STATE_MUE = 2;//禁言

    public static final int RED_PACKET_SINGLE = 0;//单人红包
    public static final int RED_PACKET_GROUP = 1;//群组红包

    public static final int SYSTEM_OPERID = -1;

    public static  final  int USER_STATE_VALID = 1; //用户正常
    public static  final  int USER_STATE_FREEZING = 2;//用户冻结
    public static  final  int USER_STATE_CANCEL = 3;//用户注销

    public static  final  int WITHDRAW_STATE_FINISH = 1; //提现完成
    public static  final  int WITHDRAW_STATE_CANCEL = 2;//提现撤销



    public static final String FILE_UPLOAD_PATH="http://img.yb.link/upload.php";//文件上传地址

    public static final String FILE_URL="http://img.yb.link/";//文件服务器域名

    public static final int SYSTEM_PARAMS_VERSION = 3;/*启动参数版本号*/

    public static  final int RED_PACKET_STATE_TRAININE=0;//未领取
    public static  final int RED_PACKET_STATE_FINISHED=1;//已领取
    public static  final int RED_PACKET_STATE_OUTTIME=2;//已超时
    public static final String PARAM="params";
    public static  final String SIGN="sign";
    public static final String SECRET = "g33uFoxOkWbTDsrp";


    public static final int ORDER_TYPE_SPOT = 0; //现货交易挂单
    public static final int ORDER_TYPE_LEVER = 1;//杠杆交易挂单
}
