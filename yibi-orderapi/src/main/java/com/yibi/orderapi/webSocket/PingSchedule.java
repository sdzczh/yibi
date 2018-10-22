package com.yibi.orderapi.webSocket;

import com.yibi.common.utils.WebsocketClientUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/8/5 0005.
 */
@Component
public class PingSchedule {

    @Scheduled(cron="2/6 * * * * ?")
    public void sendPing(){
        WebsocketClientUtils.sendPing();

    }
}
