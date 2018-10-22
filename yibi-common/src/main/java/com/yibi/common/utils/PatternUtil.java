package com.yibi.common.utils;

/**
 * Created by BlueT on 2017/3/16.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 正则工具类
 * 提供验证邮箱、手机号、电话号码、身份证号码、数字等方法
 *
 */
public final class PatternUtil {

    /**
     * 验证Email
     * @param email email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

    /**
     * 验证身份证号码
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isIdCard(String idCard) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex,idCard);
    }

    public static boolean isImage(String suffix) {
        if(null != suffix && !"".equals(suffix)){
            String regex = "(.*?)(?i)(jpg|jpeg|png|gif|bmp|webp)";
            return Pattern.matches(regex, suffix);
        }
        return false;
    }

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     * @param mobile 移动、联通、电信运营商的号码段
     *<p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
     *<p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
     *<p>电信的号段：133、153、180（未启用）、189</p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isMobile(String mobile) {
        String regex = "(\\+\\d+)?1[34578]\\d{9}$";
        return Pattern.matches(regex,mobile);
    }

    /**
     * 验证整数（正整数和负整数）
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isDigit(String digit) {
        String regex = "\\-?[1-9]\\d+";
        return Pattern.matches(regex,digit);
    }

    /**
     * 验证整数和浮点数（正负整数和正负浮点数）
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isDecimals(String decimals) {
        String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
        return Pattern.matches(regex,decimals);
    }

    /**
     * 验证空白字符
     * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isBlankSpace(String blankSpace) {
        String regex = "\\s+";
        return Pattern.matches(regex,blankSpace);
    }

    /**
     * 验证中文
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isChinese(String chinese) {
        String regex = "^[\u4E00-\u9FA5]+$";
        return Pattern.matches(regex,chinese);
    }

    /**
     * 验证中文字母数字空格
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isRealName(String chinese) {
        String regex = "^[A-Za-z0-9\\s\u4E00-\u9FA5]+$";
        return Pattern.matches(regex,chinese);
    }

    /**
     * 检测是否是数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        String regex = "^[1-9]\\d*$";
        return Pattern.matches(regex,str);
    }

    /**
     * 验证学生学号
     * @param num
     * @return
     */
    public static boolean isStudentNum(String num) {
        String regex = "^[A-Za-z0-9-_]+$";
        return Pattern.matches(regex,num);
    }

    /**
     * 验证日期（年月日）
     * @param birthday 日期，格式：1992-09-03，或1992.09.03
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isBirthday(String birthday) {
        String regex = "^(\\d{4})-(\\d{2})-(\\d{2})$";
        return Pattern.matches(regex,birthday);
    }

    /**
     * 验证URL地址
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isURL(String url) {
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
        return Pattern.matches(regex, url);
    }

    /**
     * 匹配中国邮政编码
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isPostcode(String postcode) {
        String regex = "[1-9]\\d{5}";
        return Pattern.matches(regex, postcode);
    }

    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isIpAddress(String ipAddress) {
        String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
        return Pattern.matches(regex, ipAddress);
    }

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
}
