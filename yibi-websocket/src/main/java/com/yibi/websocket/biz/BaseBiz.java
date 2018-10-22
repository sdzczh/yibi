package com.yibi.websocket.biz;

import com.yibi.websocket.model.ResultObj;
import io.netty.channel.Channel;

public interface BaseBiz {
    void sendError(Channel channel);

    void sendMessage(Channel incoming, ResultObj resultObj);
}
