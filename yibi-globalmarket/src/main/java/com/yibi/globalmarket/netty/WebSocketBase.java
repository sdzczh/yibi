package com.yibi.globalmarket.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslContext;
import org.apache.log4j.Logger;

import java.net.URI;
import java.util.*;

public abstract class WebSocketBase {

	private Logger log = Logger.getLogger(WebSocketBase.class);
	private WebSocketService service = null;
	private Timer timerTask = null;
	private MoniterTask moniter = null;
	private EventLoopGroup group = null;
	private Bootstrap bootstrap = null;
	private Channel channel = null;
	private String url = null;
	private ChannelFuture future = null;
	private boolean isAlive = false;
	/** 国内站siteFlag=0,国际站siteFlag=1 */
	private int siteFlag = 0;
	private Set<String> subscribChannel = new HashSet<String>();

	public WebSocketBase(String url, WebSocketService serivce) {
		this.url = url;
		this.service = serivce;
	}

	public void start() {
		if (url == null) {
			log.info("WebSocketClient start error  url can not be null");
			return;
		}
		if (service == null) {
			log.info("WebSocketClient start error  WebSocketService can not be null");
			return;
		}
		moniter = new MoniterTask(this);
		this.connect();
		timerTask = new Timer();
		timerTask.schedule(moniter, 1000, 5000);
	}

	public void setStatus(boolean flag) {
		this.isAlive = flag;
	}

	public void addChannel(String channel) {
		if (channel == null) {
			return;
		}
		this.sendMessage("{'event':'addChannel','channel':'" + channel + "'}");
		subscribChannel.add(channel);
	}

	public void removeChannel(String channel) {
		if (channel == null) {
			return;
		}
		String dataMsg = "{'event':'removeChannel','channel':'" + channel
				+ "'}";
		this.sendMessage(dataMsg);
		subscribChannel.remove(channel);
	}

	public void sendText(String text){
		this.sendMessage(text);
	}




	private void connect() {
		try {
			final URI uri = new URI(url);
			if (uri == null) {
				return;
			}
			if (uri.getHost().contains("com")) {
				siteFlag = 1;
			}
			group = new NioEventLoopGroup(1);
			bootstrap = new Bootstrap();
			final SslContext sslCtx = SslContext.newClientContext();
			final WebSocketClientHandler handler = new WebSocketClientHandler(
					WebSocketClientHandshakerFactory.newHandshaker(uri,
							WebSocketVersion.V13, null, false,
							new DefaultHttpHeaders(), Integer.MAX_VALUE),
					service, moniter);
			bootstrap.group(group).option(ChannelOption.TCP_NODELAY, true)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						protected void initChannel(SocketChannel ch) {
							ChannelPipeline p = ch.pipeline();
							if (sslCtx != null) {
								p.addLast(sslCtx.newHandler(ch.alloc(),
										uri.getHost(), uri.getPort()));
							}
							p.addLast(new HttpClientCodec(),
									new HttpObjectAggregator(8192), handler);
						}
					});

			future = bootstrap.connect(uri.getHost(), uri.getPort());
			future.addListener(new ChannelFutureListener() {
				public void operationComplete(final ChannelFuture future)
						throws Exception {
				}
			});
			channel = future.sync().channel();
			handler.handshakeFuture().sync();
			this.setStatus(true);
		} catch (Exception e) {
			log.info("WebSocketClient start error ", e);
			group.shutdownGracefully();
			this.setStatus(false);
		}
	}

	private void sendMessage(String message) {
		if (!isAlive) {
			log.info("WebSocket is not Alive addChannel error");
		}
		channel.writeAndFlush(new TextWebSocketFrame(message));
	}

	public void sentPing() {
		String dataMsg = "{'event':'ping'}";
		this.sendMessage(dataMsg);
	}

	public void reConnect() {
		try {
			this.group.shutdownGracefully();
			this.group = null;
			this.connect();
			if (future.isSuccess()) {
				this.setStatus(true);
				this.sentPing();
				Iterator<String> it = subscribChannel.iterator();
				while (it.hasNext()) {
					String channel = it.next();
					this.addChannel(channel);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

class MoniterTask extends TimerTask {

	private Logger log = Logger.getLogger(WebSocketBase.class);
	private long startTime = System.currentTimeMillis();
	private int checkTime = 5000;
	private WebSocketBase client = null;

	public void updateTime() {
		// log.info("startTime is update");
		startTime = System.currentTimeMillis();
	}

	public MoniterTask(WebSocketBase client) {
		this.client = client;
		// log.info("TimerTask is starting.... ");
	}

	public void run() {
		if (System.currentTimeMillis() - startTime > checkTime) {
			client.setStatus(false);
			// log.info("Moniter reconnect....... ");
			client.reConnect();
		}
		client.sentPing();
		// log.info("Moniter ping data sent.... ");
	}

}
