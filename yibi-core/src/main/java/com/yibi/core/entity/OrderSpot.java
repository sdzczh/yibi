package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderSpot implements Serializable {
    private static final long serialVersionUID = 66674291244590637L;

    private Integer id;

    private Integer userid;

    private Integer ordercointype;

    private Integer unitcointype;

    private Integer type;

    private Integer ordertype;

    private BigDecimal price;

    private BigDecimal amount;

    private BigDecimal remain;

    private Integer state;

    private Integer levflag;

    private BigDecimal total;

    private BigDecimal average;

    private String ordernum;

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

    public Integer getOrdercointype() {
        return ordercointype;
    }

    public void setOrdercointype(Integer ordercointype) {
        this.ordercointype = ordercointype;
    }

    public Integer getUnitcointype() {
        return unitcointype;
    }

    public void setUnitcointype(Integer unitcointype) {
        this.unitcointype = unitcointype;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(Integer ordertype) {
        this.ordertype = ordertype;
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

    public BigDecimal getRemain() {
        return remain;
    }

    public void setRemain(BigDecimal remain) {
        this.remain = remain;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getLevflag() {
        return levflag;
    }

    public void setLevflag(Integer levflag) {
        this.levflag = levflag;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getAverage() {
        return average;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum == null ? null : ordernum.trim();
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
        sb.append(", ordercointype=").append(ordercointype);
        sb.append(", unitcointype=").append(unitcointype);
        sb.append(", type=").append(type);
        sb.append(", ordertype=").append(ordertype);
        sb.append(", price=").append(price);
        sb.append(", amount=").append(amount);
        sb.append(", remain=").append(remain);
        sb.append(", state=").append(state);
        sb.append(", levflag=").append(levflag);
        sb.append(", total=").append(total);
        sb.append(", average=").append(average);
        sb.append(", ordernum=").append(ordernum);
        sb.append(", remark=").append(remark);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }

    private BigDecimal dealAmount;


    public BigDecimal getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(BigDecimal dealAmount) {
        this.dealAmount = dealAmount;
    }
}