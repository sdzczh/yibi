package com.yibi.web.controller.base;

import com.yibi.common.model.PageModel;
import com.yibi.common.model.SessionInfo;
import com.yibi.common.utils.UuidUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    public SessionInfo user() {
        SessionInfo sessionInfo = (SessionInfo) getRequest().getSession().getAttribute("sessionInfo");
        return sessionInfo;
    }

    /**
     * 得到32位的uuid
     *
     * @return
     */
    public String get32UUID() {

        return UuidUtil.get32UUID();
    }

    /**
     * 得到分页实体
     */
    public PageModel getPageModel(Integer page, Integer rows) {
        return new PageModel(page, rows);
    }


    /**
     * 异步 返回数据
     *
     * @return
     */
    public String putPrintWirter(HttpServletResponse response, String result) {

        response.setContentType("application/json");
        try {
            PrintWriter out = response.getWriter();
            out.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



}
