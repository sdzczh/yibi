package com.yibi.core.entity;

import java.io.Serializable;

public class Role implements Serializable {
    private static final long serialVersionUID = -3597014698495028889L;

    private Integer id;

    private String rolecode;

    private String rolename;

    private String roledesc;

    private Integer ordernum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolecode() {
        return rolecode;
    }

    public void setRolecode(String rolecode) {
        this.rolecode = rolecode == null ? null : rolecode.trim();
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    public String getRoledesc() {
        return roledesc;
    }

    public void setRoledesc(String roledesc) {
        this.roledesc = roledesc == null ? null : roledesc.trim();
    }

    public Integer getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", rolecode=").append(rolecode);
        sb.append(", rolename=").append(rolename);
        sb.append(", roledesc=").append(roledesc);
        sb.append(", ordernum=").append(ordernum);
        sb.append("]");
        return sb.toString();
    }
}