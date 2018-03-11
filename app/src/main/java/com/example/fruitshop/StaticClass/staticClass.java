package com.example.fruitshop.StaticClass;

import okhttp3.OkHttpClient;

/**
 * Created by a26665203 on 2018/1/24 0024.
 */

public class staticClass {
    public static final String REGISTE="http://120.78.87.169:8080/sxsc/api/registe";
    public static OkHttpClient client=new OkHttpClient();
    public static final String LOGIN="http://120.78.87.169:8080/sxsc/api/login";
    public static final String SHOPMESSAGE="http://120.78.87.169:8080/sxsc/api/getShopList";
    public static final String SHOPGET="http://120.78.87.169:8080/sxsc/api/getAllGoodsOfShop?SID=";
    public static final String ADDRESSGET="http://120.78.87.169:8080/sxsc/api/getUserAllReceivingAddress?UID=";
    public static final String ADDADDRESS="http://120.78.87.169:8080/sxsc/api/addUserReceivingAddress";
    public static final String GETALLSHARE="http://120.78.87.169:8080/sxsc/api/getAllShare";
    public static final String ALLDINGDAN="http://120.78.87.169:8080/sxsc/api/getAllOrder?UID=";
    public static final String WAITJUDGE="http://120.78.87.169:8080/sxsc/api/getWaitEvaluateOrder?UID=";
    public static final String WAITRECEIVE="http://120.78.87.169:8080/sxsc/api/getWaitReceivingOrder?UID=";
    public static final String WAITPAY="http://120.78.87.169:8080/sxsc/api/getWaitPayOrder?UID=";
}
