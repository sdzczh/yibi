package com.yibi.core.entity;

import java.io.Serializable;
import java.util.Date;

public class CoinIntroduction implements Serializable {
    private static final long serialVersionUID = 676519784987251440L;

    private Integer id;

    private Integer cointype;

    private String coinname;

    private String releasetime;

    private String releasetotalamt;

    private String circulationtotalamt;

    private String crowdprice;

    private String whitepaper;

    private String officialneturl;

    private String blockquery;

    private String introduction;

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

    public String getCoinname() {
        return coinname;
    }

    public void setCoinname(String coinname) {
        this.coinname = coinname == null ? null : coinname.trim();
    }

    public String getReleasetime() {
        return releasetime;
    }

    public void setReleasetime(String releasetime) {
        this.releasetime = releasetime == null ? null : releasetime.trim();
    }

    public String getReleasetotalamt() {
        return releasetotalamt;
    }

    public void setReleasetotalamt(String releasetotalamt) {
        this.releasetotalamt = releasetotalamt == null ? null : releasetotalamt.trim();
    }

    public String getCirculationtotalamt() {
        return circulationtotalamt;
    }

    public void setCirculationtotalamt(String circulationtotalamt) {
        this.circulationtotalamt = circulationtotalamt == null ? null : circulationtotalamt.trim();
    }

    public String getCrowdprice() {
        return crowdprice;
    }

    public void setCrowdprice(String crowdprice) {
        this.crowdprice = crowdprice == null ? null : crowdprice.trim();
    }

    public String getWhitepaper() {
        return whitepaper;
    }

    public void setWhitepaper(String whitepaper) {
        this.whitepaper = whitepaper == null ? null : whitepaper.trim();
    }

    public String getOfficialneturl() {
        return officialneturl;
    }

    public void setOfficialneturl(String officialneturl) {
        this.officialneturl = officialneturl == null ? null : officialneturl.trim();
    }

    public String getBlockquery() {
        return blockquery;
    }

    public void setBlockquery(String blockquery) {
        this.blockquery = blockquery == null ? null : blockquery.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
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
        sb.append(", releasetime=").append(releasetime);
        sb.append(", releasetotalamt=").append(releasetotalamt);
        sb.append(", circulationtotalamt=").append(circulationtotalamt);
        sb.append(", crowdprice=").append(crowdprice);
        sb.append(", whitepaper=").append(whitepaper);
        sb.append(", officialneturl=").append(officialneturl);
        sb.append(", blockquery=").append(blockquery);
        sb.append(", introduction=").append(introduction);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}