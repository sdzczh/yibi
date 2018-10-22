package com.yibi.orderapi.biz;

import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/12 0012.
 */
public interface IdCardValidateBiz {


    /**
     * 实名认证校验次数
     * @param userId
     * @return Map<String,Object>
     */
    Map<String,Object> queryValidateTimes(Integer userId,Integer dateMis);
}
