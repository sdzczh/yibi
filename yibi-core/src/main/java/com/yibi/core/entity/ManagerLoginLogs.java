package com.yibi.core.entity;

import java.io.Serializable;
import java.util.Date;

public class ManagerLoginLogs implements Serializable {
    private static final long serialVersionUID = -6277056323337692530L;

    private Integer id;

    private Integer userid;

    private String userip;

    private Date createtime;

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

    public String getUserip() {
        return userip;
    }

    public void setUserip(String userip) {
        this.userip = userip == null ? null : userip.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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
        sb.append(", userip=").append(userip);
        sb.append(", createtime=").append(createtime);
        sb.append("]");
        return sb.toString();
    }
}