package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DealDigConfig implements Serializable {
    private static final long serialVersionUID = -7421725888860492119L;

    private Integer id;

    private Integer ordercointype;

    private Integer buyrole;

    private Integer salerole;

    private BigDecimal feerate;

    private BigDecimal salecashback;

    private BigDecimal buycashback;

    private BigDecimal salerefcashback;

    private BigDecimal buyrefcashback;

    private Integer ordertype;

    private Date createtime;

    private Date updatetime;

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

    public Integer getBuyrole() {
        return buyrole;
    }

    public void setBuyrole(Integer buyrole) {
        this.buyrole = buyrole;
    }

    public Integer getSalerole() {
        return salerole;
    }

    public void setSalerole(Integer salerole) {
        this.salerole = salerole;
    }

    public BigDecimal getFeerate() {
        return feerate;
    }

    public void setFeerate(BigDecimal feerate) {
        this.feerate = feerate;
    }

    public BigDecimal getSalecashback() {
        return salecashback;
    }

    public void setSalecashback(BigDecimal salecashback) {
        this.salecashback = salecashback;
    }

    public BigDecimal getBuycashback() {
        return buycashback;
    }

    public void setBuycashback(BigDecimal buycashback) {
        this.buycashback = buycashback;
    }

    public BigDecimal getSalerefcashback() {
        return salerefcashback;
    }

    public void setSalerefcashback(BigDecimal salerefcashback) {
        this.salerefcashback = salerefcashback;
    }

    public BigDecimal getBuyrefcashback() {
        return buyrefcashback;
    }

    public void setBuyrefcashback(BigDecimal buyrefcashback) {
        this.buyrefcashback = buyrefcashback;
    }

    public Integer getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(Integer ordertype) {
        this.ordertype = ordertype;
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
        sb.append(", ordercointype=").append(ordercointype);
        sb.append(", buyrole=").append(buyrole);
        sb.append(", salerole=").append(salerole);
        sb.append(", feerate=").append(feerate);
        sb.append(", salecashback=").append(salecashback);
        sb.append(", buycashback=").append(buycashback);
        sb.append(", salerefcashback=").append(salerefcashback);
        sb.append(", buyrefcashback=").append(buyrefcashback);
        sb.append(", ordertype=").append(ordertype);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}