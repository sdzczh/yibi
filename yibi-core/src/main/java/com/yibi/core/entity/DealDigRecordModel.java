package com.yibi.core.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DealDigRecordModel {
	private BigDecimal today;

	private BigDecimal yesterday;

	private BigDecimal total;

	private String amount;

	private String createtime;

	private String operType;
}
