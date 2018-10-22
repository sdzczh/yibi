package com.yibi.core.service.impl;

import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.dao.SysparamsMapper;
import com.yibi.core.entity.Sysparams;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.DigHonorsService;
import com.yibi.core.service.SysparamsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:系统参数 sysparams
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("sysparamsService")
public class SysparamsServiceImpl implements SysparamsService {
    @Resource
    private SysparamsMapper sysparamsMapper;
    @Resource
    private CoinManageService coinManageService;
    @Resource
    private DigHonorsService digHonorsService;
    @Autowired
    private RedisTemplate<String, String> redis;

    private static final Logger logger = LoggerFactory.getLogger(SysparamsServiceImpl.class);

    @Override
    public int insert(Sysparams record) {
        return this.sysparamsMapper.insert(record);
    }

    @Override
    public int insertSelective(Sysparams record) {
        return this.sysparamsMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Sysparams record) {
        return this.sysparamsMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Sysparams record) {
        return this.sysparamsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.sysparamsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Sysparams selectByPrimaryKey(Integer id) {
        return this.sysparamsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Sysparams> selectAll(Map<Object, Object> param) {
        return this.sysparamsMapper.selectAll(param);
    }

    @Override
    public List<Sysparams> selectPaging(Map<Object, Object> param) {
        return this.sysparamsMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.sysparamsMapper.selectCount(param);
    }

    @Override
    public Sysparams getValByKey(String key) {
        String redisKey = String.format(RedisKey.SYSTEM_PARAM, key);
        String systemParamVal = RedisUtil.searchString(redis, redisKey);
        Sysparams systemParam = null;
        if(systemParamVal==null){
            systemParam = sysparamsMapper.selectByKey(key);
            if(systemParam!=null){
                RedisUtil.addStringObj(redis, redisKey, systemParam.getKeyval());
            }
            return systemParam;
        }else{
            systemParam = new Sysparams();
            systemParam.setKeyname(key);
            systemParam.setKeyval(systemParamVal);
            return systemParam;
        }
    }

    @Override
    public String getValStringByKey(String key) {
        Sysparams param =  getValByKey( key);
        if(param == null) return "";
        return param.getKeyval();
    }

    @Override
    public List<Map<String,Object>> selectSystemParamByCondition(Map<Object,Object> map) {
        return this.sysparamsMapper.selectSystemParamByCondition(map);
    }

    @Override
    public int selectSystemParamCountByCondition(Map<Object, Object> map) {
        return this.sysparamsMapper.selectSystemParamCountByCondition(map);
    }


    @Override
    public int getValIntByKey(String key) {
        Sysparams param =  getValByKey( key);
        if(param == null|| StrUtils.isBlank(param.getKeyval())) return 0;
        return Integer.valueOf(param.getKeyval());
    }

    public String getParam(String key){
        Sysparams param = getValByKey(key);
        if(param ==null){
            return "";
        }else{
            return param.getKeyval();
        }
    }
}