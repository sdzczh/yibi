package com.yibi.core.entity;

import java.io.Serializable;
import java.util.Date;

public class DigcalRecord implements Serializable {
    private static final long serialVersionUID = 3982563970195650715L;

    private Integer id;

    private Integer userid;

    private Integer digcalcul;

    private Integer allcalculforce;

    private String name;

    private Integer type;

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

    public Integer getDigcalcul() {
        return digcalcul;
    }

    public void setDigcalcul(Integer digcalcul) {
        this.digcalcul = digcalcul;
    }

    public Integer getAllcalculforce() {
        return allcalculforce;
    }

    public void setAllcalculforce(Integer allcalculforce) {
        this.allcalculforce = allcalculforce;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
        sb.append(", digcalcul=").append(digcalcul);
        sb.append(", allcalculforce=").append(allcalculforce);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}