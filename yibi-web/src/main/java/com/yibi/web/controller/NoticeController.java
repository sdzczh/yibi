package com.yibi.web.controller;

import com.yibi.common.model.Grid;
import com.yibi.common.model.Json;
import com.yibi.common.model.PageModel;
import com.yibi.common.model.SessionInfo;
import com.yibi.common.utils.PictureUtils;
import com.yibi.core.entity.Notice;
import com.yibi.core.service.NoticeService;
import com.yibi.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/document")
public class NoticeController extends BaseController {
    @Autowired
    private NoticeService noticeService;

    @RequestMapping("noticeListPage")
    public String noticeListPage(){
        return "/document/notice/noticeListPage";
    }
    @RequestMapping("getNoticeList")
    @ResponseBody
    public Object getPictureList(Integer type,Integer page,Integer rows){
        Map<Object,Object> map = new HashMap<>();
        if (type != null){
            map.put("type",type);
        }
        PageModel pageModel = new PageModel(page,rows);
        map.put("maxResult", pageModel.getMaxResult());
        map.put("firstResult",pageModel.getFirstResult());
        Grid grid = new Grid();
        grid.setRows(this.noticeService.selectPaging(map));
        grid.setTotal(this.noticeService.selectCount(map));
        return grid;
    }

    @RequestMapping("saveOrUpdateNoticePage")
    public String saveOrUpdateNoticePage(Integer id, Map<String ,Object> map ){
        if (id != null){
            map.put("notice",this.noticeService.selectByPrimaryKey(id));
            return  "/document/notice/updateNoticePage";
        }
        return  "/document/notice/saveNoticePage";
    }
    @RequestMapping("saveNotice")
    @ResponseBody
    public String saveNotice(Notice notice, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        if (file.isEmpty()){
            return "The file is not picture!";
        }
        String fileName = file.getOriginalFilename();
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
        if(!"png".equalsIgnoreCase(fileSuffix)){
            return "file is invalid Picture";
        }
        String imageName = "imageName"+new Date().getTime()+".jpg";
        String fileDir = "pictureTr";
        String path = PictureUtils.uploadImg(fileDir, imageName,file.getInputStream());
        notice.setPic(path);
        Integer operId = ((SessionInfo)session.getAttribute("loginUser")).getUserid();
        notice.setCreateid(operId);
        notice.setOperid(operId);
        this.noticeService.insertSelective(notice);
        return "true";
    }

    @RequestMapping("updateNotice")
    @ResponseBody
    public String updateNotice(Notice notice,@RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
       if (!file.isEmpty()){
           String fileName = file.getOriginalFilename();
           String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
           if(!"png".equalsIgnoreCase(fileSuffix)){
               return "file is invalid Picture";
           }
           String imageName = "imageName"+new Date().getTime()+".jpg";
           String fileDir = "pictureTr";
           String path = PictureUtils.uploadImg(fileDir, imageName,file.getInputStream());
           notice.setPic(path);
       }
       Integer operId = ((SessionInfo)session.getAttribute("loginUser")).getUserid();
       notice.setOperid(operId);
       this.noticeService.updateByPrimaryKeySelective(notice);
        return "sucess";
    }

    @RequestMapping("changeState")
    @ResponseBody
    public Object changeState(Integer id){
        Notice notice = this.noticeService.selectByPrimaryKey(id);
        Json json = new Json();
        json.setSuccess(true);
       int result =  this.noticeService.deleteByPrimaryKey(id);
       if (result != 0){
           json.setMsg("删除失败！");
       }
        json.setMsg("删除成功成功");
        return json;
    }


}
