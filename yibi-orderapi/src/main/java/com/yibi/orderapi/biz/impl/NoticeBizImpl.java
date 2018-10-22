package com.yibi.orderapi.biz.impl;

import com.yibi.core.entity.Notice;
import com.yibi.core.service.NoticeService;
import com.yibi.orderapi.biz.NoticeBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
@Service
@Transactional
public class NoticeBizImpl implements NoticeBiz {
    @Autowired
    private NoticeService noticeService;

    @Override
    public Notice queryInfoByIndex() {
        List<Notice> list = noticeService.selectInfoByIndex();
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Notice getNoticeById(Map<String, Object> map, Integer id) {
        Map param = new HashMap();
        param.put("state", 1);
        param.put("id", id);
        List<Notice> list = noticeService.selectByIdAndState(param);
        return list == null || list.isEmpty() ? null : list.get(0);

    }
}
