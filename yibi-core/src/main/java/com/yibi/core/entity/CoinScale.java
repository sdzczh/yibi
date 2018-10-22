package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CoinScale implements Serializable {
    private static final long serialVersionUID = -4070088190247624944L;

    private Integer id;

    private Integer ordercointype;

    private Integer unitcointype;

    private Integer orderamtpricescale;

    private Integer orderamtamountscale;

    private Integer availofspotunitscale;

    private Integer availofspotorderscale;

    private Integer marketpriceofcnyscale;

    private Integer markettradenumscale;

    private Integer klinepricescale;

    private Integer calculscale;

    private Integer availofcnyscale;

    private Integer yubiscale;

    private Integer c2cpricescale;

    private Integer c2cnumscale;

    private Integer withdrawScale;

    private Integer c2ctotalamtscale;

    private BigDecimal minspottransamt;

    private BigDecimal minspottransnum;

    private BigDecimal minc2ctransamt;

    private BigDecimal minwithdrawnum;

    private BigDecimal marketbuyminamt;

    private BigDecimal marketsaleminnum;

    private Date createtime;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWithdrawScale() {
        return withdrawScale;
    }

    public void setWithdrawScale(Integer withdrawScale) {
        this.withdrawScale = withdrawScale;
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

    public Integer getOrderamtpricescale() {
        return orderamtpricescale;
    }

    public void setOrderamtpricescale(Integer orderamtpricescale) {
        this.orderamtpricescale = orderamtpricescale;
    }

    public Integer getOrderamtamountscale() {
        return orderamtamountscale;
    }

    public void setOrderamtamountscale(Integer orderamtamountscale) {
        this.orderamtamountscale = orderamtamountscale;
    }

    public Integer getAvailofspotunitscale() {
        return availofspotunitscale;
    }

    public void setAvailofspotunitscale(Integer availofspotunitscale) {
        this.availofspotunitscale = availofspotunitscale;
    }

    public Integer getAvailofspotorderscale() {
        return availofspotorderscale;
    }

    public void setAvailofspotorderscale(Integer availofspotorderscale) {
        this.availofspotorderscale = availofspotorderscale;
    }

    public Integer getMarketpriceofcnyscale() {
        return marketpriceofcnyscale;
    }

    public void setMarketpriceofcnyscale(Integer marketpriceofcnyscale) {
        this.marketpriceofcnyscale = marketpriceofcnyscale;
    }

    public Integer getMarkettradenumscale() {
        return markettradenumscale;
    }

    public void setMarkettradenumscale(Integer markettradenumscale) {
        this.markettradenumscale = markettradenumscale;
    }

    public Integer getKlinepricescale() {
        return klinepricescale;
    }

    public void setKlinepricescale(Integer klinepricescale) {
        this.klinepricescale = klinepricescale;
    }

    public Integer getCalculscale() {
        return calculscale;
    }

    public void setCalculscale(Integer calculscale) {
        this.calculscale = calculscale;
    }

    public Integer getAvailofcnyscale() {
        return availofcnyscale;
    }

    public void setAvailofcnyscale(Integer availofcnyscale) {
        this.availofcnyscale = availofcnyscale;
    }

    public Integer getYubiscale() {
        return yubiscale;
    }

    public void setYubiscale(Integer yubiscale) {
        this.yubiscale = yubiscale;
    }

    public Integer getC2cpricescale() {
        return c2cpricescale;
    }

    public void setC2cpricescale(Integer c2cpricescale) {
        this.c2cpricescale = c2cpricescale;
    }

    public Integer getC2cnumscale() {
        return c2cnumscale;
    }

    public void setC2cnumscale(Integer c2cnumscale) {
        this.c2cnumscale = c2cnumscale;
    }

    public Integer getC2ctotalamtscale() {
        return c2ctotalamtscale;
    }

    public void setC2ctotalamtscale(Integer c2ctotalamtscale) {
        this.c2ctotalamtscale = c2ctotalamtscale;
    }

    public BigDecimal getMinspottransamt() {
        return minspottransamt;
    }

    public void setMinspottransamt(BigDecimal minspottransamt) {
        this.minspottransamt = minspottransamt;
    }

    public BigDecimal getMinspottransnum() {
        return minspottransnum;
    }

    public void setMinspottransnum(BigDecimal minspottransnum) {
        this.minspottransnum = minspottransnum;
    }

    public BigDecimal getMinc2ctransamt() {
        return minc2ctransamt;
    }

    public void setMinc2ctransamt(BigDecimal minc2ctransamt) {
        this.minc2ctransamt = minc2ctransamt;
    }

    public BigDecimal getMinwithdrawnum() {
        return minwithdrawnum;
    }

    public void setMinwithdrawnum(BigDecimal minwithdrawnum) {
        this.minwithdrawnum = minwithdrawnum;
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

    public BigDecimal getMarketbuyminamt() {
        return marketbuyminamt;
    }

    public void setMarketbuyminamt(BigDecimal marketbuyminamt) {
        this.marketbuyminamt = marketbuyminamt;
    }

    public BigDecimal getMarketsaleminnum() {
        return marketsaleminnum;
    }

    public void setMarketsaleminnum(BigDecimal marketsaleminnum) {
        this.marketsaleminnum = marketsaleminnum;
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
        sb.append(", orderamtpricescale=").append(orderamtpricescale);
        sb.append(", orderamtamountscale=").append(orderamtamountscale);
        sb.append(", availofspotunitscale=").append(availofspotunitscale);
        sb.append(", availofspotorderscale=").append(availofspotorderscale);
        sb.append(", marketpriceofcnyscale=").append(marketpriceofcnyscale);
        sb.append(", markettradenumscale=").append(markettradenumscale);
        sb.append(", klinepricescale=").append(klinepricescale);
        sb.append(", calculscale=").append(calculscale);
        sb.append(", availofcnyscale=").append(availofcnyscale);
        sb.append(", yubiscale=").append(yubiscale);
        sb.append(", c2cpricescale=").append(c2cpricescale);
        sb.append(", c2cnumscale=").append(c2cnumscale);
        sb.append(", c2ctotalamtscale=").append(c2ctotalamtscale);
        sb.append(", minspottransamt=").append(minspottransamt);
        sb.append(", minspottransnum=").append(minspottransnum);
        sb.append(", minc2ctransamt=").append(minc2ctransamt);
        sb.append(", minwithdrawnum=").append(minwithdrawnum);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}