package com.yibi.core.exception;

/**
 * Created by Administrator on 2018/7/12 0012.
 * 账户余额不足
 */
public class BanlanceNotEnoughException extends RuntimeException {

    public BanlanceNotEnoughException(String message){
        super(message);
    }

}
