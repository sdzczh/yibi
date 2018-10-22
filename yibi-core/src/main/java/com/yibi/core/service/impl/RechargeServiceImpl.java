package com.yibi.core.service.impl;

import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.dao.RechargeMapper;
import com.yibi.core.entity.CoinManage;
import com.yibi.core.entity.Recharge;
import com.yibi.core.entity.User;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.CoinScaleService;
import com.yibi.core.service.RechargeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:充值记录 recharge
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/
@Slf4j
@Service("rechargeService")
public class RechargeServiceImpl implements RechargeService {
    @Resource
    private RechargeMapper rechargeMapper;
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private CoinScaleService coinScaleService;
    @Autowired
    private RedisTemplate<String, String> redis;

    private static final Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);

    @Override
    public int insert(Recharge record) {
        return this.rechargeMapper.insert(record);
    }

    @Override
    public int insertSelective(Recharge record) {
        return this.rechargeMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Recharge record) {
        return this.rechargeMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Recharge record) {
        return this.rechargeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.rechargeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Recharge selectByPrimaryKey(Integer id) {
        return this.rechargeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Recharge> selectAll(Map<Object, Object> param) {
        return this.rechargeMapper.selectAll(param);
    }

    @Override
    public List<Recharge> selectPaging(Map<Object, Object> param) {
        return this.rechargeMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.rechargeMapper.selectCount(param);
    }

    @Override
    public Recharge getRechargeByTxid(String txid) {
        Map<Object, Object> param = new HashMap<>();
        param.put("address", txid);
        List<Recharge> recharge= this.rechargeMapper.selectAll(param);
        if(recharge == null || recharge.isEmpty() || recharge.size() == 0){
            return null;
        }
        return recharge.get(0);
    }

    @Override
    public Recharge rechargeApply(User user, BigDecimal amount, Integer coinType, String txid) {
        /*充值费率 */
        BigDecimal rate = getRechargeRate(coinType);
        log.info("费率："+rate);
        BigDecimal fee = BigDecimalUtils.multiply(amount, rate, coinScaleService.queryByCoin(coinType, -1).getCalculscale());
        log.info("手续费："+fee);
        BigDecimal remain = BigDecimalUtils.subtract(amount, fee, coinScaleService.queryByCoin(coinType, -1).getCalculscale());
        log.info("剩余金额："+remain);
        Recharge recharge = new Recharge();
        recharge.setUserid(user.getId());
        recharge.setAddress(txid);
        recharge.setAmount(BigDecimalUtils.round(amount, coinScaleService.queryByCoin(coinType, -1).getCalculscale()));
        recharge.setFee(fee);
        recharge.setRemain(remain);
        recharge.setCointype(coinType);
        recharge.setOrdernum("R"+user.getId()+System.currentTimeMillis());
        recharge.setState(1);//已支付
        insertSelective(recharge);
        return recharge;
    }
    @Override
    public List<Map<String, Object>> selectRechargeOrPhone(Map<Object, Object> map) {
        return this.rechargeMapper.selectRechargeOrPhone(map);
    }

    @Override
    public int selectCountRechargeOrPhone(Map<Object, Object> map) {
        return this.rechargeMapper.selectCountRechargeOrPhone(map);
    }



    /**
     * 获取充值费率
     * @param type
     * @return BigDecimal
     * @date 2017-12-29
     * @author lina
     */
    public BigDecimal getRechargeRate(Integer coinType){
        String keyName = String.format(RedisKey.C2C_MANAGE, coinType);
        CoinManage mag = coinManageService.queryByCoinType(coinType);
        String rate = mag.getRechspotrate().toString();
        return new BigDecimal(rate);
    }
}