package com.yibi.core.service.impl;

import com.yibi.core.dao.CommissionInviteMapper;
import com.yibi.core.entity.CommissionInvite;
import com.yibi.core.service.CommissionInviteService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑实现类:邀请奖励表 commission_invite
 * 
 * @author: autogeneration
 * @date: 2018-07-25 09:26:09
 **/ 
@Service("commissionInviteService")
public class CommissionInviteServiceImpl implements CommissionInviteService {
    @Resource
    private CommissionInviteMapper commissionInviteMapper;

    private static final Logger logger = LoggerFactory.getLogger(CommissionInviteServiceImpl.class);

    @Override
    public int insert(CommissionInvite record) {
        return this.commissionInviteMapper.insert(record);
    }

    @Override
    public int insertSelective(CommissionInvite record) {
        return this.commissionInviteMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(CommissionInvite record) {
        return this.commissionInviteMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(CommissionInvite record) {
        return this.commissionInviteMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.commissionInviteMapper.deleteByPrimaryKey(id);
    }

    @Override
    public CommissionInvite selectByPrimaryKey(Integer id) {
        return this.commissionInviteMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CommissionInvite> selectAll(Map<Object, Object> param) {
        return this.commissionInviteMapper.selectAll(param);
    }

    @Override
    public List<CommissionInvite> selectPaging(Map<Object, Object> param) {
        return this.commissionInviteMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.commissionInviteMapper.selectCount(param);
    }
}