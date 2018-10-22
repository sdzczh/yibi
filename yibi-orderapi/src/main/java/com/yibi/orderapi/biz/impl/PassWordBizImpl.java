package com.yibi.orderapi.biz.impl;

import com.yibi.common.encrypt.MD5;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.SmsRecord;
import com.yibi.core.entity.Sysparams;
import com.yibi.core.entity.User;
import com.yibi.core.service.SmsRecordService;
import com.yibi.core.service.SysparamsService;
import com.yibi.core.service.UserService;
import com.yibi.orderapi.biz.PassWordBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
@Service("passWordBiz")
public class PassWordBizImpl extends BaseBizImpl implements PassWordBiz {
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private SmsRecordService smsRecordService;
    @Autowired
    private UserService userService;
    @Override
    public String update(User user, String password, String code, Integer codeId) {
        if(code!=null && codeId!=null){
			/*校验验证码是否正确*/
            SmsRecord sms = smsRecordService.getByIdAndPhone(codeId, user.getPhone());
            if(sms==null||!code.equals(sms.getCode().toString())){
                if(validateErrorTimesOfSms(codeId)){
                    return Result.toResult(ResultCode.SMS_CHECK_ERROR);
                }else{
                    return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
                }
            }

			/*校验验证码有效期*/
            Sysparams timeLimit = sysparamsService.getValByKey(SystemParams.SMS_TIME_LIMIT);
            int interval = (int) ((System.currentTimeMillis()-sms.getCreatetime().getTime())/(1000*60));
            if(timeLimit==null || sms.getTimes()!= GlobalParams.ACTIVE|| interval>=Integer.parseInt(timeLimit.getKeyval()) || !validataStateOfSms(codeId)){
                return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
            }
            sms.setTimes(GlobalParams.INACTIVE);
            smsRecordService.updateByPrimaryKey(sms);
        }

        String pwdEnd = "";
        try {
            pwdEnd = MD5.getMd5(password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("密码加密失败");
        }
        user.setUserpassword(pwdEnd);
        userService.updateByPrimaryKey(user);


        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String setOrderPwd(User user, String password, String code, Integer codeId) {
        /*校验验证码是否正确*/
        SmsRecord sms = smsRecordService.getByIdAndPhone(codeId, user.getPhone());
        if(sms==null||!code.equals(sms.getCode().toString())){
            if(validateErrorTimesOfSms(codeId)){
                return Result.toResult(ResultCode.SMS_CHECK_ERROR);
            }else{
                return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
            }
        }

		/*校验验证码有效期*/
        Sysparams timeLimit = sysparamsService.getValByKey(SystemParams.SMS_TIME_LIMIT);
        int interval = (int) ((System.currentTimeMillis()-sms.getCreatetime().getTime())/(1000*60));
        if(timeLimit==null || sms.getTimes()!=GlobalParams.ACTIVE|| interval>=Integer.parseInt(timeLimit.getKeyval()) || !validataStateOfSms(codeId)){
            return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
        }


        String pwdEnd = "";
        try {
            pwdEnd = MD5.getMd5(password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("密码加密失败");
        }

        user.setOrderpwd(pwdEnd);
        userService.updateByPrimaryKey(user);

        sms.setTimes(GlobalParams.INACTIVE);
        smsRecordService.updateByPrimaryKey(sms);
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String updateOrderPwd(User user, String password, String code, Integer codeId) {
        /*校验验证码是否正确*/
        SmsRecord sms = smsRecordService.getByIdAndPhone(codeId, user.getPhone());
        if(sms==null||!code.equals(sms.getCode().toString())){
            if(validateErrorTimesOfSms(codeId)){
                return Result.toResult(ResultCode.SMS_CHECK_ERROR);
            }else{
                return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
            }
        }

		/*校验验证码有效期*/
        Sysparams timeLimit = sysparamsService.getValByKey(SystemParams.SMS_TIME_LIMIT);
        int interval = (int) ((System.currentTimeMillis()-sms.getCreatetime().getTime())/(1000*60));
        if(timeLimit==null || sms.getTimes()!=GlobalParams.ACTIVE|| interval>=Integer.parseInt(timeLimit.getKeyval()) || !validataStateOfSms(codeId)){
            return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
        }

        String pwdEnd = "";
        String oldPwdEnd = "";
        try {
            pwdEnd = MD5.getMd5(password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("密码加密失败");
        }
/*        *//*验证旧密码*//*
        if(!user.getOrderpwd().equals(oldPwdEnd)){
            return Result.toResult(ResultCode.OLD_PASSWORD_ERROR);
        }*/

        user.setOrderpwd(pwdEnd);
        userService.updateByPrimaryKey(user);
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String getback(String phone, String password, String code, Integer codeId) {
        if(code!=null && codeId!=null){
			/*校验验证码是否正确*/
            SmsRecord sms = smsRecordService.getByIdAndPhone(codeId, phone);
            if(sms==null||!code.equals(sms.getCode().toString())){
                if(validateErrorTimesOfSms(codeId)){
                    return Result.toResult(ResultCode.SMS_CHECK_ERROR);
                }else{
                    return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
                }
            }

			/*校验验证码有效期*/
            Sysparams timeLimit = sysparamsService.getValByKey(SystemParams.SMS_TIME_LIMIT);
            int interval = (int) ((System.currentTimeMillis()-sms.getCreatetime().getTime())/(1000*60));
            if(timeLimit==null || sms.getTimes()!= GlobalParams.ACTIVE|| interval>=Integer.parseInt(timeLimit.getKeyval()) || !validataStateOfSms(codeId)){
                return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
            }
            sms.setTimes(GlobalParams.INACTIVE);
            smsRecordService.updateByPrimaryKey(sms);
        }

        String pwdEnd = "";
        User user = userService.selectByPhone(phone);
        try {
            pwdEnd = MD5.getMd5(password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("密码加密失败");
        }
        user.setUserpassword(pwdEnd);
        userService.updateByPrimaryKey(user);


        return Result.toResult(ResultCode.SUCCESS);
    }
}
