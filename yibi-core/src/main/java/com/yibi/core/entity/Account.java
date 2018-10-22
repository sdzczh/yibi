package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Account implements Serializable {
    private static final long serialVersionUID = -6722343510910427326L;

    private Integer id;

    private Integer userid;

    private BigDecimal availbalance;

    private BigDecimal frozenblance;

    private Integer accounttype;

    private Integer cointype;

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

    public BigDecimal getAvailbalance() {
        return availbalance;
    }

    public void setAvailbalance(BigDecimal availbalance) {
        this.availbalance = availbalance;
    }

    public BigDecimal getFrozenblance() {
        return frozenblance;
    }

    public void setFrozenblance(BigDecimal frozenblance) {
        this.frozenblance = frozenblance;
    }

    public Integer getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(Integer accounttype) {
        this.accounttype = accounttype;
    }

    public Integer getCointype() {
        return cointype;
    }

    public void setCointype(Integer cointype) {
        this.cointype = cointype;
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
        sb.append(", availbalance=").append(availbalance);
        sb.append(", frozenblance=").append(frozenblance);
        sb.append(", accounttype=").append(accounttype);
        sb.append(", cointype=").append(cointype);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}