package com.yibi.common.http;

import okhttp3.*;
import okhttp3.Request.Builder;

import java.io.IOException;
import java.util.Map;

public class OkHttp {

	public static String get(String url,Map<String, String> headers){
		OkHttpClient okHttpClient = new OkHttpClient();
		Builder builder = new Builder()
		.url(url);

		if(headers!=null){
			for (String key : headers.keySet()) {
				builder.addHeader(key, headers.get(key));
			}
		}
		Request request = builder.build();
		Call call = okHttpClient.newCall(request);

		try {
			Response response = call.execute();
			return response.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String postForm(String url ,Map<String, String> headers,Map<String , String > bodys){
		OkHttpClient okHttpClient = new OkHttpClient();
		Builder headerBuilder = new Builder()
		.url(url);
		if(headers!=null){
			for (String key : headers.keySet()) {
				headerBuilder.addHeader(key, headers.get(key));
			}
		}

		FormBody.Builder bodyBuild = new FormBody.Builder();
		if(bodys!=null){
			for (String key : bodys.keySet()) {
				bodyBuild.add(key, bodys.get(key));
			}
		}
		RequestBody body = bodyBuild.build();
		Request request = headerBuilder.post(body).build();
		Call call = okHttpClient.newCall(request);

		try {
			Response response = call.execute();
			return response.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static String postXmlString(String url ,Map<String, String> headers,String params){
		OkHttpClient okHttpClient = new OkHttpClient();
		MediaType mediaType = MediaType.parse("text/xml");
		RequestBody  body = RequestBody .create(mediaType, params);
		Builder headerBuilder = new Builder()
		.url(url)
		.post(body);
		if(headers!=null){
			for (String key : headers.keySet()) {
				headerBuilder.addHeader(key, headers.get(key));
			}
		}
		Request request = headerBuilder.build();
		Call call = okHttpClient.newCall(request);
		
		try {
			Response response = call.execute();
			return response.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
