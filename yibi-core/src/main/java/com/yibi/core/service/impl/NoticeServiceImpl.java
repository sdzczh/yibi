package com.yibi.core.service.impl;

import com.yibi.core.dao.NoticeMapper;
import com.yibi.core.entity.Notice;
import com.yibi.core.service.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:公告 notice
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
    @Resource
    private NoticeMapper noticeMapper;

    private static final Logger logger = LoggerFactory.getLogger(NoticeServiceImpl.class);

    @Override
    public int insert(Notice record) {
        return this.noticeMapper.insert(record);
    }

    @Override
    public int insertSelective(Notice record) {
        return this.noticeMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Notice record) {
        return this.noticeMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Notice record) {
        return this.noticeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.noticeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Notice selectByPrimaryKey(Integer id) {
        return this.noticeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Notice> selectAll(Map<Object, Object> param) {
        return this.noticeMapper.selectAll(param);
    }
    @Override
    public List<Notice> selectByIdAndState(Map<Object, Object> param) {
        return this.noticeMapper.selectByIdAndState(param);
    }

    @Override
    public List<Notice> selectPaging(Map<Object, Object> param) {
        return this.noticeMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.noticeMapper.selectCount(param);
    }

    @Override
    public List<Notice> selectInfoByIndex() {
        return this.noticeMapper.selectInfoByIndex();
    }
}