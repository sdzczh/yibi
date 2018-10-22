package com.yibi.web.wallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.LinkedHashMap;

/**
 * @author: lqh
 * @description: rpc请求工具类
 * @program: coinApi
 * @create: 2018-07-12 16:43
 **/
public class RpcUtil {
    private static Logger logger = LoggerFactory.getLogger(RpcUtil.class);
    public static String generateRequest(RpcObject rpcObject,final String method, final Object... param) {
        String requestBody = JSON.toJSONString(new LinkedHashMap() {
            {
                put("jsonrpc", "1.0");
                put("id", "1");
                put("method", method);
                put("params", param);
            }
        });
        logger.info("requestBody【{}】" + requestBody);
        final PasswordAuthentication temp = new PasswordAuthentication(rpcObject.rpcuser, rpcObject.rpcpassword.toCharArray());
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return temp;
            }
        });
        String uri = "http://" + rpcObject.rpcurl + ":" + rpcObject.rpcport;
        //logger.info("url【{}】" + uri);

        HttpURLConnection connection = null;
        try {
            URL url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Length", Integer.toString(requestBody.getBytes().length));
            connection.setUseCaches(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(1000);
            OutputStream out = connection.getOutputStream();
            out.write(requestBody.getBytes());
            out.flush();
            out.close();
        } catch (Exception ioE) {
            connection.disconnect();
            logger.info("connection error,msg【{}】",ioE.getMessage());
        }

        try {
            System.out.println("connection:"+connection.getResponseCode());
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();

                String responseToString = response.toString();
                logger.info("responseBody【{}】：" + responseToString);
                try {
                    JSONObject jsonObject = JSON.parseObject(responseToString);
                    String returnAnswer = jsonObject.get("result").toString();
                    //logger.info("result【{}】" + returnAnswer);
                    return returnAnswer;
                } catch (Exception e) {
                    return "";
                }
            } else {
                System.out.println("Coudln't connet to rpc!");
                logger.info("Coudln't connet to rpc!");
                connection.disconnect();
            }
        } catch (Exception e) {
        }
        logger.info("Couldn't get a decent answer");
        return "";
    }
}
