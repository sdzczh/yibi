package com.yibi.web.controller;

import com.yibi.common.model.Json;
import com.yibi.core.entity.Resource;
import com.yibi.core.service.ResourceService;
import com.yibi.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统资源管理
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {

    @Autowired
    private ResourceService resourceService;


    /**
     * main页面
     *
     * @return
     */
    @RequestMapping("/mainpage")
    public String mainpage(HttpServletRequest request) {
        return "/resource/mainpage";
    }

    /**
     * ajax获取资源树列表
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list() {
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("pid", 0);
        List<Resource> list = resourceService.selectAll(params);
        for (Resource resource : list) {
            addResourceChildren(resource);
        }
        return list;
    }

    /**
     * 进入添加页面
     * @param pid
     * @param request
     * @return
     */
    @RequestMapping("/resourceAdd")
    public String resourceAdd(Integer pid, HttpServletRequest request) {
        Resource resource = resourceService.selectByPrimaryKey(pid);
        request.setAttribute("resource", resource);
        return "/resource/resourceAdd";
    }

    /**
     * 添加资源
     * @param resource
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Object add(Resource resource){
        if (resource.getPid() == null) {
            resource.setPid(0);
        }
        resourceService.insertSelective(resource);
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg("操作成功");
        return json;
    }

    /**
     * 递归获取子节点方法
     * @param resource
     */
    private void addResourceChildren(Resource resource) {
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("pid", resource.getId());
        List<Resource> childrenList = resourceService.selectAll(params);
        resource.setChildren(childrenList);
        for (Resource resourceChild : childrenList) {
            addResourceChildren(resourceChild);
        }
    }

    @ResponseBody
    @RequestMapping("/getIndexInfo")
    public Map<String,Object> getIndexInfo(){
        resourceService.getIndexInfo();
        return resourceService.getIndexInfo();
    }

    @RequestMapping("/resourceUpdatePage")
    public String resourceUpdatePage(Integer id, HttpServletRequest request) {
        Resource resource = resourceService.selectByPrimaryKey(id);
        request.setAttribute("resource", resource);
        return "/resource/resourceUpdate";
    }
    @RequestMapping("/resourceUpdate")
    @ResponseBody
    public Object resourceUpdate(Resource resource){
        resourceService.updateByPrimaryKeySelective(resource);
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg("操作成功");
        return json;
    }

    @RequestMapping("/deleteResource")
    @ResponseBody
    public Object deleteResource(Integer id){
        resourceService.deleteByPrimaryKey(id);
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg("操作成功");
        return json;
    }


}
