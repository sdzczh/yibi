package com.yibi.web.controller;

import com.yibi.common.model.Json;
import com.yibi.common.model.Tree;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import com.yibi.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统角色管理
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private RoleResourceService roleResourceService;


    /**
     * 角色管理主页面
     *
     * @return
     */
    @RequestMapping("/mainpage")
    public String mainpage() {
        return "/role/mainpage";
    }

    /**
     * ajax加载数据列表
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list() {
        List<Role> list = roleService.selectAll(null);
        return list;
    }

    /**
     * 角色授权页面
     *
     * @return
     */
    @RequestMapping("/addResource")
    public String addResource(Integer roleid, HttpServletRequest request) {
        request.setAttribute("roleid", roleid);
        return "/role/addResource";
    }


    /**
     * 获取角色拥有的资源树
     * @return
     */
    @RequestMapping("/getResourceTree")
    @ResponseBody
    public Object getResourceTree(Integer roleid) {
        Map<Object, Object> resourceParams = new HashMap<Object, Object>();
        resourceParams.put("pid", 0);
        List<Resource> resourceList = resourceService.selectAll(resourceParams);
        Map<Object, Object> roleResourceParams = new HashMap<Object, Object>();
        roleResourceParams.put("roleid", roleid);
        List<RoleResource> roleResourceList = roleResourceService.selectAll(roleResourceParams);

        List<Tree> treeList0 = new ArrayList<Tree>();
        for (Resource resource : resourceList) {
            Tree tree0 = new Tree();
            tree0.setId(resource.getId() + "");
            tree0.setText(resource.getName());
            boolean checked = false;
            for (RoleResource roleResource : roleResourceList) {
                if (resource.getId() == roleResource.getResourceid()) {
                    checked = true;
                }
            }
            tree0.setChecked(checked);
            tree0.setState("open");
            tree0.setIconCls("icon-dept");
            getResourceTreeChildren(tree0, roleResourceList);
            treeList0.add(tree0);
        }
        return treeList0;
    }

    /**
     * 给角色授权资源
     * @return
     */
    @RequestMapping("/doAddResource")
    @ResponseBody
    public Object doAddResource(Integer roleid, String resourceArr) {
        //查出原有资源，删掉
        Map<Object, Object> roleResourceParams = new HashMap<Object, Object>();
        roleResourceParams.put("roleid", roleid);
        List<RoleResource> listRoleResource = roleResourceService.selectAll(roleResourceParams);
        for (RoleResource roleResource : listRoleResource) {
            roleResourceService.deleteByPrimaryKey(roleResource.getId());
        }
        String[] ResourceArray = resourceArr.split(",");
        for (String resourceid : ResourceArray) {
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleid(roleid);
            roleResource.setResourceid(Integer.parseInt(resourceid));
            roleResourceService.insertSelective(roleResource);
        }
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg("授权成功");
        return json;
    }

    private void getResourceTreeChildren(Tree resource, List<RoleResource> roleResourceList){
        Map<Object, Object> resourceParams = new HashMap<Object, Object>();
        resourceParams.put("pid", resource.getId());
        List<Resource> listResource = resourceService.selectAll(resourceParams);
        List<Tree> children = new ArrayList<Tree>();
        for (Resource child : listResource) {
            Tree tree0 = new Tree();
            tree0.setId(child.getId() + "");
            tree0.setText(child.getName());
            boolean checked = false;
            for (RoleResource roleResource : roleResourceList) {
                if (child.getId() == roleResource.getResourceid()) {
                    checked = true;
                }
            }
            tree0.setChecked(checked);
            tree0.setState("open");
            tree0.setIconCls("icon-dept");
            getResourceTreeChildren(tree0, roleResourceList);
            children.add(tree0);
        }
        resource.setChildren(children);
    }

    @RequestMapping("addRolePage")
    public String addRolePage(){ return "role/addRolePage";}

    @RequestMapping("addRole")
    @ResponseBody
    public Object addRole(Role role){
        this.roleService.insertSelective(role);
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg("添加角色成功");
        return json;
    }

    @RequestMapping("updateRolePage")
    public String updateRolePage(Integer id,HttpServletRequest request){
        Role  role = this.roleService.selectByPrimaryKey(id);
        request.setAttribute("role",role);
        return "role/updateRolePage";

    }

    @RequestMapping("updateRole")
    @ResponseBody
    public Object updateRole(Role role){
        this.roleService.updateByPrimaryKeySelective(role);
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg("修改角色成功");
        return json;
    }

    @RequestMapping("deleteRole")
    @ResponseBody
    public Object deleteRole(Integer id){
        this.roleService.deleteByPrimaryKey(id);
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg("删除角色成功");
        return json;
    }

}
