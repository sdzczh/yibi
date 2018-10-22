package com.yibi.orderapi.dto;

import lombok.Data;

@Data
public class WithdrawModel {
	private Integer id;

	private Integer userid;

	private Integer type;

	private Integer accounttype;

	private String payaddress;

	private String amount;

	private String fee;

	private String remain;

	private Integer cointype;

	private Integer state;

	private String ordernum;

	private Integer operid;

	private String remark;

	private String createtime;

	private String updatetime;
}
