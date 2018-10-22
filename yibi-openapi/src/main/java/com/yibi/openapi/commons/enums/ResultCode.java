package com.yibi.openapi.commons.enums;


import com.yibi.openapi.commons.utils.Result;

public enum ResultCode {
	/* 成功状态码 */
    SUCCESS(10000, "成功"),

    /* 参数错误：10001-19999 */
    PARAM_IS_INVALID(10001, "参数无效"),
    PARAM_IS_BLANK(10002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(10003, "参数类型错误"),

    /* 用户错误：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    USER_LOGIN_ERROR(20002, "用户名或密码错误"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被冻结"),
    USER_NOT_EXIST(20004, "用户不存在"),
    USER_HAS_EXISTED(20005, "用户已存在"),
    USER_ACCOUNT_LOGOFF(20006, "账号已被注销"),
    USER_NOT_REALNAME(20007, "用户未实名"),

    /* 业务错误：30001-39999 */
    SMS_INTERFACE_ERROR(30001, "短信接口异常"),
    SMS_FREQUENT_SEND(30002, "手机号频繁限制"),
    SMS_CHECK_ERROR(30003, "验证码错误"),
    SMS_TIME_LIMIT_ERROR(30004, "验证码已过期"),
    SMS_COUNTS_LIMIT_ERROR(30005, "验证码使用超出次数限制"),
    REFER_USER_NOT_EXIST(30006, "推荐人账号不存在"),
    REFER_USER_ACCOUNT_FORBIDDEN(30007, "推荐人账号已被冻结"),
    REFER_USER_ACCOUNT_LOGOFF(30008, "推荐人账号已被注销"),
    ORDERNUM_HAS_EXISTED(30009, "订单号已存在"),
    CARDID_NOT_EXISTED(30010, "银行卡不存在"),
    CARD_DELETE_FAIL(30011, "银行卡删除失败"),
    AMOUNT_ERROR(30012, "金额输入不正确"),
    ORDERPWD_IN_LOCK(30013, "交易密码错误次数超限，%s分钟后再试。若忘记密码，请联系客服进行重置处理!"),
    ORDERPWD_ERROR(30014, "交易密码错误，您还有%s次机会"),
    AMOUNT_NOT_ENOUGH(30015, "余额不足"),
    TO_USER_NOT_EXIST(30016, "对方用户不存在"),
    TO_USER_FORBIDDEN(30017, "对方账号已被冻结"),
    TO_USER_LOGOFF(30018, "对方账号已被注销"),
    TO_USER_NOT_REALNAME(30019, "对方用户未实名"),
    WITHDRAW_COUNT_LIMINT(30020, "日提现次数超出限制"),
    WITHDRAW_SUM_LIMIT(30021, "日提现金额超出限制"),
    TO_USER_NOT_ONESELF(30022, "不能给自己转账"),
    TO_USER_ACCOUNT_NOT_EXIST(30023, "对方钱包信息不存在"),
    OLD_PASSWORD_ERROR(30024, "旧密码错误"),
    ORDERPWD_NOT_EXISITED(30025, "未设置交易密码"),
    FILE_TYPE_ERROR(30026, "文件类型错误"),
    FILE_UPLOAD_ERROR(30027, "文件上传失败"),
    ORDER_STATE_INACTIVE(30028, "订单状态不合法"),
    ALIPAY_HAS_EXIST(30029,"支付宝账号已绑定"),
    ORDERPWD_HAS_EXIST(30030, "交易密码已设置"),
    FEE_USER_NOT_EXIST(30031, "平台用户不存在"),
    DIG_RECHARGE_ACTIVE(30032, "挖火蚁正在有效期中，无需充值"),
    COUNT_BEYOND_LIMIT(30033, "档位超出最大限制"),
    DIG_RECHARGE_INACTIVE(30034, "挖火蚁服务已失效"),
    REAL_NAME_FINISHED(30035, "用户已完成实名认证"),
    REAL_NAME_INIT_FAIL(30036, "实名认证初始化失败"),
    REAL_NAME_TASK_NOT_EXIST(30037, "未进行实人认证"),
    REAL_NAME_ING(30038, "认证中"),
    REAL_NAME_FAIL(30039, "实名认证失败"),
    REAL_NAME_AGE_ILLEGAL(30040, "实名认年龄不合法"),
    REAL_NAME_IDCARD_EXIST(30041, "身份证号已经在其他账号认证"),
    REAL_NAME_LIMIT(30042, "实名认证限制中"),
    REGISTER_ERROR(30043, "注册失败"),
    SMS_ERROR(30044, "验证码获取失败"),
    PAY_ALIPAY_NOT_BIND(30045, "未绑定支付宝"),
    PAY_WECHANT_NOT_BIND(30046, "未绑定微信"),
    PAY_BANK_NOT_BIND(30047, "未绑定银行卡"),
    ORDER_STATE_HAS_DEAL(30048, "订单已成交"),
    ORDER_STATE_HAS_STOP_RECEIPT(30049, "商户已停止接单"),
    AMOUNT_MAKER_NOT_ENOUGH(30050, "商家可交易数量不足"),
    ORDER_USER_ILLEGAL(30051, "自己无法跟自己交易"),
    ORDER_NO_MATCH(30053, "不存在匹配交易"),
    PAY_INFO_NOT_BIND(30054, "请绑定支付信息"),
    OPERATOR_NOT_LIMIT(30055, "操作无权限"),
    WECHANT_HAS_EXIST(30056, "微信已绑定"),
    BANK_HAS_EXIST(30057, "银行卡已绑定"),
    ORDER_C2C_CANCEL_LIMIT(30058, "今日买入取消次数已达上限"),
    ORDER_C2C_TOTAL_ERROR(30059, "成交额不在商家要求范围内"),
    ORDER_UNFINISHED_EXIST(30060, "存在未完成的订单"),
    WITHDRAW_AMOUNT_MIN_LIMIT(30061, "提现最小金额为%s"),
    RECHARGE_AMOUNT_MIN_LIMIT(30062, "充值最小金额为%s"),
    ORDER_C2C_TOTAL_MIN_LIMIT(30063, "最低交易额为%s"),
    CALCULATE_FORCE_REPEAT(30064, "奖励领取过了"),
    CALCULATE_FORCE_OVERTIME(30065, "奖励已经过期"),
    WITHDRAW_DIG_AMOUNT_MIN_LIMIT(30066, "提取最小金额为%s"),
    ORDER_C2C_BUY_LIMIT(30067, "买入订单数量已到上限"),
    ORDER_C2C_SALE_LIMIT(30068, "卖出订单数量已到上限"),
    ORDER_C2C_MARKER_BUY_LIMIT(30069, "您已有一笔买入委托，请在未成交订单中撤消后继续发布"),
    ORDER_C2C_MARKER_SALE_LIMIT(30070, "您已有一笔卖出委托，请在未成交订单中撤消后继续发布"),
    DAY_TASK_SUCCESS(30071, "每日奖励领取成功"),
    DAY_TASK_FALSE(30072, "这个任务今天已经完成啦，请明天再来"),
    AMOUNT_NEGATIVE(30073, "数量不能为负数"),

    /* 系统错误：40001-49999 */
    SYSTEM_INNER_ERROR(40001, "系统异常"),
    SYSTEM_PARAM_ERROR(40002, "系统参数未配置"),

    /* 数据错误：50001-599999 */
    RESULE_DATA_NONE(50001, "数据未找到"),
    DATA_ALREADY_EXISTED(50002, "数据已存在"),
    UPDATE_DATA_NOT_EXIST(50003, "数据无更新"),

    /* 接口错误：60001-69999 */
    INTERFACE_SIGN_ERROR(60001,"接口签名认证错误"),
    INTERFACE_DECRYPT_ERROR(60002,"接口解密失败"),
    INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(60006, "接口负载过高"),
    OKCOIN_INTERFACE_ERROR(61001, "OKCoin接口返回出错"),

    /* 权限错误：70001-79999 */
    PERMISSION_NO_ACCESS(70001, "功能已关闭"),
    PERMISSION_REGISTER_NO_ACCESS(70002, "注册功能已关闭");
    
    private Integer code;

    private String msg;
    
    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.msg;
    }
    
    public void setMessage(String msg){
    	this.msg = msg;
    }
    
    public static String getMessage(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.msg;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }
    
    public static void main(String[] args) {
		System.out.println(Result.toResultFormatNotEncrypt(ResultCode.ORDERPWD_IN_LOCK,"4"));
		System.out.println(Result.toResultFormatNotEncrypt(ResultCode.ORDERPWD_ERROR,"2"));
		System.out.println(Result.toResultFormatNotEncrypt(ResultCode.ORDERPWD_ERROR,"1"));
		System.out.println(ResultCode.PARAM_IS_BLANK.msg);
		System.out.println(ResultCode.getMessage("PARAM_IS_BLANK"));
		System.out.println(ResultCode.getCode("PARAM_IS_BLANK"));
	}

}
