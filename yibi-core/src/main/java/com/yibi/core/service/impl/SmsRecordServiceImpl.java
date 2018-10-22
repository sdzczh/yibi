package com.yibi.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.dao.SmsRecordMapper;
import com.yibi.core.entity.SmsRecord;
import com.yibi.core.entity.Sysparams;
import com.yibi.core.service.SmsRecordService;
import com.yibi.core.service.SysparamsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-11 11:18:12
 **/
@Slf4j
@Service("smsRecordService")
public class SmsRecordServiceImpl implements SmsRecordService {
    @Resource
    private SmsRecordMapper smsRecordMapper;
    @Resource
    private SysparamsService sysparamsService;

    private static final Logger logger = LoggerFactory.getLogger(SmsRecordServiceImpl.class);

    @Override
    public int insert(SmsRecord record) {
        return this.smsRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(SmsRecord record) {
        return this.smsRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(SmsRecord record) {
        return this.smsRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(SmsRecord record) {
        return this.smsRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.smsRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SmsRecord selectByPrimaryKey(Integer id) {
        return this.smsRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SmsRecord> selectAll(Map<Object, Object> param) {
        return this.smsRecordMapper.selectAll(param);
    }

    @Override
    public List<SmsRecord> selectPaging(Map<Object, Object> param) {
        return this.smsRecordMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.smsRecordMapper.selectCount(param);
    }

    @Override
    public SmsRecord getByIdAndPhone(Integer codeId, String phone) {
        Map<Object, Object> params = new HashMap<>();
        params.put("id", codeId);
        params.put("phone", phone);
        List<SmsRecord> list = selectAll(params);
        return list == null || list.isEmpty() ? null:list.get(0);
    }

    @Override
    public List<SmsRecord> queryListByTimeLimit(Map map) {
        return this.smsRecordMapper.queryListByTimeLimit(map);
    }
}