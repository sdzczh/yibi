package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderTaker implements Serializable {
    private static final long serialVersionUID = 3874838687932819354L;

    private Integer id;

    private Integer userid;

    private Integer type;

    private Integer cointype;

    private Integer makeruserid;

    private Integer makerid;

    private BigDecimal price;

    private BigDecimal amount;

    private BigDecimal total;

    private Integer payid;

    private String ordernum;

    private String flagnum;

    private Integer state;

    private Date inactivetime;

    private String remark;

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

    public Integer getMakeruserid() {
        return makeruserid;
    }

    public void setMakeruserid(Integer makeruserid) {
        this.makeruserid = makeruserid;
    }

    public Integer getMakerid() {
        return makerid;
    }

    public void setMakerid(Integer makerid) {
        this.makerid = makerid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getPayid() {
        return payid;
    }

    public void setPayid(Integer payid) {
        this.payid = payid;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum == null ? null : ordernum.trim();
    }

    public String getFlagnum() {
        return flagnum;
    }

    public void setFlagnum(String flagnum) {
        this.flagnum = flagnum == null ? null : flagnum.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getInactivetime() {
        return inactivetime;
    }

    public void setInactivetime(Date inactivetime) {
        this.inactivetime = inactivetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
        sb.append(", makeruserid=").append(makeruserid);
        sb.append(", makerid=").append(makerid);
        sb.append(", price=").append(price);
        sb.append(", amount=").append(amount);
        sb.append(", total=").append(total);
        sb.append(", payid=").append(payid);
        sb.append(", ordernum=").append(ordernum);
        sb.append(", flagnum=").append(flagnum);
        sb.append(", state=").append(state);
        sb.append(", inactivetime=").append(inactivetime);
        sb.append(", remark=").append(remark);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}