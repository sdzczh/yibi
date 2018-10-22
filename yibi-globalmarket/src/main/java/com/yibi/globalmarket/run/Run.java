package com.yibi.globalmarket.run;

import com.yibi.common.utils.PropertyUtils;
import com.yibi.globalmarket.netty.WebSocketService;
import com.yibi.globalmarket.netty.WebSoketClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
@Log4j2
public class Run {
    public static void main(String[] args) {
        log.info("全球行情更新方法启动");
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:globalmarket-spring.xml");
        PropertyUtils pt = new PropertyUtils("serverUrl.properties");
        String okcoinServer = pt.getProperty("OKCOIN.WEBSOCKET");
        WebSocketService service = context.getBean(WebSocketService.class);
        // WebSocket客户端
        WebSoketClient client = new WebSoketClient(okcoinServer, service);
        log.info("websocket--okcoin链接建立");
        // 启动客户端
        client.start();

        // 添加订阅btc_usd, ltc_usd, eth_usd, etc_usd
        client.addChannel("ok_sub_spot_btc_usd_ticker");
        client.addChannel("ok_sub_spot_ltc_usd_ticker");
        client.addChannel("ok_sub_spot_eth_usd_ticker");
        client.addChannel("ok_sub_spot_etc_usd_ticker");
    }
}
