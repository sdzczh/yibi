package com.yibi.web.wallet;

/**
 * create by liukeling
 * class name ValidateUtil
 * 校验工具类
 */
public class ValidateUtil {
    private ValidateUtil(){

    }
    /**
     * 判断是否为null
     * @param value  需要判断的数据
     * @return  结果
     */
    public static boolean isNull(Object value){
        if("".equals(value)  || "null".equals(value) || " ".equals(value) || value == null) return true;
        return false;
    }
    public static StringBuffer strTo64(String toAddress) {
        int makeUp = 64 - toAddress.length();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < makeUp; ++i) {
            sb.append("0");
        }

        return sb.append(toAddress);
    }
    public static String hexSubstring(String str) {
        int len = str.length();
        int index = 0;
        char[] strs = str.toCharArray();

        for(int i = 0; i < len; ++i) {
            if ('0' != strs[i]) {
                index = i;
                break;
            }
        }

        return str.substring(index, len);
    }
}
