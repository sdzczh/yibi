package com.yibi.common.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @描述 RedisTemplate工具类<br>
 * redis-2.6.14
 * spring-data-redis-1.6.0
 * redis.clients-2.6.1
 * @author administrator
 * @版本 v1.0.0
 * @日期 2017-10-10
 */
public class RedisUtil {

	/**
	 * @描述 获取Reids的匹配Key值<br>
	 * @param redis
	 * @param pattern
	 * @return
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-11
	 */
	public static Set<String> getKeys(RedisTemplate<String,String> redis,String pattern){
		Set<String> keys = redis.keys(pattern);
		return keys;
	}
	
	
	/**
	 * @描述 删除Key值<br>
	 * @param redis
	 * @param key
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-14
	 */
	public static void deleteKey(RedisTemplate<String,String> redis,String key){
		redis.delete(key);
	}
	
	/**
	 * @描述 String添加String<br>
	 * @param redis Redis对象
	 * @param key Key值 
	 * @param value Value值
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static void addString(RedisTemplate<String,String> redis,String key,String value){
		redis.opsForValue().set(key, value);
	}

	/**
	 * @描述 String添加String可过期<br>
	 * @param redis Redis对象
	 * @param key Key值 
	 * @param timeout 过期秒数 
	 * @param value Value值
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static void addString(RedisTemplate<String,String> redis,String key,long timeout,String value){
		redis.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}
		
	/**
	 * @描述 String添加Obj<br>
	 * @param redis Redis对象
	 * @param key Key值 
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static void addStringObj(RedisTemplate<String,String> redis,String key,Object obj){
		redis.opsForValue().set(key, JSON.toJSON(obj).toString());
	}
	/**
	 * @描述 String添加Obj可过期<br>
	 * @param redis Redis对象
	 * @param key Key值 
	 * @param timeout 过期秒数 
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static void addStringObj(RedisTemplate<String,String> redis,String key,long timeout,Object obj){
		redis.opsForValue().set(key,JSON.toJSON(obj).toString(),timeout,TimeUnit.SECONDS);
	}
	
	/**
	 * @描述 String查询对象<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @return 查询结果
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static <T> T searchStringObj(RedisTemplate<String,String> redis,String key,Class<T> classType){
		String obj = redis.opsForValue().get(key);
		if(obj!=null){
			T object = JSON.toJavaObject(JSON.parseObject(obj), classType);
			return object;
		}else{
			return null;
		}
	}
	
	/**
	 * @描述 String查询<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @return 查询结果
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static String searchString(RedisTemplate<String,String> redis,String key){
		String obj = redis.opsForValue().get(key);
		return obj;
	}
	
	
	/**
	 * @描述 String更新<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @param value Value值
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static void updateString(RedisTemplate<String,String> redis,String key,String value){
		redis.delete(key);
		redis.opsForValue().set(key,value);
	}
	
	/**
	 * @描述 String删除<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static void deleteString(RedisTemplate<String,String> redis,String key){
		redis.delete(key);
	}
	
	/**
	 * @描述 List添加单个(左)<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static <T> void addList(RedisTemplate<String,String> redis,String key,Object value){
		redis.opsForList().leftPushAll(key, JSON.toJSON(value).toString());
	}
	
	/**
	 * @描述 List添加集合(左)<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @param collection 数据集合
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static <T> void addList(RedisTemplate<String,String> redis,String key,Collection<T> collection){
		if(collection!=null &&!collection.isEmpty()){
			List<String> list = new ArrayList<>();
			for (Object object : collection) {
				list.add(JSON.toJSON(object).toString()) ;
			}
			redis.opsForList().leftPushAll(key, list);
		}

	}
	
	/**
	 * @描述 List添加单个(右)<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static <T> void addListRight(RedisTemplate<String,String> redis,String key,Object value){
		redis.opsForList().rightPushAll(key, JSON.toJSON(value).toString());
	}
	
	/**
	 * @描述 List添加集合(右)<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @param collection 数据集合
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static <T> void addListRight(RedisTemplate<String,String> redis,String key,Collection<T> collection){
		if(collection!=null &&!collection.isEmpty()){
			List<String> list = new ArrayList<>();
			for (Object object : collection) {
				list.add(JSON.toJSON(object).toString()) ;
			}
			redis.opsForList().rightPushAll(key, list);
		}

	}
	
	/**
	 * 弹出list最左侧的元素，弹出后该值在列表中将不复存在
	 * @param redis Redis对象
	 * @param key
	 * @param classType
	 * @return T
	 * @date 2018-3-5
	 * @author lina
	 */
	public static <T> T leftPopObj(RedisTemplate<String,String> redis,String key,Class<T> classType){
		String obj = redis.opsForList().leftPop(key);
		if(obj!=null){
			T object = JSON.toJavaObject(JSON.parseObject(obj), classType);
			return object;
		}else{
			return null;
		}
	}
	
	/**
	 * 弹出list最右侧的元素，弹出后该值在列表中将不复存在
	 * @param redis
	 * @param key
	 * @param classType
	 * @return T
	 * @date 2018-3-5
	 * @author lina
	 */
	public static <T> T rightPopObj(RedisTemplate<String,String> redis,String key,Class<T> classType){
		String obj = redis.opsForList().rightPop(key);
		if(obj!=null){
			T object = JSON.toJavaObject(JSON.parseObject(obj), classType);
			return object;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取list中指定下标的String值，index 从0开始
	 * @param redis
	 * @param key
	 * @param index
	 * @return String
	 * @date 2018-3-5
	 * @author lina
	 */
	public static String searchIndexList(RedisTemplate<String,String> redis,String key,long index){
		return redis.opsForList().index(key, index);
	}
	
	/**
	 * 获取list中指定下标的对象，index 从0开始
	 * @param redis
	 * @param key
	 * @param index
	 * @param classType
	 * @return T
	 * @date 2018-3-5
	 * @author lina
	 */
	public static <T> T searchIndexList(RedisTemplate<String,String> redis,String key,long index,Class<T> classType){
		String obj = redis.opsForList().index(key, index);
		if(obj!=null){
			T object = JSON.toJavaObject(JSON.parseObject(obj), classType);
			return object;
		}else{
			return null;
		}
	}
	
	
	/**
	 * @描述 List获取长度<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @return List长度
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-14
	 */
	public static long searchListSize(RedisTemplate<String,String> redis,String key){
		return redis.opsForList().size(key);
	}
	
	/**
	 * @描述 List查询字符串<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @param start 开始位置
	 * @param end 结束位置
	 * @return 查询结果
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static List<String> searchList(RedisTemplate<String,String> redis,String key,long start,long end){
		List<String> result = redis.opsForList().range(key, start, end);
		return result;
	}
	
	/**
	 * @描述 List查询对象模型<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @param start 开始位置
	 * @param end 结束位置
	 * @param classType 对象模型
	 * @return 查询结果
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static <T> List<T> searchList(RedisTemplate<String,String> redis,String key,long start,long end,Class<T> classType){
		List<String> results = redis.opsForList().range(key, start, end);
		List<T> objs = new ArrayList<>();
		for (Object json : results) {
			T obj = JSON.toJavaObject(JSON.parseObject((String) json), classType);
			objs.add(obj);
		}
		return objs;
	}
	
	/**
	 * @描述 List更新<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @param oldValue 目标值
	 * @param newValue 更新值
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static void updateList(RedisTemplate<String,String> redis,String key,String oldValue, String newValue){
		redis.opsForList().remove(key, 1l, oldValue);
		redis.opsForList().leftPushAll(key, newValue);
		
	}
	
	/**
	 * @描述 List删除<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @param value Value值
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static void deleteList(RedisTemplate<String,String> redis,String key,String value){
		redis.opsForList().remove(key, 1l, value);
	}
	
	/**
	 * list删除等于value 的所有元素
	 * @param redis
	 * @param key
	 * @param value
	 * @return void
	 * @date 2018-3-5
	 * @author lina
	 */
	public static void deleteListAll(RedisTemplate<String,String> redis,String key,String value){
		redis.opsForList().remove(key, 0l, value);
	}
	
	/**
	 * @描述 Hash添加String<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @param hashKey hashKey值
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static void addHashString(RedisTemplate<String, String>redis,String key,String hashKey,String value){
		redis.opsForHash().put(key, hashKey, value);
	}
	
	/**
	 * @描述 Hash添加对象<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @param hashKey hashKey值
	 * @param obj 数据对象
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static <T> void addHashObject(RedisTemplate<String, String>redis,String key,String hashKey,T obj){
		redis.opsForHash().put(key, hashKey, JSON.toJSON(obj).toString());
	}
	
	/**
	 * @描述 Hash添加Map<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @param map Value值
	 * @param jsonType Map中Value值是否为对象
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static void addHashMap(RedisTemplate<String, String>redis,String key,Map<String,Object> map,boolean jsonType){
		Set<String> keys = map.keySet();
		for (String hashKey : keys) {
			if(jsonType){
				redis.opsForHash().put(key, hashKey, JSON.toJSON(map.get(hashKey)).toString());
			}else{
				redis.opsForHash().put(key, hashKey,map.get(hashKey).toString());
			}
		}
	}
	
	/**
	 * @描述 Hash查询String<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @param hashKey hashKey值
	 * @return Value值
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static String searchHashString(RedisTemplate<String, String>redis,String key,String hashKey){
		String value = (String) redis.opsForHash().get(key, hashKey);
		return value;
	}
	
	/**
	 * @描述 Hash查询对象<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @param hashKey hashKey值
	 * @param classType 对象类型
	 * @return 对象
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static <T> T searchHashObject(RedisTemplate<String, String>redis,String key,String hashKey,Class<T> classType){
		String value = (String) redis.opsForHash().get(key, hashKey);
		T obj = JSON.toJavaObject(JSON.parseObject(value), classType);
		return obj;
	}
	
	
	/**
	 * @描述 Hash查询Map<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @return Map结果
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static Map<Object,Object> searchHashMap(RedisTemplate<String, String>redis,String key){
		Map<Object, Object> value = redis.opsForHash().entries(key);
		return value;
	}
	
	/**
	 * @描述 Hash查询对象列表<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @param classType 对象类型
	 * @return 对象
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static <T> List<T> searchHashListObject(RedisTemplate<String, String>redis,String key,Class<T> classType){
		List<T> result = new ArrayList<>();
		List<Object> values = redis.opsForHash().values(key);
		for (Object object : values) {
			T obj = JSON.toJavaObject(JSON.parseObject(object.toString()), classType);
			result.add(obj);
		}
		return result;
	}
	
	/**
	 * @描述 Hash查询所有数据集合<br>
	 * @param redis Redis对象
	 * @param key key值
	 * @return List结果
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-10
	 */
	public static List<Object> searchHashMapList(RedisTemplate<String, String>redis,String key){
		List<Object> values = redis.opsForHash().values(key);
		return values;
	}
	
	/**
	 * @描述 Hash数据更新<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @param hashKey HashKey值
	 * @param value 更新数据
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-11
	 */
	public static void updateHash(RedisTemplate<String, String>redis,String key,String hashKey,String value){
		redis.opsForHash().put(key, hashKey, value);
	}

	/**
	 * @描述 Hash删除单条数据<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @param hashKey HashKey值
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-11
	 */
	public static void deleteHash(RedisTemplate<String, String>redis,String key,String hashKey){
		redis.opsForHash().delete(key, hashKey);
	}
	
	/**
	 * @描述 Hash删除数据<br>
	 * @param redis Redis对象
	 * @param key Key值
	 * @param hashKeys HashKey值集合
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-11
	 */
	public static void deleteHash(RedisTemplate<String, String>redis,String key,List<Object> hashKeys){
		for (Object hashKey : hashKeys) {
			redis.opsForHash().delete(key, hashKey);
		}
	}
	
}
