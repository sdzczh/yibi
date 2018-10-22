package com.yibi.web.controller;

import com.yibi.common.model.Grid;
import com.yibi.common.model.Json;
import com.yibi.common.model.PageModel;
import com.yibi.common.model.SessionInfo;
import com.yibi.core.entity.Bank;
import com.yibi.core.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("bank")
public class BankController {
    @Autowired
    private BankService bankService;

    @RequestMapping("bankListPage")
    public String bankListPage(){
        return "fiance/bank/banlListPage";
    }
    @RequestMapping("banlList")
    @ResponseBody
    public Object banlList(Integer page,Integer rows){
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        map.put("maxResult",pageModel.getMaxResult());
        map.put("firstResult",pageModel.getFirstResult());
        Grid grid = new Grid();
        grid.setRows(this.bankService.selectRelationPaging(map));
        grid.setTotal(this.bankService.selectRelationCount(map));
        return grid;
    }

    @RequestMapping("addBankPage")
    public String addBankPage(){
        return "finance/bank/addBankPage";
    }
    @RequestMapping("addBank")
    @ResponseBody
    public Object addBank(Bank bank, HttpSession session){
        Json json = new Json();
        json.setSuccess(true);
        bank.setOperid(((SessionInfo)session.getAttribute("loginUser")).getUserid());
        int result = this.bankService.insertSelective(bank);
        if (result != 0){
            json.setMsg("新增银行卡成功");
            return json;
        }
        json.setMsg("新增失败 不 " +
                "");
        return json;
    }
}
