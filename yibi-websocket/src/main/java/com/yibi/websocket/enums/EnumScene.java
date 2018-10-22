package com.yibi.websocket.enums;

public enum EnumScene {
	SCENE_ORDER(350,"现货交易",150),
	SCENE_LEV(353,"杠杆交易",353),
	SCENE_MARKET_YIBI(3511,"行情一币",3511),
	SCENE_MARKET_ZULIU(3512,"行情主流",3512),
	SCENE_KLINE_YIBI(3521,"K一币",3521),
	SCENE_KLINE_ZULIU(3522,"k主流",3522);
	
	private final int scene;
	private final int type;
	private final String desc;
	EnumScene(int scene,String desc,int type) {
		this.scene = scene;
		this.desc = desc;
		this.type = type;
	}
	public int getScene(){
		return this.scene;
	}
	public String getDesc(){
		return this.desc;
	}
	public int getType(){
		return this.type;
	}
	
}
