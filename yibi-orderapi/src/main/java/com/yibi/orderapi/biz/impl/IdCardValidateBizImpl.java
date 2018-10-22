package com.yibi.orderapi.biz.impl;

import com.yibi.common.utils.DATE;
import com.yibi.common.utils.DateUtils;
import com.yibi.common.utils.TimeStampUtils;
import com.yibi.core.service.IdcardValidateService;
import com.yibi.orderapi.biz.IdCardValidateBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/12 0012.
 */
@Service
@Transactional
public class IdCardValidateBizImpl implements IdCardValidateBiz{

    @Autowired
    private IdcardValidateService idcardValidateService;
    @Override
    public Map<String, Object> queryValidateTimes(Integer userId,Integer dateMis) {
        String currDate = DATE.getCurrentTimeStr();
        String  startDate = DateUtils.getSomeDay(-dateMis)+" 00:00:00";
        String sql = "SELECT COUNT(*) as counts, DATE_FORMAT(createTime, '%Y-%m-%d') as valiDate  FROM t_idcard_validate WHERE userId=:userId and createTime between :startDate and :currDate GROUP BY  valiDate";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("startDate", TimeStampUtils.StrToTimeStamp(startDate));
        map.put("currDate", TimeStampUtils.StrToTimeStamp(currDate));
        List<?> results =  idcardValidateService.queryValidateTimes(map);

        if(results!=null&&!results.isEmpty()){
            Map<String, Object> countMap = new HashMap<>();
            for(Object res :results){
                Map<String, Object> data =(Map<String, Object>)res;
                countMap.put((String)data.get("valiDate"), data.get("counts"));
            }
            return countMap;

        }else{
            return null;
        }
    }
}
