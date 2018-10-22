package com.yibi.web.service.impl;

import com.yibi.common.model.Json;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.entity.Robot;
import com.yibi.core.entity.RobotTask;
import com.yibi.core.entity.User;
import com.yibi.core.service.RobotService;
import com.yibi.core.service.RobotTaskService;
import com.yibi.core.service.UserService;
import com.yibi.web.robot.QuartzManager;
import com.yibi.web.service.RobotServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class RobotServiceImpl implements RobotServiceI {
    @Autowired
    private RobotService robotService;
    @Autowired
    private RobotTaskService robotTaskService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuartzManager quartzManager;

    @Override
    public Object robotList() {
        return  this.robotService.selectRobotOrUserName();
    }

    @Override
    public Object addRobot(Integer coinType, Integer operId) {
        Robot robot = new Robot();
        robot.setCointype(coinType);
        robot.setOnoff(0);
        robot.setState(1);
        robot.setOperid(operId);
        this.robotService.insertSelective(robot);
        for(int i= 0;i<4;i++){
            String namePre = coinType+"_"+i+"_";
            RobotTask task = new RobotTask();
            task.setCointype(coinType);
            task.setCountmax(0);
            task.setCountmin(0);
            task.setRobotid(robot.getId());
            task.setExcuteuserid(0);
            task.setType(i);
            task.setJobgroupname(namePre+"JoGroup");
            task.setTriggername(namePre+"Trigger");
            task.setTriggergroupname(namePre+"TriggerGroup");
            task.setJobname(namePre+"Job");
            task.setNumbermax(BigDecimal.ZERO);
            task.setNumbermin(BigDecimal.ZERO);
            task.setPriceradiomax(0d);
            task.setPriceradiomin(0d);
            task.setTimeinterval(0);
            task.setOperid(operId);
            this.robotTaskService.insertSelective(task);
        }
        Json json = new Json();
        json.setMsg("新增成功");
        json.setSuccess(true);
        return json;
    }

    @Override
    public Object operRobot(Integer id, Integer type, Integer operId) {
        Json json = new Json();
        json.setSuccess(true);
        Robot robot = this.robotService.selectByPrimaryKey(id);
        if (robot.getState() == 0){
            json.setMsg("当前机器人已经是删除的状态了，不能开启或者关闭");
            json.setSuccess(false);
            return  json;
        }
        if(robot.getOnoff() == type){
            json.setMsg("当前状态已经是你操作的状态了");
            json.setSuccess(false);
            return  json;
        }
        //删除或增加定时任务
        Map<Object,Object> params = new HashMap<>();
        params.put("robotid",robot.getId());
        List<RobotTask> tasks = this.robotTaskService.selectAll(params);
        for (RobotTask task :tasks){
            if(task.getOnoff() != type){
                if(type == 1){
                    quartzManager.addJob(task.getJobname(), task.getJobgroupname(), task.getTriggername(), task.getTriggergroupname(), task.getType(), task.getTimeinterval(),task.getStarttime(), task.getEndtime(),task.getId());
                }if(type==0){
                    quartzManager.removeJob(task.getJobname(), task.getJobgroupname(), task.getTriggername(), task.getTriggergroupname());
                }
            }
        }

        robot.setOperid(operId);
        robot.setOnoff(type);
        this.robotService.updateByPrimaryKeySelective(robot);
        Map<String,Object> map  = new HashMap<>();
        map.put("operId",operId);
        map.put("coinType",robot.getCointype());
        map.put("type",type);
        this.robotTaskService.updateTaskByCoinType(map);
        json.setMsg("修改成功");
        return  json;
    }

    @Override
    public Object deleteRobot(Integer id, Integer operId) {
        Json json = new Json();
        json.setSuccess(true);
        Robot robot = this.robotService.selectByPrimaryKey(id);
        robot.setOperid(operId);
        robot.setOnoff(0);
        robot.setState(0);//0：关闭状态
        this.robotService.updateByPrimaryKeySelective(robot);
        //删除所有定时任务
        Map<Object,Object> params = new HashMap<>();
        params.put("robotid",robot.getId());
        List<RobotTask> tasks = this.robotTaskService.selectAll(params);
        for (RobotTask task :tasks){
            if(task.getOnoff() == 1){
                //删除任务
                quartzManager.removeJob(task.getJobname(), task.getJobgroupname(), task.getTriggername(), task.getTriggergroupname());
            }
        }

        Map<String,Object> map  = new HashMap<>();
        map.put("operId",operId);
        map.put("coinType",robot.getCointype());
        map.put("type",0);
        this.robotTaskService.updateTaskByCoinType(map);
        json.setMsg("修改成功");
        return  json;
    }

    @Override
    public Object robotTaskList(Integer id) {
        Map<Object,Object> params = new HashMap<>();
        params.put("robotid",id);
        return this.robotTaskService.selectTaskByRobotId(params);
    }

    @Override
    public Object operTask(Integer id, Integer type, Integer operId) {
        Json json = new Json();
        json.setSuccess(true);
        RobotTask robotTask = this.robotTaskService.selectByPrimaryKey(id);
        //验证是否机器人删除了
        Robot robot = this.robotService.selectByPrimaryKey(robotTask.getRobotid());
        if (robot.getState() == 0){
            json.setMsg("当前机器人是删除状态，不能修改了");
            json.setSuccess(false);
            return  json;
        }
        if(robotTask.getOnoff() == type){
            json.setMsg("当前状态已经是你操作的状态了");
            json.setSuccess(false);
            return  json;
        }
        if(robotTask.getOnoff() != type){
            if(type == 1){
                quartzManager.addJob(robotTask.getJobname(), robotTask.getJobgroupname(), robotTask.getTriggername(), robotTask.getTriggergroupname(), robotTask.getType(), robotTask.getTimeinterval(),robotTask.getStarttime(), robotTask.getEndtime(),robotTask.getId());
            }if(type==0){
                quartzManager.removeJob(robotTask.getJobname(), robotTask.getJobgroupname(), robotTask.getTriggername(), robotTask.getTriggergroupname());
            }
        }
        robotTask.setOnoff(type);
        robotTask.setOperid(operId);
        this.robotTaskService.updateByPrimaryKeySelective(robotTask);
        json.setSuccess(true);
        json.setMsg("修改任务状态成功");
        return json;
    }

    @Override
    public Object updateTask(RobotTask robotTask, String phone, String startTime, String endTime) {
        Json json = new Json();
        json.setSuccess(true);
        RobotTask robotTask1 = this.robotTaskService.selectByPrimaryKey(robotTask.getId());
        if(!StrUtils.isBlank(phone)){
            Map<Object,Object> map = new HashMap<>();
            map.put("phone",phone);
            User user = this.userService.selectAll(map).get(0);
            robotTask.setExcuteuserid(user.getId());
        }
        if (!StrUtils.isBlank(startTime)){
            robotTask.setStarttime(Timestamp.valueOf(startTime));
        }else {
            robotTask.setStarttime(robotTask1.getStarttime());
        }
        if (!StrUtils.isBlank(endTime)){
            robotTask.setEndtime(Timestamp.valueOf(endTime));
        }else {
            robotTask.setEndtime(robotTask1.getEndtime());
        }
        //修改定时任务
        if(robotTask.getOnoff()==1){
            quartzManager.modifyJobTime(robotTask.getJobname(), robotTask.getJobgroupname(),
                    robotTask.getTriggername(), robotTask.getTriggergroupname(), robotTask.getTimeinterval(), robotTask.getStarttime(), robotTask.getEndtime());
        }
        this.robotTaskService.updateByPrimaryKeySelective(robotTask);
        json.setMsg("跟新成功");
        return json;
    }

    @Override
    public RobotTask loadForm(Integer id) {
        return this.robotTaskService.selectByPrimaryKey(id);
    }

    @Override
    public void updateTaskPage(HttpServletRequest request) {
        Map<Object,Object> map = new HashMap<>();
        map.put("role",GlobalParams.ROLE_TYPE_ROBOT);
        request.setAttribute("users",userService.selectAll(map));
    }
}
