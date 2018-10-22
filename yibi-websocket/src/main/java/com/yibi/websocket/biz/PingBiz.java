package com.yibi.websocket.biz;

import io.netty.channel.Channel;

public interface PingBiz {
    void ping(Channel channel, String cmsgCode);
}
