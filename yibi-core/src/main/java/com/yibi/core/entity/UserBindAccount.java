package com.yibi.core.entity;

import java.io.Serializable;
import java.util.Date;

public class UserBindAccount implements Serializable {
    private static final long serialVersionUID = 7072090902710328968L;

    private Integer id;

    private Integer userid;

    private Integer type;

    private String account;

    private String token;

    private String expfield1;

    private String expfield2;

    private String expfield3;

    private Date createtime;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getExpfield1() {
        return expfield1;
    }

    public void setExpfield1(String expfield1) {
        this.expfield1 = expfield1 == null ? null : expfield1.trim();
    }

    public String getExpfield2() {
        return expfield2;
    }

    public void setExpfield2(String expfield2) {
        this.expfield2 = expfield2 == null ? null : expfield2.trim();
    }

    public String getExpfield3() {
        return expfield3;
    }

    public void setExpfield3(String expfield3) {
        this.expfield3 = expfield3 == null ? null : expfield3.trim();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", userid=").append(userid);
        sb.append(", type=").append(type);
        sb.append(", account=").append(account);
        sb.append(", token=").append(token);
        sb.append(", expfield1=").append(expfield1);
        sb.append(", expfield2=").append(expfield2);
        sb.append(", expfield3=").append(expfield3);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}