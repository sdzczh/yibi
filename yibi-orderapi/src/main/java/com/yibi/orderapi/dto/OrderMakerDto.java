package com.yibi.orderapi.dto;

import lombok.Data;

/**
 * Created by Administrator on 2018/8/2 0002.
 */

@Data
public class OrderMakerDto {
    private Integer id;

    private Integer payType;

    private String price;

    private String totalMin;

    private String totalMax;

    private Integer coinType;

    private String amount;

}
