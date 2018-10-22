package com.yibi.web.robot;

import com.yibi.core.entity.RobotTask;
import com.yibi.core.service.RobotService;
import com.yibi.core.service.RobotTaskService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuartzJobListener implements ServletContextListener {
    private WebApplicationContext springContext;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /***处理获取数据库的job表，然后遍历循环每个加到job中 ***/
        springContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        if(springContext!=null){
            RobotTaskService robotTaskService = (RobotTaskService)springContext.getBean("robotTaskService");
            QuartzManager quartzManager = (QuartzManager)springContext.getBean("quartzManager");
            Map<Object,Object> map = new HashMap<>();
            map.put("onoff",1);
            List<RobotTask> list = robotTaskService.selectAll(map);

            for (RobotTask task : list) {
                try {
                    quartzManager.addJob(task.getJobname(), task.getJobgroupname(), task.getTriggername(),
                            task.getTriggergroupname(), task.getType(), task.getTimeinterval(), task.getStarttime(), task.getEndtime(), task.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("QuartzJobListener 启动了");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
