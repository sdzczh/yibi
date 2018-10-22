package com.yibi.orderapi.biz.impl;

import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SmsTemplateCode;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.SmsRecord;
import com.yibi.core.entity.Sysparams;
import com.yibi.core.entity.User;
import com.yibi.core.service.SmsRecordService;
import com.yibi.core.service.SysparamsService;
import com.yibi.core.service.UserService;
import com.yibi.extern.api.aliyun.smscode.SMSCodeUtil;
import com.yibi.orderapi.biz.SmsCodeBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/12 0012.
 */
@Slf4j
@Service
@Transactional
public class SmsCodeBizImpl implements SmsCodeBiz{
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private UserService userService;
    @Autowired
    private SMSCodeUtil smsCodeUtil;
    @Autowired
    private SmsRecordService smsRecordService;

    @Override
    public String getValidateCode(String phone, Integer type) {
        Map<String, Object> data = new HashMap<String, Object>();
        Sysparams systemParam = sysparamsService.getValByKey(SystemParams.SMS_ONOFF);
		/*判断功能是否关闭*/
        if(systemParam.getKeyval().equals("-1")){
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }

        /*每分钟最多一次*/
        Map map = new HashMap();
        map.put("phone", phone);
        List<SmsRecord> list = smsRecordService.queryListByTimeLimit(map);
        if(!list.isEmpty() || list.size() != 0){
            return Result.toResult(ResultCode.SMS_COUNTS_LIMIT_ERROR);
        }

        User user = userService.selectByPhone(phone);
        if(type==1){
			/*判断功能是否关闭*/
            Sysparams registerParam = sysparamsService.getValByKey(SystemParams.REGIST_ONOFF);
            if(registerParam==null||registerParam.getKeyval().equals("-1")){
                return Result.toResult(ResultCode.PERMISSION_REGISTER_NO_ACCESS);
            }
			/*注册时判断手机号是不是已经注册*/
            if(user!=null){
                return Result.toResult(ResultCode.USER_HAS_EXISTED);
            }
        }else if(type==2){
			/*其他情况判断用户状态*/
            if(user==null){
                return Result.toResult(ResultCode.USER_NOT_EXIST);
            }else if(user.getState()== GlobalParams.FORBIDDEN){
                return Result.toResult(ResultCode.USER_ACCOUNT_FORBIDDEN);
            }else if(user.getState()==GlobalParams.LOGOFF){
                return Result.toResult(ResultCode.USER_ACCOUNT_LOGOFF);
            }
        }else{
            //参数类型无效
            return Result.toResult(ResultCode.PARAM_IS_INVALID);
        }

		/*发送短信，并处理结果*/

        JSONObject codeJson = smsCodeUtil.getValidateCode(phone, SmsTemplateCode.SMS_VALIDATE_CODE);
        String state =codeJson.getString("code");
        String  code = "";

        if(state.equals("200")){
            code = codeJson.getString("obj");
            if(code==null){
                return Result.toResult(ResultCode.SMS_INTERFACE_ERROR);
            }
        }else if(state.equals("416")){
            return Result.toResult(ResultCode.SMS_FREQUENT_SEND);
        }else{
            return Result.toResult(ResultCode.SMS_INTERFACE_ERROR);
        }

		/*保存短信记录*/
        SmsRecord smsRecord = new SmsRecord();
        smsRecord.setPhone(phone);
        smsRecord.setType(type);
        smsRecord.setState(200);
        smsRecord.setCode(code);
        smsRecord.setTimes(GlobalParams.ACTIVE);
        smsRecordService.insert(smsRecord);

        data.put("codeId", smsRecord.getId());
        return Result.toResult(ResultCode.SUCCESS, data);
    }
}
