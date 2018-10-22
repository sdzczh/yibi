package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderMaker implements Serializable {
    private static final long serialVersionUID = -5132065989005552479L;

    private Integer id;

    private Integer userid;

    private Integer type;

    private Integer cointype;

    private Integer paytype;

    private BigDecimal price;

    private BigDecimal amount;

    private BigDecimal totalmin;

    private BigDecimal totalmax;

    private BigDecimal remain;

    private BigDecimal frozen;

    private BigDecimal deposit;

    private Boolean orderflag;

    private Integer state;

    private String ordernum;

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

    public Integer getPaytype() {
        return paytype;
    }

    public void setPaytype(Integer paytype) {
        this.paytype = paytype;
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

    public BigDecimal getTotalmin() {
        return totalmin;
    }

    public void setTotalmin(BigDecimal totalmin) {
        this.totalmin = totalmin;
    }

    public BigDecimal getTotalmax() {
        return totalmax;
    }

    public void setTotalmax(BigDecimal totalmax) {
        this.totalmax = totalmax;
    }

    public BigDecimal getRemain() {
        return remain;
    }

    public void setRemain(BigDecimal remain) {
        this.remain = remain;
    }

    public BigDecimal getFrozen() {
        return frozen;
    }

    public void setFrozen(BigDecimal frozen) {
        this.frozen = frozen;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public Boolean getOrderflag() {
        return orderflag;
    }

    public void setOrderflag(boolean orderflag) {
        this.orderflag = orderflag;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum == null ? null : ordernum.trim();
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
        sb.append(", paytype=").append(paytype);
        sb.append(", price=").append(price);
        sb.append(", amount=").append(amount);
        sb.append(", totalmin=").append(totalmin);
        sb.append(", totalmax=").append(totalmax);
        sb.append(", remain=").append(remain);
        sb.append(", frozen=").append(frozen);
        sb.append(", deposit=").append(deposit);
        sb.append(", orderflag=").append(orderflag);
        sb.append(", state=").append(state);
        sb.append(", ordernum=").append(ordernum);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}