package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TokenApi implements Serializable {
    private static final long serialVersionUID = -3010974005284787645L;

    private Integer id;


    private Integer cointype;

    private String rpcurl;

    private String rpcport;

    private String tokenaddress;

    private String transmethodid;

    private String balancemethodid;

    private BigDecimal wei;

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

    public String getRpcurl() {
        return rpcurl;
    }

    public void setRpcurl(String rpcurl) {
        this.rpcurl = rpcurl == null ? null : rpcurl.trim();
    }

    public String getRpcport() {
        return rpcport;
    }

    public void setRpcport(String rpcport) {
        this.rpcport = rpcport == null ? null : rpcport.trim();
    }

    public String getTokenaddress() {
        return tokenaddress;
    }

    public void setTokenaddress(String tokenaddress) {
        this.tokenaddress = tokenaddress == null ? null : tokenaddress.trim();
    }

    public String getTransmethodid() {
        return transmethodid;
    }

    public void setTransmethodid(String transmethodid) {
        this.transmethodid = transmethodid == null ? null : transmethodid.trim();
    }

    public String getBalancemethodid() {
        return balancemethodid;
    }

    public void setBalancemethodid(String balancemethodid) {
        this.balancemethodid = balancemethodid == null ? null : balancemethodid.trim();
    }

    public BigDecimal getWei() {
        return wei;
    }

    public void setWei(BigDecimal wei) {
        this.wei = wei;
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
        sb.append(", rpcurl=").append(rpcurl);
        sb.append(", rpcport=").append(rpcport);
        sb.append(", tokenaddress=").append(tokenaddress);
        sb.append(", transmethodid=").append(transmethodid);
        sb.append(", balancemethodid=").append(balancemethodid);
        sb.append(", wei=").append(wei);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}