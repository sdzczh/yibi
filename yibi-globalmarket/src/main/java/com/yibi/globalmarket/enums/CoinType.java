package com.yibi.globalmarket.enums;

public enum CoinType {
    USDT(2),
    BTC(1),
    LTC(3),
    ETH(4),
    ETC(5)
    ;
    //币种代码
    private Integer code;
    
    CoinType(Integer code) {
        this.code = code;
    }
    
    public Integer code() {
        return this.code;
    }


    
    public static Integer getCode(String name){
    	switch(name){
    		case "KN" : return 0;
    		case "BTC" : return 1;
    		case "USDT" : return 2;
    		case "LTC" : return 3;
    		case "ETH" : return 4;
    		case "ETC" : return 5;
    		case "HLC" : return 6;
    		case "HYC" : return 7;
    		case "DK" : return 8;
    		case "INC": return 9;
    		case "RCC": return 10;
    		case "LKS": return 11;
    		case "DOGE": return 12;
    		default:
    			return null;
    	}
    	
    }


}
