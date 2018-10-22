package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountLeverage implements Serializable {
    private static final long serialVersionUID = 5398422479580500828L;

    private Integer id;

    private Integer userid;

    private Integer ordercointype;

    private Integer unitcointype;

    private BigDecimal orderavailbalance;

    private BigDecimal unitavailbalance;

    private BigDecimal orderfrozenbalance;

    private BigDecimal unitfrozenbalance;

    private BigDecimal orderarrears;

    private BigDecimal unitarrears;

    private Date createtime;

    private Date updatetime;

    private BigDecimal orderinterest;

    private BigDecimal unitinterest;

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

    public BigDecimal getOrderavailbalance() {
        return orderavailbalance;
    }

    public void setOrderavailbalance(BigDecimal orderavailbalance) {
        this.orderavailbalance = orderavailbalance;
    }

    public BigDecimal getUnitavailbalance() {
        return unitavailbalance;
    }

    public void setUnitavailbalance(BigDecimal unitavailbalance) {
        this.unitavailbalance = unitavailbalance;
    }

    public BigDecimal getOrderfrozenbalance() {
        return orderfrozenbalance;
    }

    public void setOrderfrozenbalance(BigDecimal orderfrozenbalance) {
        this.orderfrozenbalance = orderfrozenbalance;
    }

    public BigDecimal getUnitfrozenbalance() {
        return unitfrozenbalance;
    }

    public void setUnitfrozenbalance(BigDecimal unitfrozenbalance) {
        this.unitfrozenbalance = unitfrozenbalance;
    }

    public BigDecimal getOrderarrears() {
        return orderarrears;
    }

    public void setOrderarrears(BigDecimal orderarrears) {
        this.orderarrears = orderarrears;
    }

    public BigDecimal getUnitarrears() {
        return unitarrears;
    }

    public void setUnitarrears(BigDecimal unitarrears) {
        this.unitarrears = unitarrears;
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

    public BigDecimal getOrderinterest() {
        return orderinterest;
    }

    public void setOrderinterest(BigDecimal orderinterest) {
        this.orderinterest = orderinterest;
    }

    public BigDecimal getUnitinterest() {
        return unitinterest;
    }

    public void setUnitinterest(BigDecimal unitinterest) {
        this.unitinterest = unitinterest;
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
        sb.append(", orderavailbalance=").append(orderavailbalance);
        sb.append(", unitavailbalance=").append(unitavailbalance);
        sb.append(", orderfrozenbalance=").append(orderfrozenbalance);
        sb.append(", unitfrozenbalance=").append(unitfrozenbalance);
        sb.append(", orderarrears=").append(orderarrears);
        sb.append(", unitarrears=").append(unitarrears);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", orderinterest=").append(orderinterest);
        sb.append(", unitinterest=").append(unitinterest);
        sb.append("]");
        return sb.toString();
    }
}