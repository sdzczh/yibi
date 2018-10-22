package com.yibi.web.controller;

import com.yibi.common.model.SessionInfo;
import com.yibi.core.constants.CoinType;
import com.yibi.core.entity.RobotTask;
import com.yibi.core.service.BaseService;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.UserService;
import com.yibi.web.controller.base.BaseController;
import com.yibi.web.service.RobotServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("robot")
public class RobotController extends BaseController {
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private RobotServiceI robotServiceI;
    @Autowired
    private BaseService baseService;
    @Autowired
    private UserService userService;

    @RequestMapping("robotListPage")
    public String robotListPage(){
        return "/robot/robotListPage";
    }

    @RequestMapping("robotList")
    @ResponseBody
    public Object robotList(){
        return  this.robotServiceI.robotList();
    }
    @RequestMapping("addRobotPage")
    public String addRobotPage(HttpServletRequest request){
        request.setAttribute("coins",this.coinManageService.selectAll(null));
        return "/robot/addRobot";
    }
    @RequestMapping("addRobot")
    @ResponseBody
    public Object addRobot(Integer coinType, HttpSession session){
        Integer operId = ((SessionInfo)session.getAttribute("loginUser")).getUserid();
        return robotServiceI.addRobot(coinType,operId);
    }

    @RequestMapping("operRobot")
    @ResponseBody
    public Object operRobot(Integer id,Integer type,HttpSession session){
        Integer operId = ((SessionInfo)session.getAttribute("loginUser")).getUserid();
        return this.robotServiceI.operRobot(id,type,operId);
    }

    @RequestMapping("deleteRobot")
    @ResponseBody
    public Object deleteRobot(Integer id,HttpSession session){
        Integer operId = ((SessionInfo)session.getAttribute("loginUser")).getUserid();
        return this.robotServiceI.deleteRobot(id,operId);
    }

    @RequestMapping("robotTaskListPage")
    public String robotTaskListPage(Integer id,HttpServletRequest request){
        request.setAttribute("robotid",id);
        return "/robot/robotTaskListPage";
    }
    @RequestMapping("robotTaskList")
    @ResponseBody
    public Object robotTaskList(Integer id){
        return this.robotServiceI.robotTaskList(id);
    }
    @RequestMapping("operTask")
    @ResponseBody
    public Object operTask(Integer id,Integer type,HttpSession session){
        Integer operId = ((SessionInfo)session.getAttribute("loginUser")).getUserid();
        return this.robotServiceI.operTask(id,type,operId);
    }
    @RequestMapping("updateTaskPage")
    public  String updateTaskPage(Integer id,Integer coinType,HttpServletRequest request){
        request.setAttribute("taskId",id);
        request.setAttribute("latestPrice",baseService.getSpotLatestPrice(coinType, CoinType.KN));
        robotServiceI.updateTaskPage(request);
        RobotTask robotTask = this.robotServiceI.loadForm(id);
        if (robotTask.getType() == 2){//买入成交
            return "/robot/updateTask2";
        }
        return "/robot/updateTask";
    }
    @RequestMapping("updateTask")
    @ResponseBody
    public Object updateTask(RobotTask robotTask,String phone,String startTime,String endTime){
        return this.robotServiceI.updateTask(robotTask,phone,startTime,endTime);
    }
    @RequestMapping("loadForm")
    @ResponseBody
    public Object loadForm(Integer id){
        return this.robotServiceI.loadForm(id);
    }

    @RequestMapping("robotLog")
    public String robotLog(){
        return "/robot/robotLog";
    }

}
