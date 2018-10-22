package com.yibi.batch.scheduler;

import com.yibi.batch.biz.RedPacketBiz;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class RedPacketSchedule {
	@Autowired
	private RedPacketBiz redPacketBiz;
	
	/**
	 * 红包超时取消定时
	* @Description: 24小时未领取红包则退回，每1分钟执行一次
	* @author lina 
	* @date 2018-4-5
	* @version V1.0
	 */
	@Scheduled(cron="0 0/1 * * * ?")
	public void start(){

		redPacketBiz.cancelRedPacket();
	}
	
}
