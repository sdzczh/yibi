package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LoanRecord implements Serializable {
    private static final long serialVersionUID = -5624563015273272341L;

    private Integer id;

    private Integer accountid;

    private Integer cointype;

    private BigDecimal amount;

    private BigDecimal rate;

    private BigDecimal interest;

    private Date repaymentdeadline;

    private Date repaymenttime;

    private Integer status;

    private Integer autorepayflag;

    private Integer autorenewflag;

    private Date createtime;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountid() {
        return accountid;
    }

    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
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

    public Date getRepaymentdeadline() {
        return repaymentdeadline;
    }

    public void setRepaymentdeadline(Date repaymentdeadline) {
        this.repaymentdeadline = repaymentdeadline;
    }

    public Date getRepaymenttime() {
        return repaymenttime;
    }

    public void setRepaymenttime(Date repaymenttime) {
        this.repaymenttime = repaymenttime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAutorepayflag() {
        return autorepayflag;
    }

    public void setAutorepayflag(Integer autorepayflag) {
        this.autorepayflag = autorepayflag;
    }

    public Integer getAutorenewflag() {
        return autorenewflag;
    }

    public void setAutorenewflag(Integer autorenewflag) {
        this.autorenewflag = autorenewflag;
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
        sb.append(", accountid=").append(accountid);
        sb.append(", cointype=").append(cointype);
        sb.append(", amount=").append(amount);
        sb.append(", rate=").append(rate);
        sb.append(", interest=").append(interest);
        sb.append(", repaymentdeadline=").append(repaymentdeadline);
        sb.append(", repaymenttime=").append(repaymenttime);
        sb.append(", status=").append(status);
        sb.append(", autorepayflag=").append(autorepayflag);
        sb.append(", autorenewflag=").append(autorenewflag);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}