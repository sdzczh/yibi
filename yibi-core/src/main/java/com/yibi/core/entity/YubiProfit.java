package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class YubiProfit implements Serializable {
    private static final long serialVersionUID = -8319162242785630393L;

    private Integer id;

    private Integer userid;

    private Integer cointype;

    private BigDecimal principle;

    private BigDecimal rate;

    private BigDecimal interest;

    private BigDecimal yubitotalprofit;

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

    public Integer getCointype() {
        return cointype;
    }

    public void setCointype(Integer cointype) {
        this.cointype = cointype;
    }

    public BigDecimal getPrinciple() {
        return principle;
    }

    public void setPrinciple(BigDecimal principle) {
        this.principle = principle;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getYubitotalprofit() {
        return yubitotalprofit;
    }

    public void setYubitotalprofit(BigDecimal yubitotalprofit) {
        this.yubitotalprofit = yubitotalprofit;
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

    public YubiProfit() {
    }

    public YubiProfit(Integer userid, Integer cointype, BigDecimal principle, BigDecimal rate, BigDecimal interest, BigDecimal yubitotalprofit) {
        this.userid = userid;
        this.cointype = cointype;
        this.principle = principle;
        this.rate = rate;
        this.interest = interest;
        this.yubitotalprofit = yubitotalprofit;
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
        sb.append(", cointype=").append(cointype);
        sb.append(", principle=").append(principle);
        sb.append(", rate=").append(rate);
        sb.append(", interest=").append(interest);
        sb.append(", yubitotalprofit=").append(yubitotalprofit);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}