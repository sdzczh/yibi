package com.yibi.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class PictureUtils {
    /**
     * 图片上传
     * @param fileDir  文件夹名
     * @param imageName 文件名
     * @param fileStream  文件输入流
     * @return
     */
    public static String uploadImg(String fileDir, String imageName, InputStream fileStream){
        String result= "";
        String urls = "http://img.huolicoin.com/upload.php";
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
                if(code == 1){
                    return "http://img.huolicoin.com/"+js.getString("info");
                }else{
                    return "";
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
