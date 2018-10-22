package com.yibi.web.controller;

import com.yibi.common.encrypt.MD5;
import com.yibi.common.model.Json;
import com.yibi.common.model.Tree;
import com.yibi.common.utils.UuidUtil;
import com.yibi.core.entity.Manager;
import com.yibi.core.entity.ManagerRole;
import com.yibi.core.entity.Role;
import com.yibi.core.service.ManagerRoleService;
import com.yibi.core.service.ManagerService;
import com.yibi.core.service.RoleService;
import com.yibi.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 管理员管理
 */
@Controller
@RequestMapping("/manager")
public class ManagerController extends BaseController {

    @Autowired
    private ManagerService managerService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ManagerRoleService managerRoleService;


    /**
     * 查看管理员信息页面
     *
     * @return
     */
    @RequestMapping("/infoPage")
    public String infoPage(HttpServletRequest request, Integer id) {
        Manager manager = managerService.selectByPrimaryKey(id);
        request.setAttribute("manager", manager);
        return "/manager/infoPage";
    }

    /**
     * 管理员管理主页面
     *
     * @return
     */
    @RequestMapping("/mainpage")
    public String mainpage() {
        return "/manager/mainpage";
    }

    /**
     * ajax加载数据列表
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list() {
        List<Manager> list = managerService.selectAll(null);
        return list;
    }

    /**
     * 管理员授权页面
     *
     * @param managerid
     * @return
     */
    @RequestMapping("/addRole")
    public String addRole(Integer managerid, HttpServletRequest request) {
        request.setAttribute("managerid", managerid);
        return "/manager/addRole";
    }


    /**
     * 获取管理员拥有的角色树
     * @param managerid
     * @return
     */
    @RequestMapping("/getRoleTree")
    @ResponseBody
    public Object getRoleTree(Integer managerid) {
        List<Role> sysRoleList = roleService.selectAll(null);
        Map<Object, Object> managerRoleParams = new HashMap<Object, Object>();
        managerRoleParams.put("managerid", managerid);
        List<ManagerRole> managerRoleList = managerRoleService.selectAll(managerRoleParams);

        List<Tree> treeList0 = new ArrayList<Tree>();
        for (Role sysRole : sysRoleList) {
            Tree tree0 = new Tree();
            tree0.setId(sysRole.getId() + "");
            tree0.setText(sysRole.getRolename());
            boolean checked = false;
            for (ManagerRole managerRole : managerRoleList) {
                if (sysRole.getId() == managerRole.getRoleid()) {
                    checked = true;
                }
            }
            tree0.setChecked(checked);
            tree0.setState("open");
            tree0.setIconCls("icon-dept");
            treeList0.add(tree0);
        }
        return treeList0;
    }

    /**
     * 给管理员授权角色
     * @param managerid
     * @param roleArr
     * @return
     */
    @RequestMapping("/doAddRole")
    @ResponseBody
    public Object doAddRole(Integer managerid, String roleArr) {
        //查出原有角色，删掉
        Map<Object, Object> managerRoleParams = new HashMap<Object, Object>();
        managerRoleParams.put("managerid", managerid);
        List<ManagerRole> listManagerRole = managerRoleService.selectAll(managerRoleParams);
        for (ManagerRole managerRole : listManagerRole) {
            managerRoleService.deleteByPrimaryKey(managerRole.getId());
        }
        String[] roleArray = roleArr.split(",");
        for (String roleid : roleArray) {
            ManagerRole managerRole = new ManagerRole();
            managerRole.setManagerid(managerid);
            managerRole.setRoleid(Integer.parseInt(roleid));
            managerRoleService.insertSelective(managerRole);
        }
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg("授权成功");
        return json;
    }

    @RequestMapping("/addManagerPage")
    public String addManagerPage() {
        return "/manager/addManager";
    }

    @RequestMapping("/addManager")
    @ResponseBody
    public Object addManager(Manager manager){
        try {
            manager.setUserpassword(MD5.getMd5(manager.getUserpassword()));
        }catch ( Exception e){
            e.printStackTrace();
        }
        this.managerService.insertSelective(manager);
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg("添加成功");
        return json;
    }

    @RequestMapping("/deleteManager")
    @ResponseBody
    public Object deleteManager(Integer id){
        this.managerService.deleteByPrimaryKey(id);
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg("删除成功");
        return json;
    }
    @RequestMapping("updatePwdPage")
    public  String updatePwdPage(Integer id,HttpServletRequest request){
        request.setAttribute("userId",id);
        return "/manager/updatePwdPage";
    }
    @RequestMapping("updatePwd")
    @ResponseBody
    public Object updatePwd(Integer id,String pwd){
        Json json = new Json();
        Manager manager = managerService.selectByPrimaryKey(id);
        if (manager == null){
            json.setMsg("管理员异常，请核实管理员账号");
            return json;
        }
        try {
            manager.setUserpassword(MD5.getMd5(pwd));
            manager.setToken(UuidUtil.get32UUID());
            manager.setTokencreatetime(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            json.setMsg("输入的密码加密异常！");
            return json;
        }
        int result = managerService.updateByPrimaryKeySelective(manager);
        if (result == 0){
            json.setMsg("更新管理员密码失败!");
            return json;
        }
        json.setMsg("更新管理员密码成功");
            return json;
    }

}
