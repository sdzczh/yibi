package com.yibi.web.controller;

import com.yibi.common.encrypt.MD5;
import com.yibi.common.model.Json;
import com.yibi.common.model.SessionInfo;
import com.yibi.common.utils.UuidUtil;
import com.yibi.core.entity.Manager;
import com.yibi.core.entity.Resource;
import com.yibi.core.service.ManagerService;
import com.yibi.core.service.ResourceService;
import com.yibi.web.controller.base.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户登录
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    private ManagerService managerService;
    @Autowired
    private ResourceService resourceService;


    /**
     * 用户登录页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "/login/login";
    }

    /**
     * 用户登录
     *
     * @return
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public Object doLogin(String userAccount, String userPassword, HttpSession session) throws Exception {
        Json json = new Json();
        if (StringUtils.isBlank(userAccount)) {
            json.setMsg("用户名不能为空");
            return json;
        }
        if (StringUtils.isBlank(userPassword)) {
            json.setMsg("密码不能为空");
            return json;
        }
        Subject user = SecurityUtils.getSubject();
        if (user != null) {
            UsernamePasswordToken token = new UsernamePasswordToken(userAccount, MD5.getMd5(userPassword));
            if (token != null) {
                token.setRememberMe(true);
                try {
                    user.login(token);
                    SessionInfo sessionInfo = (SessionInfo) user.getPrincipal();
                    Manager manager = managerService.selectByPrimaryKey(sessionInfo.getUserid());
                    if (manager != null) {
                        manager.setToken(UuidUtil.get32UUID());
                        manager.setTokencreatetime(new Date());
                        managerService.updateByPrimaryKeySelective(manager);
                    }
                    sessionInfo.setToken(manager.getToken());
                    session.setAttribute("loginUser", sessionInfo);
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("managerid", manager.getId());
                    param.put("resourcetype", 0);
                    param.put("pid", 0);
                    List<Resource> resourceList = resourceService.selectRescourcesByManager(param);
                    for (Resource resource : resourceList) {
                        param.put("pid", resource.getId());
                        List<Resource> resourceSubList = resourceService.selectRescourcesByManager(param);
                        resource.setChildren(resourceSubList);
                    }
                    session.setAttribute("sysMenuList", resourceList);
                } catch (UnknownAccountException e) {
                    json.setMsg("账号不存在");
                    return json;
                } catch (DisabledAccountException e) {
                    json.setMsg("账号未启用");
                    return json;
                } catch (IncorrectCredentialsException e) {
                    json.setMsg("密码错误");
                    return json;
                } catch (RuntimeException e) {
                    json.setMsg("未知错误,请联系管理员");
                    return json;
                }
            }
        }
        json.setSuccess(true);
        return json;
    }

    @RequestMapping("/index")
    public String enterIndex() {
        return "/index";
    }

    /**
     * 用户退出
     *
     * @param request
     * @return
     */
    @RequestMapping("/loginout")
    public String loginout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        session.invalidate();
        return "redirect:/login/login.do";
    }
}
