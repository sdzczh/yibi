package com.yibi.batch.scheduler;

import com.alibaba.fastjson.JSON;
import com.yibi.batch.biz.WalletBiz;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.AccountChain;
import com.yibi.core.entity.CoinManage;
import com.yibi.core.entity.Sysparams;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.SysparamsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 定时轮询用户钱包状态
* @Description: TODO
* @author zhaohe 
* @date 2018-5-17
* @version V1.0
 */
@Slf4j
@Component
public class WalletScheduler {

	@Autowired
	private WalletBiz walletBiz;
	@Autowired
	private SysparamsService sysparamsService;
	@Autowired
	private CoinManageService coinManageService;


	/**
	 * 处理除ETH之外的充值
	 */
	@Scheduled(cron="0 0/15 * * * ?")
	public void start(){
		Map<Object, Object> map = new HashMap<>();
		map.put("rechspotonoff", 1);
		List<CoinManage> list = coinManageService.selectAll(map);
//		List<Integer> list = JSON.parseArray(getParam(SystemParams.CHAIN_COIN), Integer.class);
		List<AccountChain> AccountChainList = new ArrayList<>();
		//log.info("【充值轮询】=========开始=========");
		for (CoinManage cm : list) {
			/*处理ETH、DK之外的币*/
			if(cm.getCointype() == CoinType.ETH || cm.getCointype() == CoinType.DK){
				continue;
			}
			log.info("【充值轮询】=========当前币种【" + cm.getCointype() + "】=========");
			int page = 0;
			AccountChainList = walletBiz.getChainList(cm.getCointype(), page, 100);
			while(AccountChainList != null && !AccountChainList.isEmpty()){
				walletBiz.startCheckAccount(cm.getCointype(), AccountChainList);
				page = page +1;
				AccountChainList = walletBiz.getChainList(cm.getCointype(), page, 100);
			}
		}
 		//log.info("【充值轮询】=========结束=========");
	}

	/**
	 * ETH充值轮询
	 */
	@Scheduled(cron="0 0/20 * * * ?")
	public void startETH(){
		Map<Object, Object> map = new HashMap<>();
		map.put("rechspotonoff", 1);
		map.put("cointype", 4);
		List<CoinManage> list = coinManageService.selectAll(map);
//		List<Integer> list = JSON.parseArray(getParam(SystemParams.CHAIN_COIN), Integer.class);
		List<AccountChain> AccountChainList = new ArrayList<>();
	//	log.info("【充值轮询】=========开始=========");
		for (CoinManage cm : list) {
			log.info("【充值轮询】=========当前币种【" + cm.getCointype() + "】=========");
			int page = 0;
			AccountChainList = walletBiz.getChainList(cm.getCointype(), page, 100);
			while(AccountChainList != null && !AccountChainList.isEmpty()){
				walletBiz.startCheckAccount(cm.getCointype(), AccountChainList);
				page = page +1;
				AccountChainList = walletBiz.getChainList(cm.getCointype(), page, 100);
			}
		}
		//log.info("【充值轮询】=========结束=========");
	}

	@Scheduled(cron="0 0 1 * * ?")
	public void transfer(){
		log.info("【币种余额转移】=========开始=========");
		Sysparams param = sysparamsService.getValByKey(SystemParams.TRANS_TO_MAIN_COIN);
		List<Object> list = JSON.parseArray(param.getKeyval());
		List<AccountChain> AccountChainList = new ArrayList<AccountChain>();
		for (Object type : list) {
			int page = 0;
			AccountChainList = walletBiz.getChainList((Integer)type, page, 100);
			while(AccountChainList!=null && !AccountChainList.isEmpty()){
				walletBiz.transToMainAccount((Integer)type, AccountChainList);
				page = page +1;
				AccountChainList = walletBiz.getChainList((Integer)type, page, 100);
			}
		}
		log.info("【币种余额转移】=========结束=========");
	}

	public String getParam(String key){
		Sysparams param = sysparamsService.getValByKey(key);
		if(param ==null){
			return "";
		}else{
			return param.getKeyval();
		}
	}

	@Scheduled(cron="0 0/20 * * * ?")
	public void recharge() {
		Map<Object, Object> map = new HashMap<>();
		map.put("rechspotonoff", 1);
		map.put("cointype", 8);
		List<CoinManage> list = coinManageService.selectAll(map);
		List<AccountChain> AccountChainList = new ArrayList<>();
		log.info("【充值轮询】【YT】=========开始=========");
		for (CoinManage cm : list) {
			log.info("【充值轮询】=========当前币种【" + cm.getCointype() + "】=========");
			int page = 0;
			AccountChainList = walletBiz.getChainList(cm.getCointype(), page, 100);
			while(AccountChainList != null && !AccountChainList.isEmpty()){
				walletBiz.recargeERC(cm.getCointype(), AccountChainList);
				page = page +1;
				AccountChainList = walletBiz.getChainList(cm.getCointype(), page, 100);
			}
		}
		log.info("【充值轮询】【YT】=========结束=========");


	}
}
