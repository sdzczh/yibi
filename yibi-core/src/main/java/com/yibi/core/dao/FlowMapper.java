package com.yibi.core.dao;

import com.alibaba.druid.sql.dialect.mysql.ast.MysqlForeignKey;
import com.yibi.core.entity.Flow;
import java.util.List;
import java.util.Map;

public interface FlowMapper {
    int insert(Flow record);

    int insertSelective(Flow record);

    int updateByPrimaryKey(Flow record);

    int updateByPrimaryKeySelective(Flow record);

    int deleteByPrimaryKey(Integer id);

    Flow selectByPrimaryKey(Integer id);

    List<Flow> selectAll(Map<Object, Object> param);

    List<Flow> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<Flow> selectPagingForAccount(Map map);

    List<Map<String,Object>> selectFlowOrPhone(Map<Object,Object> map);
    int selectFlowCount(Map<Object,Object> map);
}