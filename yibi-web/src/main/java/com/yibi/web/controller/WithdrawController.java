package com.yibi.web.controller;

import com.yibi.core.service.*;
import com.yibi.web.controller.base.BaseController;
import com.yibi.web.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("finance")
public class WithdrawController extends BaseController {
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private WithdrawService withdrawService;

    @RequestMapping("withdrawListPage")
    public String withdrawListPage(HttpServletRequest request){
        request.setAttribute("coins",this.coinManageService.selectAll(null));
        return "finance/withdraw/withdrawListPage";
    }

    @RequestMapping("getWithdrawList")
    @ResponseBody
    public Object getWithdrawList(Integer accountType,Integer rows,Integer page,Integer coinType,Integer state,String phone,String username,String orderNum){
        return withdrawService.getWithdrawList(accountType,rows,page,coinType,state,phone,username,orderNum);
    }
    @RequestMapping("updateWithdrawState")
    @ResponseBody
    public Object updateWithdrawState(Integer id, Integer type, HttpSession session){
        return withdrawService.updateWithdrawState(id,type,session);
    }


}
