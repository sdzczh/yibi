package com.yibi.websocket.model;

public enum ResultCode {
	/* 成功状态码 */
    SUCCESS(1, "成功"),
    SUCCESS_INIE(2,"初始化成功"),
    TYPE_PONE(0,"pong"),
    TYPE_SUCCESS_JOIN(51, "进入场景成功"), 
    TYPE_SUCCESS_LEAVE(52, "离开场景成功"), 
    TYPE_ERROR_UNKNOW(-3000, "未知错误"),
    TYPE_ERROR_PARAMS(-3001, "参数错误"),
    TYPE_ERROR_UNAUTHORIZED(-3002, "身份认证失败"),
    TYPE_ERROR_FORBIDDEN(-3003, "没有权限"),
    SCENE_DIG_HLC(301, "挖火粒页"), 
    SCENE_DIG_HYC(302, "挖火蚁页"), 
    SCENE_HLC_GEAR_ONE(351, "hlc行情5档"), 
    SCENE_HLC_GEAR_TWO(352, "hlc行情10档"), 
    SCENE_HLC_GEAR_THREE(353, "hlc行情20档"), 
    SCENE_HLC_GEAR_FOUR(354, "hlc行情50档"), 
    SCENE_HLC_GEAR_FIVE(355, "hlc行情100档"), 
    
    SCENE_HYC_GEAR_ONE(361, "hyc行情5档"), 
    SCENE_HYC_GEAR_TWO(362, "hyc行情10档"), 
    SCENE_HYC_GEAR_THREE(363, "hyc行情20档"), 
    SCENE_HYC_GEAR_FOUR(364, "hyc行情50档"), 
    SCENE_HYC_GEAR_FIVE(365, "hyc行情100档"), 

    TYPE_ERROR_INVALID_IDENTITY(-3004, "用户状态无效（被冻结）");
    
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
    

}
