package com.yibi.batch.biz.impl;

import com.yibi.batch.biz.C2cOrderBiz;
import com.yibi.common.utils.DATE;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.TimeStampUtils;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.entity.OrderTaker;
import com.yibi.core.service.OrderTakerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/7/24 0024.
 */

@Service
@Transactional
@Slf4j
public class C2cOrderBizImpl implements C2cOrderBiz {
    @Autowired
    private RedisTemplate<String, String> redis;
    @Autowired
    private OrderTakerService orderTakerService;

    @Override
    public void startCancelUnpaidOrders() {
        String currentTimeStr = DATE.getCurrentTimeStr();
        //log.info("【撤销未付款订单定时】============开始,当前时间："+currentTimeStr+"===============");
        Timestamp currentTime = TimeStampUtils.StrToTimeStamp(currentTimeStr);

		/*从redis获取订单队列的keys*/
        List<String> keyNames = getKeyNames(RedisKey.C2C_ORDERS_NOTPAY_KEY_NAME);
        if(keyNames == null){
            //log.info("【撤销未付款订单定时】==============无需要处理的订单===============");
            return ;
        }

		/*循环处理订单队列：如果超时则撤销，否则结束*/
        for(String  key : keyNames){
           // log.info("【撤销未付款订单定时】处理队列--->"+key);
            boolean flag = true;
            while(flag){
                //从redis队列中取出需要处理的订单
                OrderTaker taker = RedisUtil.leftPopObj(redis, key, OrderTaker.class);
                if(taker==null){
				/*超时队列为空，结束循环,并删除队列的名称*/
                    flag = false;
                    RedisUtil.deleteListAll(redis, RedisKey.C2C_ORDERS_NOTPAY_KEY_NAME, key);
                    continue;
                }


                OrderTaker takerSelected = orderTakerService.selectByPrimaryKey(taker.getId());
			/*订单状态是否未处理过*/
                if(takerSelected.getState()==GlobalParams.C2C_ORDER_STATE_PENDINGPAY){

				/*判断订单的超时时间*/
                    Date inactiveTime = taker.getInactivetime();
                    if(inactiveTime.before(currentTime )){
					/*如果此订单已经超时，则撤销此订单*/
                        log.info("【撤销未付款订单定时】-撤销订单--->id:"+taker.getId());
                        orderTakerService.cancelOrder(taker,0,GlobalParams.C2C_ORDER_STATE_OVERTIME);
                    }else{

					/*此订单未超时，则结束循环，并将订单返回到redis队列中*/
                        RedisUtil.addListRight(redis,key,taker );
                        flag = false;
                    }

                }else{
                    log.info("【撤销未付款订单定时】-此订单已处理--->id:"+taker.getId());
                }
            }
        }

    }

    @Override
    public void startConfirmOrders() {

        String currentTimeStr = DATE.getCurrentTimeStr();
        //log.info("【确认未收款订单定时】============开始,当前时间："+currentTimeStr+"===============");
        Timestamp currentTime = TimeStampUtils.StrToTimeStamp(currentTimeStr);

		/*获取订单队列的keys*/
        List<String> keyNames = getKeyNames(RedisKey.C2C_ORDERS_NOTCONFIRM_KEY_NAME);
        if(keyNames == null){
            //log.info("【确认未收款订单定时】==============无需要处理的订单===============");
            return ;
        }

		/*循环处理订单队列：如果超时则确认收款，否则结束*/
        for(Object keyName : keyNames){
            String key = (String)keyName;
           // log.info("【确认未收款订单定时】处理队列--->"+key);
            boolean flag = true;

            while(flag){
                //从redis队列中取出最左边的订单
                OrderTaker taker = RedisUtil.leftPopObj(redis, key, OrderTaker.class);
                if(taker==null){
				/*超时队列为空，结束循环,并删除队列的名称*/
                    flag = false;
                    RedisUtil.deleteListAll(redis, RedisKey.C2C_ORDERS_NOTCONFIRM_KEY_NAME, key);
                    continue;
                }


                OrderTaker takerSelected = orderTakerService.selectByPrimaryKey(taker.getId());
			/*订单状态是否未处理过*/
                if(takerSelected.getState()==GlobalParams.C2C_ORDER_STATE_PENDINGRECEIPT){

				/*判断订单的超时时间*/
                    Date inactiveTime = taker.getInactivetime();
                    if(inactiveTime.before(currentTime )){
					/*如果此订单已经超时，则确认收款*/
                        orderTakerService.confirmOrder(takerSelected,0);
                        log.info("【确认未收款订单定时】-确认收款--->id:"+taker.getId());
                    }else{

					/*此订单未超时，则结束循环，并将订单返回到redis队列*/
                        RedisUtil.addListRight(redis, key,taker );
                        flag = false;
                    }

                }else{
                    log.info("【确认未收款订单定时】-订单已处理-->id:"+taker.getId());
                }
            }
        }

    }

    /**
     * 获取超时队列的key值
     * @param key
     * @return
     * @return List<Object>
     * @date 2018-3-5
     * @author lina
     */
    public List<String> getKeyNames(String key){
        long size = RedisUtil.searchListSize(redis, key);
        if(size == 0){
            return null;
        }

        return  RedisUtil.searchList(redis, key, 0, size-1);
    }


}
