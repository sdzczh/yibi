package com.yibi.web.controller;


import com.yibi.common.model.Grid;
import com.yibi.common.model.Json;
import com.yibi.common.model.PageModel;
import com.yibi.common.utils.PictureUtils;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.entity.Picture;
import com.yibi.core.service.PictureService;
import com.yibi.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("document")
@Controller
public class PictureController extends BaseController {
    @Autowired
    private PictureService  pictureService;

    @RequestMapping("pictureList")
    public String pictureList(){
        return "/document/picture/pictureListPage";
    }
    @RequestMapping("getPictureList")
    @ResponseBody
    public Object getPictureList(Integer rows,Integer page){
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        map.put("firstResult",pageModel.getFirstResult());
        map.put("maxResult",pageModel.getMaxResult());
        List<Picture> list = this.pictureService.selectPaging(map);
        Integer total = this.pictureService.selectCount(map);
        Grid grid = new Grid();
        grid.setRows(list);
        grid.setTotal(total);
        return grid;
    }

    @RequestMapping("savePicturePage")
    public String savePicturePage(){
        return "/document/picture/addPicture";
    }
    @RequestMapping("savePicture")
    @ResponseBody
    public Object savePicture(Picture picture,@RequestParam("file") MultipartFile file) throws IOException {
        Json json = new Json();
        json.setSuccess(true);
        String fileName = file.getOriginalFilename();
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
        if("jpeg".equalsIgnoreCase(fileSuffix) || "png".equalsIgnoreCase(fileSuffix)  || "bmp".equalsIgnoreCase(fileSuffix) ){
            String imageName = "imageName"+new Date().getTime()+".jpg";
            String fileDir = "pictureTr";
            String path = PictureUtils.uploadImg(fileDir, imageName,file.getInputStream());
            if (StrUtils.isBlank(path)){
                json.setMsg("图片上传失败！！");
                return  json;
            }
            picture.setAddress(path);
            this.pictureService.insertSelective(picture);
            json.setMsg("上传图片成功！");
            return  json;
        }
        json.setMsg("上传的图片格式不正确！");
        return  json;
    }
    @RequestMapping("deletePicture")
    @ResponseBody
    public Object deletePicture(Integer id){
        this.pictureService.deleteByPrimaryKey(id);
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg("删除成功了！");
        return json;
    }

}
