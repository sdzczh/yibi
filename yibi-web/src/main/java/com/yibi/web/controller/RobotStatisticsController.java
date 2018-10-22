package com.yibi.web.controller;

import com.yibi.common.model.Grid;
import com.yibi.common.model.PageModel;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.RobotStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("robotStatistics")
public class RobotStatisticsController {
    @Autowired
    private RobotStatisticsService robotStatisticsService;
    @Autowired
    private CoinManageService coinManageService;
    @RequestMapping("robotStatisticsListPage")
   public String robotStatisticsListPage(HttpServletRequest request){
        request.setAttribute("coins",this.coinManageService.selectAll(null));
        return "order/robotStatistics/robotStatisticsListPage";
    }
    @RequestMapping("robotStatisticsList")
    @ResponseBody
    public Object robotStatisticsList(Integer page,Integer rows,Integer coinType){
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        map.put("maxResult",pageModel.getMaxResult());
        map.put("firstResult",pageModel.getFirstResult());
        if (coinType != null){
            map.put("coinType",coinType);
        }
        Grid grid = new Grid();
        grid.setRows(this.robotStatisticsService.selectRelationPaging(map));
        grid.setTotal(this.robotStatisticsService.selectRelationCount(map));
        return grid;
    }
}
