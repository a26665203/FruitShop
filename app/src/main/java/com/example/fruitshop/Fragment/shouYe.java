package com.example.fruitshop.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.fruitshop.R;
import com.example.fruitshop.StaticClass.staticClass;
import com.example.fruitshop.ui.DtealActvity;
import com.example.fruitshop.ui.MainUi;
import com.example.fruitshop.utils.shopAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by a26665203 on 2018/1/27 0027.
 */

public class shouYe extends android.support.v4.app.Fragment {
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    shouYe my;
    int i=0;
    com.alibaba.fastjson.JSONArray shop=new com.alibaba.fastjson.JSONArray();
    com.alibaba.fastjson.JSONArray all1=new com.alibaba.fastjson.JSONArray();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle){
        View v=inflater.inflate(R.layout.shouye,null);
        my=this;
        try {
            initView(v);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return v;

    }
    public synchronized void initView( View view) throws InterruptedException {
        if(i<1){
       Thread a =new Thread(new Runnable() {
            @Override
            public void run() {
                Request i=new Request.Builder().url(staticClass.SHOPMESSAGE).build();
                Response response= null; try {
                    response = staticClass.client.newCall(i).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String body= null;
                try {
                    body = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                com.alibaba.fastjson.JSONArray all=new com.alibaba.fastjson.JSONArray();
                System.out.println("你的"+body);
                        com.alibaba.fastjson.JSONObject w= JSON.parseObject(body);
                        String body1=w.getString("result");
                        System.out.println("你的sw"+body1);
                        all=JSON.parseArray(body1);
                        System.out.println("你的swwd"+all.toString());
                        for(int k=0;k<4;k++){

                            shop.add(all.getJSONObject(k));
                            System.out.println("ss："+shop.size());
                        };
                all1=all;


                    }
                });
        a.start();
        i++;
        }
        while(shop==null||all1==null){};
       recyclerView=view.findViewById(R.id.recycle);
        refreshLayout=view.findViewById(R.id.refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    ;System.out.println(shop);
        final shopAdapter w=new shopAdapter(this,shop);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int i=shop.size();
                if(i==all1.size()){
                    refreshLayout.setRefreshing(false);
                }
                else{
                    shop.add(all1.getJSONObject(i));
                    w.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);

                }
            }
        });
        w.setItemClickListener(new shopAdapter.OnItemClickListener() {
            @Override
            public void onClick(int k) {
                Intent i=new Intent(my.getActivity(), DtealActvity.class);
                i.putExtra("uid",((MainUi)my.getActivity()).getUid());
                i.putExtra("touxiang",all1.getJSONObject(k).getString("img"));
                i.putExtra("name",all1.getJSONObject(k).getString("name"));
                i.putExtra("introduce",all1.getJSONObject(k).getString("introduce"));
                i.putExtra("id",all1.getJSONObject(k).getString("iD"));
                startActivity(i);

            }

        });
        recyclerView.setAdapter(w);

    }

}

