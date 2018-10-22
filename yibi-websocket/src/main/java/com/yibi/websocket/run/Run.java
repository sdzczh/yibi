package com.yibi.websocket.run;


import com.yibi.websocket.channelInitializer.ChatServerInitializer;
import com.yibi.websocket.handler.TextWebSocketFrameHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.InetSocketAddress;

@Log4j2
public class Run {

    private final EventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;
    private static ChatServerInitializer chatServerInitializer;

    public ChannelFuture start(InetSocketAddress address) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group).channel(NioServerSocketChannel.class)
                .childHandler(chatServerInitializer);
        ChannelFuture future = bootstrap.bind(address);
        future.syncUninterruptibly();
        channel = future.channel();
        return future;
    }



    public void destroy() {
        if (channel != null) {
            channel.close();
        }
        log.info("destroy方法执行");
        TextWebSocketFrameHandler.group.close();
        group.shutdownGracefully();
    }

    public static void main(String[] args) {
        log.info("websocketServer启动");
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:globalmarket-spring.xml");
        chatServerInitializer = (ChatServerInitializer) context.getBean("chatServerInitializer");
        int port = 1606;
        final Run endpoint = new Run();
        ChannelFuture future = endpoint.start(new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                endpoint.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();

    }
}
