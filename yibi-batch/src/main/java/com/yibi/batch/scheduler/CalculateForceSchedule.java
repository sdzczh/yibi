package com.yibi.batch.scheduler;

import com.yibi.batch.biz.CalculateForceBiz;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.Sysparams;
import com.yibi.core.entity.User;
import com.yibi.core.entity.UserDiginfo;
import com.yibi.core.service.SysparamsService;
import com.yibi.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class CalculateForceSchedule {
	@Autowired
	private CalculateForceBiz calculateForceBiz;
	@Autowired
	private SysparamsService sysparamsService;
	@Autowired
	private UserService userService;
	
	/**
	 * 算力定时
	* @Description: 用户间断一天签到，算力-1，上限为用户签到所得算力，每天1点执行
	* @author lina 
	* @date 2018-4-5
	* @version V1.0
	 */
	@Scheduled(cron="15 0 1 * * ?")
	public void start(){
		
		Sysparams param1 = sysparamsService.getValByKey(SystemParams.NOTSIGN_CALCUL_FORCE_RATE);
		int calDay = param1==null?1:Integer.valueOf(param1.getKeyval());//未签到减少的算力
		int page = 0;
		List<UserDiginfo> users = calculateForceBiz.queryListPage(page, 1000);
		while(users!=null && !users.isEmpty()){
			calculateForceBiz.calculateForceCheck(users,calDay);
			page = page +1;
			users = calculateForceBiz.queryListPage(page, 1000);
			log.info("id:{}",1);
		}
	}
	
	/**
	 * 用户交易间断一天，算力-25，上限为交易所得算力，每天2点执行     取消交易中断扣除
	 * @return void
	 * @date 2018-4-20
	 * @author lina
	 */
	//@Scheduled(cron="30 0 2 * * ?")
	public void orderCalculForceCheck(){
		log.info("交易中断定时开始");
		Sysparams param1 = sysparamsService.getValByKey(SystemParams.ORDER_FORCE_MINUS);
		int forceMinus = param1==null?25:Integer.valueOf(param1.getKeyval());//最大增加的算力
		int page = 0;
		List<User> users = userService.queryActiveUserList(page, 1000);
		while(users!=null && !users.isEmpty()){
			calculateForceBiz.orderCalculForceCheck(users,forceMinus);
			page = page +1;
			users = userService.queryActiveUserList(page, 1000);
		}
		log.info("交易中断定时结束");		
	}
}
