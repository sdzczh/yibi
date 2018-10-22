package com.yibi.web.controller;

import com.yibi.common.model.Json;
import com.yibi.common.utils.PictureUtils;
import com.yibi.core.entity.AboutInfo;
import com.yibi.core.service.AboutInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("community")
public class CommunityController {

    @Autowired
    private AboutInfoService aboutInfoService;

    @RequestMapping("communityListPage")
    public String  communityListPage(){
        return "/document/community/communityListPage";
    }
    @RequestMapping("communityList")
    @ResponseBody
    public Object communityList(){
        return this.aboutInfoService.selectAll(null);
    }


    @RequestMapping("addCommunityPage")
    public String addCommunityPage(Integer id, HttpServletRequest request){
        request.setAttribute("communityId",id);
        return "/document/community/addCommunityPage";
    }

    @RequestMapping("loadForm")
    @ResponseBody
    public  Object loadForm(Integer id){
        return  this.aboutInfoService.selectByPrimaryKey(id);
    }

    @ResponseBody
    @RequestMapping("saveOrUpdateCommunity")
    public Object saveOrUpdateCommunity(AboutInfo aboutInfo, MultipartFile file) throws IOException {
        Json json = new Json();
        json.setSuccess(true);
        if (aboutInfo.getId() != null){//更新
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
                aboutInfo.setImgpath(imgPath);
            }

            aboutInfoService.updateByPrimaryKeySelective(aboutInfo);
            json.setMsg("更新成功");
            return json;
        } else {//新增
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
                aboutInfo.setImgpath(imgPath);
                aboutInfoService.insertSelective(aboutInfo);
                json.setMsg("新增成功");
                return json;
            }else {
                json.setMsg("请上传图片");
                return json;
            }

        }
    }
}
