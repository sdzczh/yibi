package com.yibi.websocket.handler;

import com.alibaba.fastjson.JSONObject;
import com.yibi.websocket.biz.*;
import com.yibi.websocket.model.Result;
import com.yibi.websocket.model.ResultCode;
import com.yibi.websocket.model.ResultObj;
import com.yibi.websocket.model.WebSocketClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.ImmediateEventExecutor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Log4j2
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends
        SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Resource
    private PingBiz pingBiz;
    @Resource(name = "baseBizImpl")
    private BaseBiz baseBiz;
    @Autowired
    private JoinBiz joinBiz;
    @Autowired
    private OrderBiz orderBiz;
    @Autowired
    private BroadCastBiz broadCastBiz;
    public static ChannelGroup group = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

    public static Map<String, WebSocketClient> allSocketClients = new ConcurrentHashMap<>();


    /**
     * 客户端发来消息时触发
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg)
            throws Exception {
        Channel incoming = ctx.channel();
        String reciveMsg = msg.text();
        log.info("websocket服务器收到数据：" + reciveMsg);
        try {
            JSONObject json = JSONObject.parseObject(reciveMsg);
            String action = json.getString("action");
            String cmsgCode = json.getString("cmsg_code");
            if (cmsgCode == null || "".equals(cmsgCode)) {
                cmsgCode = "-1";
            }
            ResultObj resultObj = new ResultObj();
            resultObj.setCmsgCode(cmsgCode);
            switch (action) {
                case "ping": {
                    pingBiz.ping(incoming, cmsgCode);
                    break;
                }
                case "join": {
                    JSONObject data = json.getJSONObject("data");
                    if (data == null) {
                        resultObj.setCode(ResultCode.TYPE_ERROR_PARAMS.code());
                        resultObj.setMsg(ResultCode.TYPE_ERROR_PARAMS.message());
                        baseBiz.sendMessage(incoming, resultObj);
                        return;
                    }
                    joinBiz.join(incoming, data, resultObj, allSocketClients);

                    break;
                }
                case "order": {
                    JSONObject data = json.getJSONObject("data");
                    if (data != null) {
                        orderBiz.orderBroadcast(data, allSocketClients);
                        return;
                    }

                    break;
                }
                case "broadcast": {
                    JSONObject data = json.getJSONObject("data");
                    if (action == null || "".equals(action) || data == null) {
                        resultObj.setCode(ResultCode.TYPE_ERROR_PARAMS.code());
                        resultObj.setMsg(ResultCode.TYPE_ERROR_PARAMS.message());
                        baseBiz.sendMessage(incoming, resultObj);
                        return;
                    }
                    broadCastBiz.broadCast(data, allSocketClients);
                    break;
                }
                default: {
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            ResultObj resultObj = new ResultObj();
            resultObj.setMsg(ResultCode.TYPE_ERROR_UNKNOW.message());
            resultObj.setCode(ResultCode.TYPE_ERROR_UNKNOW.code());
            //baseBiz.sendMessage(incoming, resultObj);
            // ctx.close();
        }
    }


    //处理广播信息,通知大家这是一条广播
//	private void broadcastDel(JSONObject data){
//		logger.info("websocket服务器收到广播："+data.toString());
//		int scene = data.getIntValue("scene");
//		int c1 = data.getIntValue("c1");
//		int c2 = data.getIntValue("c2");
//		int gear = data.getIntValue("gear");
//		if(scene==EnumScene.SCENE_HLC.getScene()){
//			//如果是交易场景
//			JSONObject info = data.getJSONObject("info");
//			JSONObject json = new JSONObject();
//			json.put("type",EnumScene.SCENE_HLC.getType());
//			json.put("code",1);
//			for(WebSocketClient client : allSocketClients.values()){
//				Channel incoming = client.getChannel();
//				if(client.getC1()==c1&&client.getC2()==c2&&client.getScene()==scene){
//					List<Object> buys = info.getJSONArray("buys");
//					List<Object> sales = info.getJSONArray("sales");
//					if(buys == null)buys = new ArrayList<Object>();
//					if(sales == null)sales = new ArrayList<Object>();
//					int maxSize = client.getGear();
//					if(buys.size() <= maxSize) buys = buys.subList(0, buys.size());
//					else buys = buys.subList(0, maxSize);
//					if(sales.size() <= maxSize) sales = sales.subList(0, sales.size());
//					else sales = sales.subList(sales.size()-maxSize, sales.size());
//					info.put("buys", buys);
//					info.put("sales", sales);
//					json.put("info",info.toString());
//					sendMessage(incoming,json.toJSONString());
//					logger.info("websocket服务器发送广播："+json.toString());
//				}
//			}
//		} else if(scene == EnumScene.SCENE_LEV.getScene()){
//			//如果是杠杆交易场景
//			JSONObject info = data.getJSONObject("info");
//			JSONObject json = new JSONObject();
//			json.put("type",EnumScene.SCENE_LEV.getType());
//			json.put("code",1);
//			for(WebSocketClient client : allSocketClients.values()){
//				Channel incoming = client.getChannel();
//				if(client.getC1()==c1&&client.getC2()==c2&&client.getScene()==scene){
//					List<Object> buys = info.getJSONArray("buys");
//					List<Object> sales = info.getJSONArray("sales");
//					if(buys == null)buys = new ArrayList<Object>();
//					if(sales == null)sales = new ArrayList<Object>();
//					int maxSize = client.getGear();
//					if(buys.size() <= maxSize) buys = buys.subList(0, buys.size());
//					else buys = buys.subList(0, maxSize);
//					if(sales.size() <= maxSize) sales = sales.subList(0, sales.size());
//					else sales = sales.subList(sales.size()-maxSize, sales.size());
//					info.put("buys", buys);
//					info.put("sales", sales);
//					json.put("info",info.toString());
//					sendMessage(incoming,json.toJSONString());
//				}
//			}
//		} else if(scene==EnumScene.SCENE_HYC_YIBI.getScene()||scene==EnumScene.SCENE_HYC_ZULIU.getScene()){
//			//如果是行情场景
//			JSONObject json = new JSONObject();
//			json.put("type",scene);
//			json.put("code",1);
//			//String istr = "{'action':'broadcast','data':{'scene':3511,'info':'{\'price\':\'10\',\'salesversion\':\'1524625043\',\'sales\':[],\'buysversion\':\'1524625043\',\'buys\':[{\'num\':\'买1\',\'price\':\'10\',\'remain\':\'0.2888\'},{\'num\':\'买2\',\'price\':\'4\',\'remain\':\'0.601\'},{\'num\':\'买3\',\'price\':\'1\',\'remain\':\'1.5\'}]}}}";
//			//String istr = "{'action':'broadcast','data':{'scene':3511,'info':'{\"price\":\"10\",\"salesversion\":\"1524625043\",\"sales\":[],\"buysversion\":\"1524625043\",\"buys\":[{\"num\":\"买1\",\"price\":\"10\",\"remain\":\"0.2888\"},{\"num\":\"买2\",\"price\":\"4\",\"remain\":\"0.601\"},{\"num\":\"买3\",\"price\":\"1\",\"remain\":\"1.5\"}]}'}}";
//			JSONObject info = data.getJSONObject("info");
//			json.put("info",info);
//			for(WebSocketClient client : allSocketClients.values()){
//				Channel incoming = client.getChannel();
//				if(client.getScene()==scene){
//					sendMessage(incoming,json.toJSONString());
//				}
//			}
//
//		}else if(scene==EnumScene.SCENE_HKC_YIBI.getScene() || scene==EnumScene.SCENE_HKC_ZULIU.getScene()){
//			//如果是k线图场景
//			JSONObject json = new JSONObject();
//			json.put("type",scene);
//			json.put("code",1);
//			json.put("info",data.getString("info"));
//			for(WebSocketClient client : allSocketClients.values()){
//				Channel incoming = client.getChannel();
//				if(gear == 0 && client.getScene()==scene && client.getC1() == c1 && client.getC2() == c2){//k线图场景行情
//					sendMessage(incoming,json.toJSONString());
//				}
//				if(client.getScene()==scene && client.getC1() == c1 && client.getC2() == c2 && client.getGear() == gear){//k线图数据
//					sendMessage(incoming,json.toJSONString());
//				}
//			}
//		}
//
//
//	}
//
//
//
//	// 发送当前场景信息
//	private void sceneJoin(Channel incoming, JSONObject data) {
//		int scene = data.getIntValue("scene");
//		int cmsgCode = data.getIntValue("cmsg_code");
//		int gear = data.getIntValue("gear");
//		int c1 = data.getIntValue("c1");
//		int c2 = data.getIntValue("c2");
//		if (scene == 0) {
//			sendMessage(incoming,
//					Result.toResultStr(ResultCode.TYPE_ERROR_PARAMS));
//			return;
//		}
//		String comingIp = incoming.remoteAddress().toString();
//		WebSocketClient wsc = allSocketClients.get(comingIp);
//		if(wsc == null){
//			wsc = new WebSocketClient(comingIp,scene,gear,c1,c2);
//			wsc.setChannel(incoming);
//			wsc.setScene(scene);
//			wsc.setGear(gear);
//			wsc.setC1(c1);
//			wsc.setC2(c2);
//			allSocketClients.put(comingIp,wsc);
//		}else{
//			wsc.setChannel(incoming);
//			wsc.setScene(scene);
//			wsc.setGear(gear);
//			wsc.setC1(c1);
//			wsc.setC2(c2);
//		}
//
//		if(cmsgCode==0) cmsgCode = -1;
//		// 处理返回数据
//		if (scene == EnumScene.SCENE_HLC.getScene()) {// 现货350
//			dealSceneHlc(data,cmsgCode,incoming,wsc);
//		} else if(scene == EnumScene.SCENE_LEV.getScene()){
//			dealSceneLev(data,cmsgCode,incoming,wsc);
//		} else if (scene == EnumScene.SCENE_HYC_YIBI.getScene()||scene == EnumScene.SCENE_HYC_ZULIU.getScene()) {// 行情
//			//dealSceneHyc(scene,cmsgCode,incoming,wsc);
//		} else if (scene == EnumScene.SCENE_HKC_YIBI.getScene()||scene == EnumScene.SCENE_HKC_ZULIU.getScene()) {// K线图
//			dealSceneHkc(data,cmsgCode,incoming,wsc);
//		} else {
//			// 什么都不做
//			sendMessage(incoming,
//					Result.toResultStr(ResultCode.TYPE_ERROR_PARAMS));
//			return;
//		}
//	}
//
//	//处理初始化HLC场景
//	public void dealSceneHlc(JSONObject data,int cmsgCode,Channel incoming,WebSocketClient wsc){
//		String c1 = data.getString("c1");//计价币
//		String c2 = data.getString("c2");//交易币
//		int gear = data.getIntValue("gear");
//		int scene = data.getIntValue("scene");
//		int count = 10;// 默认10挡
//		if (gear == EnumGear.GEAR_THREE.getGear())
//			count = EnumGear.GEAR_THREE.getPageByGear(gear);
//		if (gear == EnumGear.GEAR_FOUR.getGear())
//			count = EnumGear.GEAR_FOUR.getPageByGear(gear);
//		if (gear == EnumGear.GEAR_FIVE.getGear())
//			count = EnumGear.GEAR_FIVE.getPageByGear(gear);
//		List saleslist = null;
//		List buyslist = null;
//		String price = "";
//		String buysversion = "";
//		String salesversion = "";
//		// 获取redis链接
//		try {
//			jedis = RedisPool.getJedis();
//
//			StringBuffer sb = new StringBuffer();
//			//现货交易350
//			if(scene == EnumScene.SCENE_HLC.getScene()){
//				sb.append("coinorder:order:");
//			}else if(scene == EnumScene.SCENE_LEV.getScene()){
//				sb.append("coinorder:orderLev:");
//			}
//			sb.append(c1);
//			sb.append(":");
//			sb.append(c2);
//			sb.append(":");
//			String price_key = sb.toString() + "price";
//			String buylist_key = sb.toString() + "buys:list";
//			String buysversion_key = sb.toString() + "buys:version";
//			String saleslist_key = sb.toString() + "sales:list";
//			String salesversion_key = sb.toString() + "sales:version";
//			long saleslistSize = jedis.llen(saleslist_key);
//			if(saleslistSize <= count)
//				saleslist = jedis.lrange(saleslist_key, 0, count-1);
//			else
//				saleslist = jedis.lrange(saleslist_key, saleslistSize-count, saleslistSize-1);
//			buyslist = jedis.lrange(buylist_key, 0, count-1);
//			buysversion = jedis.get(buysversion_key);
//			buysversion = buysversion == null ? "" : buysversion;
//			salesversion = jedis.get(salesversion_key);
//			salesversion = salesversion == null ? "" : salesversion;
//			price = jedis.get(price_key);
//			//System.out.println(buyslist.toString());
//			JSONArray buyjson = JSONObject.parseArray(buyslist.toString());
//			JSONArray salejson = JSONObject.parseArray(saleslist.toString());
//			JSONObject json = new JSONObject();
//			json.put("buysversion", buysversion);
//			json.put("buys", buyjson);
//			json.put("sales", salejson);
//			json.put("salesversion", salesversion.toString());
//			json.put("price", price);
//
//			logger.info("发送现货数据包:" + json.toString());
//			JSONObject result = new JSONObject();
//			if(scene == EnumScene.SCENE_HLC.getScene()){
//				result.put("type", EnumScene.SCENE_HLC.getType());
//			}else if(scene == EnumScene.SCENE_LEV.getScene()){
//				result.put("type", EnumScene.SCENE_LEV.getType());
//			}
//			result.put("code", cmsgCode);
//			result.put("info", json.toString());
//			sendMessage(incoming,result.toJSONString());
//		}catch(Exception e){
//			e.printStackTrace();
//		} finally {// 释放redis链接
//			RedisPool.returnResource(jedis);
//		}
//	}
//
//	//处理初始化HLC场景
//		public void dealSceneLev(JSONObject data,int cmsgCode,Channel incoming,WebSocketClient wsc){
//			String c1 = data.getString("c1");//计价币
//			String c2 = data.getString("c2");//交易币
//			int gear = data.getIntValue("gear");
//			int scene = data.getIntValue("scene");
//			int count = 10;// 默认10挡
//			if (gear == EnumGear.GEAR_THREE.getGear())
//				count = EnumGear.GEAR_THREE.getPageByGear(gear);
//			if (gear == EnumGear.GEAR_FOUR.getGear())
//				count = EnumGear.GEAR_FOUR.getPageByGear(gear);
//			if (gear == EnumGear.GEAR_FIVE.getGear())
//				count = EnumGear.GEAR_FIVE.getPageByGear(gear);
//			List saleslist = null;
//			List buyslist = null;
//			String price = "";
//			String buysversion = "";
//			String salesversion = "";
//			// 获取redis链接
//			try {
//				jedis = RedisPool.getJedis();
//
//				StringBuffer sb = new StringBuffer();
//				sb.append("coinorder:orderLev:");
//				sb.append(c1);
//				sb.append(":");
//				sb.append(c2);
//				sb.append(":");
//				String price_key = sb.toString() + "price";
//				String buylist_key = sb.toString() + "buys:list";
//				String buysversion_key = sb.toString() + "buys:version";
//				String saleslist_key = sb.toString() + "sales:list";
//				String salesversion_key = sb.toString() + "sales:version";
//				long saleslistSize = jedis.llen(saleslist_key);
//				if(saleslistSize <= count)
//					saleslist = jedis.lrange(saleslist_key, 0, count-1);
//				else
//					saleslist = jedis.lrange(saleslist_key, saleslistSize-count, saleslistSize-1);
//				buyslist = jedis.lrange(buylist_key, 0, count-1);
//				buysversion = jedis.get(buysversion_key);
//				buysversion = buysversion == null ? "" : buysversion;
//				salesversion = jedis.get(salesversion_key);
//				salesversion = salesversion == null ? "" : salesversion;
//				price = jedis.get(price_key);
//				//System.out.println(buyslist.toString());
//				JSONArray buyjson = JSONObject.parseArray(buyslist.toString());
//				JSONArray salejson = JSONObject.parseArray(saleslist.toString());
//				JSONObject json = new JSONObject();
//				json.put("buysversion", buysversion);
//				json.put("buys", buyjson);
//				json.put("sales", salejson);
//				json.put("salesversion", salesversion.toString());
//				json.put("price", price);
//
//				logger.info("发送现货数据包:" + json.toString());
//				JSONObject result = new JSONObject();
//				result.put("type", EnumScene.SCENE_LEV.getType());
//				result.put("code", cmsgCode);
//				result.put("info", json.toString());
//				sendMessage(incoming,result.toJSONString());
//			}catch(Exception e){
//				e.printStackTrace();
//			} finally {// 释放redis链接
//				RedisPool.returnResource(jedis);
//			}
//		}
//
//	//处理初始化HYC场景
//	public void dealSceneHyc(int scene,int cmsgCode,Channel incoming,WebSocketClient wsc){
//		int marketType = 1;
//		if(scene==EnumScene.SCENE_HYC_ZULIU.getScene()){
//			marketType=2;
//		}
//		try {
//			jedis = RedisPool.getJedis();
//			List<Map<String, Object>> listOM = Dao.getOrderManager(marketType);
//			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//			for (Map<String, Object> map : listOM) {
//				Integer unitCoin = (Integer)map.get("unitCoinType");
//				Integer orderCoin = (Integer)map.get("orderCoinType");
//				String redisKey = String.format("coinorder:market:%s:%s:%s", marketType, unitCoin, orderCoin);
//				String redisVal = jedis.get(redisKey);
//				if(redisVal != null && !redisVal.equals("")){
//					Map<String, Object> jsonMap = JSON.parseObject(redisVal, Map.class);
//					list.add(jsonMap);
//				}
//			}
//			Map<String, Object> resultMap = new HashMap<String, Object>();
//			resultMap.put("list", list);
//			JSONObject rjson = new JSONObject();
//			rjson.put("info",JSON.toJSONString(resultMap));
//			rjson.put("code", cmsgCode);
//			rjson.put("type", scene);
//			sendMessage(incoming,rjson.toJSONString());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			sendMessage(incoming,
//					Result.toResultStr(ResultCode.TYPE_ERROR_UNKNOW, "请求远程接口错误！"));
//		} finally {// 释放redis链接
//			RedisPool.returnResource(jedis);
//		}
//	}
//	//处理初始化K线图
//	public void dealSceneHkc(JSONObject data,int cmsgCode,Channel incoming,WebSocketClient wsc){
//		if(StrUtils.checkNull(data.getString("scene"))
//				||StrUtils.checkNull(data.getString("gear"))
//				||StrUtils.checkNull(data.getString("c1"))
//				||StrUtils.checkNull(data.getString("c2"))){
//			sendMessage(incoming,
//					Result.toResultStr(ResultCode.TYPE_ERROR_PARAMS, "传递参数错误"));
//			return ;
//		}
//		int scene = data.getIntValue("scene");
//		Integer c1 = data.getIntValue("c1");
//		Integer c2 = data.getIntValue("c2");
//		Integer gear = data.getIntValue("gear");
//		wsc.setC1(c1);
//		wsc.setC2(c2);
//		wsc.setGear(gear);
//		WebRequest wr = new WebRequest();
//		int marketType = 0;
//		try {
//			if(scene==EnumScene.SCENE_HKC_YIBI.getScene()){ //一币K图
//				marketType = 1;
//			}
//			if(scene==EnumScene.SCENE_HKC_ZULIU.getScene()){//主流k图
//				marketType = 2;
//			}
//			Map<String, Object> map_p = new HashMap<String, Object>();
//			map_p.put("marketType", marketType);
//			map_p.put("scene", scene);
//			switch (gear) {
//			case 1:
//				map_p.put("timeInteval", 60000);//一分钟
//				break;
//			case 2:
//				map_p.put("timeInteval", 300000);//五分钟
//				break;
//			case 3:
//				map_p.put("timeInteval", 1800000);//三十分钟
//				break;
//			case 4:
//				map_p.put("timeInteval", 3600000);//一小时
//				break;
//			case 5:
//				map_p.put("timeInteval", 86400000);//一天
//				break;
//			default:
//				map_p.put("timeInteval", 60000);//一分钟
//				break;
//			}
//			map_p.put("unitCoinType", c1);//计价币
//			map_p.put("orderCoinType", c2);//交易币
//			JSONObject json_p = new JSONObject(map_p);
//			String params = BASE64.encoder(json_p.toJSONString());
//			String returnStr = wr.get(Constants.GETKLINEDATA, params, null);
//			logger.info("发送K线初始化信息数据包:" + returnStr);
//			JSONObject sjson = JSONObject.parseObject(returnStr);
//			if("10000".equals(sjson.getString("code"))&&sjson!=null){
//				JSONObject rjson = new JSONObject();
//				rjson.put("info",sjson.get("data"));
//				rjson.put("code", cmsgCode);
//				rjson.put("type", scene);
//				sendMessage(incoming,rjson.toJSONString());
//			}else{
//				sendMessage(incoming,
//						Result.toResultStr(ResultCode.TYPE_ERROR_UNKNOW, "请求远程接口错误！"));
//			}
//			returnStr = wr.get(Constants.MARKETDETAIL, params, null);
//			logger.info("发送行情初始化信息数据包:" + returnStr);
//			sjson = JSONObject.parseObject(returnStr);
//			if("10000".equals(sjson.getString("code"))&&sjson!=null){
//				JSONObject rjson = new JSONObject();
//				rjson.put("info",sjson.get("data"));
//				rjson.put("code", cmsgCode);
//				rjson.put("type", scene);
//				sendMessage(incoming,rjson.toJSONString());
//			}else{
//				sendMessage(incoming,
//						Result.toResultStr(ResultCode.TYPE_ERROR_UNKNOW, "请求远程接口错误！"));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception { // (2)
        Channel incoming = ctx.channel();
        group.add(incoming);
        incoming.writeAndFlush(new TextWebSocketFrame(""));
        log.info("Client:" + incoming.remoteAddress().toString() + "加入,当前链接数："
                + group.size());
		/*WebSocketClient sendClient = new WebSocketClient(incoming.remoteAddress().toString());
		allSocketClients.put(incoming.remoteAddress().toString(),sendClient);
		
		logger.info("当前所有用户的remoteAddress：");
		for (String key : allSocketClients.keySet()) {
			logger.info(key);
		}*/
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { // (3)
        Channel incoming = ctx.channel();
		/*group.writeAndFlush(new TextWebSocketFrame("[SERVER] - "
				+ incoming.remoteAddress() + " 离开"));*/
        log.info("Client:" + incoming.remoteAddress().toString() + "离开,当前链接数："
                + group.size());
        allSocketClients.remove(incoming.remoteAddress().toString());
        incoming.close();
        // Broadcast a message to multiple Channels
        // A closed Channel is automatically removed from ChannelGroup,
        // so there is no need to do "channels.remove(ctx.channel());"

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) // (7)
            throws Exception {
        Channel incoming = ctx.channel();
        log.info("Client:" + incoming.remoteAddress() + "异常");
        allSocketClients.remove(incoming.remoteAddress().toString());
        cause.printStackTrace();
        //sendMessage(incoming, Result.toResultStr(ResultCode.TYPE_ERROR_UNKNOW));
    }


}