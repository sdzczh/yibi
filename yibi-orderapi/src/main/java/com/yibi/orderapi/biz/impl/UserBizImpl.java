package com.yibi.orderapi.biz.impl;

import com.google.common.eventbus.EventBus;
import com.yibi.common.encrypt.MD5;
import com.yibi.common.utils.*;
import com.yibi.core.constants.CalculForceType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import com.yibi.extern.api.aliyun.cloudauth.AliyunRPBasicAuthenticate;
import com.yibi.extern.api.aliyun.cloudauth.MaterialModel;
import com.yibi.extern.api.rongcloud.request.RongCloudUserRequest;
import com.yibi.orderapi.biz.ChatGroupBiz;
import com.yibi.orderapi.biz.IdCardValidateBiz;
import com.yibi.orderapi.biz.UserBiz;
import com.yibi.orderapi.dto.IdCardListenerBean;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
@Slf4j
@Service
@Transactional
public class UserBizImpl extends BaseBizImpl implements UserBiz{

    @Resource
    private UserService  userService;
    @Resource
    private SmsRecordService smsRecordService;
    @Resource
    private AccountService accountService;
    @Resource
    private DigcalRecordService digcalRecordService;
    @Resource
    private UserBindAccountService userBindAccountService;
    @Resource
    private SysparamsService sysparamsService;
    @Resource
    private UserDiginfoService userDiginfoService;
    @Autowired
    private RedisTemplate<String, String> redis;
    @Autowired
    private RongCloudUserRequest userRequest;
    @Autowired
    private ChatGroupBiz chatGroupBiz;
    @Autowired
    private IdCardValidateBiz idcardValidateBiz;
    @Autowired
    private IdcardValidateService idcardValidateService;
    @Autowired
    private BindInfoService bindInfoService;
    @Autowired
    private DigHonorsService digHonorsService;
    @Autowired
    private CommissionInviteService commissionInviteService;
    @Autowired
    private CoinManageService coinManageService;
    @Resource
    private EventBus orderEventBus;
    @Resource
    private AliyunRPBasicAuthenticate aliyunRPBasicAuthenticate;


    @Override
    public User queryUser() {
        return userService.selectByPrimaryKey(39);
    }

    @Override
    public String register(String phone, String code, Integer codeId, String password, String referPhone, String deviceNum, Integer syetemType) throws Exception {
        /*判断功能是否关闭*/
        Sysparams systemParam = sysparamsService.getValByKey(SystemParams.REGIST_ONOFF);
        if(systemParam.getKeyval().equals("-1")){
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }

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

		/*校验手机号是否存在*/
        User oldUser = userService.selectByPhone(phone);
        if(oldUser!=null){
            return Result.toResult(ResultCode.USER_HAS_EXISTED);
        }

		/*校验推荐人是否有效*/
        User referUser = null;
        if(!StrUtils.isBlank(referPhone)){
            referUser = userService.selectByPhone(referPhone);
            if(referUser==null){
                return Result.toResult(ResultCode.REFER_USER_NOT_EXIST);
            }

            if(referUser.getState()==GlobalParams.FORBIDDEN){
                return Result.toResult(ResultCode.REFER_USER_ACCOUNT_FORBIDDEN);
            }
            if(referUser.getState()==GlobalParams.LOGOFF){
                return Result.toResult(ResultCode.REFER_USER_ACCOUNT_LOGOFF);
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
        user.setDevicenum(deviceNum);
        user.setRole(GlobalParams.ROLE_TYPE_COMMON);//普通
        user.setIdcard("");
        user.setSecretkey("");
        user.setToken("");
        user.setOrderpwd("");
        user.setNickname("一币"+phone.substring(phone.length()-4));
        user.setHeadpath(sysparamsService.getValByKey(SystemParams.DEFAULT_HEAD_IMG_URL).getKeyval());
//        user.setHeadpath("1");
        user.setPartnerflag(2); //普通用户
        userService.insert(user);

        Map map = new HashMap();
        List<CoinManage> list = coinManageService.selectAll(map);
        /*初始化C2C账户*/
        for(int i = 0; i < list.size(); i++){
            Account account = new Account();
            account.setUserid(user.getId());
            account.setCointype(list.get(i).getCointype());
            account.setAvailbalance(new BigDecimal(0));
            account.setFrozenblance(new BigDecimal(0));
            account.setAccounttype(0);
            accountService.insert(account);
        }
        /*初始化现货账户*/
        for(int i = 0; i < list.size(); i++){
            Account account = new Account();
            account.setUserid(user.getId());
            account.setCointype(list.get(i).getCointype());
            account.setAvailbalance(new BigDecimal(0));
            account.setFrozenblance(new BigDecimal(0));
            account.setAccounttype(1);
            accountService.insert(account);
        }
        /*初始化余币宝账户*/
        for(int i = 0; i < list.size(); i++){
            Account account = new Account();
            account.setUserid(user.getId());
            account.setCointype(list.get(i).getCointype());
            account.setAvailbalance(new BigDecimal(0));
            account.setFrozenblance(new BigDecimal(0));
            account.setAccounttype(4);
            accountService.insert(account);
        }


		/*modify by lina 2018-4-11 新用户添加算力  begin*/
        Sysparams param = sysparamsService.getValByKey(SystemParams.CALCULATE_FORCE_REGISTER);
        int forceInc = param==null?0:Integer.valueOf(param.getKeyval());
        UserDiginfo diginfo = new UserDiginfo();
        diginfo.setUserid(user.getId());
        diginfo.setDigcalcul(forceInc);
        diginfo.setDigflag(false);
        diginfo.setLogindays(0);
        diginfo.setLasttime(null);
        diginfo.setDayrewardstate(false);
        diginfo.setTenrewardstate(false);
        diginfo.setMonthrewardstate(false);
        userDiginfoService.saveOrUpdate(diginfo);

		/*添加算力记录*/
        DigcalRecord rec = new DigcalRecord();
        rec.setUserid(user.getId());
        rec.setType(CalculForceType.REGISTER.getCode());
        rec.setName(CalculForceType.REGISTER.getName());
        rec.setDigcalcul(forceInc);
        rec.setAllcalculforce(diginfo.getDigcalcul());
        digcalRecordService.insert(rec);
		/*modify by lina 2018-4-11 新用户添加算力  end*/

        sms.setTimes(GlobalParams.INACTIVE);
        smsRecordService.updateByPrimaryKey(sms);

        /*获取融云token*/
        Map<Object, Object> params = new HashMap<>();
        params.put("userid", user.getId());
        params.put("type", GlobalParams.BIND_ACCOUNT_RONGCLOUD);
        UserBindAccount bindAccount = userBindAccountService.selectByUserAndType(params);
        if(bindAccount == null){
            bindAccount = new UserBindAccount();
            bindAccount.setUserid(user.getId());
            bindAccount.setExpfield1(GlobalParams.INACTIVE+"");//是否已经自动加入群组
            bindAccount.setType(GlobalParams.BIND_ACCOUNT_RONGCLOUD);
            try {
                bindAccount.setToken(userRequest.getToken(user.getPhone(), user.getNickname(), user.getHeadpath()));
            } catch (Exception e) {
                e.printStackTrace();
                return Result.toResult(ResultCode.RONGCLOUD_INTERFACE_ERROR);
            }
            userBindAccountService.insert(bindAccount);
        }

        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String login(String phone, String password, String deviceNum, Integer syetemType, String secretKey) throws Exception {
        User user = userService.selectByPhone(phone);
		
		/*用户状态校验*/
        if(user==null){
            return Result.toResult(ResultCode.USER_NOT_EXIST);
        }
        if(user.getState()==GlobalParams.FORBIDDEN){
            return Result.toResult(ResultCode.USER_ACCOUNT_FORBIDDEN);
        }
        if(user.getState()==GlobalParams.LOGOFF){
            return Result.toResult(ResultCode.USER_ACCOUNT_LOGOFF);
        }
		
		/*密码校验*/
        String passwordEn = MD5.getMd5(password);
        if(!passwordEn.equals(user.getUserpassword())){
            return Result.toResult(ResultCode.USER_LOGIN_ERROR);
        }

        //登录时给用户添加现有所有账户钱包
        insertAccount(user);
        return getLoginInfo(user,deviceNum,syetemType,secretKey);
    }


    @Override
    public String loginByPhone(String phone, String code, Integer codeId, String deviceNum, Integer systemType, String secretKey) {
        User user = userService.selectByPhone(phone);

		/*用户状态校验*/
        if(user==null){
            return Result.toResult(ResultCode.USER_NOT_EXIST);
        }
        if(user.getState()==GlobalParams.FORBIDDEN){
            return Result.toResult(ResultCode.USER_ACCOUNT_FORBIDDEN);
        }
        if(user.getState()==GlobalParams.LOGOFF){
            return Result.toResult(ResultCode.USER_ACCOUNT_LOGOFF);
        }

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
        if(timeLimit==null || sms.getTimes()!=GlobalParams.ACTIVE|| interval>=Integer.parseInt(timeLimit.getKeyval()) || !validataStateOfSms(codeId)){
            return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
        }

        //登录时给用户添加现有所有账户钱包
        insertAccount(user);
        return getLoginInfo(user,deviceNum,systemType,secretKey);
    }


    /**
     * 登录时给用户添加现有所有账户钱包
     * @param user
     */
    private void insertAccount(User user) {
        Map map = new HashMap();
        List<CoinManage> list = coinManageService.selectAll(map);
        List<Integer> accountTypeList = new ArrayList<>();
        accountTypeList.add(GlobalParams.ACCOUNT_TYPE_C2C);
        accountTypeList.add(GlobalParams.ACCOUNT_TYPE_SPOT);
        accountTypeList.add(GlobalParams.ACCOUNT_TYPE_YUBI);
        for(Integer accountType : accountTypeList) {
            for(CoinManage coinManage :  list){
                Account account = accountService.queryByUserIdAndCoinTypeAndAccountType(user.getId(), coinManage.getCointype(), accountType);
                if(account == null){
                    account = new Account();
                    account.setUserid(user.getId());
                    account.setCointype(coinManage.getCointype());
                    account.setAvailbalance(new BigDecimal(0));
                    account.setFrozenblance(new BigDecimal(0));
                    account.setAccounttype(accountType);
                    accountService.insert(account);
                    log.info("用户：【" + user.getPhone() + "】增加了账户：【" + accountType + "】,币种：【" + coinManage.getCointype() + "】");
                }
            }
        }
    }

    @Override
    public String setHeadimg(User user, String imgPath) {
        user.setHeadpath(imgPath);
        userService.updateByPrimaryKey(user);

		/*同步融云用户信息*/
        try {
            userRequest.update(user.getPhone(), user.getNickname(), user.getHeadpath());
        } catch (Exception e) {
            log.info("融云-头像修改失败");
            e.printStackTrace();
            throw new RuntimeException();
        }
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String setNickname(User user, String nickname) {
        user.setNickname(nickname);
        userService.updateByPrimaryKey(user);

		/*同步融云用户信息*/
        try {
            userRequest.update(user.getPhone(), user.getNickname(), user.getHeadpath());
        } catch (Exception e) {
            log.info("融云-昵称修改失败");
            e.printStackTrace();
            throw new RuntimeException();
        }
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String bindInfo(User user,String password,String account,String name ,String imgUrl,String bankName,String branchName,Integer type) {
        Map<String, Object>map = new HashMap<String, Object>();
        /*校验交易密码*/
        if(!StrUtils.isBlank(password)){
            String valiStr = validateOrderPassword(user, password);
            if(valiStr!=null){
                return valiStr;
            }
        }

        BindInfo bind = new BindInfo();
        bind.setUserid(user.getId());
        bind.setType(type);
        bind.setAccount(account);
        bind.setName(name);
        bind.setImgurl(imgUrl);
        bind.setBankname(bankName);
        bind.setBranchname(branchName);
        bind.setState(GlobalParams.ACTIVE);
        bindInfoService.updateOrInsertBindInfo(bind);
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public ResultCode exit(User user) {
        user.setSecretkey("");
        user.setToken("");
        user.setTokencreatetime(null);

        userService.updateByPrimaryKey(user);
        return ResultCode.SUCCESS;
    }

    @Override
    public String getToken(User user) {
        /*判断功能是否关闭*/
        Sysparams systemParam = sysparamsService.getValByKey(SystemParams.REAL_NAME_ONOFF);
        if(systemParam==null||systemParam.getKeyval().equals("-1")){
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }

		/*用户是否已经实名*/
        if(user.getIdstatus()==GlobalParams.ACTIVE){
            return Result.toResult(ResultCode.REAL_NAME_FINISHED);
        }
		/*判断验证次数*/
        Map<String, Object> countMap = idcardValidateBiz.queryValidateTimes(user.getId(),3);
        Sysparams timesLimit = sysparamsService.getValByKey(SystemParams.IDCARD_VALIDATE_TIMES_LIMIT);
        if(timesLimit==null){
            return Result.toResult(ResultCode.SYSTEM_PARAM_ERROR);
        }
        int times = Integer.parseInt(timesLimit.getKeyval());
        if(countMap!=null&&times>0){
            //当日认证次数
            BigInteger dateCount = (BigInteger)countMap.get(DATE.getCurrentDateStr());
            if(dateCount!=null&&dateCount.intValue()>=times){
                return Result.toResult(ResultCode.REAL_NAME_LIMIT);
            }

            //连续两天次数限制
            BigInteger dateCount1 = (BigInteger)countMap.get(DateUtils.getSomeDay(-1));
            BigInteger dateCount2 = (BigInteger)countMap.get(DateUtils.getSomeDay(-2));
            BigInteger dateCount3 = (BigInteger)countMap.get(DateUtils.getSomeDay(-3));
            if((dateCount1!=null&&dateCount1.intValue()>=times&&dateCount2!=null&&dateCount2.intValue()>=times)
                    ||(dateCount2!=null&&dateCount2.intValue()>=times&&dateCount3!=null&&dateCount3.intValue()>=times)){
                return Result.toResult(ResultCode.REAL_NAME_LIMIT);
            }
        }
        String ticketId = UuidUtil.get32UUID();
        String token = aliyunRPBasicAuthenticate.getVerifyToken(ticketId);
        if(token == null){
            return Result.toResult(ResultCode.REAL_NAME_INIT_FAIL);
        }
        Map<String, Object>  map = new HashMap<String, Object>();
        map.put("token", token);
        map.put("taskId", ticketId);

        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String getCalcul(User user) {
        Map<Object, Object> data = new HashMap<>();
        Map<Object, Object> params = new HashMap<>();
        Map<Object, Object> param = new HashMap<>();
        params.put("userid", user.getId());
        List<UserDiginfo> list = userDiginfoService.selectAll(params);
        if(list == null || list.isEmpty() || list.size() == 0){
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
        Integer calCul = list.get(0).getDigcalcul();
        DigHonors dh = digHonorsService.selectByCalcul(calCul);
        if(dh == null){
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
        Integer roleGrade = dh.getRolegrade();
        String result = dh.getRolename() + " " + calCul;
        param.put("userid", user.getId());
        List<IdcardValidate> u = idcardValidateService.selectAll(param);
        if(u != null && !u.isEmpty() && u.size() != 0){
            data.put("sex", u.get(0).getSex());
        }
        data.put("grade", result);
        return Result.toResult(ResultCode.SUCCESS, data);
    }

    @Override
    public String getStatus(User user, String taskId) {
        /*用户是否已经实名*/
        if(user.getIdstatus()==GlobalParams.ACTIVE){
            return Result.toResult(ResultCode.SUCCESS);
        }


        Integer status = aliyunRPBasicAuthenticate.getStatus(taskId);
        //认证记录不存在，直接返回
        if(status==GlobalParams.REALNAME_STATE_NOT_EXIST){
            return Result.toResult(ResultCode.REAL_NAME_TASK_NOT_EXIST);
        }
        //认证中，等待两秒继续请求一次
        if(status==GlobalParams.REALNAME_STATE_ING){
            try {
                TimeUnit.SECONDS.sleep(2);
                status = aliyunRPBasicAuthenticate.getStatus(taskId);
            } catch (InterruptedException e) {
                log.info("实人认证等待被打断---");
                e.printStackTrace();
            }
        }
        HashMap<String, Object> map = new HashMap<>();

        IdcardValidate iv = idcardValidateService.queryByTaskId(taskId);
        if(iv!=null){
            if(iv.getState() ==GlobalParams.REALNAME_STATE_SUCCESS ){
                map.put("name", iv.getName());
                return Result.toResult(ResultCode.SUCCESS, map);
            }else{
                if(iv.getState() == GlobalParams.REALNAME_STATE_FAIL){
                    return Result.toResult(ResultCode.REAL_NAME_FAIL);
                }
                if(iv.getState() == GlobalParams.REALNAME_STATE_AGE_ILLEGAL){
                    return Result.toResult(ResultCode.REAL_NAME_AGE_ILLEGAL);
                }
                if(iv.getState() == GlobalParams.REALNAME_STATE_IDCARD_EXIST){
                    return Result.toResult(ResultCode.REAL_NAME_IDCARD_EXIST);
                }
            }
        }
        ResultCode code = ResultCode.REAL_NAME_FAIL;
        iv = new IdcardValidate();
        if(status ==GlobalParams.REALNAME_STATE_SUCCESS ||status==GlobalParams.REALNAME_STATE_FAIL){
            MaterialModel mate = aliyunRPBasicAuthenticate.getMaterials(taskId,status);
            BeanUtils.copyProperties(mate, iv);
            iv.setState(status);
            iv.setTaskid(taskId);
            iv.setUserid(user.getId());
            iv.setIdentificationnumber(mate.getIdentificationNumber());
        }

        if(status==GlobalParams.REALNAME_STATE_SUCCESS){
            User old = userService.getByidcard(iv.getIdentificationnumber());
            //年龄判断
            int age = DateUtils.idCardToAge(iv.getIdentificationnumber().substring(6,14));
            if(age<18||age>70){
                iv.setState(GlobalParams.REALNAME_STATE_AGE_ILLEGAL);
                code = ResultCode.REAL_NAME_AGE_ILLEGAL;
            }
            //身份证是否已经认证过
            else if( old!=null && old.getId()!=user.getId()){
                iv.setState(GlobalParams.REALNAME_STATE_IDCARD_EXIST);
                code = ResultCode.REAL_NAME_IDCARD_EXIST;
            }else{
                user.setIdstatus(GlobalParams.ACTIVE);
                user.setIdcard(iv.getIdentificationnumber());
                user.setUsername(iv.getName());
                userService.updateByPrimaryKeySelective(user);
                code = ResultCode.SUCCESS;
                map.put("name", iv.getName());
            }
        }

        //下载图片
        downloadIdCardPic(iv,user.getPhone());

        if(code == ResultCode.SUCCESS){
            //实名奖励
            commission_realName(user);
            //算力奖励
            calculate_force(user);
        }
        return Result.toResult(code,map);
    }


    /*实名奖励*/
    public void commission_realName(User user){
        Sysparams onoff = sysparamsService.getValByKey(SystemParams.COMMISSION_REALNAME_ONOFF);
        if(onoff == null ||"1".equals(onoff.getKeyval())){

            Sysparams realCoin = sysparamsService.getValByKey(SystemParams.COMMISSION_REALNAME_COIN);  //奖励币种 json
            String realCoinStr = realCoin.getKeyval();
            //String a=realCoinStr.replace("[","").replace("]","");
             if(!StrUtils.isBlank(realCoinStr)){
                String[] array = realCoinStr.split(",");
                Sysparams realCoinAmount = sysparamsService.getValByKey(SystemParams.COMMISSION_REALNAME_COIN_AMOUNT_USER);   //奖励币种对应奖励数量--用户 json
                Sysparams realCoinAmountRefer = sysparamsService.getValByKey(SystemParams.COMMISSION_REALNAME_COIN_AMOUNT_REFER);   //奖励币种对应奖励数量--推荐人 json
                Integer i = 0;
                String realCoinAmountStr = realCoinAmount.getKeyval();
                String b=realCoinAmountStr.replace("[","").replace("]","");
                String[] realCoinAmountStrArray = b.split(",");
                List<String> realCoinAmountList = new ArrayList<String>(); //奖励币种对应奖励数量 list
                for (String str : realCoinAmountStrArray){
                    realCoinAmountList.add(str);
                }
                String realCoinAmountReferStr = realCoinAmountRefer.getKeyval();
                String c=realCoinAmountReferStr.replace("[","").replace("]","");
                String[] realCoinAmountReferStrArray = c.split(",");
                List<String> realCoinAmountReferList = new ArrayList<String>(); //奖励币种对应奖励数量 list
                for (String str : realCoinAmountReferStrArray){
                    realCoinAmountReferList.add(str);
                }
                for (int j =0;j<array.length ;j++) {
                    Integer coinType = Integer.valueOf(array[j]);
                    String amountUser;
                    String amountRefer;
                    CommissionInvite comm = null;
                    try {
                        amountUser = realCoinAmountList.get(i);
                        amountRefer = realCoinAmountReferList.get(i);
                        comm = createCommission(user, coinType, amountUser, amountRefer);
                    } catch (Exception e1) {
                        throw new RuntimeException("系统参数格式配置错误");
                    }
                    try {
                        //用户奖励
                        accountService.updateAccountAndInsertFlow(user.getId(), GlobalParams.ACCOUNT_TYPE_SPOT, coinType, comm.getAmount(), BigDecimal.ZERO, user.getId(), "实名奖励", comm.getId());
                        //推荐人奖励
                        if(user.getReferenceid()!=null && user.getReferenceid() >0) {
                            User referUser = userService.selectByPrimaryKey(user.getReferenceid());
                            if (referUser.getToken() != "" && referUser.getToken().length() > 5) {
                                accountService.updateAccountAndInsertFlow(user.getReferenceid(), GlobalParams.ACCOUNT_TYPE_SPOT, coinType, comm.getReferamount(), BigDecimal.ZERO, user.getReferenceid(), "实名推荐人奖励", comm.getId());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException();
                    }
                    i++;
                    log.info("【实名认证奖励】【"+user.getPhone()+"】增加了币种编码【"+coinType+"】数量【"+amountUser+"】");
                }
            }
        }
    }

    public CommissionInvite createCommission(User user, Integer coinType, String amountUser, String amountRefer){
		/*用户奖励金额*/
		Sysparams commAmtParam = sysparamsService.getValByKey(SystemParams.COMMISSION_REALNAME_AMOUNT_USER);
		String commAmt = commAmtParam==null?"0":commAmtParam.getKeyval();

		/*推荐人奖励金额*/
		Sysparams referAmtParam = sysparamsService.getValByKey(SystemParams.COMMISSION_REALNAME_AMOUNT_REFER);
		String referAmt = referAmtParam==null?"0":referAmtParam.getKeyval();

        CommissionInvite comm = new CommissionInvite();
        comm.setUserid(user.getId());
        comm.setCointype(coinType);
        comm.setAmount(new BigDecimal(amountUser));
        comm.setReferuserid(user.getReferenceid());
        comm.setReferamount(new BigDecimal(amountRefer));
        comm.setType(GlobalParams.COMMISSION_TYPE_REALNAME);
        commissionInviteService.insertSelective(comm);
        return comm;
    }
    /*算力奖励*/
    public void calculate_force(User user){
		/*modify by lina 2018-4-11 新用户实名添加算力，并给推荐人邀请算力  begin*/
        Sysparams param = sysparamsService.getValByKey(SystemParams.CALCULATE_FORCE_REALNAME);
        int forceInc = param==null?0:Integer.valueOf(param.getKeyval());
        UserDiginfo diginfo = userDiginfoService.queryByUserId(user.getId());
        if(diginfo ==null ){
            diginfo = new UserDiginfo();
            diginfo.setUserid(user.getId());
        }
        diginfo.setDigcalcul(diginfo.getDigcalcul()+forceInc);
        userDiginfoService.saveOrUpdate(diginfo);

		/*添加算力记录*/
        DigcalRecord rec = new DigcalRecord();
        rec.setUserid(user.getId());
        rec.setType(CalculForceType.REALNAME.getCode());
        rec.setName(CalculForceType.REALNAME.getName());
        rec.setDigcalcul(forceInc);
        rec.setAllcalculforce(diginfo.getDigcalcul());
        digcalRecordService.insertSelective(rec);

		/*添加推荐人算力*/
        if(user.getReferenceid()!=null && user.getReferenceid() >0){
            Sysparams param1 = sysparamsService.getValByKey(SystemParams.CALCULATE_FORCE_INVITE);
            int forceInc1 = param1==null?0:Integer.valueOf(param1.getKeyval());
            UserDiginfo diginfo1 = userDiginfoService.queryByUserId(user.getReferenceid());
            if(diginfo1 ==null ){
                diginfo1 = new UserDiginfo();
                diginfo1.setUserid(user.getReferenceid());
                diginfo1.setDigcalcul(0);
                diginfo1.setDigflag(false);
                diginfo1.setLogindays(0);
                diginfo1.setDayrewardstate(false);
                diginfo1.setTenrewardstate(false);
                diginfo1.setMonthrewardstate(false);
                diginfo1.setLasttime(new Date());
            }
            diginfo1.setDigcalcul(diginfo1.getDigcalcul()+forceInc1);
            userDiginfoService.saveOrUpdate(diginfo1);

            DigcalRecord rec1 = new DigcalRecord();
            rec1.setUserid(user.getReferenceid());
            rec1.setType(CalculForceType.INVITE.getCode());
            rec1.setName(CalculForceType.INVITE.getName());
            rec1.setDigcalcul(forceInc1);
            rec1.setAllcalculforce(diginfo1.getDigcalcul());
            digcalRecordService.insertSelective(rec1);

			/*判断是否升级发送推送消息*/
//			talkMenuService.cheackIsUp(user.getReferenceId(), forceInc1, GlobalParams.PUSH_TO_INDEX);
        }
		/*modify by lina 2018-4-11 新用户添加算力  end*/
    }

    /**
     * 下载图片
     * @param iv
     * @return void
     * @date 2018-1-19
     * @author lina
     */
    public void downloadIdCardPic(IdcardValidate iv,String phone){
        IdCardListenerBean bean = new IdCardListenerBean();
        bean.setIv(iv);
        bean.setPhone(phone);
        orderEventBus.post(bean);
    }


    /**
     * 从redis中获取错误次数并加1返回
     * @param key
     * @return int
     * @date 2018-1-26
     * @author lina
     */
    private int getNextErrorTimes(String key){
        String val = RedisUtil.searchString(redis, key);
        if(StrUtils.isBlank(val)){
            return 1;
        }
        return Integer.parseInt(val)+1;
    }
    /**
     * 获取登录信息
     * @param user
     * @param deviceNum
     * @param systemType
     * @param secretKey
     * @return String
     * @date 2018-4-19
     * @author lina
     */
    public String getLoginInfo(User user,String deviceNum, Integer systemType,String secretKey){

		/*获取融云token*/
        Map<Object, Object> params = new HashMap<>();
        params.put("userid", user.getId());
        params.put("type", GlobalParams.BIND_ACCOUNT_RONGCLOUD);
        UserBindAccount bindAccount = userBindAccountService.selectByUserAndType(params);
        if(bindAccount == null){
            bindAccount = new UserBindAccount();
            bindAccount.setUserid(user.getId());
            bindAccount.setExpfield1(GlobalParams.INACTIVE+"");//是否已经自动加入群组
            bindAccount.setType(GlobalParams.BIND_ACCOUNT_RONGCLOUD);
            try {
                bindAccount.setToken(userRequest.getToken(user.getPhone(), user.getNickname(), user.getHeadpath()));
            } catch (Exception e) {
                e.printStackTrace();
                return Result.toResult(ResultCode.RONGCLOUD_INTERFACE_ERROR);
            }
        }
        if(bindAccount.getExpfield1()==null||(GlobalParams.INACTIVE+"").equals(bindAccount.getExpfield1())){
            try {
                chatGroupBiz.joinChatRoomAuto(user);
                bindAccount.setExpfield1(GlobalParams.ACTIVE+"");
            } catch (Exception e) {
                log.info("自动加群失败");
                e.printStackTrace();
            }
        }
        if(bindAccount.getId() == null){
            userBindAccountService.insert(bindAccount);
        }else{
            userBindAccountService.updateByPrimaryKey(bindAccount);
        }


        user.setLogintime(new java.sql.Date(new Date().getTime()));
        user.setDevicenum(deviceNum==null?"":deviceNum);
        user.setToken(UUIDs.uuid());
        user.setTokencreatetime(new java.sql.Date(new Date().getTime()));
        user.setSecretkey(secretKey);
        userService.updateByPrimaryKey(user);


        Map map = new HashMap();
        map.put("userid", user.getId());
        List<UserDiginfo> list= userDiginfoService.selectAll(map);
        UserDiginfo diginfo = list == null || list.isEmpty() ? null :list.get(0);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("name", user.getNickname());
        data.put("phone", user.getPhone());
        data.put("token", user.getToken());
        data.put("talkToken", bindAccount.getToken());
        data.put("headImg", user.getHeadpath());
        data.put("orderPwdFlag", !StrUtils.isBlank(user.getOrderpwd()));
        data.put("idCheckFlag", user.getIdstatus()==GlobalParams.ACTIVE);
        data.put("digFlag", diginfo==null?false:diginfo.getDigflag());

        Map<Object, Object> param = new HashMap<>();
        param.put("userId", user.getId());
        param.put("state", GlobalParams.REALNAME_STATE_SUCCESS);
        List<IdcardValidate> idCard = idcardValidateService.selectAll(param);
        if(idCard != null && !idCard.isEmpty() && idCard.size() != 0){
            data.put("sex", "m".equals(idCard.get(0).getSex())?GlobalParams.SEX_MALE:GlobalParams.SEX_FAMALE);
            data.put("birthday", StrUtils.getBirthdayFromIdCard(idCard.get(0).getIdentificationnumber()));
        }

        Map<Integer, Object> payInfo = new HashMap<Integer, Object>();

        Map<Object, Object> bindsParam = new HashMap<>();
        bindsParam.put("userid", user.getId());
        bindsParam.put("state", 1);
        List<BindInfo> binds = bindInfoService.selectAll(bindsParam);

        for(BindInfo bind:binds){
             BindInfoModel infoM  = new BindInfoModel();
            infoM.setBankName(bind.getBankname());
            infoM.setBranchName(bind.getBranchname());
            BeanUtils.copyProperties(bind, infoM);
            payInfo.put(bind.getType(), infoM);
        }
        data.put("bindInfo", payInfo);
        data.put("secretkey", user.getSecretkey());

        return Result.toResult(ResultCode.SUCCESS, data);
    }



}
