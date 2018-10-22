package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;
import com.yibi.orderapi.enums.ResultCode;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public interface UserBiz {
    User queryUser();

    /**
     * 用户注册
     * @param phone 手机号
     * @param code 验证码
     * @param codeId 验证码ID
     * @param password 密码
     * @param referPhone 推荐人手机号
     * @param deviceNum 设备号
     * @param syetemType 手机类型
     * @return
     */
    String register(String phone, String code, Integer codeId, String password, String referPhone, String deviceNum, Integer syetemType) throws Exception;

    /**
     * 用户密码登录
     * @param phone 手机号
     * @param password 密码
     * @param deviceNum 设备号
     * @param syetemType 手机类型
     * @param secretKey 私钥
     * @return
     */
    String login(String phone, String password, String deviceNum, Integer syetemType, String secretKey) throws Exception;

    /**
     * 验证码登录
     * @param phone 手机号
     * @param code 验证码
     * @param codeId 验证码ID
     * @param deviceNum 设备号
     * @param syetemType 手机类型
     * @param secretKey 私钥
     * @return
     */
    String loginByPhone(String phone, String code, Integer codeId, String deviceNum, Integer systemType, String secretKey);

    /**
     * 设置头像
     * @param user
     * @param imgPath
     * @return
     */
    String setHeadimg(User user, String imgPath);

    /**
     * 设置昵称
     * @param user
     * @param nickname
     * @return
     */
    String setNickname(User user, String nickname);

    /**
     * 绑定支付信息
     * @param user
     * @param password 交易密码
     * @param account  帐号
     * @param name  姓名
     * @param imgUrl  支付二维码url
     * @param bankName 银行名称
     * @param branchName 开户行地址
     * @param type 类型 0支付宝 1微信 2银行卡
     * @return
     */
    String bindInfo(User user,String password,String account,String name ,String imgUrl,String bankName,String branchName,Integer type);

    /**
     * 退出登录
     * @param user
     * @return
     */
    ResultCode exit(User user);

    /**
     * 实名认证初始化 获取token
     * @param user
     * @return
     */
    String getToken(User user);


    /**
     * 获取用户魂力-称号
     * @param user
     * @return
     */
    String getCalcul(User user);

    /**
     * 获取实人认证信息
     * @param user
     * @param taskId
     * @return String
     * @date 2018-1-18
     * @author lina
     */
    String getStatus(User user, String taskId);
}
