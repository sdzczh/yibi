package com.yibi.web.service;

import com.yibi.core.entity.IdcardValidate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RealNameService {
    public Object getRealNameList(Integer page,Integer rows,String phone,Integer state);

    public Object addRealName(IdcardValidate idcardValidate,String phone, MultipartFile frontFile, MultipartFile backFile) throws IOException;
}
