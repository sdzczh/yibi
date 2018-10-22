package com.yibi.turnapi.commons.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidateUtils {
	/**验证是否为手机号*/
    public static boolean isPhone(String userPhone){
    	if(StrUtils.isBlank(userPhone)) return true;
        String regexPhone = "^1\\d{10}$";
        Pattern p = Pattern.compile(regexPhone);
        Matcher m = p.matcher(userPhone);
        return m.matches();
    }

    /**验证是否为6-18位数字和字母的组合*/
    public static boolean isDigitalAndWord(String userInput){
        if (StrUtils.isBlank(userInput)){
            return false;
        }
        //用户代码必须为数字或字母，只能为6~18位
        String regexLoginPwd = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,18}$";
        return userInput.matches(regexLoginPwd);
    }

    /**验证是否为6位数字的交易密码*/
    public static boolean isTradePwd(String userPwd){
        if (StrUtils.isBlank(userPwd)){
            return false;
        }
        String regexTradePwd = "^\\d{6}$";
        return userPwd.matches(regexTradePwd);
    }

    /**验证是否为6位数字的验证码*/
    public static boolean isVerificationCode(String verificationCode){
        if (StrUtils.isBlank(verificationCode)){
            return false;
        }
        String regexTradePwd = "^\\d{6}$";
        return verificationCode.matches(regexTradePwd);
    }
    
    
    public static String replacePwdOfLog(String str){
    	String pwdStr = StrUtils.replaceAll(str, "\"password\":\".+?\"", "\"password\":\"***\"");
    	return StrUtils.replaceAll(pwdStr, "\"oldPassword\":\".+?\"", "\"oldPassword\":\"***\"");
    }

    public static void main(String args[]) {
		String str = "{\"code\":\"528010\",\"codeId\":\"1247\",\"oldPassword\":\"FEDA789A-00E2-49EF-9665-A0417E7A695D\",\"password\":\"111111\",\"secretKey\":\"EQa5Tf6FBh0QuEzW\",\"systemType\":\"2\",\"timeStamp\":\"1517541875000\"}";
		System.out.println(replacePwdOfLog(str));
	}
}
