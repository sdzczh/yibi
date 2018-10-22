package com.yibi.core.service;

import com.yibi.core.entity.DigRecord;

import java.util.List;

/**
 * Created by Administrator on 2018/7/19 0019.
 */
public interface DigRecordService {

    /**
     * 添加
     *
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/
    int insert(DigRecord record);

    /**
     * 添加
     *
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/
    int insertSelective(DigRecord record);

    /**
     * 更新
     *
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/
    int updateByPrimaryKey(DigRecord record);

    /**
     * 更新
     *
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/
    int updateByPrimaryKeySelective(DigRecord record);

    /**
     * 按用户和币种查询
     *
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/
    List<DigRecord> selectByUserIdAndType(Integer userId, Integer coinType);

    /**
     * 查询用户收矿记录
     * @param userId
     * @param page
     * @param rows
     * @return
     */
    List<?> queryByUser(Integer userId, Integer page, Integer rows);

}
