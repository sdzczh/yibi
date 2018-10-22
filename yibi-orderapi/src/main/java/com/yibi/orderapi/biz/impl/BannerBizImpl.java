package com.yibi.orderapi.biz.impl;

import com.yibi.core.entity.Banner;
import com.yibi.core.service.BannerService;
import com.yibi.orderapi.biz.BannerBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
@Service
public class BannerBizImpl implements BannerBiz {
    @Autowired
    private BannerService bannerService;


    @Override
    public List<Banner> queryAllInfo(Map<Object, Object> map) {
        return bannerService.selectAllInfo(map);
    }

    @Override
    public String getBannerByType(Integer bannerType) {
        Map<String, Object> data = new HashMap<>();
        Map<Object, Object> map = new HashMap<>();
        map.put("bannertype", bannerType);
        map.put("state", 1);
        List<Banner> list = queryAllInfo(map);
        data.put("list", list);
        return Result.toResult(ResultCode.SUCCESS, data);
    }
}
