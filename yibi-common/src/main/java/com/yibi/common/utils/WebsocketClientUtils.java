package com.yibi.common.utils;

import lombok.extern.log4j.Log4j2;
import org.java_websocket.WebSocket.READYSTATE;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

@Log4j2
public class WebsocketClientUtils {
	public static WebSocketClient client;

	public static void send(byte[] bytes){
		client.send(bytes);
	}
	public static void sessionConnectMethod(){
		PropertyUtils pt = new PropertyUtils("common.properties");
		String YBWebSocketServer = pt.getProperty("YB.WEBSOCKET");
		/*if (client != null) {
			client.close();
		}*/
		if (client == null || !client.getReadyState().equals(READYSTATE.OPEN)) {
            client = null;
			try {
				client = new WebSocketClient(new URI(YBWebSocketServer),new Draft_6455()) {

					@Override
					public void onOpen(ServerHandshake arg0) {
						log.info("websocket客户端打开链接");
					}

					@Override
					public void onMessage(String arg0) {
						log.info("websocket客户端收到消息"+arg0);
					}

					@Override
					public void onError(Exception arg0) {
						arg0.printStackTrace();
						log.info("websocket客户端发生错误已关闭");
					}

					@Override
					public void onClose(int arg0, String arg1, boolean arg2) {
						log.info("websocket客户端链接已关闭");
					}

					@Override
					public void onMessage(ByteBuffer bytes) {
						try {
							log.info(new String(bytes.array(),"utf-8"));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}


				};

				client.connect();

				while(!client.getReadyState().equals(READYSTATE.OPEN)){
					log.info("等待websocket链接打开");
				}
				log.info("websocket通道已打开");
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}

	public static synchronized  void sendPing(){
		try {
			if(client !=null){
				client.sendPing();
				log.debug("服务器发送ping");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			client = null;
		}
	}

	public static synchronized void sendTextMessage(String text) {
		sessionConnectMethod();
		try {
			//session.getAsyncRemote().sendText(text);
			client.send(text);
			log.info("服务器发送websocket:"+text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
