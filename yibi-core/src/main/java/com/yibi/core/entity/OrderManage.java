package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderManage implements Serializable {
    private static final long serialVersionUID = -1941374270059524340L;

    private Integer id;

    private Integer ordercointype;

    private Integer unitcointype;

    private Integer onoff;

    private BigDecimal performrate;
    private BigDecimal marketpPerformRate;

    private BigDecimal referrate;
    private BigDecimal marketReferRate;

    private Integer marketseque;

    private Date createtime;

    private Date updatetime;

    private Integer okcoinflag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMarketReferRate() {
        return marketReferRate;
    }

    public void setMarketReferRate(BigDecimal marketReferRate) {
        this.marketReferRate = marketReferRate;
    }

    public BigDecimal getMarketpPerformRate() {
        return marketpPerformRate;
    }

    public void setMarketpPerformRate(BigDecimal marketpPerformRate) {
        this.marketpPerformRate = marketpPerformRate;
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

    public Integer getMarketseque() {
        return marketseque;
    }

    public void setMarketseque(Integer marketseque) {
        this.marketseque = marketseque;
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

    public Integer getOkcoinflag() {
        return okcoinflag;
    }

    public void setOkcoinflag(Integer okcoinflag) {
        this.okcoinflag = okcoinflag;
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
        sb.append(", performrate=").append(performrate);
        sb.append(", referrate=").append(referrate);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", okcoinflag=").append(okcoinflag);
        sb.append("]");
        return sb.toString();
    }
}