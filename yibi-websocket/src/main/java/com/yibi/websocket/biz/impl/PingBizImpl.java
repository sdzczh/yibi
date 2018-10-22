package com.yibi.websocket.biz.impl;


import com.alibaba.fastjson.JSONObject;
import com.yibi.websocket.biz.PingBiz;
import com.yibi.websocket.model.ResultCode;
import com.yibi.websocket.model.ResultObj;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PingBizImpl extends BaseBizImpl implements PingBiz {

    @Override
    public void ping(Channel channel, String cmsgCode) {
        ResultObj resultObj = new ResultObj();
        resultObj.setCmsgCode(cmsgCode);
        resultObj.setInfo("pong");
        resultObj.setCode(ResultCode.SUCCESS.code());
        resultObj.setMsg(ResultCode.SUCCESS.message());
        sendMessage(channel, resultObj);
    }
}
