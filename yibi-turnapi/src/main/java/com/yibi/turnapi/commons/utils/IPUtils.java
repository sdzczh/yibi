package com.yibi.turnapi.commons.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPUtils {
	/**
	 * @描述 获取当前网络IP地址<br>
	 * @param request 请求对旬
	 * @return ip地址
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-6-7
	 */
    public static String getIpAddr(HttpServletRequest request){
        String ipAddress = request.getHeader("x-forwarded-for");
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getHeader("Proxy-Client-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getHeader("WL-Proxy-Client-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getRemoteAddr();  
                if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){  
                    //根据网卡取本机配置的IP  
                    InetAddress inet=null;
                    try {  
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();  
                    }  
                    ipAddress= inet.getHostAddress();  
                }  
            }  
            //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
            if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
                if(ipAddress.indexOf(",")>0){  
                    ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
                }  
            }  
            return ipAddress;   
    }
    
    /**
     * IP转成long
     * @param ip
     * @throws UnknownHostException
     * @return int
     * @date 2017-12-25
     * @author lina
     */
    public static long ip2long(String ip) {
        String[] slice = ip.split("\\.");
        return (Long.valueOf(slice[0]) << 24) | (Long.valueOf(slice[1]) << 16) | (Long.valueOf(slice[2]) << 8) | Long.valueOf(slice[3]);
    }
    
    /**
     * long 转ip
     * @param ip
     * @return String
     * @date 2017-12-25
     * @author lina
     */
    public static String long2ip(long ip) {
        StringBuilder ipStr = new StringBuilder();
        ipStr.append((ip >>> 24)).append(".");
        ipStr.append((ip >>> 16) & 0xFF).append(".");
        ipStr.append((ip >>> 8) & 0xFF).append(".");
        ipStr.append(ip & 0xFF);
        return ipStr.toString();
    }
    
    public static void main(String[] args) throws UnknownHostException {
    	System.out.println(ip2long("172.16.1.110"));
    	System.out.println(long2ip(2886730094l));
    	System.out.println(System.currentTimeMillis());
	}
}
