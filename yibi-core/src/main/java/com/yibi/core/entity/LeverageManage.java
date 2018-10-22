package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LeverageManage implements Serializable {
    private static final long serialVersionUID = 6914034318833688640L;

    private Integer id;

    private Integer ordercointype;

    private Integer unitcointype;

    private Integer onoff;

    private BigDecimal orderrate;

    private BigDecimal unitrate;

    private Integer ordertimelimit;

    private Integer unittimelimit;

    private BigDecimal ordermin;

    private BigDecimal unitmin;

    private Integer orderonoff;

    private Integer unitonoff;

    private Integer orderrerate;

    private Integer unitrerate;

    private Date createtime;

    private Date updatetime;

    private BigDecimal performrate;

    private BigDecimal referrate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getOnoff() {
        return onoff;
    }

    public void setOnoff(Integer onoff) {
        this.onoff = onoff;
    }

    public BigDecimal getOrderrate() {
        return orderrate;
    }

    public void setOrderrate(BigDecimal orderrate) {
        this.orderrate = orderrate;
    }

    public BigDecimal getUnitrate() {
        return unitrate;
    }

    public void setUnitrate(BigDecimal unitrate) {
        this.unitrate = unitrate;
    }

    public Integer getOrdertimelimit() {
        return ordertimelimit;
    }

    public void setOrdertimelimit(Integer ordertimelimit) {
        this.ordertimelimit = ordertimelimit;
    }

    public Integer getUnittimelimit() {
        return unittimelimit;
    }

    public void setUnittimelimit(Integer unittimelimit) {
        this.unittimelimit = unittimelimit;
    }

    public BigDecimal getOrdermin() {
        return ordermin;
    }

    public void setOrdermin(BigDecimal ordermin) {
        this.ordermin = ordermin;
    }

    public BigDecimal getUnitmin() {
        return unitmin;
    }

    public void setUnitmin(BigDecimal unitmin) {
        this.unitmin = unitmin;
    }

    public Integer getOrderonoff() {
        return orderonoff;
    }

    public void setOrderonoff(Integer orderonoff) {
        this.orderonoff = orderonoff;
    }

    public Integer getUnitonoff() {
        return unitonoff;
    }

    public void setUnitonoff(Integer unitonoff) {
        this.unitonoff = unitonoff;
    }

    public Integer getOrderrerate() {
        return orderrerate;
    }

    public void setOrderrerate(Integer orderrerate) {
        this.orderrerate = orderrerate;
    }

    public Integer getUnitrerate() {
        return unitrerate;
    }

    public void setUnitrerate(Integer unitrerate) {
        this.unitrerate = unitrerate;
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

    public BigDecimal getPerformrate() {
        return performrate;
    }

    public void setPerformrate(BigDecimal performrate) {
        this.performrate = performrate;
    }

    public BigDecimal getReferrate() {
        return referrate;
    }

    public void setReferrate(BigDecimal referrate) {
        this.referrate = referrate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", ordercointype=").append(ordercointype);
        sb.append(", unitcointype=").append(unitcointype);
        sb.append(", onoff=").append(onoff);
        sb.append(", orderrate=").append(orderrate);
        sb.append(", unitrate=").append(unitrate);
        sb.append(", ordertimelimit=").append(ordertimelimit);
        sb.append(", unittimelimit=").append(unittimelimit);
        sb.append(", ordermin=").append(ordermin);
        sb.append(", unitmin=").append(unitmin);
        sb.append(", orderonoff=").append(orderonoff);
        sb.append(", unitonoff=").append(unitonoff);
        sb.append(", orderrerate=").append(orderrerate);
        sb.append(", unitrerate=").append(unitrerate);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", performrate=").append(performrate);
        sb.append(", referrate=").append(referrate);
        sb.append("]");
        return sb.toString();
    }
}