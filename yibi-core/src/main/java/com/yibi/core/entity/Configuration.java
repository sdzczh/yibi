package com.yibi.core.entity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;




/**
 * Created by xuqingzhong on 2018/1/16 0016.
 * 配置文件
 */
@Data
public class Configuration implements Serializable{
	
	private static final long serialVersionUID = -4328261889873040163L;
	private int versionCode;    /*版本号*/
    private Map<Integer, String> paymentType;    /*支付提现方式*/
    private Map<Integer, String> transferType;   /*转账方式*/
    private Map<Integer, String> coinTypeAndName;    /*币种及其名称对应*/ 
    private Map<Integer, String> coinTypeAndUrl;    /*币种及其对应图标地址*/ 
    private Map<Integer,List<Integer>> spotCoinPair;    /*现货顶部币币交易*/ 
    private Map<Integer,List<Integer>> spotQueryCoinPair;    /*现货顶部币币交易*/ 
    /**各种动态页面url*/
    private  String rechargeDocUrl ;   /*充值帮助文档*/
    private  String withdrawDocUrl ;   /*提现帮助文档*/
    private  String agreenmentUrl;     /*注册协议*/
    private  String c2cHelpDocUrl;     /*帮助文档*/
    private  String rateDocUrl;     /*费率文档*/
    private  String notLoggedShareUrl;     /*未登录分享地址*/
    private  String loggedShareUrl;     /*分享地址*/
    private  String inviteUrl;     /*邀请返佣*/
    private  String calculateInstructionUrl;     /*魂力提升*/
    private  String yubibaoHelpUrl;     /*余币宝帮助文档*/
    private  String activityUrl;     /*活动URL*/
    private  String indexUrl;     /*官网*/
    private  String dealDigDocUrl; //交易挖矿URL
    private  int[] transCoinType ;/*转账*/
    private  List<Integer> orderCount ;/*交易档位*/
    private String shareTitle;/*分享标题*/
    private String shareDes;/*分享描述*/
    
    private List<Integer> c2cCoin;/*c2c交易币种*/
    private List<Integer> rechAndWithCoin;/*充值提现币种*/
    private Map<Integer, CoinManageModel> coinInfo;
    private String maxCancelOfMaker;   /*商家最高取消次数*/
    private String maxCancelOfTaker;   /*普通用户最高取消次数*/
    
    private  List<Integer> digCoinType ;/*挖矿币种*/
    private boolean httpsFlag;
    private Map<String, Object> honorList;//称号集合
    private String guidesUrl;//挖矿秘籍
    private String mineInfoUrl;//矿区介绍
    private List<Integer> redPacketCoin;/*红包币种*/
    private List<Integer> talkTransferCoin;/*红包币种*/
    private List<Integer> yubiCoin;/*余币币种*/
    private String coinIntroUrl ;//币种介绍
    private Map<String,Object> yibiElve;//一币精灵
  


}
