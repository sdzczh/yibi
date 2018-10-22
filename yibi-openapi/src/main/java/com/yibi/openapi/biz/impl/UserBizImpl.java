package com.yibi.openapi.biz.impl;

import com.yibi.common.encrypt.MD5;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.CalculForceType;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.dao.UserMapper;
import com.yibi.core.entity.*;
import com.yibi.core.service.AccountService;
import com.yibi.core.service.DigcalRecordService;
import com.yibi.core.service.SysparamsService;
import com.yibi.core.service.UserDiginfoService;
import com.yibi.openapi.biz.UserBiz;
import com.yibi.openapi.commons.enums.ResultCode;
import com.yibi.openapi.commons.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserBizImpl implements UserBiz {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserDiginfoService userDiginfoService;
    @Autowired
    private DigcalRecordService digcalRecordService;


    @Override
    public Object validateUser(String phone, String password) throws Exception {
        Map<Object, Object> selectMap = new HashMap<Object, Object>();
        selectMap.put("phone", phone);
        List<User> list = userMapper.selectAll(selectMap);
        User user = null;
        if (list != null && !list.isEmpty()){
            user = list.get(0);
        }
        /*用户状态校验*/
        if(user==null){
            return Result.toResultNotEncrypt(ResultCode.USER_NOT_EXIST);
        }
        if(user.getState()==GlobalParams.FORBIDDEN){
            return Result.toResultNotEncrypt(ResultCode.USER_ACCOUNT_FORBIDDEN);
        }
        if(user.getState()==GlobalParams.LOGOFF){
            return Result.toResultNotEncrypt(ResultCode.USER_ACCOUNT_LOGOFF);
        }

        /*密码校验*/
        if(!password.equals(user.getUserpassword())){
            return Result.toResultNotEncrypt(ResultCode.USER_LOGIN_ERROR);
        }
        return Result.toResultNotEncrypt(ResultCode.SUCCESS);
    }

    @Override
    public Object register(String phone, String password, String referPhone) throws Exception {

        /*判断功能是否关闭*/
        Sysparams systemParam = sysparamsService.getValByKey(SystemParams.REGIST_ONOFF);
        if(systemParam.getKeyval().equals("-1")){
            return Result.toResultNotEncrypt(ResultCode.PERMISSION_NO_ACCESS);
        }

        /*校验手机号是否存在*/
        Map<Object, Object> selectUser = new HashMap<Object, Object>();
        selectUser.put("phone", phone);
        List<User> oldUserList = userMapper.selectAll(selectUser);
        if(oldUserList != null && !oldUserList.isEmpty()){
            return Result.toResultNotEncrypt(ResultCode.USER_HAS_EXISTED);
        }

        /*校验推荐人是否有效*/
        User referUser = null;
        if(!StrUtils.isBlank(referPhone)){
            selectUser.put("phone", referPhone);
            List<User> referUserList = userMapper.selectAll(selectUser);
            if(referUserList != null && !referUserList.isEmpty()){
                referUser = referUserList.get(0);
            }
            if(referUser==null){
                return Result.toResultNotEncrypt(ResultCode.REFER_USER_NOT_EXIST);
            }
            if(referUser.getState()==GlobalParams.FORBIDDEN){
                return Result.toResultNotEncrypt(ResultCode.REFER_USER_ACCOUNT_FORBIDDEN);
            }
            if(referUser.getState()==GlobalParams.LOGOFF){
                return Result.toResultNotEncrypt(ResultCode.REFER_USER_ACCOUNT_LOGOFF);
            }
        }



        /*保存用户信息*/
        User user = new User();
        user.setPhone(phone);
        user.setUserpassword(MD5.getMd5(password));
        user.setUsername(phone);
        user.setState(GlobalParams.ACTIVE);//有效
        user.setReferenceid(StrUtils.isBlank(referPhone)?null: referUser.getId());
        user.setIdstatus(0);//未认证
        user.setDevicenum("");
        user.setRole(0);//普通
        user.setHeadpath(sysparamsService.getValStringByKey(SystemParams.DEFAULT_HEAD_IMG_URL));
        user.setNickname("一币"+phone.substring(phone.length()-4));
        userMapper.insertSelective(user);

        /*创建KN默认现货账户*/
        Account knspot = new Account();
        knspot.setUserid(user.getId());
        knspot.setCointype(CoinType.KN);
        knspot.setAvailbalance(new BigDecimal(0));
        knspot.setFrozenblance(new BigDecimal(0));
        knspot.setAccounttype(GlobalParams.ACCOUNT_TYPE_SPOT);
        accountService.insertSelective(knspot);

        /*创建DK默认现货账户*/
        Account DKspot = new Account();
        DKspot.setUserid(user.getId());
        DKspot.setCointype(CoinType.DK);
        DKspot.setAvailbalance(new BigDecimal(0));
        DKspot.setFrozenblance(new BigDecimal(0));
        DKspot.setAccounttype(GlobalParams.ACCOUNT_TYPE_SPOT);
        accountService.insertSelective(DKspot);

        /*modify by lina 2018-4-11 新用户添加算力  begin*/
        Sysparams param = sysparamsService.getValByKey(SystemParams.CALCULATE_FORCE_REGISTER);
        int forceInc = param==null?0:Integer.valueOf(param.getKeyval());
        UserDiginfo diginfo = new UserDiginfo();
        diginfo.setUserid(user.getId());
        diginfo.setDigcalcul(forceInc);
        diginfo.setLasttime(new Date());
        diginfo.setDigflag(false);
        userDiginfoService.insertSelective(diginfo);

        /*添加算力记录*/
        DigcalRecord rec = new DigcalRecord();
        rec.setUserid(user.getId());
        rec.setType(CalculForceType.REGISTER.getCode());
        rec.setName(CalculForceType.REGISTER.getName());
        rec.setDigcalcul(forceInc);
        rec.setAllcalculforce(diginfo.getDigcalcul());
        digcalRecordService.insertSelective(rec);
        /*modify by lina 2018-4-11 新用户添加算力  end*/
        return Result.toResultNotEncrypt(ResultCode.SUCCESS);
    }

    @Override
    public Object updatePwd(String account, String oldPassword, String password) {
        Map<Object, Object> param = new HashMap<Object, Object>();
        param.put("phone", account);
        User user = null;
        List<User> list = userMapper.selectAll(param);
        if (list != null && !list.isEmpty()) {
            user = list.get(0);
        }
        String pwdEnd = "";
        String oldPwdEnd = "";
        try {
            pwdEnd = MD5.getMd5(password);
            oldPwdEnd = MD5.getMd5(oldPassword);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("密码加密失败");
        }
        if (user == null) {
            return Result.toResultNotEncrypt(ResultCode.FEE_USER_NOT_EXIST);
        }
        /*验证旧密码*/
        if(!user.getUserpassword().equals(oldPwdEnd)){
            return Result.toResultNotEncrypt(ResultCode.OLD_PASSWORD_ERROR);
        }

        user.setUserpassword(pwdEnd);
        userMapper.updateByPrimaryKeySelective(user);
        return Result.toResultNotEncrypt(ResultCode.SUCCESS);
    }
}
