package com.yibi.batch.scheduler;

import com.yibi.batch.biz.KlineBiz;
import com.yibi.common.utils.WebsocketClientUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * Kline定时器
 */
@Log4j
@Component
public class KlineScheduler {
    @Autowired
    private KlineBiz klineBiz;

    private Long lastTime1;
    private Long lastTime2;
    private Long lastTime3;
    private Long lastTime4;
    private Long lastTime5;

    @Scheduled(cron="2/6 * * * * ?")
    public void sendPing(){
        WebsocketClientUtils.sendPing();

    }

    /**
     * 每分钟执行一次
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void job1() {
        log.info("【k线数据更新（1分钟）】=========开始=========");
        Long timeInteval = 60000l;
        lastTime1 = klineBiz.klineJob(timeInteval, lastTime1);
        //log.info("【k线数据更新（1分钟）】=========结束=========");
    }

    /**
     * 每5分钟执行一次
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void job2() {
        log.info("【k线数据更新（5分钟）】=========开始=========");
        Long timeInteval = 300000l;
        lastTime2 =  klineBiz.klineJob(timeInteval, lastTime2);
        //log.info("【k线数据更新（5分钟）】=========结束=========");
    }

    /**
     * 每30分钟执行一次
     */
    @Scheduled(cron = "0 */30 * * * ?")
    public void job3() {
        log.info("【k线数据更新（30分钟）】=========开始=========");
        Long timeInteval = 1800000l;
        lastTime3 = klineBiz.klineJob(timeInteval, lastTime3);
        //log.info("【k线数据更新（30分钟）】=========结束=========");
    }

    /**
     * 每小时执行一次
     */
    @Scheduled(cron = "0 0 */1 * * ?")
    public void job4() {
        log.info("【k线数据更新（1小时）】=========开始=========");
        Long timeInteval = 3600000l;
        lastTime4 = klineBiz.klineJob(timeInteval, lastTime4);
        //log.info("【k线数据更新（1小时）】=========结束=========");
    }

    /**
     * 每天执行一次
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void job5() {
        log.info("【k线数据更新（1天）】=========开始=========");
        Long timeInteval = 86400000l;
        lastTime5 =  klineBiz.klineJob(timeInteval, lastTime5);
        //log.info("【k线数据更新（1天）】=========结束=========");
    }

   @PostConstruct
    public void initMethod(){
        job1();
        job2();
        job3();
        job4();
        job5();
    }



}
