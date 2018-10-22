package com.yibi.websocket.channelInitializer;

import java.util.concurrent.TimeUnit;

import com.yibi.websocket.handler.HeartbeatServerHandler;
import com.yibi.websocket.handler.TextWebSocketFrameHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ChatServerInitializer extends ChannelInitializer<Channel> {
	private static final int READ_IDEL_TIME_OUT = 200; // 读超时
	private static final int WRITE_IDEL_TIME_OUT = 200;// 写超时
	private static final int ALL_IDEL_TIME_OUT = 300; // 所有超时
    @Autowired
	private TextWebSocketFrameHandler textWebSocketFrameHandler;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new IdleStateHandler(READ_IDEL_TIME_OUT,
				WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS));
        pipeline.addLast(new HeartbeatServerHandler()); // 2
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        //pipeline.addLast(new HttpRequestHandler(""));
        pipeline.addLast(new WebSocketServerProtocolHandler(""));
        pipeline.addLast(textWebSocketFrameHandler);
        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65536,0,2));
    }
}
