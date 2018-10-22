package com.yibi.core.entity;

import java.io.Serializable;
import java.util.Date;

public class DigBase implements Serializable {
    private static final long serialVersionUID = -9217487757948644617L;

    private Integer id;

    private Integer cointype;

    private Double rate0;

    private Double rate1;

    private Double rate2;

    private Double rate3;

    private Double rate4;

    private Double rate5;

    private Double rate6;

    private Double rate7;

    private Double rate8;

    private Double rate9;

    private Date createtime;

    private Date updatetime;

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

    public Double getRate0() {
        return rate0;
    }

    public void setRate0(Double rate0) {
        this.rate0 = rate0;
    }

    public Double getRate1() {
        return rate1;
    }

    public void setRate1(Double rate1) {
        this.rate1 = rate1;
    }

    public Double getRate2() {
        return rate2;
    }

    public void setRate2(Double rate2) {
        this.rate2 = rate2;
    }

    public Double getRate3() {
        return rate3;
    }

    public void setRate3(Double rate3) {
        this.rate3 = rate3;
    }

    public Double getRate4() {
        return rate4;
    }

    public void setRate4(Double rate4) {
        this.rate4 = rate4;
    }

    public Double getRate5() {
        return rate5;
    }

    public void setRate5(Double rate5) {
        this.rate5 = rate5;
    }

    public Double getRate6() {
        return rate6;
    }

    public void setRate6(Double rate6) {
        this.rate6 = rate6;
    }

    public Double getRate7() {
        return rate7;
    }

    public void setRate7(Double rate7) {
        this.rate7 = rate7;
    }

    public Double getRate8() {
        return rate8;
    }

    public void setRate8(Double rate8) {
        this.rate8 = rate8;
    }

    public Double getRate9() {
        return rate9;
    }

    public void setRate9(Double rate9) {
        this.rate9 = rate9;
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
        sb.append(", cointype=").append(cointype);
        sb.append(", rate0=").append(rate0);
        sb.append(", rate1=").append(rate1);
        sb.append(", rate2=").append(rate2);
        sb.append(", rate3=").append(rate3);
        sb.append(", rate4=").append(rate4);
        sb.append(", rate5=").append(rate5);
        sb.append(", rate6=").append(rate6);
        sb.append(", rate7=").append(rate7);
        sb.append(", rate8=").append(rate8);
        sb.append(", rate9=").append(rate9);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}