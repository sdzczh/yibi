package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderSpotRecord implements Serializable {
    private static final long serialVersionUID = 4254745509339951971L;

    private Integer id;

    private Integer buyid;

    private Integer saleid;

    private Integer buyuserid;

    private Integer saleuserid;

    private Integer ordercointype;

    private Integer unitcointype;

    private BigDecimal amount;

    private BigDecimal price;

    private BigDecimal total;

    private Date createtime;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBuyid() {
        return buyid;
    }

    public void setBuyid(Integer buyid) {
        this.buyid = buyid;
    }

    public Integer getSaleid() {
        return saleid;
    }

    public void setSaleid(Integer saleid) {
        this.saleid = saleid;
    }

    public Integer getBuyuserid() {
        return buyuserid;
    }

    public void setBuyuserid(Integer buyuserid) {
        this.buyuserid = buyuserid;
    }

    public Integer getSaleuserid() {
        return saleuserid;
    }

    public void setSaleuserid(Integer saleuserid) {
        this.saleuserid = saleuserid;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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
        sb.append(", buyid=").append(buyid);
        sb.append(", saleid=").append(saleid);
        sb.append(", buyuserid=").append(buyuserid);
        sb.append(", saleuserid=").append(saleuserid);
        sb.append(", ordercointype=").append(ordercointype);
        sb.append(", unitcointype=").append(unitcointype);
        sb.append(", amount=").append(amount);
        sb.append(", price=").append(price);
        sb.append(", total=").append(total);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}