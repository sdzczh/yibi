package com.yibi.core.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DigHonors implements Serializable {
    private static final long serialVersionUID = -8454853224066846271L;

    private Integer id;

    private String minename;

    private String minepicurl;

    private String rolename;

    private Integer rolegrade;

    private Integer soulminforce;

    private Integer soulmaxforce;

    private String rolepicurl;

    private String cointype;

    private List<Integer> mineCoin;

    private Date createtime;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMinename() {
        return minename;
    }

    public void setMinename(String minename) {
        this.minename = minename == null ? null : minename.trim();
    }

    public String getMinepicurl() {
        return minepicurl;
    }

    public void setMinepicurl(String minepicurl) {
        this.minepicurl = minepicurl == null ? null : minepicurl.trim();
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    public Integer getRolegrade() {
        return rolegrade;
    }

    public void setRolegrade(Integer rolegrade) {
        this.rolegrade = rolegrade;
    }

    public Integer getSoulminforce() {
        return soulminforce;
    }

    public void setSoulminforce(Integer soulminforce) {
        this.soulminforce = soulminforce;
    }

    public Integer getSoulmaxforce() {
        return soulmaxforce;
    }

    public void setSoulmaxforce(Integer soulmaxforce) {
        this.soulmaxforce = soulmaxforce;
    }

    public String getRolepicurl() {
        return rolepicurl;
    }

    public void setRolepicurl(String rolepicurl) {
        this.rolepicurl = rolepicurl == null ? null : rolepicurl.trim();
    }

    public String getCointype() {
        return cointype;
    }

    public void setCointype(String cointype) {
        this.cointype = cointype;
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

    public List<Integer> getMineCoin() {
        return mineCoin;
    }

    public void setMineCoin(List<Integer> mineCoin) {
        this.mineCoin = mineCoin;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", minename=").append(minename);
        sb.append(", minepicurl=").append(minepicurl);
        sb.append(", rolename=").append(rolename);
        sb.append(", rolegrade=").append(rolegrade);
        sb.append(", soulminforce=").append(soulminforce);
        sb.append(", soulmaxforce=").append(soulmaxforce);
        sb.append(", rolepicurl=").append(rolepicurl);
        sb.append(", cointype=").append(cointype);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}