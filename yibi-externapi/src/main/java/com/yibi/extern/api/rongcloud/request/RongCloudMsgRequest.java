package com.yibi.extern.api.rongcloud.request;

import io.rong.example.message.MessageExample;
import io.rong.messages.BaseMessage;
import io.rong.methods.message._private.Private;
import io.rong.methods.message.group.Group;
import io.rong.methods.message.system.MsgSystem;
import io.rong.models.message.BroadcastMessage;
import io.rong.models.message.GroupMessage;
import io.rong.models.message.PrivateMessage;
import io.rong.models.message.SystemMessage;
import io.rong.models.message.TemplateMessage;
import io.rong.models.response.ResponseResult;
import io.rong.util.GsonUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RongCloudMsgRequest extends RongCloudRequest{
    
	
	/**
	 * 發送系統消息
	 * @param sendPhone 发送人 手机号（必传）
	 * @param msg  消息, 分为两类: 内置消息类型 、自定义消息类型（必传）
	 * @param pushContent push 内容, 分为两类 内置消息 Push 、自定义消息 Push（可选）
	 * @param pushData  iOS 平台为 Push 通知时附加到 payload 中，Android 客户端收到推送消息时对应字段名为 pushData（可选）
	 * @param count 针对 iOS 平台，Push 时用来控制未读消息显示数（可选）
	 * @param isPersisted 是否在融云服务器存储, 0: 不存储, 1: 存储, 默认: 1（可选）
	 * @param isCounted 在各端是否计数, 0: 不计数, 1: 计数, 默认: 1（可选）
	 * @param targetPhones 接收方 手机号（可多传）
	 * @throws Exception
	 * @return void
	 * @date 2018-5-23
	 * @author lina
	 */
	public void sendSystemMassage(String sendPhone, 
			BaseMessage msg, String pushContent, String pushData,
			Integer count, Integer isPersisted, Integer isCounted, String... targetPhones)
			throws Exception {
		MsgSystem system = rongCloud.message.system;
		/**
		 * API 文档:
		 * http://rongcloud.github.io/server-sdk-nodejs/docs/v1/message/system.html#send
		 */
		
		SystemMessage sysMsg = new SystemMessage()
		.setSenderId(sendPhone)
		.setTargetId(targetPhones)
		.setObjectName(msg.getType())
		.setContent(msg)
		.setPushContent(pushContent)
		.setPushData(pushData)
		.setIsCounted(isCounted)
		.setIsPersisted(isPersisted)
		.setContentAvailable(count);
		
		 ResponseResult result = system.send(sysMsg);
		 log.info("【融云】发送系统消息-code:"+result.getCode()+",msg:"+result.getMsg());
			
		if(result.getCode()!=200){
			throw new RuntimeException("融云接口调用异常");
		}

	}
	
	/**
	 * 发送系统消息
	 * @param sendPhone 发送人 手机号（必传）
	 * @param msg 消息, 分为两类: 内置消息类型 、自定义消息类型（必传）
	 * @param targetPhones 接收方 手机号（可多传）
	 * @throws Exception
	 * @return void
	 * @date 2018-5-23
	 * @author lina
	 */
	public void  sendSystemMassage(String sendPhone,BaseMessage msg,String... targetPhones) throws Exception{
		sendSystemMassage(sendPhone,msg,null,null,1,1,1,targetPhones);
	}
	
	/**
	 * 发送系统模板消息方法
	 * @param templatePath 模板地址
	 * @throws Exception
	 * @return void
	 * @date 2018-5-23
	 * @author lina
	 */
	public void sendSystenTemplateMessage(String templatePath)throws Exception{
		/**
         * API 文档: http://rongcloud.github.io/server-sdk-nodejs/docs/v1/message/system.html#sendTemplate
         *
         * 发送系统模板消息方法
         *
         */
		MsgSystem system = rongCloud.message.system;
		
		Reader reader = null ;
        try {
            reader =new BufferedReader( new InputStreamReader(MessageExample.class.getClassLoader().getResourceAsStream(templatePath)));
            TemplateMessage template = (TemplateMessage)GsonUtil.fromJson(reader, TemplateMessage.class);
            ResponseResult result = system.sendTemplate(template);
            log.info("【融云】-发送系统模板消息-->"+result.toString());
            if(result.getCode()!=200){
            	throw new RuntimeException("融云接口调用异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null != reader){
                reader.close();
            }
        }
	}
	
	/**
	 * 发送广播消息
	 * @param sendPhone 发送人用户 Id。（必传）
	 * @param msg 消息，
	 * @param pushContent 定义显示的 Push 内容，如果 objectName 为融云内置消息类型时，则发送后用户一定会收到 Push 信息。 如果为自定义消息，则 pushContent 为自定义消息显示的 Push 内容，如果不传则用户不会收到 Push 通知。(可选)
	 * @param pushData 针对 iOS 平台为 Push 通知时附加到 payload 中，Android 客户端收到推送消息时对应字段名为 pushData。(可选)
	 * @param os 针对操作系统发送 Push，值为 iOS 表示对 iOS 手机用户发送 Push ,为 Android 时表示对 Android 手机用户发送 Push ，如对所有用户发送 Push 信息，则不需要传 os 参数。(可选)
	 * @throws Exception
	 * @return void
	 * @date 2018-5-23
	 * @author lina
	 */
	public void sendBroadcastMessage(String sendPhone,BaseMessage msg,String pushContent, String pushData,String os ) throws Exception{
		/**
         * API 文档: http://rongcloud.github.io/server-sdk-nodejs/docs/v1/message/broadcast.html#sendTemplate
         *
         * 发送广播消息方法
         *
         */
        BroadcastMessage message = new BroadcastMessage()
                .setSenderId(sendPhone)
                .setObjectName(msg.getType())
                .setContent(msg)
                .setPushContent(pushContent)
                .setPushData(pushData)
                .setOs(os);
        ResponseResult result = rongCloud.message.system.broadcast(message);
        log.info("【融云】-发送广播消息-->"+result.toString());
        if(result.getCode()!=200){
        	throw new RuntimeException("融云接口调用异常");
        }
	}
	
	
	/**
	 * 一个用户向另外一个用户发送消息，单条消息最大 128k。
	 * @param sendPhone 发送人用户 Id。（必传）
	 * @param msg 消息（必传）
	 * @param pushContent  定义显示的 Push 内容，如果 objectName 为融云内置消息类型时，则发送后用户一定会收到 Push 信息。 如果为自定义消息，则 pushContent 为自定义消息显示的 Push 内容，如果不传则用户不会收到 Push 通知。(可选)
	 * @param pushData 针对 iOS 平台为 Push 通知时附加到 payload 中，Android 客户端收到推送消息时对应字段名为 pushData。(可选)
	 * @param count 针对 iOS 平台，Push 时用来控制未读消息显示数，只有在 toUserId 为一个用户 Id 的时候有效。(可选)
	 * @param verifyBlacklist 是否过滤接收用户黑名单列表，0 表示为不过滤、 1 表示为过滤，默认为 0 不过滤。(可选)
	 * @param isPersisted 针对用户当前使用的客户端版本，如果没有对应 objectName 赋值的消息类型时，客户端收到消息后是否进行存储，0 表示为不存储、 1 表示为存储，默认为 1 存储消息。(可选)
	 * @param isCounted 针对用户当前使用的客户端版本，如果没有对应 objectName 赋值的消息类型时，客户端收到消息后是否进行未读消息计数，0 表示为不计数、 1 表示为计数，默认为 1 计数，未读消息数增加 1。(可选)
	 * @param isIncludeSender 发送用户自己是否接收消息，0 表示为不接收，1 表示为接收，默认为 0 不接收，只有在 toUserId 为一个用户 Id 的时候有效。（可选）
	 * @param targetPhones 接收用户 Id，可以实现向多人发送消息，每次上限为 1000 人。（必传）
	 * @return void
	 * @date 2018-5-23
	 * @author lina
	 * @throws Exception 
	 */
	public void sendPrivateMessage(String sendPhone, 
			BaseMessage msg, String pushContent, String pushData,
			Integer count,Integer verifyBlacklist, Integer isPersisted, Integer isCounted,Integer isIncludeSender, String... targetPhones) throws Exception{
		/**
         * API 文档: http://rongcloud.github.io/server-sdk-nodejs/docs/v1/message/private.html#send
         *
         * 发送单聊消息
         * */
		Private Private = rongCloud.message.msgPrivate;
		
		PrivateMessage privateMessage = new PrivateMessage()
        .setSenderId(sendPhone)
        .setTargetId(targetPhones)
        .setObjectName(msg.getType())
        .setContent(msg)
        .setPushContent(pushContent)
        .setPushData(pushData)
        .setCount(count.toString())
        .setVerifyBlacklist(verifyBlacklist)
        .setIsPersisted(isPersisted)
        .setIsCounted(isCounted)
        .setIsIncludeSender(isIncludeSender);
		ResponseResult result = Private.send(privateMessage);
		log.info("【融云】-发送单聊消息-->"+result.toString());
        if(result.getCode()!=200){
        	throw new RuntimeException("融云接口调用异常");
        }
	}
	
	/**
	 * 发送单人消息
	 * @param sendPhone
	 * @param msg
	 * @param targetPhones
	 * @throws Exception
	 * @return void
	 * @date 2018-5-23
	 * @author lina
	 */
	public void sendPrivateMessage(String sendPhone,BaseMessage msg,String... targetPhones ) throws Exception{
		sendPrivateMessage(sendPhone, msg, null, null, 0, 0, 1, 1, 0, targetPhones);
	}
	
	/**
	 * 以一个用户身份向群组发送消息，单条消息最大 128k。
	 * @param sendPhone 发送人用户 Id 。（必传）
	 * @param pushContent  定义显示的 Push 内容，如果 objectName 为融云内置消息类型时，则发送后用户一定会收到 Push 信息。 如果为自定义消息，则 pushContent 为自定义消息显示的 Push 内容，如果不传则用户不会收到 Push 通知。(可选)
	 * @param pushData 针对 iOS 平台为 Push 通知时附加到 payload 中，Android 客户端收到推送消息时对应字段名为 pushData。(可选)
	 * @param count 针对 iOS 平台，Push 时用来控制未读消息显示数，只有在 toUserId 为一个用户 Id 的时候有效。(可选)
	 * @param isPersisted 针对用户当前使用的客户端版本，如果没有对应 objectName 赋值的消息类型时，客户端收到消息后是否进行存储，0 表示为不存储、 1 表示为存储，默认为 1 存储消息。(可选)
	 * @param isCounted 针对用户当前使用的客户端版本，如果没有对应 objectName 赋值的消息类型时，客户端收到消息后是否进行未读消息计数，0 表示为不计数、 1 表示为计数，默认为 1 计数，未读消息数增加 1。(可选)
	 * @param isIncludeSender 发送用户自己是否接收消息，0 表示为不接收，1 表示为接收，默认为 0 不接收，只有在 toUserId 为一个用户 Id 的时候有效。（可选）
	 * @param targetGroupIds 接收群 Id，提供多个本参数可以实现向多群发送消息，最多不超过 3 个群组。（必传）
	 * @throws Exception
	 * @return void
	 * @date 2018-5-23
	 * @author lina
	 */
	public void sendGroupMassage(String sendPhone, 
			BaseMessage msg, String pushContent, String pushData,
			Integer count, Integer isPersisted, Integer isCounted,Integer isIncludeSender, String... targetGroupIds) throws Exception{
		Group group = rongCloud.message.group;
		/**
         * API 文档: http://rongcloud.github.io/server-sdk-nodejs/docs/v1/message/group.html#send
         *
         * 群组消息
         * */
		GroupMessage groupMessage = new GroupMessage()
        .setSenderId(sendPhone)
        .setTargetId(targetGroupIds)
        .setObjectName(msg.getType())
        .setContent(msg)
        .setPushContent(pushContent)
        .setPushData(pushData)
        .setIsPersisted(isPersisted)
        .setIsCounted(count)
        .setIsIncludeSender(isIncludeSender)
        .setContentAvailable(isCounted);
		ResponseResult result = group.send(groupMessage);
		log.info("【融云】-发送单聊消息-->"+result.toString());
        if(result.getCode()!=200){
        	throw new RuntimeException("融云接口调用异常");
        }
	}
	
	/**
	 * 发送群组消息
	 * @param sendPhone
	 * @param msg
	 * @param targetGroupIds
	 * @return void
	 * @date 2018-5-23
	 * @author lina
	 * @throws Exception 
	 */
	public void sendGroupMassage(String sendPhone,BaseMessage msg,String... targetGroupIds) throws Exception{
		sendGroupMassage(sendPhone, msg, null, null, 0, 1, 1, 0, targetGroupIds);
	}
}
