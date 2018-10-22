package com.yibi.web.controller;

import com.yibi.web.controller.base.BaseController;
import com.yibi.web.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
@RequestMapping("user")
public class AccountController extends BaseController {
    @Autowired
    private AccountService accountService;
    @RequestMapping("walletListPage")
    public String walletListPage(HttpServletRequest request){
        accountService.walletListPage(request);
        return "user/walletManage/walletManageListPage";
    }

    @RequestMapping("getWalletList")
    @ResponseBody
    public Object getWalletList(Integer page,Integer rows,Integer coinType,Integer accountType,String phone,String userName){
        return accountService.getWalletList(page,rows,coinType,accountType,phone,userName);
    }

    @RequestMapping("updateAccountPage")
    public String updateAccountPage(HttpServletRequest request,Integer id){
        request.setAttribute("id",id);
        return "user/walletManage/updateAccount";
    }

    @RequestMapping("updateAccount")
    @ResponseBody
    public Object updateAccount(Integer id, BigDecimal amount, HttpSession session){
        return accountService.updateAccount(id,amount,session);
    }
}
