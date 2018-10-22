
package com.yibi.batch.api;


import com.yibi.batch.util.RpcUtil;
import com.yibi.batch.util.ValidateUtil;
import com.yibi.common.utils.StrUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/*
* tokenAddress ： token地址
* tokenAddress ： 用于转账的 MethodId
* balanceMethodId ： 用于查询余额的 MethodId
* wei ： token最小单位
* */
public class EthTokenAPI extends API {

    private String tokenAddress;
    private String transMethodId;
    private String balanceMethodId;
    private BigDecimal wei;

    public EthTokenAPI( String rpcurl, String rpcport, String tokenAddress, String transMethodId, String balanceMethodId,BigDecimal wei) {
        super( rpcurl, rpcport);
        this.tokenAddress = tokenAddress;
        this.transMethodId = transMethodId;
        this.balanceMethodId = balanceMethodId;
        this.wei = wei;
    }


    @Override
    public BigDecimal getBalance(String address) {
        String paramsDatAddress = address.substring(address.indexOf("0x") + "0x".length());
        StringBuffer strTo64 = ValidateUtil.strTo64(paramsDatAddress);
        String data = balanceMethodId + strTo64.toString();
        Map<String, String> param = new HashMap(5);
        param.put("from", address);
        param.put("to", tokenAddress);
        param.put("data", data);
        param.put("value", "0x0");
        String resoponseBody = RpcUtil.generateRequest(this, "eth_call", param, "latest");
        return resoponseBody.equals("") ||resoponseBody.equals("0x")||resoponseBody.equals("0x0")? new BigDecimal(0) : super.unit16To10(resoponseBody);
    }

    @Override
    public String sendTransaction(String from, String to, String value) {
        String rpcResponse = "";
        try {
            //转账
            String data = this.transMethodId;
            data += ValidateUtil.strTo64(to.substring(to.indexOf("0x") + "0x".length()));
            data += ValidateUtil.strTo64(value.substring(value.indexOf("0x") + "0x".length()));
            Map<String, String> reqMap = new HashMap();
            reqMap.put("from", from);
            reqMap.put("to", tokenAddress);
            reqMap.put("data", data);
            rpcResponse = RpcUtil.generateRequest(this, "eth_sendTransaction", reqMap);
        } catch (Exception e) {
            rpcResponse = "";
            e.printStackTrace();
        }
        return rpcResponse;
    }


    public String[] resolveInput(String input){
        if(StrUtils.isBlank(input)  || input.length() != this.getBalanceMethodId().length()+64*2){
            return null;
        }
        String[] resolve = new String[2];
        //获取到代币的转出地址和金额
        String dataBody = input.substring(this.getBalanceMethodId().length());
        String address = ValidateUtil.hexSubstring(dataBody.substring(0,64));
        if(address.length() < 40){
            //补充0
            for(int i =0; i < (40-address.length()); i ++){
                address = "0"+address;
            }
        }
        resolve[0] ="0x"+ address;
        resolve[1] ="0x"+  ValidateUtil.hexSubstring(dataBody.substring(64));
        return resolve;
    }


    public String getTokenAddress() {
        return tokenAddress;
    }

    public EthTokenAPI setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
        return this;
    }

    public String getTransMethodId() {
        return transMethodId;
    }

    public EthTokenAPI setTransMethodId(String transMethodId) {
        this.transMethodId = transMethodId;
        return this;
    }

    public String getBalanceMethodId() {
        return balanceMethodId;
    }

    public EthTokenAPI setBalanceMethodId(String balanceMethodId) {
        this.balanceMethodId = balanceMethodId;
        return this;
    }

    public BigDecimal getWei() {
        return wei;
    }

    public EthTokenAPI setWei(BigDecimal wei) {
        this.wei = wei;
        return this;
    }
}
