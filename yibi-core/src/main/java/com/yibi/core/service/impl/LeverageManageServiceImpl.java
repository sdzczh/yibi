package com.yibi.core.service.impl;

import com.yibi.core.dao.LeverageManageMapper;
import com.yibi.core.entity.LeverageManage;
import com.yibi.core.service.LeverageManageService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑实现类:杠杆管理 leverage_manage
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("leverageManageService")
public class LeverageManageServiceImpl implements LeverageManageService {
    @Resource
    private LeverageManageMapper leverageManageMapper;

    private static final Logger logger = LoggerFactory.getLogger(LeverageManageServiceImpl.class);

    @Override
    public int insert(LeverageManage record) {
        return this.leverageManageMapper.insert(record);
    }

    @Override
    public int insertSelective(LeverageManage record) {
        return this.leverageManageMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(LeverageManage record) {
        return this.leverageManageMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(LeverageManage record) {
        return this.leverageManageMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.leverageManageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public LeverageManage selectByPrimaryKey(Integer id) {
        return this.leverageManageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<LeverageManage> selectAll(Map<Object, Object> param) {
        return this.leverageManageMapper.selectAll(param);
    }

    @Override
    public List<LeverageManage> selectPaging(Map<Object, Object> param) {
        return this.leverageManageMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.leverageManageMapper.selectCount(param);
    }
}