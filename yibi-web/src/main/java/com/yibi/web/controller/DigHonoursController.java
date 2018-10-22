package com.yibi.web.controller;

import com.yibi.common.model.Json;
import com.yibi.common.utils.PictureUtils;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.entity.CoinManage;
import com.yibi.core.entity.DigHonors;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.DigHonorsService;
import com.yibi.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("dig")
public class DigHonoursController extends BaseController {
    @Autowired
    private DigHonorsService digHonorsService;
    @Autowired
    private CoinManageService coinManageService;

    @RequestMapping("digHonoursPage")
    public String digHonoursPage(){
        return "dig/digHonoursListPage";
    }
    @RequestMapping("digHonoursList")
    @ResponseBody
    public Object digHonoursList(){
        List<DigHonors> list = this.digHonorsService.selectAll(null);
        return list;
    }
    @RequestMapping("queryCoinNameByCoinTypes")
    @ResponseBody
    public String queryCoinNameByCoinTypes(String coinTypes){
        if(StrUtils.isBlank(coinTypes)){
            return "";
        }
        String [] coins = coinTypes.split(",");
        StringBuffer sb = new StringBuffer();
        for(String coin :coins){
            Integer coinType = Integer.valueOf(coin);
            Map<Object,Object> map = new HashMap<>();
            map.put("cointype",coinType);
            CoinManage coinManage = this.coinManageService.selectAll(map).get(0);
            sb.append(coinManage.getCoinname());
            sb.append(",");
        }
        String result = sb.substring(0,sb.lastIndexOf(","));
        return  result;
    }

    @RequestMapping("saveOrUpdateDigHonoursPage")
    public String saveOrUpdateDigHonoursPage(Integer id, HttpServletRequest request){
        request.setAttribute("id",id);
        request.setAttribute("coins",this.coinManageService.selectAll(null));
        if (id != null){
            DigHonors digHonors = this.digHonorsService.selectByPrimaryKey(id);
            String coinTypes = digHonors.getCointype().trim();
            if (!StrUtils.isBlank(coinTypes)){
                String [] coins = coinTypes.split(",");
                List<String> list = new ArrayList<>();
                for(String coin :coins){
                    Integer coinType = Integer.valueOf(coin);
                    Map<Object,Object> map = new HashMap<>();
                    map.put("cointype",coinType);
                    CoinManage coinManage = this.coinManageService.selectAll(map).get(0);
                    list.add(coinManage.getCoinname());
                }
                request.setAttribute("coinNames",list);
            }
        }
        return "/dig/saveOrUpdateDigHonours";
    }

    @RequestMapping("loadForm")
    @ResponseBody
    public Object loadForm(Integer id){
        DigHonors digHonors = this.digHonorsService.selectByPrimaryKey(id);
        return digHonors;
    }

    @RequestMapping("saveOrUpdateDigHonours")
    @ResponseBody
    public Object saveOrUpdateDigHonours(DigHonors digHonors,Integer [] coinType, MultipartFile mineFile,MultipartFile honoursFile) throws IOException {
        Json json = new Json();
        json.setSuccess(true);
        StringBuffer sb = new StringBuffer();
        if (coinType != null){
            for (Integer coin : coinType){
                sb.append(coin);
                sb.append(",");
            }
            digHonors.setCointype(sb.substring(0,sb.lastIndexOf(",")));
        }else {
            digHonors.setCointype("");
        }
        if (digHonors.getId() != null){//更新操作
            if (!mineFile.isEmpty()){
                String fileName = mineFile.getOriginalFilename();
                String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
                if(!"png".equalsIgnoreCase(fileSuffix)){
                    json.setSuccess(false);
                    json.setMsg("图片格式异常！");
                    return json;
                }
                //上传图片
                String imageName = "imageName"+new Date().getTime()+".jpg";
                String fileDir = "pictureTr";
                String imgPath = PictureUtils.uploadImg(fileDir, imageName,mineFile.getInputStream());
                digHonors.setMinepicurl(imgPath);
            }
            if (!honoursFile.isEmpty()){
                String fileName = honoursFile.getOriginalFilename();
                String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
                if(!"png".equalsIgnoreCase(fileSuffix)){
                    json.setSuccess(false);
                    json.setMsg("图片格式异常！");
                    return json;
                }
                //上传图片
                String imageName = "imageName"+new Date().getTime()+".jpg";
                String fileDir = "pictureTr";
                String imgPath = PictureUtils.uploadImg(fileDir, imageName,honoursFile.getInputStream());
                digHonors.setRolepicurl(imgPath);
            }
            this.digHonorsService.updateByPrimaryKeySelective(digHonors);
            json.setMsg("更新矿区成功！");
            return json;

        } else {//插入
            if ((!mineFile.isEmpty())&&(!honoursFile.isEmpty())){
                    String fileName = mineFile.getOriginalFilename();
                    String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
                    if(!"png".equalsIgnoreCase(fileSuffix)){
                        json.setSuccess(false);
                        json.setMsg("图片格式异常！");
                        return json;
                    }
                    //上传图片
                    String imageName = "imageName"+new Date().getTime()+".jpg";
                    String fileDir = "pictureTr";
                    String imgPath = PictureUtils.uploadImg(fileDir, imageName,mineFile.getInputStream());
                    digHonors.setMinepicurl(imgPath);
                    String honoursName = honoursFile.getOriginalFilename();
                    String honoursSuffix = honoursName.substring(honoursName.lastIndexOf(".")+1);
                    if(!"png".equalsIgnoreCase(honoursSuffix)) {
                        json.setSuccess(false);
                        json.setMsg("图片格式异常！");
                        return json;
                    }
                    //上传图片
                    String honourName = "imageName"+new Date().getTime()+".jpg";
                    String honoursDir = "pictureTr";
                    String honoursPath = PictureUtils.uploadImg(honoursDir, honourName,honoursFile.getInputStream());
                    digHonors.setRolepicurl(honoursPath);
                    this.digHonorsService.insertSelective(digHonors);
                    json.setMsg("插入新矿区成功！");
                    return json;
            }
            json.setMsg("插入失败！需要上传图片");
            return json;
        }
    }

}
