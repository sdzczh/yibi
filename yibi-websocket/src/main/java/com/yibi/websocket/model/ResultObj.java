package com.yibi.websocket.model;

public class ResultObj {
    private String cmsgCode;
    private String info;
    private Integer code = ResultCode.SUCCESS.code();
    private String msg = ResultCode.SUCCESS.message();
    private Integer scene;

    public String getCmsgCode() {
        return cmsgCode;
    }

    public void setCmsgCode(String cmsgCode) {
        this.cmsgCode = cmsgCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getScene() {
        return scene;
    }

    public void setScene(Integer scene) {
        this.scene = scene;
    }
}
