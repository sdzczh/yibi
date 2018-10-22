package com.yibi.batch.util;

/**
 * @Author:lqh
 * @Description:
 * @Date:10:31 2018/1/11
 * @Modified by:
 */
public class Res {

    public int code;

    public Object result;

    public String msg;

    final public static int CODE_100=100;//创建地址成功
    final public static int CODE_101=101;//查询余额成功
    final public static int CODE_102=102;//转账成功

    final public static int CODE_200=200;//创建地址失败
    final public static int CODE_201=201;//金额不足
    final public static int CODE_202=202;//转账失败
    final public static int CODE_203=203;//账户解锁失败，无法转账

    public int getCode() {
        return code;
    }

    public Res setCode(int code) {
        this.code = code;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public Res setResult(Object result) {
        this.result = result;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Res setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
