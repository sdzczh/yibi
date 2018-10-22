package com.yibi.turnapi.commons.enums;

public enum CalculForceType {
    SIGN(0,"签到"),
    REGISTER(1,"注册"),
    REALNAME(2,"实名认证"),
    INVITE(3,"推荐一人注册并实名认证"),
    NOT_SIGN(4,"签到中断一日"),
    WATCH_PUBLICNUMBER(5,"关注微信公众号"),
    JOIN_QQGROUP(6,"加入QQ群"),
    JOIN_WECHAT(7,"加入微信群"),
    SHARE_DAY_QQ(8,"每日分享QQ"),
    SHARE_DAY_WECHAT(9,"每日分享微信"),
    SHARE_DAY_QZONE(10,"每日分享Q空间"),
    SHARE_DAY_CIRCLE(11,"每日分享朋友圈"),
    ORDER(12,"交易"),
    NOT_ORDER(13,"交易中断一日"),
	UPDATE_DIGCUL(14,"手动修改"),
    ORDER_OVER(15,"交易增加魂力超出限制"),
    WORLDCUP_GUESS_EVERY(16,"参加世界杯竞猜每次奖励"),
    WORLDCUP_GUESS_ONCE(17,"投注世界杯竞猜一次奖励"),
    FORTUNEWHEEL(18,"幸运转盘奖励");
    
    //算力类型代码
    private Integer code;
    //算力类型名称
    private String name;
    
    CalculForceType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
    
    public static String getName(Integer code) {
        for (CalculForceType item : CalculForceType.values()) {
            if (item.getCode().equals(code)) {
                return item.name;
            }
        }
        return null;
    }
    


}
