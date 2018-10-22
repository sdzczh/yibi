package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class CoinManageModel implements Serializable {
    private static final long serialVersionUID = -7740286190199711275L;

    private Integer id;

    private Integer cointype;

    private String coinname;

    private String cnname;

    private String description;

    private String imgurl;

    private BigDecimal minC2cTransAmt;   //最低法币交易额限制

    private String minC2cTransNum;   //最低法币交易额限制

    private BigDecimal minwithdrawNum;   //最低提现数量限制

    private String withdrawNum;   //最低提现数量限制

    private Integer c2cNumScale;    //法币交易数量小数位数

    private Integer yubiScale;      //余币宝计算小数位数

    private Integer calculScale;   //资金划转小数位数

    private Integer c2cPriceScale;   //法币交易价格小数位数

    private Integer withdrawScale;  //提现小数位数

    public Integer getC2cPriceScale() {
        return c2cPriceScale;
    }

    public void setC2cPriceScale(Integer c2cPriceScale) {
        this.c2cPriceScale = c2cPriceScale;
    }

    public String getWithdrawNum() {
        return withdrawNum;
    }

    public void setWithdrawNum(String withdrawNum) {
        this.withdrawNum = withdrawNum;
    }

    public String getMinC2cTransNum() {
        return minC2cTransNum;
    }

    public void setMinC2cTransNum(String minC2cTransNum) {
        this.minC2cTransNum = minC2cTransNum;
    }

    public BigDecimal getMinwithdrawNum() {
        return minwithdrawNum;
    }

    public void setMinwithdrawNum(BigDecimal minwithdrawNum) {
        this.minwithdrawNum = minwithdrawNum;
    }

    public BigDecimal getMinC2cTransAmt() {
        return minC2cTransAmt;
    }

    public void setMinC2cTransAmt(BigDecimal minC2cTransAmt) {
        this.minC2cTransAmt = minC2cTransAmt;
    }

    public Integer getC2cNumScale() {
        return c2cNumScale;
    }

    public void setC2cNumScale(Integer c2cNumScale) {
        this.c2cNumScale = c2cNumScale;
    }

    public Integer getYubiScale() {
        return yubiScale;
    }

    public void setYubiScale(Integer yubiScale) {
        this.yubiScale = yubiScale;
    }

    public Integer getCalculScale() {
        return calculScale;
    }

    public void setCalculScale(Integer calculScale) {
        this.calculScale = calculScale;
    }

    public Integer getWithdrawScale() {
        return withdrawScale;
    }

    public void setWithdrawScale(Integer withdrawScale) {
        this.withdrawScale = withdrawScale;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCointype() {
        return cointype;
    }

    public void setCointype(Integer cointype) {
        this.cointype = cointype;
    }

    public String getCoinname() {
        return coinname;
    }

    public void setCoinname(String coinname) {
        this.coinname = coinname;
    }

    public String getCnname() {
        return cnname;
    }

    public void setCnname(String cnname) {
        this.cnname = cnname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", cointype=").append(cointype);
        sb.append(", coinname=").append(coinname);
        sb.append(", cnname=").append(cnname);
        sb.append(", description=").append(description);
        sb.append(", imgurl=").append(imgurl);
        sb.append("]");
        return sb.toString();
    }
}