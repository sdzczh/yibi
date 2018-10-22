package com.yibi.core.service;

import com.yibi.core.entity.Bank;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:开户行表 bank
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:09
 **/ 
public interface BankService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int insert(Bank record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int insertSelective(Bank record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int updateByPrimaryKey(Bank record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int updateByPrimaryKeySelective(Bank record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    Bank selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    List<Bank> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    List<Bank> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 后台关联查询银行卡
     * @param param
     * @return
     */
    List<Map<String,Object>> selectRelationPaging(Map<Object, Object> param);

    /**
     * 后台关联统计银行卡记录
     * @param param
     * @return
     */
    int selectRelationCount(Map<Object, Object> param);
}