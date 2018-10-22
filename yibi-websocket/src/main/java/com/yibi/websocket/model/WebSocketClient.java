package com.yibi.websocket.model;

import io.netty.channel.Channel;

import java.lang.reflect.Field;

public class WebSocketClient{
	private Channel channel;//当前的通道
	private String remoteIp;
	private Integer scene;//当前所处的场景 
	private Integer gear;//推送时间类型1表示1分钟，2表示5分钟   3表示30分钟，4表示60分钟， 5-表示1天
	private Integer c1;//计价币种
	private Integer c2;//交易币种
	
	public WebSocketClient(String remoteIp){
		this.remoteIp = remoteIp;
		this.scene = 0;
	}

	public WebSocketClient(String comingIp, int scene2) {
		this.remoteIp = comingIp;
		this.scene = scene2;
	}
	
	public WebSocketClient(String comingIp, int scene2,Channel channel) {
		this.remoteIp = comingIp;
		this.scene = scene2;
		this.channel = channel;
	}
	
	public WebSocketClient(String remoteIp, Integer scene, Integer gear, Integer c1, Integer c2){
		this.remoteIp = remoteIp;
		this.scene = scene;
		this.gear = gear;
		this.c1 = c1;
		this.c2 = c2;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public Integer getScene() {
		return scene;
	}

	public void setScene(Integer scene) {
		this.scene = scene;
	}
	
	
	public Integer getGear() {
		return gear;
	}

	public void setGear(Integer gear) {
		this.gear = gear;
	}

	public Integer getC1() {
		return c1;
	}

	public void setC1(Integer c1) {
		this.c1 = c1;
	}

	public Integer getC2() {
		return c2;
	}

	public void setC2(Integer c2) {
		this.c2 = c2;
	}
	
	

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	//判断是否跟另一个object是否一致
	public  boolean equals(Object obj1, Object obj2) {

	    if (obj1 == obj2) {
	        return true;
	    }
	    if (obj1 == null || obj2 == null) {
	        return false;
	    }
	    return obj1.equals(obj2);
	}
	
	public boolean compare(WebSocketClient obj1,String del[])
	        throws Exception {
	    Field[] fs = obj1.getClass().getDeclaredFields();
	    boolean flag = false;
	   
	    for (Field f : fs) {
	    	for(int i = 0;i<del.length;i++){
	 	    	if(f.toString().endsWith(del[i])){
	 	    		flag = true;
	 	    		break;
	 	    	}
	 	    }
	    	if(flag){
	    		continue;
	    	}else{
	    		f.setAccessible(true);
	 	        Object v1 = f.get(obj1);
	 	        Object v2 = f.get(this);
	 	        if( ! equals(v1, v2)){
	 	            return false;
	 	        }
	    	}
	       
	    }
	    return true;
	}
	
}
