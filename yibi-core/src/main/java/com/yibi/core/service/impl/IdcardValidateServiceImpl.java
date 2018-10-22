package com.yibi.core.service.impl;

import com.yibi.core.dao.IdcardValidateMapper;
import com.yibi.core.entity.IdcardValidate;
import com.yibi.core.service.IdcardValidateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:身份认证记录 idcard_validate
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("idcardValidateService")
public class IdcardValidateServiceImpl implements IdcardValidateService {
    @Resource
    private IdcardValidateMapper idcardValidateMapper;

    private static final Logger logger = LoggerFactory.getLogger(IdcardValidateServiceImpl.class);

    @Override
    public int insert(IdcardValidate record) {
        return this.idcardValidateMapper.insert(record);
    }

    @Override
    public int insertSelective(IdcardValidate record) {
        return this.idcardValidateMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(IdcardValidate record) {
        return this.idcardValidateMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(IdcardValidate record) {
        return this.idcardValidateMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.idcardValidateMapper.deleteByPrimaryKey(id);
    }

    @Override
    public IdcardValidate selectByPrimaryKey(Integer id) {
        return this.idcardValidateMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<IdcardValidate> selectAll(Map<Object, Object> param) {
        return this.idcardValidateMapper.selectAll(param);
    }

    @Override
    public List<IdcardValidate> selectPaging(Map<Object, Object> param) {
        return this.idcardValidateMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.idcardValidateMapper.selectCount(param);
    }

    @Override
    public List<?> queryValidateTimes(Map<String, Object> map) {
        return this.idcardValidateMapper.queryValidateTimes(map);
    }

    @Override
    public IdcardValidate queryByTaskId(String taskId) {
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("taskid", taskId);
        List<IdcardValidate> list = this.idcardValidateMapper.selectAll(params);
        return list == null || list.isEmpty() ? null :list.get(0);
    }

    @Override
    public List<Map<String, Object>> selectConditionPaging(Map<Object, Object> param) {
        return this.idcardValidateMapper.selectConditionPaging(param);
    }

    @Override
    public int selectConditionCount(Map<Object, Object> param) {
        return this.idcardValidateMapper.selectConditionCount(param);
    }
}