package com.yibi.orderapi.biz;

import com.yibi.core.entity.Banner;

import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
public interface BannerBiz {
    /**
     * 查看banner信息
     * @return id, imgPath, type, address
     * @param map
     */
    List<Banner> queryAllInfo(Map<Object, Object> map);

    /**
     * 根据banner位置查询  banner位置 0首页 1生态 2提升魂力
     * @param bannerType
     * @return
     */
    String getBannerByType(Integer bannerType);
}
