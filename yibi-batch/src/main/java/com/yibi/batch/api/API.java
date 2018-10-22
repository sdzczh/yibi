
package com.yibi.batch.api;


import com.yibi.batch.util.RpcObject;
import com.yibi.batch.util.RpcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;


public class API extends RpcObject {
    private static Logger logger = LoggerFactory.getLogger(API.class);

    public static Long GAS = 90000l;

    public static BigDecimal WEI = new BigDecimal("1000000000000000000");

    public API(String rpcurl, String rpcport) {
        super("", "", rpcurl, rpcport, "");
    }

    public boolean unlockAccount(String account, String pwd) {
        String unlockAccountRes = RpcUtil.generateRequest(this, "personal_unlockAccount", account, pwd);
//        logger.info(unlockAccountRes);
        return unlockAccountRes.equals("") ? false : true;
    }

    /**
     * 新建账户
     *
     * @param pwd : 密码
     * @return
     */
    public String newAccount(String pwd) {
//        logger.info(pwd);
        String returnAnswer = RpcUtil.generateRequest(this, "personal_newAccount", pwd);
//        logger.info(returnAnswer);
        return returnAnswer;
    }


    public String sendTransaction(String from, String to, String value) {
        Map<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("from", from);
        reqMap.put("to", to);
        reqMap.put("value", value);
        reqMap.put("gas", API.unit10To16(new BigDecimal(GAS)));
        reqMap.put("gasPrice", eth_gasPrice());
        String returnAnswer = RpcUtil.generateRequest(this, "eth_sendTransaction", reqMap);
//        logger.info(returnAnswer);
        return returnAnswer;
    }


    public BigDecimal getBalance(String address) {
        String returnAnswer = RpcUtil.generateRequest(this, "eth_getBalance", address, "latest");
        return returnAnswer.equals("") ? new BigDecimal(0) : API.unit16To10(returnAnswer);
    }



    public String eth_gasPrice() {
        String res = RpcUtil.generateRequest(this, "eth_gasPrice");
        return res;
    }

    public BigDecimal getGasAndGasPrice() {
        BigDecimal gas = new BigDecimal(GAS);
        BigDecimal gasPrice = new BigDecimal(0);
        String gasPriceStr = RpcUtil.generateRequest(this, "eth_gasPrice");
        if (gasPriceStr.equals("")) {
            gasPrice = new BigDecimal(18000000000l);
        } else {
            gasPriceStr = gasPriceStr.substring(gasPriceStr.indexOf("0x") + "0x".length());
            BigInteger transValue_16 = new BigInteger(gasPriceStr, 16);
            gasPrice = new BigDecimal(transValue_16.toString(10));
        }
        return gas.multiply(gasPrice);
    }


    public static BigDecimal unit16To10(String num_16) {
        num_16 = num_16.substring(num_16.indexOf("0x") + "0x".length());
        String bigInteger = new BigInteger(num_16, 16).toString(10);
        return new BigDecimal(bigInteger);
    }

    public static String unit10To16(BigDecimal num_10) {
        num_10 = num_10.setScale(0);
        String bigInteger = "0x" + new BigInteger(num_10.toString(), 10).toString(16);
        return bigInteger;
    }

    public BigDecimal getMinerFee() {
        BigDecimal gas = new BigDecimal(GAS);
        BigDecimal gasPrice = new BigDecimal(0);
        String gasPriceStr = RpcUtil.generateRequest(this, "eth_gasPrice");
        gasPrice = gasPriceStr.equals("") ? new BigDecimal(18000000000l) : API.unit16To10(gasPriceStr);
        return gas.multiply(gasPrice);
    }

    public BigDecimal eth_blockNumber() {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        String res = RpcUtil.generateRequest(this, "eth_blockNumber");
        if (!res.equals("")) {
            bigDecimal = API.unit16To10(res);
        }
        return bigDecimal;
    }

    public String eth_getBlockByNumber(BigDecimal blockNumber) {
        String res = RpcUtil.generateRequest(this, "eth_getBlockByNumber", API.unit10To16(blockNumber), true);
        return res;
    }
    public String eth_getTransactionByHash(String hash) {

        String res = RpcUtil.generateRequest(this, "eth_getTransactionByHash", hash);
        return res;
    }

    public String eth_syncing() {
        String res = RpcUtil.generateRequest(this, "eth_syncing");
        return res;
    }

    public static void main(String[] args) {

    }
}
