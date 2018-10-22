package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CommissionRecord implements Serializable {
    private static final long serialVersionUID = 1586496699695906907L;

    private Integer id;

    private Integer userid;

    private BigDecimal commamount;

    private Integer commcointype;

    private BigDecimal orderamount;

    private Integer ordercointype;

    private Integer type;

    private Integer referenceid;

    private Integer orderid;

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

    public BigDecimal getCommamount() {
        return commamount;
    }

    public void setCommamount(BigDecimal commamount) {
        this.commamount = commamount;
    }

    public Integer getCommcointype() {
        return commcointype;
    }

    public void setCommcointype(Integer commcointype) {
        this.commcointype = commcointype;
    }

    public BigDecimal getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(BigDecimal orderamount) {
        this.orderamount = orderamount;
    }

    public Integer getOrdercointype() {
        return ordercointype;
    }

    public void setOrdercointype(Integer ordercointype) {
        this.ordercointype = ordercointype;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getReferenceid() {
        return referenceid;
    }

    public void setReferenceid(Integer referenceid) {
        this.referenceid = referenceid;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
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
        sb.append(", commamount=").append(commamount);
        sb.append(", commcointype=").append(commcointype);
        sb.append(", orderamount=").append(orderamount);
        sb.append(", ordercointype=").append(ordercointype);
        sb.append(", type=").append(type);
        sb.append(", referenceid=").append(referenceid);
        sb.append(", orderid=").append(orderid);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}