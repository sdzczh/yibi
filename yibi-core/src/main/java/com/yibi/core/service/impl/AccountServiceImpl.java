package com.yibi.core.service.impl;

import com.google.common.collect.Maps;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.dao.AccountMapper;
import com.yibi.core.dao.FlowMapper;
import com.yibi.core.entity.Account;
import com.yibi.core.entity.Flow;
import com.yibi.core.entity.User;
import com.yibi.core.exception.BanlanceNotEnoughException;
import com.yibi.core.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:账户 account
 *
 * @author: autogeneration
 * @date: 2018-07-09 18:26:09
 **/
@Slf4j
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountMapper accountMapper;

    @Resource
    private FlowMapper flowMapper;
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public int insert(Account record) {
        return this.accountMapper.insert(record);
    }

    @Override
    public int insertSelective(Account record) {
        return this.accountMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Account record) {
        return this.accountMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Account record) {
        return this.accountMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.accountMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Account selectByPrimaryKey(Integer id) {
        return this.accountMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Account> selectAll(Map<Object, Object> param) {
        return this.accountMapper.selectAll(param);
    }

    @Override
    public List<Account> selectPaging(Map<Object, Object> param) {
        return this.accountMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.accountMapper.selectCount(param);
    }

    @Override
    public void updateAccountAndInsertFlow(Integer userId, Integer accountType, Integer coinType,
                                           BigDecimal availIncrement, BigDecimal frozenIncrement, Integer operId, String operType, Integer relateId) throws BanlanceNotEnoughException {

        Map map = Maps.newHashMap();
        map.put("userid", userId);
        map.put("accounttype", accountType);
        map.put("cointype", coinType);
        List<Account> accountList = selectAll(map);

        Account account = new Account();
        account.setUserid(userId);
        account.setCointype(coinType);
        account.setAccounttype(accountType);
        account.setAvailbalance(availIncrement);
        account.setFrozenblance(frozenIncrement);

        int ut;
        if (accountList == null || accountList.isEmpty()) {
            if (availIncrement.compareTo(BigDecimal.ZERO) == -1 || frozenIncrement.compareTo(BigDecimal.ZERO) == -1) {
                log.info("账户余额不足，update account-->{}", account.toString());
                throw new BanlanceNotEnoughException("账户余额不足");
            }
            ut = insert(account);
        } else {
            ut = this.accountMapper.updateBalance(account);
        }

        if (ut != 1) {
            log.info("账户余额不足，update account-->{}", account.toString());
            throw new BanlanceNotEnoughException("账户余额不足");
        }

        if (availIncrement.compareTo(BigDecimal.ZERO) != 0) {
            Flow flow = new Flow();
            flow.setUserid(userId);
            flow.setAccounttype(accountType);
            flow.setCointype(coinType);
            flow.setOperid(operId);
            flow.setOpertype(operType);
            flow.setRelateid(relateId);
            flow.setAmount(availIncrement);
            flowMapper.insert(flow);
        }


    }

    @Override
    public List<Account> queryByUserIdAndAccountType(Integer id, Integer accountType) {
        Map map = Maps.newHashMap();
        map.put("userid", id);
        map.put("accounttype", accountType);
        List<Account> accountList = accountMapper.selectBySeque(map);
        return accountList;
    }



    @Override
    public Account queryByUserIdAndCoinTypeAndAccountType(Integer id, Integer coinType, Integer accountType) {
        Map map = Maps.newHashMap();
        map.put("userid", id);
        map.put("accounttype", accountType);
        map.put("cointype", coinType);
        List<Account> accountList = selectAll(map);
        return accountList == null || accountList.isEmpty() ? null : accountList.get(0);
    }

    @Override
    public BigDecimal queryAvailBalance(Integer userid, Integer coinType, Integer accountType) {
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("userid", userid);
        params.put("cointype", coinType);
        params.put("accounttype", accountType);
        List<Account> list = selectAll(params);
        if (list == null || list.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return list.get(0).getAvailbalance();
    }

    @Override
    public Account queryByUserIdAndCoinType(Integer id, Integer coinType) {
        Map map = Maps.newHashMap();
        map.put("userid",id);
        map.put("cointype",coinType);
        List<Account> accountList = selectAll(map);
        return accountList == null || accountList.isEmpty()?null:accountList.get(0);
    }

    @Override
    public List<Map<String, Object>> selectAccountOrPhone(Map<Object, Object> map) {
        return this.accountMapper.selectAccountOrPhone(map);
    }

    @Override
    public int selectAccountCount(Map<Object, Object> map) {
        return this.accountMapper.selectAccountCount(map);
    }

    @Override
    public Account getSpotAcountByUserAndCoinType(User user, Integer type) {
        Map<Object, Object> param = new HashMap<>();
        param.put("userid", user.getId());
        param.put("cointype", type);
        param.put("accounttype", GlobalParams.ACCOUNT_TYPE_SPOT);
        List<Account> account= this.selectAll(param);
        return account == null || account.isEmpty() ? null : account.get(0);
    }

    @Override
    public void addAccount(User user, BigDecimal amount, Integer coinType, int accountType) {
        Account acc = getSpotAcountByUserAndCoinType(user, coinType);
         if(acc == null) {
             Account ac = new Account();
             ac.setUserid(user.getId());
             ac.setFrozenblance(BigDecimal.ZERO);
             ac.setAccounttype(accountType);
             ac.setAvailbalance(amount);
             ac.setCointype(coinType);
             this.insertSelective(ac);
        }else{
            acc.setAvailbalance(acc.getAvailbalance().add(amount));
            this.updateByPrimaryKeySelective(acc);
        }
    }

    @Override
    public Account getAccountByUserAndCoinTypeAndAccount(Integer userid, Integer type, Integer accounttype) {
        Map<Object, Object> param = new HashMap<>();
        param.put("userid", userid);
        param.put("cointype", type);
        param.put("accounttype", accounttype);
        List<Account> account= this.selectAll(param);
        return account == null || account.isEmpty() ? null : account.get(0);
    }
    @Override
    public List<Account> queryByAvailBalance(Integer accountType,Integer coinType,BigDecimal minAvailBalance, Integer page, Integer rows) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("accountType",accountType);
        map.put("coinType",coinType);
        map.put("minAvailBalance",minAvailBalance);
        map.put("firstResult",page*rows);
        map.put("maxResult",rows);
        return this.accountMapper.queryByAvailBalance(map);
    }


}