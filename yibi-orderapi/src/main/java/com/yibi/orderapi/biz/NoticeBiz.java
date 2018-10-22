package com.yibi.orderapi.biz;

import com.yibi.core.entity.Notice;

import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
public interface NoticeBiz {

    /**
     * 首页公告信息
     * @return
     */
    Notice queryInfoByIndex();

    Notice getNoticeById(Map<String, Object> map, Integer id);
}
