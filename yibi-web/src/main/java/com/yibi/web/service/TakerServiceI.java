package com.yibi.web.service;

import com.yibi.core.entity.OrderTaker;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface TakerServiceI {
    void cancelTake(Integer id, Integer operId);

    void confirmTaker(Integer id,Integer operId);

    Object makerListPage( Map<Object,Object> map);

}
