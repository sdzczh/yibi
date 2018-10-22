package com.yibi.core.service.impl;

import com.yibi.common.utils.TimeStampUtils;
import com.yibi.core.dao.WithdrawMapper;
import com.yibi.core.entity.Withdraw;
import com.yibi.core.service.WithdrawService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:提现记录 withdraw
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("withdrawService")
public class WithdrawServiceImpl implements WithdrawService {
    @Resource
    private WithdrawMapper withdrawMapper;

    private static final Logger logger = LoggerFactory.getLogger(WithdrawServiceImpl.class);

    @Override
    public int insert(Withdraw record) {
        return this.withdrawMapper.insert(record);
    }

    @Override
    public int insertSelective(Withdraw record) {
        return this.withdrawMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Withdraw record) {
        return this.withdrawMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Withdraw record) {
        return this.withdrawMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.withdrawMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Withdraw selectByPrimaryKey(Integer id) {
        return this.withdrawMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Withdraw> selectAll(Map<Object, Object> param) {
        return this.withdrawMapper.selectAll(param);
    }

    @Override
    public List<Withdraw> selectPaging(Map<Object, Object> param) {
        return this.withdrawMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.withdrawMapper.selectCount(param);
    }

    @Override
    public Map<String, Object> queryDayWithdraw(Integer userId, String currentDateStr, Integer coinType) {
        Timestamp start = TimeStampUtils.StrToTimeStamp(currentDateStr+" 00:00:00");
        Timestamp end = TimeStampUtils.StrToTimeStamp(currentDateStr+" 23:59:59");
        Map<Object, Object> map = new HashMap();
        map.put("userid", userId);
        map.put("cointype", coinType);
        map.put("starttime", start);
        map.put("endtime", end);
        List<?> users = this.withdrawMapper.queryDayWithdraw(map);

        return users==null||users.isEmpty()?null: (Map<String, Object>) users.get(0);
    }

    @Override
    public List<Withdraw> queryByUserIdAndType(Integer id, Integer pageInt, Integer rowsInt, Integer accountType, Integer coinType) {
        Map<Object, Object> map = new HashMap();
        map.put("userid", id);
        map.put("firstResult", pageInt*rowsInt);
        map.put("maxResult", (pageInt+1)*rowsInt);
        map.put("account type", accountType);
        map.put("cointype", coinType);
        return this.withdrawMapper.selectPaging(map);
    }

    @Override
    public List<Map<String, Object>> selectWithdrawOrPhone(Map<Object, Object> map) {
        return this.withdrawMapper.selectWithdrawOrPhone(map);
    }

    @Override
    public int selectCountWithdrawOrPhone(Map<Object, Object> map) {
        return this.withdrawMapper.selectCountWithdrawOrPhone(map);
    }

}