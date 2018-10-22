package com.yibi.turnapi.controller.base;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class BaseController {
    /**
     * 得到ModelAndView
     */
    public ModelAndView getModelAndView() {
        return new ModelAndView();
    }

    /**
     * 得到request对象
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 异步 返回数据
     *
     * @return
     */
    public String putPrintWiter(HttpServletResponse response, String result) {

        response.setContentType("application/json");
        try {
            PrintWriter out = response.getWriter();
            out.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
     * 获取当前的url
     */
    public String getUrl(HttpServletRequest request, String url) {
        String queryString = request.getQueryString();
        String newqs = "";
        if (queryString != null) {
            String[] qss = queryString.split("&");
            int slen = qss.length;
            for (int i = 0; i < slen; i++) {
                if (qss[i].indexOf("pageno=") != 0) {
                    newqs = newqs + "&" + qss[i];
                }
            }
            if (newqs.length() > 0) {
                queryString = newqs.substring(1, newqs.length());
                queryString = "?" + queryString;
            } else {
                queryString = "";
            }
        } else {
            queryString = "";
        }
        String strBackUrl = "";
        if (url != null && !"".equals(url)) {
            strBackUrl = url + queryString;
        } else {
            strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + request.getServletPath() + queryString;
        }

        // String strBackUrl = this.getHttpRequest().getHeader("Referer") +
        // queryString;
        return strBackUrl;
    }

    public static String getClasspath() {
        String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "../../").replaceAll("file:/", "").replaceAll("%20", " ").trim();
        if (path.indexOf(":") != 1) {
            path = File.separator + path;
        }
        return path;
    }
}
