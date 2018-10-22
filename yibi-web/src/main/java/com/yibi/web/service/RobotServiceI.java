package com.yibi.web.service;

import com.yibi.core.entity.RobotTask;

import javax.servlet.http.HttpServletRequest;

public interface RobotServiceI {
    Object robotList();

    Object addRobot(Integer coinType, Integer operId);


    Object operRobot(Integer id, Integer type, Integer operId);

    Object deleteRobot(Integer id, Integer operId);

    Object robotTaskList(Integer id);

    Object operTask(Integer id, Integer type, Integer operId);

    Object updateTask(RobotTask robotTask, String phone, String startTime, String endTime);

    RobotTask loadForm(Integer id);

    public  void updateTaskPage(HttpServletRequest request);

}
