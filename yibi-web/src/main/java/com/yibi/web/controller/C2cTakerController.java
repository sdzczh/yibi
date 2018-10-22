package com.yibi.web.controller;

import com.yibi.common.model.Json;
import com.yibi.common.model.PageModel;
import com.yibi.common.model.SessionInfo;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.service.CoinManageService;
import com.yibi.web.service.TakerServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("order")
public class C2cTakerController {
    @Autowired
    private TakerServiceI takerServiceI;
    @Autowired
    private CoinManageService coinManageService;
    @RequestMapping("takerListPage")
    public String makerListPage(HttpServletRequest request){
        request.setAttribute("coins",this.coinManageService.selectAll(null));
        return "order/takerListPage";
    }

    @RequestMapping("getTakerList")
    @ResponseBody
    public Object getTakerList(Integer page,Integer rows,Integer coinType,Integer type,Integer state,String phone,String orderNum,String flagNum){
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        if (coinType != null){
            map.put("coinType",coinType);
        }
        if (type != null){
            map.put("type",type);
        }
        if (state != null){
            map.put("state",state);
        }
        if (!StrUtils.isBlank(phone)){
            map.put("phone",phone);
        }
        if (!StrUtils.isBlank(flagNum)){
            map.put("flagNum",flagNum);
        }
        if (!StrUtils.isBlank(orderNum)){
            map.put("orderNum",orderNum);
        }
        map.put("maxResult",pageModel.getMaxResult());
        map.put("firstResult",pageModel.getFirstResult());
        return this.takerServiceI.makerListPage(map);
    }

    @RequestMapping("cancelTaker")
    @ResponseBody
    public Object cancelTaker(Integer id, HttpSession session,Integer state){
        Json json = new Json();
        json.setSuccess(true);
        if (state == 0 || state == 2){
            takerServiceI.cancelTake(id,((SessionInfo)session.getAttribute("loginUser")).getUserid());
            json.setMsg("撤销成功");
            return json;
        }
        json.setMsg("除了代付款和冻结的才能撤销");
        return json;

    }
    @RequestMapping("confirmTaker")
    @ResponseBody
    public Object confirmTaker(Integer id, HttpSession session,Integer state){
        Json json = new Json();
        json.setSuccess(true);
        if (state == 0 || state == 2){
            takerServiceI.confirmTaker(id,((SessionInfo)session.getAttribute("loginUser")).getUserid());
            json.setMsg("确认成功");
            return json;

        }
        json.setMsg("除了代付款和冻结的才能确认");
        return json;
    }

}
