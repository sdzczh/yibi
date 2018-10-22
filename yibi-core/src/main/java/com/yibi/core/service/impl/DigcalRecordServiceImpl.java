package com.yibi.core.service.impl;

import com.google.common.collect.Maps;
import com.yibi.common.utils.DATE;
import com.yibi.common.utils.TimeStampUtils;
import com.yibi.core.constants.CalculForceType;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.dao.DigcalRecordMapper;
import com.yibi.core.entity.DigcalRecord;
import com.yibi.core.entity.Sysparams;
import com.yibi.core.entity.UserDiginfo;
import com.yibi.core.service.DigcalRecordService;
import com.yibi.core.service.SysparamsService;
import com.yibi.core.service.UserDiginfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:算力记录 digcal_record
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("digcalRecordService")
public class DigcalRecordServiceImpl implements DigcalRecordService {
    @Resource
    private DigcalRecordMapper digcalRecordMapper;
    @Autowired
    private  SysparamsService sysparamsService;
    @Autowired
    private UserDiginfoService userDiginfoService;

    private static final Logger logger = LoggerFactory.getLogger(DigcalRecordServiceImpl.class);

    @Override
    public int insert(DigcalRecord record) {
        return this.digcalRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(DigcalRecord record) {
        return this.digcalRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(DigcalRecord record) {
        return this.digcalRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(DigcalRecord record) {
        return this.digcalRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.digcalRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public DigcalRecord selectByPrimaryKey(Integer id) {
        return this.digcalRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DigcalRecord> selectAll(Map<Object, Object> param) {
        return this.digcalRecordMapper.selectAll(param);
    }

    @Override
    public List<DigcalRecord> selectPaging(Map<Object, Object> param) {
        return this.digcalRecordMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.digcalRecordMapper.selectCount(param);
    }

    @Override
    public boolean existCalcalForceDay(Integer userId, Integer code) {
        return this.existCalcalForceDay(userId,code,TimeStampUtils.StrToTimeStamp(DATE.getCurrentDateStr()+" 00:00:00"),null);
    }

    @Override
    public boolean existCalcalForceDay(Integer userId, Integer code, Timestamp startTime, Timestamp endTime) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("userid", userId);
        map.put("type", code);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        List<DigcalRecord> result = digcalRecordMapper.existCalcalForceDay(map);
        return result!=null&&!result.isEmpty();
    }

    @Override
    public Integer getTotalCalculForceByUserAndType(Integer userId, Integer code) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("userid", userId);
        map.put("type", code);
        return digcalRecordMapper.getTotalCalculForceByUserAndType(map);
    }

    @Override
    public boolean existCalcalForce(Integer userId, Integer type) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("userid", userId);
        map.put("type", type);
        List<DigcalRecord> list = selectAll(map);
        return list!=null && !list.isEmpty();
    }

    @Override
    public List<?> queryListByUser(Integer userId, Integer page, Integer rows) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("userId",userId);
        map.put("firstResult",page*rows);
        map.put("maxResult",rows);
        return this.digcalRecordMapper.queryListByUser(map);
    }

    @Override
    public int selectTodayCountByUserId(Integer userId) {
        return this.digcalRecordMapper.selectTodayCountByUserId(userId);
    }

    @Override
    public void insertOrderCalculForce(Integer userId) {
        /*检查当天是否已经有交易奖励*/
        if (!this.existCalcalForceDay(userId, CalculForceType.ORDER.getCode())) {
            Sysparams param = sysparamsService.getValByKey(SystemParams.ORDER_FORCE_PER);
            int forcePer = param == null ? 50 : Integer.valueOf(param.getKeyval());//每次增加的算力

            /*Sysparams param1 = sysparamsService.getValByKey(SystemParams.ORDER_FORCE_TOTAL);
            int forceMax = param1 == null ? 1000 : Integer.valueOf(param1.getKeyval());//最大增加的算力

            int addTotal = this.getTotalCalculForceByUserAndType(userId, CalculForceType.ORDER.getCode());
            int minusTotal = this.getTotalCalculForceByUserAndType(userId, CalculForceType.NOT_ORDER.getCode());
*/

            UserDiginfo diginfo = null;
            Map<Object, Object> params = new HashMap<Object, Object>();
            params.put("userid", userId);
            List<UserDiginfo> list = userDiginfoService.selectAll(params);
            if (list != null && !list.isEmpty()) {
                diginfo = list.get(0);
            }
            if (diginfo == null) {
                diginfo = new UserDiginfo();
                diginfo.setUserid(userId);
                diginfo.setDigcalcul(0);
                diginfo.setDigflag(false);
            }


                /*添加算力记录*/
            DigcalRecord rec = new DigcalRecord();
            rec.setUserid(diginfo.getUserid());

            diginfo.setDigcalcul(diginfo.getDigcalcul() + forcePer);
            if (diginfo.getId() != null) {
                userDiginfoService.updateByPrimaryKeySelective(diginfo);
            }else{
                userDiginfoService.insertSelective(diginfo);
            }

                rec.setDigcalcul(forcePer);
                rec.setType(CalculForceType.ORDER.getCode());
                rec.setName(CalculForceType.ORDER.getName());


            rec.setAllcalculforce(diginfo.getDigcalcul());
            this.insertSelective(rec);

                /*判断是否升级发送推送消息*/
//			talkMenuService.cheackIsUp(userId, forcePer, GlobalParams.PUSH_TO_INDEX);
        }
    }

    @Override
    public int insertBatch(List<DigcalRecord> list) {
        return this.digcalRecordMapper.insertBatch(list);
    }
}