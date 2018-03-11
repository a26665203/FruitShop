package com.example.fruitshop.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * Created by a26665203 on 2018/3/9 0009.
 */

public class MyHandler extends Handler {
    JSONArray list;
    public MyHandler(JSONArray list){
        this.list=list;
    }
    @Override
    public void handleMessage(Message msg){
        switch (msg.what){
            case 0:
                Bundle bundle=msg.getData();
                String body=bundle.getString("body");
                com.alibaba.fastjson.JSONObject object= JSON.parseObject(body);
                list=object.getJSONArray("result");
                System.out.println("caonima"+body);
                break;
            default:
                break;

        }
    }
}
