package com.yibi.core.entity;

import java.io.Serializable;
import java.util.Date;

public class AppVersion implements Serializable {
    private static final long serialVersionUID = -4694299545497271228L;

    private Integer id;

    private Integer phonetype;

    private Integer appversion;

    private Integer type;

    private String address;

    private Integer size;

    private String verificate;

    private Integer state;

    private Date createtime;

    private Date updatetime;

    private String minversion;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPhonetype() {
        return phonetype;
    }

    public void setPhonetype(Integer phonetype) {
        this.phonetype = phonetype;
    }

    public Integer getAppversion() {
        return appversion;
    }

    public void setAppversion(Integer appversion) {
        this.appversion = appversion;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getVerificate() {
        return verificate;
    }

    public void setVerificate(String verificate) {
        this.verificate = verificate == null ? null : verificate.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public String getMinversion() {
        return minversion;
    }

    public void setMinversion(String minversion) {
        this.minversion = minversion == null ? null : minversion.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", phonetype=").append(phonetype);
        sb.append(", appversion=").append(appversion);
        sb.append(", type=").append(type);
        sb.append(", address=").append(address);
        sb.append(", size=").append(size);
        sb.append(", verificate=").append(verificate);
        sb.append(", state=").append(state);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", minversion=").append(minversion);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}