package com.yibi.orderapi.biz.impl;

import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.DATE;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.TimeStampUtils;
import com.yibi.core.constants.CalculForceType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import com.yibi.orderapi.biz.DigBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.dto.SoulForceTaskDto;
import com.yibi.orderapi.enums.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/19 0019.
 */
@Transactional
@Service
public class DigBizImpl extends BaseBizImpl implements DigBiz {

    @Autowired
    private UserDiginfoService userDiginfoService;
    @Autowired
    private DigHonorsService digHonorsService;
    @Autowired
    private DigRecordService digRecordService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private DigcalRecordService digcalRecordService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private WithdrawService withdrawService;
    @Autowired
    private CoinScaleService coinScaleService;

    @Override
    public String getDigPageInfo(User user, Integer level) {
        Map<String, Object> params = new HashMap<String, Object>();
        //获取当前用户所对应场景信息
        UserDiginfo userDiginfo = userDiginfoService.queryByUserId(user.getId());
        if(userDiginfo ==null){
            return Result.toResult(ResultCode.RESULE_DATA_NONE);
        }
        //获取用户魂力排名
        DigHonors digHonors = digHonorsService.selectByCalcul(userDiginfo.getDigcalcul());
        List<Integer> nonExistList = new ArrayList<Integer>();
        if(level == null) level = digHonors.getRolegrade();
        //降级
        if(level > digHonors.getRolegrade()){
            //获取降级少挖的币种
            String[] mimeCoinLast = digHonorsService.selectByLevel(level).getCointype().split(",");
            for (String coinLast : mimeCoinLast) {
                boolean exist = false;
                for (String coin : digHonors.getCointype().split(",")) {
                    if(coinLast.equals(coin)){
                        exist = true;
                        break;
                    }
                }
                if(!exist&&!StringUtils.isBlank(coinLast))nonExistList.add(Integer.parseInt(coinLast));
            }
        }
        //升级
        if(level < digHonors.getRolegrade()){
            //获取升级可以新挖的币种
            String lastCoinStr = digHonorsService.selectByLevel(level).getCointype();
            String[] mimeCoinLast =lastCoinStr==null?new String[]{}:lastCoinStr.split(",");
            for (String coin : digHonors.getCointype().split(",")) {
                boolean exist = false;
                for (String coinLast : mimeCoinLast) {
                    if(coin.equals(coinLast)){
                        exist = true;
                        break;
                    }
                }
                if(!exist)nonExistList.add(Integer.parseInt(coin));
            }
        }
        //获取当前这个称号的正在挖矿的人数
        Integer count = userDiginfoService.queryCountBySoulLevel(digHonors.getSoulminforce(), digHonors.getSoulmaxforce());
        params.put("countOnline", count);
        params.put("levelChg", digHonors.getRolegrade()-level);
        params.put("coinChg", nonExistList);
        params.put("soulVal", userDiginfo.getDigcalcul());
        params.put("level", digHonors.getRolegrade());
        params.put("honorPic", digHonors.getRolepicurl());
        params.put("mineName", digHonors.getMinename());
        params.put("mineCoin", digHonors.getCointype());
        params.put("minePic", digHonors.getMinepicurl());
        params.put("honorName", digHonors.getRolename());
        return Result.toResult(ResultCode.SUCCESS, params);
    }

    @Override
    public String updateDigState(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        UserDiginfo diginfo = userDiginfoService.queryByUserId(user.getId());
        if(diginfo ==null){
            diginfo = new UserDiginfo();
            diginfo.setUserid(user.getId());
        }
        if(!diginfo.getDigflag()){
			/*开启挖矿,判断用户是否已经实名*/
            if(user.getIdstatus() == GlobalParams.INACTIVE){
                return Result.toResult(ResultCode.USER_NOT_REALNAME);
            }
        }

        diginfo.setDigflag(!diginfo.getDigflag());
        diginfo.setStarttime(new Timestamp(System.currentTimeMillis()));
        userDiginfoService.saveOrUpdate(diginfo);
        map.put("digFlag", diginfo.getDigflag());
        return Result.toResult(ResultCode.SUCCESS,map);
    }

    @Override
    public String collectDig(User user, Integer coinType) {
        /*查询用户，指定币种的未收取并且未过期的出矿记录*/
        List<DigRecord> records = digRecordService.selectByUserIdAndType(user.getId(), coinType);

        BigDecimal total = BigDecimal.ZERO;
        if(records!=null && !records.isEmpty()){
            for(DigRecord record :records){
                total = BigDecimalUtils.add(total, record.getAmount());
                record.setState(GlobalParams.ACTIVE);
                digRecordService.updateByPrimaryKey(record);
            }

            if(total.compareTo(BigDecimal.ZERO)==1){
                accountService.updateAccountAndInsertFlow(user.getId(),GlobalParams.ACCOUNT_TYPE_DIG,coinType,total,BigDecimal.ZERO,user.getId(),"挖矿",0);
            }

        }

        Map<String, Object> map = new HashMap<>();
        map.put("collAmt", BigDecimalUtils.toString(total));
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String queryDigList(User user, Integer page, Integer rows) {
        Map<String, Object> map = new HashMap();
        List<?> list = digRecordService.queryByUser(user.getId(), page, rows);
        if(list!=null && !list.isEmpty()){
            for(Object obj :list){
                Map<String, Object> rec = (Map<String, Object>) obj;
                rec.put("createTime", TimeStampUtils.toTimeString((Timestamp)rec.get("createTime"), "HH:mm"));
                rec.put("amount", BigDecimalUtils.toString((BigDecimal)rec.get("amount")));
            }
        }
        map.put("list", list);
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String querySoulPageInfo(User user) {
        SoulForceTaskDto soulForce = new SoulForceTaskDto();
        UserDiginfo info = userDiginfoService.queryByUserId(user.getId());
        SoulForceTaskDto.SoulTask daySign =soulForce.new SoulTask() ;
        SoulForceTaskDto.SoulTask tenSign =soulForce.new SoulTask() ;
        SoulForceTaskDto.SoulTask monthSign =soulForce.new SoulTask() ;

		/*最后领取时间是昨天零点以前*/
        if(info == null ||info.getLasttime().before(TimeStampUtils.getSomeDayTime(-1))){
            soulForce.setSignDays(0);
            daySign.setFinished(false);
            tenSign.setFinished(false);
            monthSign.setFinished(false);
        }else{
			/*最后领取时间是昨天*/
            if(info.getLasttime().before(TimeStampUtils.getSomeDayTime(0))){
                daySign.setFinished(false);
                soulForce.setSignDays(info.getLogindays()>=30?0:info.getLogindays());
            }else{
				/*最后领取时间是今天*/
                daySign.setFinished(true);
                soulForce.setSignDays(info.getLogindays());
            }
            tenSign.setFinished(info.getLogindays()<10?false:info.getTenrewardstate());
            monthSign.setFinished(info.getLogindays()<30?false:info.getMonthrewardstate());
        }
        daySign.setSoulForce(sysparamsService.getValIntByKey(SystemParams.CALCULATE_FORCE_ONE));
        tenSign.setSoulForce(sysparamsService.getValIntByKey(SystemParams.CALCULATE_FORCE_TEN));
        monthSign.setSoulForce(sysparamsService.getValIntByKey(SystemParams.CALCULATE_FORCE_MONTH));
        soulForce.setDaySignTask(daySign);
        soulForce.setTenSignTask(tenSign);
        soulForce.setMonthSignTask(monthSign);

        /*邀请好友*/
        SoulForceTaskDto.SoulTask inviteTask =soulForce.new SoulTask();
        inviteTask.setFinished(digcalRecordService.existCalcalForceDay(user.getId(), CalculForceType.INVITE.getCode()));
        inviteTask.setSoulForce(sysparamsService.getValIntByKey(SystemParams.CALCULATE_FORCE_INVITE));
        soulForce.setInviteTask(inviteTask);

        /*法币/现货交易*/SoulForceTaskDto.SoulTask dealTask =soulForce.new SoulTask();
        dealTask.setFinished(digcalRecordService.existCalcalForceDay(user.getId(), CalculForceType.ORDER.getCode()));
        dealTask.setSoulForce(sysparamsService.getValIntByKey(SystemParams.ORDER_FORCE_PER));
        soulForce.setDealTask(dealTask);

        /*qq每日分享*/
        SoulForceTaskDto.SoulTask qqShareTask =soulForce.new SoulTask();
        qqShareTask.setFinished(digcalRecordService.existCalcalForceDay(user.getId(), CalculForceType.SHARE_DAY_QQ.getCode()));
        qqShareTask.setSoulForce(sysparamsService.getValIntByKey(SystemParams.SHARE_DAY_QQ));
        soulForce.setQqShareTask(qqShareTask);

        /*qq空间每日分享*/
        SoulForceTaskDto.SoulTask qzoneShareTask =soulForce.new SoulTask();
        qzoneShareTask.setFinished(digcalRecordService.existCalcalForceDay(user.getId(), CalculForceType.SHARE_DAY_QZONE.getCode()));
        qzoneShareTask.setSoulForce(sysparamsService.getValIntByKey(SystemParams.SHARE_DAY_QZONE));
        soulForce.setQzoneShareTask(qzoneShareTask);

        /*微信每日分享*/
        SoulForceTaskDto.SoulTask wechatShareTask =soulForce.new SoulTask();
        wechatShareTask.setFinished(digcalRecordService.existCalcalForceDay(user.getId(), CalculForceType.SHARE_DAY_WECHAT.getCode()));
        wechatShareTask.setSoulForce(sysparamsService.getValIntByKey(SystemParams.SHARE_DAY_WECHAT));
        soulForce.setWechatShareTask(wechatShareTask);

        /*朋友圈每日分享*/
        SoulForceTaskDto.SoulTask circleShareTask =soulForce.new SoulTask();
        circleShareTask.setFinished(digcalRecordService.existCalcalForceDay(user.getId(), CalculForceType.SHARE_DAY_CIRCLE.getCode()));
        circleShareTask.setSoulForce(sysparamsService.getValIntByKey(SystemParams.SHARE_DAY_CIRCLE));
        soulForce.setCircleShareTask(circleShareTask);

        /*加入微信群永久任务*/
        SoulForceTaskDto.SoulTask joinWechatTask =soulForce.new SoulTask();
        joinWechatTask.setFinished(digcalRecordService.existCalcalForceDay(user.getId(), CalculForceType.JOIN_WECHAT.getCode()));
        joinWechatTask.setSoulForce(sysparamsService.getValIntByKey(SystemParams.JOIN_WECHAT));
        soulForce.setJoinWechatTask(joinWechatTask);

        /*关注公众号永久任务*/
        SoulForceTaskDto.SoulTask joinPublicTask =soulForce.new SoulTask();
        joinPublicTask.setFinished(digcalRecordService.existCalcalForceDay(user.getId(), CalculForceType.WATCH_PUBLICNUMBER.getCode()));
        joinPublicTask.setSoulForce(sysparamsService.getValIntByKey(SystemParams.WATCH_PUBLICNUMBER));
        soulForce.setJoinPublicTask(joinPublicTask);

        /*加入QQ群永久任务*/
        SoulForceTaskDto.SoulTask joinQGroupTask =soulForce.new SoulTask();
        joinQGroupTask.setFinished(digcalRecordService.existCalcalForceDay(user.getId(), CalculForceType.JOIN_QQGROUP.getCode()));
        joinQGroupTask.setSoulForce(sysparamsService.getValIntByKey(SystemParams.JOIN_QQGROUP));
        soulForce.setJoinQGroupTask(joinQGroupTask);

        /*实名认证永久任务*/
        SoulForceTaskDto.SoulTask realNameTask =soulForce.new SoulTask();
        realNameTask.setFinished(user!=null&& user.getIdstatus()==GlobalParams.ACTIVE);
        realNameTask.setSoulForce(sysparamsService.getValIntByKey(SystemParams.CALCULATE_FORCE_REALNAME));
        soulForce.setRealNameTask(realNameTask);


        soulForce.setShareTitle(sysparamsService.getValStringByKey(SystemParams.APP_CONFIG_SHARE_TITLE));
        soulForce.setShareDes(sysparamsService.getValStringByKey(SystemParams.APP_CONFIG_SHARE_DES));
        soulForce.setShareUrl(sysparamsService.getValStringByKey(SystemParams.APP_SHARE_URL));

        soulForce.setInstruUrl(sysparamsService.getValStringByKey(SystemParams.CALCULATE_FORCE_INSTRUCTION_URL));

        soulForce.setBanners(bannerService.selectBannerByType(2));

        return Result.toResult(ResultCode.SUCCESS, soulForce);
    }

    @Override
    public String addSignSoul(User user, Integer type) {
        UserDiginfo info = userDiginfoService.queryByUserId(user.getId());
        if(info ==null ){
            info = new UserDiginfo();
            info.setUserid(user.getId());
        }
        int forceInc = 0;
        if(type==0){
            Sysparams param = sysparamsService.getValByKey(SystemParams.CALCULATE_FORCE_ONE);
            forceInc = param==null?0:Integer.valueOf(param.getKeyval());
            if(info.getLasttime().before(TimeStampUtils.getSomeDayTime(-1))||info.getLogindays()>=30){
			/*最后领取时间是昨天以前，或者连续天数到达30天,则重置连续天数和领取状态*/
                info.setLogindays(1);
                info.setTenrewardstate(false);
                info.setMonthrewardstate(false);
                info.setLasttime(new Timestamp(System.currentTimeMillis()));
            }else{
                if(info.getLasttime().before(TimeStampUtils.getSomeDayTime(0))){
				/*最后领取时间是昨天，则累计连续天数*/
                    info.setLogindays(info.getLogindays()+1);
                    info.setLasttime(new Timestamp(System.currentTimeMillis()));
                }else{
				/*最后领取时间是今天,则失败*/
                    return Result.toResult(ResultCode.CALCULATE_FORCE_REPEAT);
                }
            }
        }else if(type ==1){
            Sysparams param = sysparamsService.getValByKey(SystemParams.CALCULATE_FORCE_TEN);
            forceInc = param==null?0:Integer.valueOf(param.getKeyval());
            if(info.getLasttime().before(TimeStampUtils.getSomeDayTime(-1))||info.getLogindays()<10){
			/*最后领取时间是昨天以前，或者连续天数小于10天,则失败*/
                return Result.toResult(ResultCode.CALCULATE_FORCE_OVERTIME);

            }else if(info.getTenrewardstate()){
			/*已经领取过奖励*/
                return Result.toResult(ResultCode.CALCULATE_FORCE_REPEAT);
            }else{
                info.setTenrewardstate(true);
            }
        }else if (type==2){
            if(info.getLasttime().before(TimeStampUtils.getSomeDayTime(-1))||info.getLogindays()<30){
			/*最后领取时间是昨天以前，或者连续天数小于30天,则失败*/
                return Result.toResult(ResultCode.CALCULATE_FORCE_OVERTIME);

            }else if(info.getMonthrewardstate()){
			/*已经领取过奖励*/
                return Result.toResult(ResultCode.CALCULATE_FORCE_REPEAT);
            }else{
                info.setMonthrewardstate(true);
            }
        }

        /*增加算力*/
        info.setDigcalcul(info.getDigcalcul()+forceInc);

        userDiginfoService.saveOrUpdate(info);

		/*添加算力记录*/
        DigcalRecord rec = new DigcalRecord();
        rec.setUserid(info.getUserid());
        rec.setType(CalculForceType.SIGN.getCode());
        rec.setName(CalculForceType.SIGN.getName());
        rec.setDigcalcul(forceInc);
        rec.setAllcalculforce(info.getDigcalcul());
        digcalRecordService.insert(rec);

        return Result.toResult(ResultCode.SUCCESS);
    }


    @Override
    public String addShareSoul(User user, Integer type) {
        /*实名认证检查*/
        if(user.getIdstatus() == GlobalParams.INACTIVE){
            return Result.toResult(ResultCode.USER_NOT_REALNAME);
        }

        Boolean result = digcalRecordService.existCalcalForceDay(user.getId(), type);
        if(!result){//若今日还未领取算力
            String keyVal = "";
            Integer userId = user.getId();
            UserDiginfo ud = userDiginfoService.queryByUserId(userId);
            Integer digCalCul = ud.getDigcalcul();//用户当前算力
            String name = "";
            if(type == 8){
                keyVal = sysparamsService.getValStringByKey(SystemParams.SHARE_DAY_QQ);
                name = "每日分享QQ";
            }else
            if(type == 9){
                keyVal = sysparamsService.getValStringByKey(SystemParams.SHARE_DAY_WECHAT);
                name = "每日分享微信";
            }else
            if(type == 10){
                keyVal = sysparamsService.getValStringByKey(SystemParams.SHARE_DAY_QZONE);
                name = "每日分享空间";
            }else
            if(type == 11){
                keyVal = sysparamsService.getValStringByKey(SystemParams.SHARE_DAY_CIRCLE);
                name = "每日分享朋友圈";
            }else{
                return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
            }
            if(StringUtils.isBlank(keyVal)){
                keyVal = "0";
            }
			/*添加算力流水*/
            DigcalRecord dr = new DigcalRecord();
            dr.setUserid(userId);
            dr.setDigcalcul(Integer.valueOf(keyVal));
            dr.setAllcalculforce(digCalCul+Integer.valueOf(keyVal));
            dr.setType(type);
            dr.setName(name);
            digcalRecordService.insert(dr);
			
			/*增加算力*/
            ud.setDigcalcul(ud.getDigcalcul() + Integer.valueOf(keyVal));
            userDiginfoService.updateByPrimaryKey(ud);
			
			/*判断是否升级发送推送消息*/
//			talkMenuService.cheackIsUp(userId, Integer.valueOf(keyVal), GlobalParams.PUSH_TO_INDEX);
            return Result.toResult(ResultCode.SUCCESS);
        }
        return Result.toResult(ResultCode.DAY_TASK_FALSE);
    }

    @Override
    public String queryUserSoulInfo(User user) {
        Map<String,Object> map = new HashMap<>();
        UserDiginfo info = userDiginfoService.queryByUserId(user.getId());
        DigHonors digHonors = digHonorsService.selectByCalcul(info ==null? 0:info.getDigcalcul());
		map.put("rank",userDiginfoService.queryRankByUserId(user.getId()));
        map.put("currentForce",info.getDigcalcul());
        map.put("differForce",digHonors.getSoulmaxforce()-info.getDigcalcul()+1);

        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String querySoulRank(User user, Integer page, Integer rows) {
        List<?> rankList = userDiginfoService.queryRankList(page,rows);
        int index = 1+(page*rows)   ;
        for(Object obj:rankList){
            Map<String,Object> force = (Map<String,Object>)obj;
            force.put("rank", index);
    		index++;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("list",rankList);
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String querySoulFlow(User user, Integer page, Integer rows) {
        Map<String, Object> data = new HashMap<String, Object>();
        List<?> list = digcalRecordService.queryListByUser(user.getId(), page, rows);
        for(Object obj:list){
            Map<String, Object> map = (Map<String, Object>)obj;
            Timestamp time = (Timestamp) map.get("createTime");
            map.put("createTime", TimeStampUtils.toTimeString(time, "yyyy-MM-dd HH:mm:ss"));
        }
        data.put("list", list);
        return Result.toResult(ResultCode.SUCCESS, data);
    }

    @Override
    public String addDigWithdraw(User user, String password, String accountNum, Integer type, Integer coinType) {
        if(type == GlobalParams.PAY_COMMON&& StrUtils.isBlank(accountNum)){
            return Result.toResult(ResultCode.PARAM_IS_BLANK);
        }
        if(type != GlobalParams.PAY_COMMON && type !=GlobalParams.PAY_SPOT){
            return Result.toResult(ResultCode.PARAM_IS_INVALID);
        }
        /*实名认证判断*/
        if(user.getIdstatus()==0){
            return  Result.toResult(ResultCode.USER_NOT_REALNAME);
        }

		/*交易密码验证*/
        String resu = validateOrderPassword(user, password);
        if(!StrUtils.isBlank(resu)){
            return resu;
        }
        Account dig = accountService.queryByUserIdAndCoinTypeAndAccountType(user.getId(), coinType,GlobalParams.ACCOUNT_TYPE_DIG);
        if(dig ==null){
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        }
        BigDecimal amount = dig.getAvailbalance();
        CoinManage coinManage = coinManageService.queryByCoinType(coinType);
        if(coinManage!=null){
            if(type == GlobalParams.PAY_COMMON){
                /*功能开关*/
                if(coinManage.getDigwithdrwaonoff()!=GlobalParams.ON){
                    return  Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
                }
                /*判断最小提现金额*/
                if(dig.getAvailbalance().compareTo(coinManage.getWithamountmin())==-1){
                    return Result.toResultFormat(ResultCode.WITHDRAW_DIG_AMOUNT_MIN_LIMIT, coinManage.getWithamountmin().toPlainString());
                }
                Map<String, Object> dayLimit = withdrawService.queryDayWithdraw(user.getId(), DATE.getCurrentDateStr(),coinType);
                //日提现次数限制
                int times = ((Long)dayLimit.get("amountCount")).intValue();
                if(times>=coinManage.getWithdrawcountmax()){
                    return Result.toResult(ResultCode.WITHDRAW_COUNT_LIMINT);
                }

                //日提现总金额限制
                /*BigDecimal sum = BigDecimalUtils.add(new BigDecimal(dayLimit.get("amountSum").toString()), amount);
                if(sum.compareTo(coinManage.getWithamountmax())==1){
                    return Result.toResult(ResultCode.WITHDRAW_SUM_LIMIT);
                }*/
            }else{
                if(coinManage.getDigtospotonoff()!=GlobalParams.ON){
                    return  Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
                }
            }

        }

        Withdraw withdraw = createWithDraw(user.getId(),accountNum,GlobalParams.ACCOUNT_TYPE_DIG,type, coinType, amount,coinManage);

        String operType = type==GlobalParams.PAY_SPOT?"提现到现货钱包":"提现到其他钱包";
        /*减去挖矿账户钱包余额*/
        accountService.updateAccountAndInsertFlow(user.getId(),GlobalParams.ACCOUNT_TYPE_DIG,coinType,BigDecimalUtils.plusMinus(amount),BigDecimal.ZERO,
                user.getId(),operType,withdraw.getId());

        /*如果是提取到现货钱包，则增加现货钱包余额，并记录流水*/
        if(type == GlobalParams.PAY_SPOT){
            accountService.updateAccountAndInsertFlow(user.getId(),GlobalParams.ACCOUNT_TYPE_SPOT,coinType,amount,BigDecimal.ZERO,
                    user.getId(),operType,withdraw.getId());
        }

        return Result.toResult(ResultCode.SUCCESS);
    }

    public Withdraw createWithDraw(Integer userId,String onlineNum,Integer accountType,Integer type,Integer coinType,BigDecimal amount,CoinManage coinManage){
		/*手续费计算 提现到现货没有费率，其他有费率*/
        BigDecimal fee = type ==GlobalParams.PAY_SPOT||coinManage ==null?BigDecimal.ZERO:coinManage.getWithspotrate();
        BigDecimal remain = type ==GlobalParams.PAY_SPOT?amount:BigDecimalUtils.subtract(amount, fee);

        Withdraw withdraw = new Withdraw();
        withdraw.setUserid(userId);
        withdraw.setPayaddress(onlineNum==null?"":onlineNum);
        withdraw.setType(type);
        withdraw.setAccounttype(accountType);
        withdraw.setAmount(amount);
        withdraw.setFee(fee);
        withdraw.setRemain(remain);
        withdraw.setCointype(coinType);
        withdraw.setState(type ==GlobalParams.PAY_SPOT?GlobalParams.ORDER_STATE_TREATED:GlobalParams.ORDER_STATE_UNTREATED);
        withdraw.setOrdernum("W"+userId+System.currentTimeMillis());
        withdraw.setRemark("");
        withdrawService.insert(withdraw);
        return withdraw;
    }
}
