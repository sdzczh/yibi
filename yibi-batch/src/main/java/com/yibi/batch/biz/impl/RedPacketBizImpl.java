package com.yibi.batch.biz.impl;

import com.yibi.batch.biz.RedPacketBiz;
import com.yibi.common.utils.DATE;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.TimeStampUtils;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.entity.ChatRedpacket;
import com.yibi.core.service.AccountService;
import com.yibi.core.service.ChatRedpacketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 2018/7/26 0026.
 */
@Service
@Transactional
@Slf4j
public class RedPacketBizImpl implements RedPacketBiz {

    @Autowired
    private RedisTemplate<String, String> redis;
    @Autowired
    private ChatRedpacketService chatRedpacketService;
    @Autowired
    private AccountService accountService;
    @Override
    public void cancelRedPacket() {
        String currentTimeStr = DATE.getCurrentTimeStr();
        Timestamp currentTime = TimeStampUtils.StrToTimeStamp(currentTimeStr);

        boolean flag = true;
        while(flag){
            //从redis队列中取出需要处理的订单
            ChatRedpacket red = RedisUtil.leftPopObj(redis, RedisKey.RED_PACKET_OUTTIME_QUEUE, ChatRedpacket.class);
            if(red==null){
				/*超时队列为空，结束循环,并删除队列的名称*/
                flag = false;
                continue;
            }

            ChatRedpacket redSelected = chatRedpacketService.selectByPrimaryKey(red.getId());
			/*订单状态是否未处理过*/
            if(redSelected.getState()== GlobalParams.RED_PACKET_STATE_TRAININE){

				/*判断订单的超时时间*/
                Date inactiveTime = redSelected.getInactivetime();
                if(inactiveTime.before(currentTime )){
					/*如果此订单已经超时，则撤销此订单*/
                    log.info("【红包超时定时】-红包已超时，撤销红包--->id:"+redSelected.getId());
                    red.setState(GlobalParams.RED_PACKET_STATE_OUTTIME);
                    chatRedpacketService.updateByPrimaryKey(red);
                    accountService.updateAccountAndInsertFlow(red.getSenduserid(),GlobalParams.ACCOUNT_TYPE_SPOT,red.getCointype(),red.getRemainamt(),BigDecimal.ZERO,
                            0,"红包超时撤销",red.getId());


                }else{
					/*此订单未超时，则结束循环，并将订单返回到redis队列中*/
                    RedisUtil.addListRight(redis, RedisKey.RED_PACKET_OUTTIME_QUEUE,red );
                    flag = false;
                }

            }else{
                log.info("【撤销未付款订单定时】-此红包已处理--->id:"+red.getId());
            }
        }

    }

}
