package com.yibi.web.robot.job;

import com.alibaba.fastjson.JSONObject;
import com.yibi.common.encrypt.AES;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.RobotTask;
import com.yibi.core.entity.User;
import com.yibi.core.service.RobotTaskService;
import com.yibi.core.service.SysparamsService;
import com.yibi.core.service.UserService;
import com.yibi.web.service.CommonI;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Log4j
@Component
public class SaleMarketJob extends BaseJob implements Job {

    @Autowired
    private RobotTaskService robotTaskService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommonI commonI;
    @Autowired
    private SysparamsService sysparamsService;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            JobDataMap data = context.getJobDetail().getJobDataMap();

            int taskId = data.getInt("taskId");
            RobotTask task =  robotTaskService.selectByPrimaryKey(taskId);
            if(task == null){
                log.info(task.getCointype()+",【自动卖出成交】-任务不存在："+taskId);
                return ;
            }
            //判断需要提交订单数量
            int count = RandomUtils.nextInt(task.getCountmin(), task.getCountmax());
            log.info(task.getCointype()+",【自动卖出成交】-开始执行，执行笔数："+count);
            if(count >0){
                for(int i=0 ;i<count;i++){
                    Double totalAmt = nextDouble(task.getNumbermin().doubleValue(), task.getNumbermax().doubleValue(),commonI.orderAmtAmountScale(task.getCointype(),CoinType.KN));
                    User user = userService.selectByPrimaryKey(task.getExcuteuserid());

                    int resultCode = sendSaleMarketOrder(user, task.getCointype(), CoinType.KN, totalAmt);
                    if(resultCode !=10000){
                        //交易发送不成功
                        if(resultCode == 30015){
                            //余额不足，自动进行充值
                            BigDecimal rechargeAmt = new BigDecimal(commonI.queryValue(task.getCointype(), "autoRechargeAmt"));
                            commonI.rechargeAuto(user.getId(), task.getCointype(), rechargeAmt);
                            log.info(task.getCointype()+",【自动卖出成交】-自动充值");
                            i=i-1;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int  sendSaleMarketOrder(User user ,Integer orderCoinType,Integer unitCoinType,double totalAmt){
        String systemUrl = sysparamsService.getValStringByKey(SystemParams.SYSTEM_URL);
        if(StringUtils.isBlank(systemUrl)){
            throw new RuntimeException("系统地址为空");
        }

        String url = systemUrl+"/order/marketPriceSale.action";

        String timpStamp = System.currentTimeMillis()+"";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("amount", totalAmt);
        map.put("password", sysparamsService.getValStringByKey(SystemParams.ORDER_PASSWORD_DEFAULT));
        map.put("orderCoin", orderCoinType);
        map.put("unitCoin", unitCoinType);
        map.put("timeStamp", timpStamp);
        map.put("levFlag",GlobalParams.ORDER_TYPE_SPOT);


        JSONObject json = new JSONObject(map);
        String params = "";
        try {
            params = AES.encrypt(json.toJSONString(), user.getSecretkey());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String res = post(url, params, null, user.getToken(), null);
        log.info(orderCoinType+",【成交卖出】接口返回："+res);
        JSONObject jso = JSONObject.parseObject(res);
        return jso.getIntValue("code");

    }
}
