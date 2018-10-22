package com.yibi.batch.scheduler;

import com.yibi.batch.biz.YubiProfitBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class YubiProfitSchedule {
	
	@Autowired
	private YubiProfitBiz yubiProfitBiz;
	
	@Scheduled(cron="10 10 1 * * ?")
	public void  calculProfit(){
		yubiProfitBiz.start();
	}
}
