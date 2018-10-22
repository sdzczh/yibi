package com.yibi.common.utils;

import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OkHttp {
    public static String get(String url,Map<String, String> headers){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder()
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
        Request.Builder headerBuilder = new Request.Builder()
                .url(url);
        if(headers!=null){
            for (String key : headers.keySet()) {
                headerBuilder.addHeader(key, headers.get(key));
            }
        }

        okhttp3.FormBody.Builder bodyBuild = new FormBody.Builder();
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

    public static String post(String url,String params,String sign,String token,String key){
        Map<String, String> body = new HashMap<String, String>();
        Map<String, String> header = null;
        body.put("params", params);
        if(!StrUtils.isBlank(key)){
            body.put("key", key);
        }
        if(!StrUtils.isBlank(sign)){
            body.put("sign", sign);


        }

        if(!StrUtils.isBlank(token)){
            header = new HashMap<String, String>();
            header.put("token", token);
        }

        try {
            return OkHttp.postForm(url, header, body);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
