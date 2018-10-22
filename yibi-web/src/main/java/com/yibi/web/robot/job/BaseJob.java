package com.yibi.web.robot.job;

import com.yibi.common.http.OkHttp;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.entity.RobotTask;
import com.yibi.core.service.BaseService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BaseJob {
    @Autowired
    private RedisTemplate<String, String> redis;
    @Autowired
    private BaseService baseService;
    /**
     * 获取最oi\kjhwsedcfhhyul'新的价格
     * @param task
     * @return
     * @return BigDecimal
     * @date 2018-6-26
     * @author lina
     */
    public BigDecimal getNewPrice(RobotTask task){
        /**
         *直接获取输入的价格
         */
        return task.getBaseprice();

//        Integer coinType = task.getCointype();
//        String url ="";
//        if(coinType == CoinType.BTC){
//            url = "https://www.okcoin.com/api/v1/ticker.do?symbol=btc_usd";
//        }else if(coinType == CoinType.LTC){
//            url = "https://www.okcoin.com/api/v1/ticker.do?symbol=ltc_usd";
//        }else if(coinType == CoinType.ETH){
//            url = "https://www.okcoin.com/api/v1/ticker.do?symbol=eth_usd";
//        }else if(coinType == CoinType.ETC){
//            url = "https://www.okcoin.com/api/v1/ticker.do?symbol=etc_usd";
//        }else{
//            //其它币种获取设置的基础价格
//            return task.getBaseprice();
//        }
//        /*获取OKCoin最新的价格*/
//        BigDecimal lastForUsdt = BigDecimal.ZERO;
//        String res = OkHttp.get(url, null);
//        if (StrUtils.isBlank(res)){//获取不到交易价格，返回null，则获取设置的基础价格
//            return task.getBaseprice();
//        }
//        if(!StringUtils.isEmpty(url)){
//            JSONObject jso = (JSONObject) JSON.parse(res);
//            JSONObject ticker;
//            try {
//                ticker = jso.getJSONObject("ticker");
//                String last = ticker.getString("last");
//                lastForUsdt = new BigDecimal(last);
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        System.out.println(task.getCointype()+",对美元价格："+lastForUsdt);
//        //转成人民币价格
////        BigDecimal lastForCny = BigDecimalUtils.multiply(lastForUsdt, getSpotLatestPrice(CoinType.USDT,CoinType.KN), CoinType.KN);
//        BigDecimal lastForCny = BigDecimalUtils.multiply(lastForUsdt,baseService.getUsdtPrice());//获取缓存中的usdt价格
//        System.out.println(task.getCointype()+",对人民币价格："+lastForCny);
//        return lastForCny;
    }

    /**
     * 获取现货最新的成交价
     * @param orderCoinType
     * @param unitCoinType
     * @return
     * @return BigDecimal
     * @date 2018-2-10
     * @author lina
     */
    public BigDecimal getSpotLatestPrice(Integer orderCoinType,Integer unitCoinType){
        return baseService.getSpotLatestPrice(orderCoinType,unitCoinType);
    }

    /**
     * 随机取一个double值
     * @param start
     * @param end
     * @param scale
     * @return
     * @return double
     * @date 2018-7-2
     * @author lina
     */
    public static double nextDouble(double start,double end,int scale){
        double randomDouble = 0;
        if(start>=0){
            randomDouble = RandomUtils.nextDouble(start, end);
        }else if(end<=0){
            randomDouble = -(RandomUtils.nextDouble(-end, -start));
        }else{
            boolean nonNegative = org.apache.commons.lang.math.RandomUtils.nextBoolean();//随机正数标志
            if(nonNegative){
                randomDouble =  RandomUtils.nextDouble(0, end);
            }else{
                randomDouble = -(RandomUtils.nextDouble(0, -start));
            }
        }

        return BigDecimalUtils.roundDown(BigDecimal.valueOf(randomDouble), scale).doubleValue();

    }

    public static String post(String url,String params,String sign,String token,String key){
        Map<String, String> body = new HashMap<String, String>();
        Map<String, String> header = null;
        body.put("params", params);
        if(!StrUtils.isBlank(key)){
            body.put("key", key);
        }
        if(!StrUtils.isBlank(sign)){
            body.put("sign", sign);


        }

        if(!StrUtils.isBlank(token)){
            header = new HashMap<String, String>();
            header.put("token", token);
        }

        try {
            return OkHttp.postForm(url, header, body);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
