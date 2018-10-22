package com.yibi.web.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public interface AccountService {

    void walletListPage(HttpServletRequest request);

    Object getWalletList(Integer page, Integer rows, Integer coinType, Integer accountType, String phone, String userName);


    Object updateAccount(Integer id, BigDecimal amount, HttpSession session);
}
