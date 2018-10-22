package com.yibi.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @描述 HTTP工具<br>
 * @author administrator
 * @版本 v1.0.0
 * @日期 2017-8-11
 */
@SuppressWarnings("deprecation")
public class HTTP {
	
	private HTTP(){}

	/**
	 * @描述 GET请求<br>
	 * @param url 请求地址
	 * @param headers 头信息 可接收NULL值
	 * @return 返回JSON格式字符串
	 * @throws Exception
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-8-11
	 */
	public static String get(String url,Map<String,String> headers)throws Exception {
		HttpClient httpClient = SSLClientCheck(url);
		HttpGet request = new HttpGet(url);
		if(headers!=null){
			for (Map.Entry<String, String> e : headers.entrySet()) {
				request.addHeader(e.getKey(), e.getValue());
			}
		}
		HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
	}

	/**
	 * @描述 POST请求(form表单)<br>
	 * @param url 请求地址
	 * @param headers 头信息 可接收NULL值
	 * @param bodys 参数
	 * @return 返回JSON格式字符串
	 * @throws Exception
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-8-11
	 */
	public static String postFrom(String url,Map<String, String> headers, Map<String, String> bodys) throws Exception {
		HttpClient httpClient = SSLClientCheck(url);
		HttpPost request = new HttpPost(url);
		if(headers!=null){
			for (Map.Entry<String, String> e : headers.entrySet()) {
				request.addHeader(e.getKey(), e.getValue());
			}
		}
		if (bodys != null) {
			List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
			for (String key : bodys.keySet()) {
				nameValuePairList.add(new BasicNameValuePair(key, bodys
						.get(key)));
			}
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					nameValuePairList, "utf-8");
			formEntity
					.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
			request.setEntity(formEntity);
		}
		HttpResponse response = httpClient.execute(request);
		return EntityUtils.toString(response.getEntity());
	}

	/**
	 * @描述 POST请求(String)<br>
	 * @param url 请求地址
	 * @param headers 头信息 可接收NULL值
	 * @param bodys 字符串
	 * @return 返回字符串
	 * @throws Exception
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-8-11
	 */
	public static String postString(String url,Map<String, String> headers, String body)	throws Exception {
		HttpClient httpClient = SSLClientCheck(url);
		HttpPost request = new HttpPost(url);
		if(headers!=null){
			for (Map.Entry<String, String> e : headers.entrySet()) {
				request.addHeader(e.getKey(), e.getValue());
			}
		}
		if (StringUtils.isNotBlank(body)) {
			request.setEntity(new StringEntity(body, "utf-8"));
		}
		HttpResponse response = httpClient.execute(request);
		return EntityUtils.toString(response.getEntity());
	}

	/**
	 * @描述 SSL请求对象获取<br>
	 * @param url 请求地址
	 * @return HttpClient对象
	 * @throws Exception
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-8-11
	 */
	private static HttpClient SSLClientCheck(String url) throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		if (url.startsWith("https://")) {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {return null;}
				public void checkClientTrusted(X509Certificate[] xcs, String str) {}
				public void checkServerTrusted(X509Certificate[] xcs, String str) {}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = httpClient.getConnectionManager();
			SchemeRegistry registry = ccm.getSchemeRegistry();
			registry.register(new Scheme("https", 443, ssf));
		}
		return httpClient;
	}

}