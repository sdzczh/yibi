package com.yibi.globalmarket.variables;


public class WebsocketConstants {
	public static final String ACTION_BROADCAST = "broadcast";
	public static final String C1_YBMARKET = "1";
	public static final String C1_OKMARKET = "2";
	public static final int SCENE_MARKET1 = 3511;
	public static final int SCENE_MARKET2 = 3512;
	public static final int SCENE_KLine1 = 3521;
	public static final int SCENE_KLine2 = 3522;
	public static final int SCENE_ORDER = 350;
	
	/*okcoin*/
	public static final String OKCOINURL = "https://www.okcoin.com";
	//Get /api/v1/ticker  取OKCoin行情
	public static final String TICKER = OKCOINURL + "/api/v1/ticker.do?symbol=%s_usd";
	//Get /api/v1/trades 获取OKCoin交易信息(60条)
	public static final String TRADES = OKCOINURL + "/api/v1/trades.do?symbol=%s_usd";
	//Get /api/v1/kline 获取OKCoin的K线数据
	public static final String KLINE = OKCOINURL + "/api/v1/kline.do?symbol=%s_usd&type=%s&size=%s&since=%s";
	
	public static final String OKCOINSERVERURL = "wss://real.okcoin.com:10440/websocket";
	
	//public static final String YBSERVERURL = "ws://wangbian6789.kmdns.net:9999";//本机
	public static final String YBSERVERURL = "ws://39.106.20.211:1606";//测试环境
	//public static final String YBSERVERURL = "ws://socket.huolicoin.com:1606";//正式环境
	
}

