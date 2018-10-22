package com.yibi.web.controller;


import com.yibi.common.model.Json;
import com.yibi.core.entity.IdcardValidate;
import com.yibi.web.service.RealNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("realName")
public class RealNameController {
    @Autowired
    private RealNameService realNameService;

    @RequestMapping("realNameListPage")
    public String realNameListPage(){
        return "user/realName/realNameListPage";
    }
    @RequestMapping("getRealNameList")
    @ResponseBody
    public Object getRealNameList(Integer page,Integer rows,String phone,Integer state){
        return realNameService.getRealNameList(page,rows,phone,state);
    }

    @RequestMapping("addRealNamePage")
    public String addRealNamePage(){
        return "user/realName/addRealNamePage";
    }

    @RequestMapping("addRealName")
    @ResponseBody
    public Object addRealName(IdcardValidate idcardValidate,String phone, MultipartFile frontFile,MultipartFile backFile){
        try {
            return this.realNameService.addRealName(idcardValidate,phone,frontFile,backFile);
        } catch (IOException e) {
            e.printStackTrace();
            Json json = new Json();
            json.setMsg("手动实名出现异常！联系开发人员");
            return json;
        }
    }
}
