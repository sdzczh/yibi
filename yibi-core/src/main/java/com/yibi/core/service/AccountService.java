package com.yibi.core.service;

import com.yibi.core.entity.Account;
import com.yibi.core.entity.User;
import com.yibi.core.exception.BanlanceNotEnoughException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:账户 account
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:09
 **/ 
public interface AccountService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int insert(Account record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int insertSelective(Account record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int updateByPrimaryKey(Account record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int updateByPrimaryKeySelective(Account record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    Account selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    List<Account> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    List<Account> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int selectCount(Map<Object, Object> param);


    /**
     * 更新账户并保存流水
     * @param userId 用户id
     * @param accountType 账户类型
     * @param coinType 币种类型
     * @param availIncrement 可用余额增量
     * @param frozenIncrement 冻结余额增量
     * @param operId 操作人id
     * @param operType 操作类型
     * @param relateId 关联表id
     */
    void updateAccountAndInsertFlow(Integer userId, Integer accountType, Integer coinType,
                                    BigDecimal availIncrement, BigDecimal frozenIncrement, Integer operId, String operType, Integer relateId) throws BanlanceNotEnoughException;

    /**
     * 获取用户钱包信息
     * @param id
     * @param accountType 账户类型
     * @return
     */
    List<Account> queryByUserIdAndAccountType(Integer id, Integer accountType);

    /**
     * 获取用户钱包信息
     * @param id
     * @param accountType 账户类型
     * @param coinType 币种
     * @return
     */
    Account queryByUserIdAndCoinTypeAndAccountType(Integer id, Integer coinType, Integer accountType);

    /**
     * 获取某用户某账户某币种可用数量
     * @param userid
     * @param coinType
     * @param accountType
     * @return
     */
    BigDecimal queryAvailBalance(Integer userid, Integer coinType, Integer accountType);


    Account queryByUserIdAndCoinType(Integer id, Integer coinType);

    /**
     * 后台条件查询带有手机号信息的钱包
     * @param map
     * @return
     */
    List<Map<String,Object>> selectAccountOrPhone(Map<Object,Object> map);

    /**
     * 条件查询带有手机号信息的记录数
     * @param map
     * @return
     */
    int selectAccountCount(Map<Object,Object> map);


    /**
     * 根据币种查询现货余额
     * @param user
     * @param type
     * @return
     */
    Account getSpotAcountByUserAndCoinType(User user, Integer type);

    /**
     * 添加账户
     * @param user
     * @param amount 金额
     * @param coinType 币种
     * @param accountType 账户
     */
    void addAccount(User user, BigDecimal amount, Integer coinType, int accountType);


    /**
     * 根据用户 币种 账户查询钱包
     * @param userid
     * @param type 币种
     * @param accounttype 账户
     * @return
     */
    Account getAccountByUserAndCoinTypeAndAccount(Integer userid, Integer type, Integer accounttype);
    /**
     * 查询余额大于最小金额的账户信息
     * @param minAvailBalance
     * @param page
     * @param rows
     * @return
     */
    List<Account> queryByAvailBalance(Integer accountType,Integer coinType,BigDecimal minAvailBalance,Integer page,Integer rows);

}