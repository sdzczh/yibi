package com.yibi.orderapi.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yibi.core.constants.GlobalParams;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
/**
 * @描述 图片加密解密<br>
 * @author administrator
 * @版本 v1.0.0
 * @日期 2017-6-7
 */
@Slf4j
public class ImgUtils {
	
	/**
	 * @描述 图片加密<br>
	 * @param imgUrl 图片地址
	 * @return 图片加密值
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-6-7
	 */
	public static String imgEncode(String imgUrl) {
		InputStream inputStream = null;
		byte[] data = null;
		try {
			inputStream = new FileInputStream(imgUrl);
			data = new byte[inputStream.available()];
			inputStream.read(data);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 加密
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

	/**
	 * @描述 图片解密<br>
	 * @param imgCode 图片加密值
	 * @param savePath 保存地址
	 * @return 结果
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-6-7
	 */
	public static boolean imgDecode(String imgCode, String savePath) {
		if (imgCode == null){
			return false;
		}
			BASE64Decoder decoder = new BASE64Decoder();
			try {
			// 解密
			byte[] b = decoder.decodeBuffer(imgCode);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
			if (b[i] < 0) {
				b[i] += 256;
			}
		}
		OutputStream out = new FileOutputStream(savePath);
		out.write(b);
		out.flush();
		out.close();
		return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
	public static String uploadImg(String fileDir, String imageName, InputStream fileStream){
		String result= "";
		String urls = GlobalParams.FILE_UPLOAD_PATH;
		JSONObject params = new JSONObject();
		params.put("userCode", fileDir);
		params.put("imageName", imageName);
		
		 String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
         String PREFIX = "--", LINE_END = "\r\n";
         String CONTENT_TYPE = "multipart/form-data"; // 内容类型
         try {
        	 URL url = new URL(urls);
             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setReadTimeout(10000);
             conn.setConnectTimeout(10000);
             conn.setDoInput(true); // 允许输入流
             conn.setDoOutput(true); // 允许输出流
             conn.setUseCaches(false); // 不允许使用缓存
             conn.setRequestMethod("POST"); // 请求方式
             conn.setRequestProperty("Charset", "UTF8"); // 设置编码
             conn.setRequestProperty("connection", "keep-alive");
             conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
             
            
             if (fileStream != null) {
                 /**
                  * 当文件不为空，把文件包装并且上传
                  */
                 DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                 StringBuffer sb = new StringBuffer();
                 if (params != null) {
                     for (String key : params.keySet()) {
                         sb.append(LINE_END + PREFIX + BOUNDARY + LINE_END);
                         sb.append("Content-Disposition:form-data;name=\"").append(key)
                                 .append("\"" + LINE_END);
                         sb.append(LINE_END);
                         sb.append(params.get(key));
                     }
                 }
                 sb.append(LINE_END);

                 dos.write(params.toString().getBytes());
                 sb.append(PREFIX);
                 sb.append(BOUNDARY);
                 sb.append(LINE_END);
                 /**
                  * 这里重点注意： name里面的值为服务端需要key 只有这个key 才可以得到对应的文件
                  * filename是文件的名字，包含后缀名的 比如:abc.png
                  */
                 sb.append("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
                         + imageName + "\"" +LINE_END);
                 sb.append("Content-Type: application/octet-stream; charset=" + "UTF8" + LINE_END);
                 sb.append(LINE_END);

                 dos.write(sb.toString().getBytes());
                 byte[] bytes = new byte[1024];
                 int len = 0;
                 while ((len = fileStream.read(bytes)) != -1) {
                     dos.write(bytes, 0, len);
                 }
                 fileStream.close();
                 dos.write(LINE_END.getBytes());
                 byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                 dos.write(end_data);
                 dos.flush();
            	 InputStream input = conn.getInputStream();
                 StringBuffer sb1 = new StringBuffer();
                 int ss;
                 while ((ss = input.read()) != -1) {
                     sb1.append((char) ss);
                 }
                 result = sb1.toString();
                 JSONObject js = JSON.parseObject(result);
                 int code  = js.getInteger("code");
                 if(code == GlobalParams.ACTIVE){
                	 return GlobalParams.FILE_URL+js.getString("info");
                 }else{
                	 log.info("图片上传失败："+ js.get("info"));
                	 return "";
                 }
                 
             }
		} catch (Exception e) {
			e.printStackTrace();
		}
         return result;
	}
	/**
	 * @描述 工具测试方法<br>
	 * @param args
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-6-7
	 */
	public static void main(String[] args) {
		String picUrl = "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=313662881,84190770&fm=173&s=DFACA8444ED35C4F1E2C9D820300109B&w=502&h=301&img.JPEG";
		URL url = null;
		try {
			url = new URL(picUrl);
			InputStream is = url.openStream();
			String imgPath = uploadImg("test","test112.JPEG",is);
			
			System.out.println(imgPath);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
