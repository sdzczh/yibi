package com.yibi.web.shiro;

import com.yibi.common.model.SessionInfo;
import com.yibi.core.entity.Manager;
import com.yibi.core.entity.Resource;
import com.yibi.core.entity.RoleResource;
import com.yibi.core.service.ManagerRoleService;
import com.yibi.core.service.ManagerService;
import com.yibi.core.service.ResourceService;
import com.yibi.core.service.RoleResourceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * shiro权限认证
 */
public class ShiroDbRealm extends AuthorizingRealm {
    private static final Logger LOGGER = LogManager.getLogger(ShiroDbRealm.class);

    @Autowired
    private ManagerService managerService;
    @Autowired
    private ManagerRoleService managerRoleService;
    @Autowired
    private RoleResourceService roleResourceService;
    @Autowired
    private ResourceService resourceService;

    /**
     * Shiro登录认证(原理：用户提交 用户名和密码  --- shiro 封装令牌 ---- realm 通过用户名将密码查询返回 ---- shiro 自动去比较查询出密码和用户输入密码是否一致---- 进行登录控制 )
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        LOGGER.info("Shiro开始登录认证");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("useraccount", token.getUsername());
        List<Manager> list = managerService.selectAll(params);
        Manager manager = null;
        if (list != null && !list.isEmpty()) {
            manager = list.get(0);
        }
        // 账号不存在
        if (manager == null) {
            return null;
        }
        List<Integer> roleList = managerRoleService.findRoleIdListByManagerId(manager.getId());
        SessionInfo sessionInfo = new SessionInfo(manager.getId(), manager.getUseraccount(), manager.getUsername(), manager.getToken(), manager.getType(), null, null, roleList);
        // 认证缓存信息
        return new SimpleAuthenticationInfo(sessionInfo, manager.getUserpassword(), getName());

    }

    /**
     * Shiro权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {

        SessionInfo sessionInfo = (SessionInfo) principals.getPrimaryPrincipal();
        List<Integer> roleList = sessionInfo.getRoleList();

        Set<String> urlSet = new HashSet<String>();
        Map<Object, Object> params = new HashMap<Object, Object>();
        for (Integer roleId : roleList) {
            params.put("roleid", roleId);
            List<RoleResource> roleResourceList = roleResourceService.selectAll(params);
            for (RoleResource roleResource : roleResourceList) {
                Resource resource = resourceService.selectByPrimaryKey(roleResource.getResourceid());
                if (resource != null && resource.getUrl() != null) {
                    urlSet.add(resource.getUrl());
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(urlSet);
        return info;
    }
}
