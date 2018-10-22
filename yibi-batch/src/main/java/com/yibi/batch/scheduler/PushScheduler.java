package com.yibi.batch.scheduler;

import com.yibi.batch.biz.PushBiz;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.service.SysparamsService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 定时推送系统消息
* @Description: TODO
* @author zhaohe 
* @date 2018-6-7
* @version V1.0
 */
@Log4j
@Component
public class PushScheduler {

	@Autowired
	private PushBiz pushBiz;
	@Autowired
	private SysparamsService sysparamsService;

	
	@Scheduled(cron="0 0 12 * * ?")
	public void start(){
		log.info("【任务提示-推送系统消息】=========开始=========");
		String str = sysparamsService.getValStringByKey(SystemParams.SYSTEM_PUSH_MISSION);
		pushBiz.start(str);
		log.info("【任务提示-推送系统消息】=========结束=========");
	}
	@Scheduled(cron="0 0 20 * * ?")
	public void start2(){
		log.info("【收矿提醒-推送系统消息】=========开始=========");
		String str = sysparamsService.getValStringByKey(SystemParams.SYSTEM_PUSH_DIG);
		pushBiz.start(str);
		log.info("【收矿提醒-推送系统消息】=========结束=========");
	}
//	@Scheduled(cron="0 0 10 * * ?")
//	public void start3(){
//		log.info("【任务提示-推送系统消息】=========开始=========");
//		String str = sysparamsService.getValStringByKey(SystemParams.SYSTEM_PUSH_MISSION);
//		pushBiz.start(str);
//		log.info("【任务提示-推送系统消息】=========结束=========");
//	}
}
