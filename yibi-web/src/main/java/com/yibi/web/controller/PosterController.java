package com.yibi.web.controller;

import com.yibi.common.model.Json;
import com.yibi.common.utils.PictureUtils;
import com.yibi.core.entity.Poster;
import com.yibi.core.service.PosterService;
import com.yibi.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@RequestMapping("document")
@Controller
public class PosterController extends BaseController {
    @Autowired
    private PosterService posterService;

    @RequestMapping("posterListPage")
    public String posterListPage(){
        return "document/poster/posterListPage";
    }

    @RequestMapping("posterList")
    @ResponseBody
    public Object posterList(){
        return this.posterService.selectAll(null);
    }

    @RequestMapping("saveOrUpdatePosterPage")
    public String saveOrUpdatePosterPage(Integer id, HttpServletRequest request){
        if (id != null){
            request.setAttribute("id",id);
        }
        return "document/poster/saveOrUpdatePosterPage";
    }
    @RequestMapping("loadPosterForm")
    @ResponseBody
    public Object loadPosterForm(Integer id){
        return this.posterService.selectByPrimaryKey(id);
    }
    @RequestMapping("saveOrUpdatePoster")
    @ResponseBody
    public Object saveOrUpdatePoster(Poster poster, @RequestParam("file")MultipartFile file) throws IOException {
        Json json = new Json();
        json.setSuccess(true);
        if (poster.getId() != null){
            if (!file.isEmpty()){
                String fileName = file.getOriginalFilename();
                String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
                if(!"png".equalsIgnoreCase(fileSuffix)){
                    json.setSuccess(false);
                    json.setMsg("图片格式异常！");
                    return json;
                }
                //上传图片
                String imageName = "imageName"+new Date().getTime()+".jpg";
                String fileDir = "pictureTr";
                String imgPath = PictureUtils.uploadImg(fileDir, imageName,file.getInputStream());
                poster.setImgpath(imgPath);
            }
            this.posterService.updateByPrimaryKeySelective(poster);
            json.setMsg("更新海报成功！");
        }else {
            if (!file.isEmpty()){//图片文件为空，就不能执行插入操作
                String fileName = file.getOriginalFilename();
                String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
                if(!"png".equalsIgnoreCase(fileSuffix)){
                    json.setSuccess(false);
                    json.setMsg("图片格式异常！");
                    return json;
                }
                //上传图片
                String imageName = "imageName"+new Date().getTime()+".jpg";
                String fileDir = "pictureTr";
                String imgPath = PictureUtils.uploadImg(fileDir, imageName,file.getInputStream());
                poster.setImgpath(imgPath);
                this.posterService.insertSelective(poster);
                json.setMsg("新增海报成功");
                return json;
            }
            json.setMsg("添加海报失败！");
        }
        return json;
    }
    @RequestMapping("deletePoster")
    @ResponseBody
    public Object deletePoster(Integer id){
        Json json = new Json();
        json.setSuccess(true);
        int result = this.posterService.deleteByPrimaryKey(id);
        if (result!= 0){
            json.setMsg("删除海报成功");
            return json;
        }
        json.setMsg("删除失败");
        return json;
    }






}
