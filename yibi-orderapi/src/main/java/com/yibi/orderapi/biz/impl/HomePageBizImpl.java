package com.yibi.orderapi.biz.impl;

import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.Banner;
import com.yibi.core.entity.Notice;
import com.yibi.core.entity.User;
import com.yibi.core.service.CoinScaleService;
import com.yibi.core.service.SysparamsService;
import com.yibi.orderapi.biz.*;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
@Service
@Transactional
public class HomePageBizImpl implements HomePageBiz {

    @Autowired
    private BannerBiz bannerBiz;
    @Autowired
    private NoticeBiz noticeBiz;
    @Autowired
    private AccountBiz accountBiz;
    @Autowired
    private WalletBiz walletBiz;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private CoinScaleService coinScaleService;
    @Override
    public String initOut() {
        Map<String, Object> data = new HashMap<>();
        Map<Object, Object> map = new HashMap<>();
        map.put("bannertype", 0);
        map.put("state", 1);
        List<Banner> bannerList = bannerBiz.queryAllInfo(map);
        data.put("banner", bannerList);

        Notice notice = noticeBiz.queryInfoByIndex();
        data.put("notice", notice);
        data.put("noticeUrl", sysparamsService.getValStringByKey(SystemParams.SYSTEM_URL) + "/web/article/" +  notice.getId() + ".action");

        return Result.toResult(ResultCode.SUCCESS, data);
    }

    @Override
    public String initIn(User user) {
        Map<String, Object> data = new HashMap<>();
        Map<Object, Object> map = new HashMap<>();
        map.put("bannertype", 0);
        map.put("state", 1);
        List<Banner> bannerList = bannerBiz.queryAllInfo(map);
        data.put("banner", bannerList);

        Notice notice = noticeBiz.queryInfoByIndex();
        data.put("notice", notice);
        data.put("noticeUrl", sysparamsService.getValStringByKey(SystemParams.SYSTEM_URL) + "/web/article/" +  notice.getId() + ".action");

        BigDecimal soptAccount = accountBiz.queryByUser(user.getId(), GlobalParams.ACCOUNT_TYPE_SPOT);
        BigDecimal c2cAccount = accountBiz.queryByUser(user.getId(), GlobalParams.ACCOUNT_TYPE_C2C);
        BigDecimal leverAccount = accountBiz.queryByUser(user.getId(), GlobalParams.ACCOUNT_TYPE_LEVERAGE);
        BigDecimal yubiAccount = accountBiz.queryByUser(user.getId(), GlobalParams.ACCOUNT_TYPE_YUBI);
        data.put("soptAccount", BigDecimalUtils.toStringInZERO(soptAccount, 2));
        data.put("c2cAccount", BigDecimalUtils.toStringInZERO(c2cAccount, 2));
        data.put("leverAccount", BigDecimalUtils.toStringInZERO(leverAccount, 2));
        data.put("yubiAccount", BigDecimalUtils.toStringInZERO(yubiAccount, 2));
        data.put("total", BigDecimalUtils.toStringInZERO(soptAccount.add(c2cAccount).add(leverAccount).add(yubiAccount), 2));

        return Result.toResult(ResultCode.SUCCESS, data);
    }
}
