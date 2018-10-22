package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CoinManage implements Serializable {
    private static final long serialVersionUID = -7740286190199711275L;

    private Integer id;

    private Integer cointype;

    private Integer seque;

    private String coinname;

    private String cnname;

    private String description;

    private String imgurl;

    private Integer c2conoff;

    private Integer digonoff;

    private Integer spottoc2conoff;

    private Integer c2ctospotonoff;

    private BigDecimal rechspotrate;

    private Integer rechspotonoff;

    private Integer withspotonoff;

    private BigDecimal withspotrate;

    private Integer withc2conoff;

    private BigDecimal c2corderdeposit;

    private BigDecimal withamountmax;

    private BigDecimal withamountmin;

    private Integer withdrawcountmax;

    private Integer digtospotonoff;

    private Integer digwithdrwaonoff;

    private BigDecimal redpacketmaxamtsingle;

    private BigDecimal transfermaxamtday;

    private String getAddress;

    private String listTransactions;

    private String transferAddress;

    private Integer spottoyubionoff;

    private Integer yubitospotonoff;

    private BigDecimal yubitransmin;

    private BigDecimal yubitransmax;

    private BigDecimal yubirate;

    private BigDecimal yubiprofitminamt;

    private BigDecimal autorechargeamt;

    private String rechargeinfo;

    private String withdrawinfo;

    private Date createtime;

    private Date updatetime;

    public Integer getDigonoff() {
        return digonoff;
    }

    public void setDigonoff(Integer digonoff) {
        this.digonoff = digonoff;
    }

    public Integer getSeque() {
        return seque;
    }

    public void setSeque(Integer seque) {
        this.seque = seque;
    }

    public BigDecimal getYubiprofitminamt() {
        return this.yubiprofitminamt;
    }

    public void setYubiprofitminamt(BigDecimal yubiprofitminamt) {
        this.yubiprofitminamt = yubiprofitminamt;
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
        this.coinname = coinname == null ? null : coinname.trim();
    }

    public String getCnname() {
        return cnname;
    }

    public void setCnname(String cnname) {
        this.cnname = cnname == null ? null : cnname.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    public Integer getC2conoff() {
        return c2conoff;
    }

    public void setC2conoff(Integer c2conoff) {
        this.c2conoff = c2conoff;
    }

    public Integer getSpottoc2conoff() {
        return spottoc2conoff;
    }

    public void setSpottoc2conoff(Integer spottoc2conoff) {
        this.spottoc2conoff = spottoc2conoff;
    }

    public Integer getC2ctospotonoff() {
        return c2ctospotonoff;
    }

    public void setC2ctospotonoff(Integer c2ctospotonoff) {
        this.c2ctospotonoff = c2ctospotonoff;
    }

    public BigDecimal getRechspotrate() {
        return rechspotrate;
    }

    public void setRechspotrate(BigDecimal rechspotrate) {
        this.rechspotrate = rechspotrate;
    }

    public Integer getRechspotonoff() {
        return rechspotonoff;
    }

    public void setRechspotonoff(Integer rechspotonoff) {
        this.rechspotonoff = rechspotonoff;
    }

    public Integer getWithspotonoff() {
        return withspotonoff;
    }

    public void setWithspotonoff(Integer withspotonoff) {
        this.withspotonoff = withspotonoff;
    }

    public BigDecimal getWithspotrate() {
        return withspotrate;
    }

    public void setWithspotrate(BigDecimal withspotrate) {
        this.withspotrate = withspotrate;
    }

    public Integer getWithc2conoff() {
        return withc2conoff;
    }

    public void setWithc2conoff(Integer withc2conoff) {
        this.withc2conoff = withc2conoff;
    }

    public BigDecimal getC2corderdeposit() {
        return c2corderdeposit;
    }

    public void setC2corderdeposit(BigDecimal c2corderdeposit) {
        this.c2corderdeposit = c2corderdeposit;
    }

    public BigDecimal getWithamountmax() {
        return withamountmax;
    }

    public void setWithamountmax(BigDecimal withamountmax) {
        this.withamountmax = withamountmax;
    }

    public BigDecimal getWithamountmin() {
        return withamountmin;
    }

    public void setWithamountmin(BigDecimal withamountmin) {
        this.withamountmin = withamountmin;
    }

    public Integer getWithdrawcountmax() {
        return withdrawcountmax;
    }

    public void setWithdrawcountmax(Integer withdrawcountmax) {
        this.withdrawcountmax = withdrawcountmax;
    }

    public Integer getDigtospotonoff() {
        return digtospotonoff;
    }

    public void setDigtospotonoff(Integer digtospotonoff) {
        this.digtospotonoff = digtospotonoff;
    }

    public Integer getDigwithdrwaonoff() {
        return digwithdrwaonoff;
    }

    public void setDigwithdrwaonoff(Integer digwithdrwaonoff) {
        this.digwithdrwaonoff = digwithdrwaonoff;
    }

    public BigDecimal getRedpacketmaxamtsingle() {
        return redpacketmaxamtsingle;
    }

    public void setRedpacketmaxamtsingle(BigDecimal redpacketmaxamtsingle) {
        this.redpacketmaxamtsingle = redpacketmaxamtsingle;
    }

    public BigDecimal getTransfermaxamtday() {
        return transfermaxamtday;
    }

    public void setTransfermaxamtday(BigDecimal transfermaxamtday) {
        this.transfermaxamtday = transfermaxamtday;
    }

    public String getGetAddress() {
        return getAddress;
    }

    public void setGetAddress(String getAddress) {
        this.getAddress = getAddress == null ? null : getAddress.trim();
    }

    public String getListTransactions() {
        return listTransactions;
    }

    public void setListTransactions(String listTransactions) {
        this.listTransactions = listTransactions == null ? null : listTransactions.trim();
    }

    public String getTransferAddress() {
        return transferAddress;
    }

    public void setTransferAddress(String transferAddress) {
        this.transferAddress = transferAddress == null ? null : transferAddress.trim();
    }

    public Integer getSpottoyubionoff() {
        return spottoyubionoff;
    }

    public void setSpottoyubionoff(Integer spottoyubionoff) {
        this.spottoyubionoff = spottoyubionoff;
    }

    public Integer getYubitospotonoff() {
        return yubitospotonoff;
    }

    public void setYubitospotonoff(Integer yubitospotonoff) {
        this.yubitospotonoff = yubitospotonoff;
    }

    public BigDecimal getYubitransmin() {
        return yubitransmin;
    }

    public void setYubitransmin(BigDecimal yubitransmin) {
        this.yubitransmin = yubitransmin;
    }

    public BigDecimal getYubitransmax() {
        return yubitransmax;
    }

    public void setYubitransmax(BigDecimal yubitransmax) {
        this.yubitransmax = yubitransmax;
    }

    public BigDecimal getYubirate() {
        return yubirate;
    }

    public void setYubirate(BigDecimal yubirate) {
        this.yubirate = yubirate;
    }

    public BigDecimal getAutorechargeamt() {
        return autorechargeamt;
    }

    public void setAutorechargeamt(BigDecimal autorechargeamt) {
        this.autorechargeamt = autorechargeamt;
    }

    public String getRechargeinfo() {
        return rechargeinfo;
    }

    public void setRechargeinfo(String rechargeinfo) {
        this.rechargeinfo = rechargeinfo == null ? null : rechargeinfo.trim();
    }

    public String getWithdrawinfo() {
        return withdrawinfo;
    }

    public void setWithdrawinfo(String withdrawinfo) {
        this.withdrawinfo = withdrawinfo == null ? null : withdrawinfo.trim();
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
        sb.append(", coinname=").append(coinname);
        sb.append(", cnname=").append(cnname);
        sb.append(", description=").append(description);
        sb.append(", imgurl=").append(imgurl);
        sb.append(", c2conoff=").append(c2conoff);
        sb.append(", spottoc2conoff=").append(spottoc2conoff);
        sb.append(", c2ctospotonoff=").append(c2ctospotonoff);
        sb.append(", rechspotrate=").append(rechspotrate);
        sb.append(", rechspotonoff=").append(rechspotonoff);
        sb.append(", withspotonoff=").append(withspotonoff);
        sb.append(", withspotrate=").append(withspotrate);
        sb.append(", withc2conoff=").append(withc2conoff);
        sb.append(", c2corderdeposit=").append(c2corderdeposit);
        sb.append(", withamountmax=").append(withamountmax);
        sb.append(", withamountmin=").append(withamountmin);
        sb.append(", withdrawcountmax=").append(withdrawcountmax);
        sb.append(", digtospotonoff=").append(digtospotonoff);
        sb.append(", digwithdrwaonoff=").append(digwithdrwaonoff);
        sb.append(", redpacketmaxamtsingle=").append(redpacketmaxamtsingle);
        sb.append(", transfermaxamtday=").append(transfermaxamtday);
        sb.append(", getAddress=").append(getAddress);
        sb.append(", listTransactions=").append(listTransactions);
        sb.append(", transferAddress=").append(transferAddress);
        sb.append(", spottoyubionoff=").append(spottoyubionoff);
        sb.append(", yubitospotonoff=").append(yubitospotonoff);
        sb.append(", yubitransmin=").append(yubitransmin);
        sb.append(", yubitransmax=").append(yubitransmax);
        sb.append(", yubirate=").append(yubirate);
        sb.append(", autorechargeamt=").append(autorechargeamt);
        sb.append(", rechargeinfo=").append(rechargeinfo);
        sb.append(", withdrawinfo=").append(withdrawinfo);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}