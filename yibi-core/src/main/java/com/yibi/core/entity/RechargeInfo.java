package com.yibi.core.entity;

import java.io.Serializable;

public class RechargeInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String cnName;
	private String coinName;
	private String rechAddress;
	private String rechargeInfo;

	public String getWithDrawInfo() {
		return withDrawInfo;
	}

	public void setWithDrawInfo(String withDrawInfo) {
		this.withDrawInfo = withDrawInfo;
	}

	public String getRechargeInfo() {
		return rechargeInfo;
	}

	public void setRechargeInfo(String rechargeInfo) {
		this.rechargeInfo = rechargeInfo;
	}

	private String withDrawInfo;

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public String getRechAddress() {
		return rechAddress;
	}

	public void setRechAddress(String rechAddress) {
		this.rechAddress = rechAddress;
	}
}
