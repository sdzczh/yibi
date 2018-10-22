package com.yibi.turnapi.commons.enums;

public enum CoinType {
    KN(0,4,"￥"),
    BTC(1,8,""),
    USDT(2,8,"$"),
    LTC(3,8,""),
    ETH(4,8,""),
    ETC(5,8,""),
    HLC(6,4,""),
    HYC(7,4,""),
    DK(8,8,""),
    INC(9,4,""),
    RCC(10,5,""),
    LKS(11,8,""),
    DOGE(12,8,""),
    OIOCOIN(13,8,""),
    EOS(14,8,""),
    COIN_EITHT_1(15,8,""),
    COIN_EITHT_2(16,8,""),
    COIN_EITHT_3(17,8,""),
    COIN_EITHT_4(18,8,""),
    COIN_EITHT_5(19,8,""),
    COIN_EITHT_6(20,8,""),
    COIN_EITHT_7(21,8,""),
    COIN_EITHT_8(22,8,""),
    COIN_SIX_1(23,6,""),
    COIN_SIX_2(24,6,""),
    COIN_SIX_3(25,6,""),
    COIN_SIX_4(26,6,""),
    COIN_FOUR_1(27,4,""),
    FOUR_FOUR_2(28,4,""),
    COIN_FOUR_3(29,4,""),
    COIN_FOUR_4(30,4,"")
    ;
    //币种代码
    private Integer code;
    //币种最大小数位数
    private Integer scale;
    //币种符号
    private String symbol;

    CoinType(Integer code, Integer scale, String symbol) {
        this.code = code;
        this.scale = scale;
        this.symbol = symbol;
    }

    public Integer code() {
        return this.code;
    }

    public Integer scale() {
        return this.scale;
    }

    public String symbol(){
        return this.symbol;
    }

    public static Integer getScale(Integer code) {
        for (CoinType item : CoinType.values()) {
            if (item.code().equals(code)) {
                return item.scale;
            }
        }
        return null;
    }

    public static String getName(Integer code) {
        switch (code) {
            case 0: return "KN";
            case 1: return "BTC";
            case 2: return "USDT";
            case 3: return "LTC";
            case 4: return "ETH";
            case 5: return "ETC";
            case 6: return "HLC";
            case 7: return "HYC";
            case 8: return "DK";
            case 9: return "INC";
            case 10: return "RCC";
            case 11: return "LKS";
            case 12: return "DOGE";
            default:
                return "";
        }
    }
    public static String getAddress(Integer code) {
        switch (code) {
            case 0: return "";
            case 1: return "GET_ADDRESS_BTC";
            case 2: return "GET_ADDRESS_USDT";
            case 3: return "GET_ADDRESS_LTC";
            case 4: return "GET_ADDRESS_ETH";
            case 5: return "GET_ADDRESS_ETC";
            case 6: return "";
            case 7: return "";
            case 8: return "GET_ADDRESS_DK";
            case 9: return "GET_ADDRESS_INC";
            case 10: return "GET_ADDRESS_RCC";
            default:
                return "";
        }
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

    public static CoinType getCoinTypeFromCode(Integer code){
        for (CoinType item : CoinType.values()) {
            if (item.code().equals(code)) {
                return item;
            }
        }
        return null;
    }

}
