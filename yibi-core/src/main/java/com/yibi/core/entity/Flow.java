package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Flow implements Serializable {
    private static final long serialVersionUID = 747307045918159948L;

    private Integer id;

    private Integer userid;

    private String opertype;

    private Integer relateid;

    private Integer accounttype;

    private Integer cointype;

    private Integer operid;

    private BigDecimal amount;

    private String  resultAmount;

    private BigDecimal accamount;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private Date createtime;

    private Date updatetime;

    private String time;

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

    public String getOpertype() {
        return opertype;
    }

    public void setOpertype(String opertype) {
        this.opertype = opertype == null ? null : opertype.trim();
    }

    public Integer getRelateid() {
        return relateid;
    }

    public void setRelateid(Integer relateid) {
        this.relateid = relateid;
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

    public Integer getOperid() {
        return operid;
    }

    public void setOperid(Integer operid) {
        this.operid = operid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAccamount() {
        return accamount;
    }

    public void setAccamount(BigDecimal accamount) {
        this.accamount = accamount;
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

    public String getResultAmount() {
        return resultAmount;
    }

    public void setResultAmount(String resultAmount) {
        this.resultAmount = resultAmount;
    }

    public Flow() {
    }

    public Flow(Integer userid, String opertype, Integer relateid, Integer accounttype, Integer cointype, Integer operid, BigDecimal amount, BigDecimal accamount) {
        this.userid = userid;
        this.opertype = opertype;
        this.relateid = relateid;
        this.accounttype = accounttype;
        this.cointype = cointype;
        this.operid = operid;
        this.amount = amount;
        this.accamount = accamount;
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
        sb.append(", opertype=").append(opertype);
        sb.append(", relateid=").append(relateid);
        sb.append(", accounttype=").append(accounttype);
        sb.append(", cointype=").append(cointype);
        sb.append(", operid=").append(operid);
        sb.append(", amount=").append(amount);
        sb.append(", accamount=").append(accamount);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}