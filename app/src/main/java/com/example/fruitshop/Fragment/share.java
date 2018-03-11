package com.example.fruitshop.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.alibaba.fastjson.JSON;
import com.example.fruitshop.R;
import com.example.fruitshop.StaticClass.staticClass;
import com.example.fruitshop.utils.shareAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by a26665203 on 2018/1/29 0029.
 */

public class share extends android.support.v4.app.Fragment {
    RecyclerView recyclerView;
    com.alibaba.fastjson.JSONArray list;
    shareAdapter sha;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle){
        View v=inflater.inflate(R.layout.share,null);
        try {
            initView(v);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return v;

    }
    public void initView( View view) throws InterruptedException {
        recyclerView=view.findViewById(R.id.sharerecycle);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request=new Request.Builder().url(staticClass.GETALLSHARE).build();
                try {
                    Response response=staticClass.client.newCall(request).execute();
                    com.alibaba.fastjson.JSONObject object= JSON.parseObject(response.body().string());
                    list=object.getJSONArray("result");

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        while(list==null){};
        sha=new shareAdapter(this.getActivity(),list);
        if(sha!=null){
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setAdapter(sha);

        }



    }
}
