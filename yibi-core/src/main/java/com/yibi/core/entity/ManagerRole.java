package com.yibi.core.entity;

import java.io.Serializable;

public class ManagerRole implements Serializable {
    private static final long serialVersionUID = 3128343320633585696L;

    private Integer id;

    private Integer managerid;

    private Integer roleid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getManagerid() {
        return managerid;
    }

    public void setManagerid(Integer managerid) {
        this.managerid = managerid;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", managerid=").append(managerid);
        sb.append(", roleid=").append(roleid);
        sb.append("]");
        return sb.toString();
    }
}