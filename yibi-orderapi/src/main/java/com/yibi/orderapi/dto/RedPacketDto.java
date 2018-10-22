package com.yibi.orderapi.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/7/12 0012.
 */
@Data
public class RedPacketDto implements Serializable{

    private String name;
    private String headPath;
    private int coinType;
    private String amount;
    private int num;
    private String remainAmt;
    private int remainNum;
    private String amtOfCny;
    private int state;
    private String note;
    private String inactiveTime;
    private List<?> reciveList;
}
