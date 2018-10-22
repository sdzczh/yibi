package com.yibi.core.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private static final long serialVersionUID = 1701774470622162459L;

    private Integer id;

    private String phone;

    private String userpassword;

    private String username;

    private Integer referenceid;

    private String idcard;

    private Integer idstatus;

    private String headpath;

    private String openid;

    private String secretkey;

    private String token;

    private Date tokencreatetime;

    private String orderpwd;

    private Date logintime;

    private String devicenum;

    private Integer state;

    private Integer role;

    private String nickname;

    private Date createtime;

    private Date updatetime;

    private Integer partnerflag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword == null ? null : userpassword.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getReferenceid() {
        return referenceid;
    }

    public void setReferenceid(Integer referenceid) {
        this.referenceid = referenceid;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public Integer getIdstatus() {
        return idstatus;
    }

    public void setIdstatus(Integer idstatus) {
        this.idstatus = idstatus;
    }

    public String getHeadpath() {
        return headpath;
    }

    public void setHeadpath(String headpath) {
        this.headpath = headpath == null ? null : headpath.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey == null ? null : secretkey.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Date getTokencreatetime() {
        return tokencreatetime;
    }

    public void setTokencreatetime(Date tokencreatetime) {
        this.tokencreatetime = tokencreatetime;
    }

    public String getOrderpwd() {
        return orderpwd;
    }

    public void setOrderpwd(String orderpwd) {
        this.orderpwd = orderpwd == null ? null : orderpwd.trim();
    }

    public Date getLogintime() {
        return logintime;
    }

    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }

    public String getDevicenum() {
        return devicenum;
    }

    public void setDevicenum(String devicenum) {
        this.devicenum = devicenum == null ? null : devicenum.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getPartnerflag() {
        return partnerflag;
    }

    public void setPartnerflag(Integer partnerflag) {
        this.partnerflag = partnerflag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", phone=").append(phone);
        sb.append(", userpassword=").append(userpassword);
        sb.append(", username=").append(username);
        sb.append(", referenceid=").append(referenceid);
        sb.append(", idcard=").append(idcard);
        sb.append(", idstatus=").append(idstatus);
        sb.append(", headpath=").append(headpath);
        sb.append(", openid=").append(openid);
        sb.append(", secretkey=").append(secretkey);
        sb.append(", token=").append(token);
        sb.append(", tokencreatetime=").append(tokencreatetime);
        sb.append(", orderpwd=").append(orderpwd);
        sb.append(", logintime=").append(logintime);
        sb.append(", devicenum=").append(devicenum);
        sb.append(", state=").append(state);
        sb.append(", role=").append(role);
        sb.append(", nickname=").append(nickname);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", partnerflag=").append(partnerflag);
        sb.append("]");
        return sb.toString();
    }
}