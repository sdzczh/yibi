     package com.yibi.extern.api.rongcloud.request;

import io.rong.methods.conversation.Conversation;
import io.rong.models.conversation.ConversationModel;
import io.rong.models.response.ResponseResult;
import io.rong.util.CodeUtil.ConversationType;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RongCloudConversationRequest extends RongCloudRequest{
	 
	 
	 /**
	  * 设置会话消息免打扰方法
	  * @param conversationType 会话类型，二人会话是 1 、讨论组会话是 2 、群组会话是 3 、客服会话是 5 、系统通知是 6 、应用公众服务是 7 、公众服务是 8 。（必传）
	  * @param requestPhone 设置消息免打扰的用户 手机号。（必传）
	  * @param targetId 目标 Id，根据不同的 ConversationType，可能是用户 Id、讨论组 Id、群组 Id、客服 Id、公众号 Id。（必传）
	  * @param isMuted 消息免打扰设置状态，0 表示为关闭，1 表示为开启。（必传）
	  * @return void
	  * @date 2018-6-8
	  * @author lina
	 * @throws Exception 
	  */
	 public void mute(ConversationType conversationType,String requestPhone,String targetId,boolean isMuted) throws Exception{
        //自定义 api 地址方式
        // RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret,api);

        Conversation Conversation = rongCloud.conversation;

        ConversationModel conversation = new ConversationModel()
                .setType(conversationType.getName())
                .setUserId(requestPhone)
                .setTargetId(targetId);
        /**
         *
         * API 文档: http://rongcloud.github.io/server-sdk-nodejs/docs/v1/conversation/conversation.html#mute
         * 设置消息免打扰
         *
         */
        ResponseResult result = null;
        
        if(isMuted){
        	result = Conversation.mute(conversation);
        }else{
        	result = Conversation.unMute(conversation);
        }
        log.info("【融云】-设置回话消息免打扰-->"+result.toString());
        if(result.getCode()!=200){
        	throw new RuntimeException("融云接口调用异常");
		}
	 }
}
