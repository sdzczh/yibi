package com.yibi.orderapi.biz.impl;

import com.alibaba.fastjson.JSON;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import com.yibi.orderapi.biz.SystemBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
@Service
@Transactional
public class SystemBizImpl implements SystemBiz{
    @Autowired
    private AppVersionService appVersionService;
    @Autowired
    private AboutInfoService aboutInfoService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private CoinScaleService coinScaleService;
    @Autowired
    private DigHonorsService digHonorsService;
    @Autowired
    private PosterService posterService;
    @Autowired
    private UserService userService;
    @Override
    public String checkUpdate(Integer syetemType,Integer version) {
        Map<String, Object> map = new HashMap<String, Object>();
		/*版本更新信息*/
        List<AppVersion> vers = appVersionService.getByVersionAndType(version, syetemType);
        if(vers==null||vers.isEmpty()){
            map.put("updateFlag", false);
        }else{
            AppVersion listVersion = vers.get(0);
            map.put("updateFlag", true);
            map.put("updateType", getUpdateType(vers));
            map.put("updateVersion", listVersion.getAppversion());
            map.put("apkUrl", listVersion.getAddress());
            map.put("apkSize", listVersion.getSize());
            map.put("apkHash", listVersion.getVerificate());
            map.put("content", listVersion.getContent());
        }

        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String aboutInfo() {
        Map<Object, Object> map = new HashMap<>();
        Map<Object, Object> data = new HashMap<>();
        Map<Object, Object> param = new HashMap<>();
        List<AboutInfo> list = aboutInfoService.selectAll(map);
        if(list == null || list.isEmpty() || list.size() == 0){
            return null;
        }
        for(AboutInfo ai : list){
            param.put(ai.getType(), ai);
        }
        data.put("list", param);
        return Result.toResult(ResultCode.SUCCESS, data);
    }

    @Override
    public String getStartupParam(Integer versionCode) {
        String url = "";
        Sysparams noPra = sysparamsService.getValByKey(SystemParams.SYSTEM_URL);
        if(noPra!=null){
            url = noPra.getKeyval();
        }
        Sysparams paraVer = sysparamsService.getValByKey(SystemParams.APP_CONFIG_VERSION);
        Integer currVersionCode = paraVer ==null?0:Integer.parseInt(paraVer.getKeyval());

        if(versionCode!=null&&versionCode>=currVersionCode){
            return Result.toResult(ResultCode.UPDATE_DATA_NOT_EXIST);
        }
        Configuration config = new Configuration();
        config.setSpotCoinPair(JSON.parseObject(getParam(SystemParams.APP_CONFIG_SPOTCOIN_PAIR), Map.class));

        config.setSpotQueryCoinPair( JSON.parseObject(getParam(SystemParams.APP_CONFIG_SPOTQUERY_COINPAIR), Map.class));

        config.setAgreenmentUrl(getParam(SystemParams.APP_CONFIG_AGREENMENT_URL));//注册协议
        config.setNotLoggedShareUrl(getParam(SystemParams.APP_CONFIG_NOTLOGGED_SHARE_URL));//未登录分享
        config.setLoggedShareUrl(getParam(SystemParams.APP_CONFIG_LOGGED_SHARE_URL));//登录分享
        config.setInviteUrl(getParam(SystemParams.APP_CONFIG_INVITE_URL));//邀请url
        config.setRechargeDocUrl(getParam(SystemParams.APP_CONFIG_RECHARGEDOC_URL));//充值帮助
        config.setWithdrawDocUrl(getParam(SystemParams.APP_CONFIG_WITHDRAWDOC_URL));//提现帮助
        config.setGuidesUrl(getParam(SystemParams.APP_CONFIG_GUIDES_URL));//挖矿秘籍url
        config.setMineInfoUrl(getParam(SystemParams.APP_CONFIG_MIMEINFO_URL));//矿区介绍url
        config.setDealDigDocUrl(getParam(SystemParams.APP_CONFIG_DEALDIGDOC_URL));//交易挖矿URL
        config.setRateDocUrl(getParam(SystemParams.APP_CONFIG_RATEDETAILS_URL));//费率文档
        config.setCalculateInstructionUrl(getParam(SystemParams.CALCULATE_FORCE_INSTRUCTION_URL));//魂力提升url
        config.setIndexUrl(getParam(SystemParams.NET_INDEX_URL));//一币官网
        config.setActivityUrl(getParam(SystemParams.ACTIVITY_URL));//活动URL
        config.setYubibaoHelpUrl(getParam(SystemParams.APP_CONFIG_YUBIBAO_HELP_DOC));//余币宝帮助url
        config.setCoinIntroUrl(url+"/web/coin/intro.action?coinType=");//币种介绍地址

        config.setOrderCount(JSON.parseArray(getParam(SystemParams.APP_CONFIG_ORDERCOUNT), Integer.class));
        config.setShareTitle(getParam(SystemParams.APP_CONFIG_SHARE_TITLE));
        config.setShareDes(getParam(SystemParams.APP_CONFIG_SHARE_DES));
        config.setC2cCoin(JSON.parseArray(getParam(SystemParams.APP_CONFIG_C2CCOIN), Integer.class));
        config.setHttpsFlag(Boolean.parseBoolean(getParam(SystemParams.APP_CONFIG_HTTPS_FLAG)));
        config.setVersionCode(currVersionCode);
        config.setMaxCancelOfMaker(getParam(SystemParams.ORDER_C2C_CANCEL_LIMIT_MAKER));
        config.setMaxCancelOfTaker(getParam(SystemParams.ORDER_C2C_CANCEL_LIMIT_TAKER));
        config.setRedPacketCoin(JSON.parseArray(getParam(SystemParams.APP_CONFIG_REC_PACKET_COIN), Integer.class));
        config.setTalkTransferCoin(JSON.parseArray(getParam(SystemParams.APP_CONFIG_TALK_TRANSFER_COIN), Integer.class));
        config.setYubiCoin(JSON.parseArray(getParam(SystemParams.APP_CONFIG_YUBIBAO_COIN), Integer.class));
		/*币种信息*/
        List<Integer> digCoinType = new ArrayList<>();
        Map<Integer, CoinManageModel> coinInfo = new HashMap<Integer, CoinManageModel>();
        Map map = new HashMap<>();
        List<CoinManageModel> list = coinManageService.queryAllByConfig(map);
        Integer coinType = null;
        for(CoinManageModel mag :list){
            coinType = mag.getCointype();
            mag.setMinC2cTransNum(BigDecimalUtils.toStringInZERO(mag.getMinC2cTransAmt(), 8));
            mag.setWithdrawNum(BigDecimalUtils.toStringInZERO(mag.getMinwithdrawNum(), 8));
            coinInfo.put(coinType, mag);
            CoinScale cs = coinScaleService.queryByCoin(coinType, -1);
        }
        CoinManageModel all = new CoinManageModel();
        all.setCointype(-1);
        all.setCoinname("全部");
        coinInfo.put(-1, all);
        config.setCoinInfo(coinInfo);

		/*挖矿币种*/
        config.setDigCoinType(digCoinType);
		/*称号矿区信息 author:LDZ 2018-04-24*/
        Map<String, Object> honorListMap = new HashMap<String, Object>();
        List<DigHonors> HonorMineList = digHonorsService.selectAll(map);
        for (DigHonors digHonors : HonorMineList) {
            List<Integer> listCoin = new ArrayList<Integer>();
            for (String coin : digHonors.getCointype().split(",")) {
                if(!StrUtils.isBlank(coin)){
                     listCoin.add(Integer.parseInt(coin));
                }
            }
            digHonors.setMineCoin(listCoin);
            if(digHonors.getRolegrade() != null){
                honorListMap.put(digHonors.getRolegrade()+"", digHonors);
            }
        }
        config.setHonorList(honorListMap);

        //一币精灵
        User elveUser = userService.getByRole(GlobalParams.ROLE_TYPE_YIBIELVE);
        Map elveMap = new HashMap<>();
        if(elveUser !=null){
            elveMap.put("phone",elveUser.getPhone());
            elveMap.put("name",elveUser.getNickname());
            elveMap.put("headPath",elveUser.getHeadpath());
            map.put("yibiElve",elveMap);
        }
        config.setYibiElve(elveMap);

        return Result.toResult(ResultCode.SUCCESS, config);
    }

    @Override
    public String getPoster(User user) {
        Map<Object, Object> param = new HashMap<>();
        Map map = new HashMap();
        List<Poster> list = posterService.selectAll(map);
        for(Poster poster : list){
            poster.setShareUrl(sysparamsService.getValStringByKey(SystemParams.APP_CONFIG_LOGGED_SHARE_URL) + user.getPhone());
        }
        param.put("list", list);
        return Result.toResult(ResultCode.SUCCESS, param);
    }


    /**
     * 获取更新等级最高的值
     * @param list
     * @return Integer
     * @date 2017-12-28
     * @author lina
     */
    public Integer getUpdateType(List<AppVersion> list){
        int type = 0;
        for(AppVersion ver:list){
            if(ver.getType()>type){
                type = ver.getType();
            }
        }
        return type;
    }

    public String getParam(String key){
        Sysparams param = sysparamsService.getValByKey(key);
        if(param ==null){
            return "";
        }else{
            return param.getKeyval();
        }
    }
}
