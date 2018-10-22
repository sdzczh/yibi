package com.yibi.web.service.impl;

import com.yibi.common.model.Grid;
import com.yibi.common.model.Json;
import com.yibi.common.model.PageModel;
import com.yibi.common.utils.PictureUtils;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.CalculForceType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import com.yibi.web.service.RealNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class RealNameServiceImpl implements RealNameService {
    @Autowired
    private IdcardValidateService idcardValidateService;
    @Autowired
    private UserService userService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private DigcalRecordService digcalRecordService;
    @Autowired
    private CommissionInviteService commissionInviteService;
    @Autowired
    private  UserDiginfoService userDiginfoService;
    @Autowired
    private AccountService accountService;

    @Override
    public Object getRealNameList(Integer page, Integer rows, String phone, Integer state) {
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        map.put("maxResult",pageModel.getMaxResult());
        map.put("firstResult",pageModel.getFirstResult());
        if (!StrUtils.isBlank(phone)){
            map.put("phone",phone);
        }
        if (state != null){
            map.put("state",state);
        }
        Grid grid = new Grid();
        grid.setRows(this.idcardValidateService.selectConditionPaging(map));
        grid.setTotal(this.idcardValidateService.selectConditionCount(map));
        return grid;
    }

    @Override
    public Object addRealName(IdcardValidate idcardValidate,String phone, MultipartFile frontFile, MultipartFile backFile) throws IOException {
        Json json = new Json();
        json.setSuccess(true);
        if (frontFile.isEmpty()){
            json.setMsg("请上传身份证正面照片");
            return json;
        }
        //正面图片上传
        String fileName = frontFile.getOriginalFilename();
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
        if(!"png".equalsIgnoreCase(fileSuffix)){
            json.setSuccess(false);
            json.setMsg("身份证图片正面格式异常，必须为png格式！");
            return json;
        }
        String imageName = "imageName"+new Date().getTime()+".jpg";
        String fileDir = "pictureTr";
        String frontPath = PictureUtils.uploadImg(fileDir, imageName,frontFile.getInputStream());
        idcardValidate.setIdcardfrontpic(frontPath);
        //反面上传验证
        if (backFile.isEmpty()){
            json.setMsg("请上传身份证反面照片");
            return json;
        }
        String backName = backFile.getOriginalFilename();
        String fileSuffixs = backName.substring(backName.lastIndexOf(".")+1);
        if(!"png".equalsIgnoreCase(fileSuffixs)){
            json.setSuccess(false);
            json.setMsg("身份证反面图片格式异常，必须为png格式！");
            return json;
        }
        //上传图片
        String imageNames = "imageName"+new Date().getTime()+".jpg";
        String fileDirs = "pictureTr";
        String backPath = PictureUtils.uploadImg(fileDirs, imageNames,backFile.getInputStream());
        idcardValidate.setIdcardbackpic(backPath);
        //验证用户是否存在
        User user = userService.selectByPhone(phone);
        if (user == null){
            json.setMsg("输入的账号有误，用户不存在");
            return json;
        }
        //用户状态判断是否已经认证过了
        if (user.getIdstatus() == 1){
            json.setMsg("用户已经认证成功了");
            return json;
        }
        //验证身份证号是否已经被占用了
        Map<Object,Object> map = new HashMap<>();
        map.put("idcard",idcardValidate.getIdentificationnumber());
        List<User> list = userService.selectAll(map);
        if (list.size()>0){
            json.setMsg("该身份证号已经被账号:"+list.get(0).getPhone()+"--实名过了");
            return json;
        }
        int result = 0;
        //更新用户状态
        user.setIdstatus(1);
        user.setUsername(idcardValidate.getName());
        user.setIdcard(idcardValidate.getIdentificationnumber());
        result = this.userService.updateByPrimaryKeySelective(user);
        if (result == 0){
            throw new RuntimeException("更新用户信息失败");
        }
        //添加实名认证记录
        idcardValidate.setState(1);
        idcardValidate.setUserid(user.getId());
        idcardValidate.setTaskid(String.valueOf(System.currentTimeMillis()));
        result = idcardValidateService.insertSelective(idcardValidate);
        if (result == 0){
            throw new RuntimeException("插入实名认证记录失败");
        }
        //发放奖励
        commission_realName(user);
        //算力奖励
        calculate_force(user);
        json.setMsg("实名成功！");
        return json;
    }
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
                }
            }
        }
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
}
