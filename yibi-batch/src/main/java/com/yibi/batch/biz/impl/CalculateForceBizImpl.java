package com.yibi.batch.biz.impl;

import com.yibi.batch.biz.CalculateForceBiz;
import com.yibi.common.utils.TimeStampUtils;
import com.yibi.core.constants.CalculForceType;
import com.yibi.core.entity.DigcalRecord;
import com.yibi.core.entity.User;
import com.yibi.core.entity.UserDiginfo;
import com.yibi.core.service.DigcalRecordService;
import com.yibi.core.service.UserDiginfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/25 0025.
 */
@Transactional
@Service
@Slf4j
public class CalculateForceBizImpl implements CalculateForceBiz {

    @Autowired
    private UserDiginfoService userDiginfoService;
    @Autowired
    private DigcalRecordService digcalRecordService;
    @Override
    public List<UserDiginfo> queryListPage(int page, int rows) {
        return userDiginfoService.querySignInterList(page,rows);
    }

    @Override
    public void calculateForceCheck(List<UserDiginfo> list, int calDay) {
        log.info("定时处理未连续签到的用户-----");


        if(list!=null && !list.isEmpty()){
            List<Integer> userIds = new ArrayList<Integer>();
            List<DigcalRecord> records = new ArrayList<>();

            for(UserDiginfo info : list){
                //签名所得算力
                Integer signForce =  digcalRecordService.getTotalCalculForceByUserAndType(info.getUserid(),CalculForceType.SIGN.getCode());

                //未签名减去的算力
                Integer noSignForce = digcalRecordService.getTotalCalculForceByUserAndType(info.getUserid(),CalculForceType.NOT_SIGN.getCode());

				/*未签到减去的算力，最多是签到得到的算力*/
                if((signForce-noSignForce)>=(calDay)){
                    userIds.add(info.getUserid());

                    DigcalRecord rec1 = new DigcalRecord();
                    rec1.setUserid(info.getUserid());
                    rec1.setType(CalculForceType.NOT_SIGN.getCode());
                    rec1.setName(CalculForceType.NOT_SIGN.getName());
                    rec1.setDigcalcul(-calDay);
                    rec1.setAllcalculforce(info.getDigcalcul()-calDay);

                    records.add(rec1);

                    log.info(info.getUserid()+",未签到，算力减1");
                }
            }
            userDiginfoService.updateCalculForceBatch(userIds, -calDay);
            digcalRecordService.insertBatch(records);
        }

    }

    @Override
    public void orderCalculForceCheck(List<User> users, int forceMinus) {
        if(users!=null && !users.isEmpty()){
            List<Integer> userIds = new ArrayList<Integer>();
            List<DigcalRecord> records = new ArrayList<>();
            Timestamp startTime = TimeStampUtils.getSomeDayTime(-1);//昨天零点
            Timestamp endTime = TimeStampUtils.getSomeDayTime(0);//今天零点
            for(User user:users){
				/*如果昨天没有进行交易*/
                if(!digcalRecordService.existCalcalForceDay(user.getId(), CalculForceType.ORDER.getCode(),startTime,endTime)){
                    //增加的交易算力
                    int addTotal = digcalRecordService.getTotalCalculForceByUserAndType(user.getId(), CalculForceType.ORDER.getCode());
                    //减少的交易算力
                    int minusTotal = digcalRecordService.getTotalCalculForceByUserAndType(user.getId(), CalculForceType.NOT_ORDER.getCode());

					/*总数大于中断一天的算力，则减少用户交易算力*/
                    if((addTotal+minusTotal)>=forceMinus){
                        UserDiginfo diginfo = userDiginfoService.queryByUserId(user.getId());
                        userIds.add(diginfo.getUserid());

						/*添加算力记录*/
                        DigcalRecord rec = new DigcalRecord();
                        rec.setUserid(diginfo.getUserid());
                        rec.setType(CalculForceType.NOT_ORDER.getCode());
                        rec.setName(CalculForceType.NOT_ORDER.getName());
                        rec.setDigcalcul(-forceMinus);
                        rec.setAllcalculforce(diginfo.getDigcalcul()-forceMinus);
                        records.add(rec);
                        log.info(diginfo.getUserid()+",交易中断，算力减25");
                    }
                }
            }

            userDiginfoService.updateCalculForceBatch(userIds, -forceMinus);
            digcalRecordService.insertBatch(records);
        }
    }
}
