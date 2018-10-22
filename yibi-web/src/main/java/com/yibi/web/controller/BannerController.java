package com.yibi.web.controller;

import com.yibi.common.model.Json;
import com.yibi.common.utils.PictureUtils;
import com.yibi.core.entity.Banner;
import com.yibi.core.service.BannerService;
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

@Controller
@RequestMapping("document")
public class BannerController extends BaseController {
    @Autowired
    private BannerService bannerService;

    @RequestMapping("bannerListPage")
    public String bannerListPage(){
        return "/document/banner/bannerListPage";
    }

    @RequestMapping("bannerList")
    @ResponseBody
    public Object bannerList(){
        return  this.bannerService.selectAll(null);
    }

    @RequestMapping("saveBannerPage")
    public String saveBannerPage(){
        return "/document/banner/saveBannerPage";
    }
    @RequestMapping("updateBannerPage")
    public String saveOrUpdateBannerPage(HttpServletRequest request, Integer id){
        if(id != null){
            request.setAttribute("id",id);
        }
        return "/document/banner/updateBannerPage";
    }

    @RequestMapping("saveOrUpdateBanner")
    @ResponseBody
    public Object saveOrUpdateBanner(Banner banner, @RequestParam("file") MultipartFile file) throws IOException {
        Json json = new Json();
        json.setSuccess(true);
        if(banner.getId() != null){//更新
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
                banner.setImgpath(imgPath);
            }
            this.bannerService.updateByPrimaryKeySelective(banner);
            json.setMsg("更新banner成功");
            return json;
        }else {//插入
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
                banner.setImgpath(imgPath);
                this.bannerService.insertSelective(banner);
                json.setMsg("新增banner成功");
                return json;
            }
        }
        json.setSuccess(false);
        json.setMsg("新增失败！");
        return  json;
    }
    @RequestMapping("loadForm")
    @ResponseBody
    public Object loadForm(Integer id){
        return  this.bannerService.selectByPrimaryKey(id);
    }



}
