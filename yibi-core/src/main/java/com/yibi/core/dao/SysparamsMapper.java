package com.yibi.core.dao;

import com.yibi.core.entity.Sysparams;
import java.util.List;
import java.util.Map;

public interface SysparamsMapper {
    int insert(Sysparams record);

    int insertSelective(Sysparams record);

    int updateByPrimaryKey(Sysparams record);

    int updateByPrimaryKeySelective(Sysparams record);

    int deleteByPrimaryKey(Integer id);

    Sysparams selectByPrimaryKey(Integer id);
    List<Sysparams> selectAll(Map<Object, Object> param);

    List<Sysparams> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    Sysparams selectByKey(String key);

    List<Map<String,Object>> selectSystemParamByCondition(Map<Object,Object> map);

    int selectSystemParamCountByCondition(Map<Object,Object> map);

}