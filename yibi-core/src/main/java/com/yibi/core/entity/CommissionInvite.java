package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CommissionInvite implements Serializable {
    private static final long serialVersionUID = 5062624674945781198L;

    private Integer id;

    private Integer userid;

    private Integer type;

    private Integer cointype;

    private BigDecimal amount;

    private Integer referuserid;

    private BigDecimal referamount;

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

    public Integer getCointype() {
        return cointype;
    }

    public void setCointype(Integer cointype) {
        this.cointype = cointype;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getReferuserid() {
        return referuserid;
    }

    public void setReferuserid(Integer referuserid) {
        this.referuserid = referuserid;
    }

    public BigDecimal getReferamount() {
        return referamount;
    }

    public void setReferamount(BigDecimal referamount) {
        this.referamount = referamount;
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
        sb.append(", cointype=").append(cointype);
        sb.append(", amount=").append(amount);
        sb.append(", referuserid=").append(referuserid);
        sb.append(", referamount=").append(referamount);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}