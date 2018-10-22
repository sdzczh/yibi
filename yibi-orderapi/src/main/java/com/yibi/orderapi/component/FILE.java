package com.yibi.orderapi.component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class FILE
{
    public static String downLoadFromUrl(String urlStr, String savePath)
            throws IOException
    {
        String fileName = UUID.randomUUID().toString().replace("-", "");
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        conn.setConnectTimeout(3000);

        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        InputStream inputStream = conn.getInputStream();

        byte[] getData = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(getData)) != -1) {
            bos.write(getData, 0, len);
        }
        bos.close();
        getData = bos.toByteArray();

        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        System.out.println("info:" + url + " download success");
        return savePath + "/" + fileName;
    }

    public static FileType checkFileType(InputStream inputStream)
            throws Exception
    {
        byte[] src = new byte[28];
        inputStream.read(src, 0, 28);
        inputStream.close();

        StringBuilder stringBuilder = new StringBuilder();
        if ((src == null) || (src.length <= 0)) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        String fileHead = stringBuilder.toString();

        if ((fileHead == null) || (fileHead.length() == 0)) {
            return FileType.NONE;
        }

        fileHead = fileHead.toUpperCase();
        FileType[] fileTypes = FileType.values();

        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }
        return FileType.NONE;
    }

    public static void main(String[] args)
            throws Exception
    {
        String dir = System.getProperty("user.dir");
        String a = downLoadFromUrl("http://huolicoin.com/img/banner2.jpg", dir);
        System.out.println(a);
    }
}