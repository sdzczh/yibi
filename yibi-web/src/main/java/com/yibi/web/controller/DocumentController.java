package com.yibi.web.controller;

import com.yibi.common.model.Json;
import com.yibi.core.entity.Doc;
import com.yibi.core.service.DocService;
import com.yibi.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("docManage")
public class DocumentController extends BaseController {
    @Autowired
    private DocService docService;

    @RequestMapping("docListPage")
    public String docListPage(){
        return "/document/doc/docListPage";
    }
    @ResponseBody
    @RequestMapping("docList")
    public Object docList(){return this.docService.selectAll(null);}

    @RequestMapping("addDocPage")
    public String addDocPage(){
        return "/document/doc/addDocPage";
    }
    @RequestMapping("addDoc")
    @ResponseBody
    public Object addDoc(Doc doc){
        Json json = new Json();
        json.setSuccess(true);
        int result = this.docService.insertSelective(doc);
        if (result != 0){
            json.setMsg("新增文档成功");
            return json;
        }
        json.setMsg("新增失败");
        return json;
    }

    @RequestMapping("updateDocPage")
    public String updateDocPage(Integer id, HttpServletRequest request){
        request.setAttribute("doc",this.docService.selectByPrimaryKey(id));
        return "/document/doc/updateDocPage";
    }
    @RequestMapping("updateDoc")
    @ResponseBody
    public Object updateDoc(Doc doc){
        Json json = new Json();
        json.setSuccess(true);
        int result = this.docService.updateByPrimaryKeySelective(doc);
        if (result != 0){
            json.setMsg("更新文档成功");
            return json;
        }
        json.setMsg("更新失败");
        return json;
    }

    @RequestMapping("deleteDoc")
    @ResponseBody
    public Object deleteDoc(Integer id){
        Json json = new Json();
        json.setSuccess(true);
        int result = this.docService.deleteByPrimaryKey(id);
        if (result == 0){
            json.setMsg("删除失败");
            return json;
        }
        json.setMsg("删除成功！");
        return json;
    }
}
