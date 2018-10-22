package com.yibi.web.wallet;

/**
 * @author: lqh
 * @description:
 * @program: coinApi
 * @create: 2018-07-12 17:11
 **/
public class RpcObject {
    public String rpcuser;
    public String rpcpassword;
    public String rpcurl;
    public String rpcport;
    public String walletpassphrase;

    public RpcObject(String rpcuser, String rpcpassword, String rpcurl, String rpcport, String walletpassphrase) {
        this.rpcuser = rpcuser;
        this.rpcpassword = rpcpassword;
        this.rpcurl = rpcurl;
        this.rpcport = rpcport;
        this.walletpassphrase = walletpassphrase;
    }
}
