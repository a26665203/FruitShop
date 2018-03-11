package com.example.fruitshop.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.example.fruitshop.R;
import com.example.fruitshop.StaticClass.staticClass;
import com.example.fruitshop.ui.MainUi;
import com.example.fruitshop.utils.dingdanAdapter;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import static android.view.View.GONE;

/**
 * Created by a26665203 on 2018/1/29 0029.
 */

public class users extends android.support.v4.app.Fragment implements View.OnClickListener{
    ImageView usersTou;
    TextView userName;
    TextView introduce;
    String name;
    String password;
    TextView all;
    RecyclerView all1;
    TextView waitJudge;
    RecyclerView waitJudge1;
    TextView waitPay;
    RecyclerView waitPay1;
    TextView waitReceive;
    RecyclerView waitReceive1;
    JSONArray allding;
    JSONArray pay;
    JSONArray receive;
    JSONArray judge;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle){
        View v=inflater.inflate(R.layout.users,null);
        try {
            initView(v);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return v;
    }
    public void initView(final View v) throws InterruptedException {
        all=v.findViewById(R.id.all);
        all1=v.findViewById(R.id.all1);
        waitJudge=v.findViewById(R.id.waitjudge);
        waitJudge1=v.findViewById(R.id.waitjudge1);
        waitReceive=v.findViewById(R.id.waitreceive);
        waitReceive1=v.findViewById(R.id.waitreceive1);
        waitPay=v.findViewById(R.id.waitpay);
        waitPay1=v.findViewById(R.id.waitpay1);
        all.setOnClickListener(this);
        waitJudge.setOnClickListener(this);
        waitReceive.setOnClickListener(this);
        waitPay.setOnClickListener(this);
        new Thread(new Runnable() {
            @Override
            public void run() {

                Request request=new Request.Builder().url(staticClass.ALLDINGDAN+((MainUi)getActivity()).getUid()).build();
                Request request1=new Request.Builder().url(staticClass.WAITJUDGE+((MainUi)getActivity()).getUid()).build();
                Request request2=new Request.Builder().url(staticClass.WAITPAY+((MainUi)getActivity()).getUid()).build();
                Request request3=new Request.Builder().url(staticClass.WAITRECEIVE+((MainUi)getActivity()).getUid()).build();
                staticClass.client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        com.alibaba.fastjson.JSONObject object=JSON.parseObject(response.body().string());
                        allding=object.getJSONArray("result");


                    }
                });
                staticClass.client.newCall(request1).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        com.alibaba.fastjson.JSONObject object=JSON.parseObject(response.body().string());
                        judge=object.getJSONArray("result");


                    }
                });
                staticClass.client.newCall(request2).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        com.alibaba.fastjson.JSONObject object=JSON.parseObject(response.body().string());
                        pay=object.getJSONArray("result");
                    }
                });
                staticClass.client.newCall(request3).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        com.alibaba.fastjson.JSONObject object=JSON.parseObject(response.body().string());
                        receive=object.getJSONArray("result");

                    }
                });



            }
        }).start();
        while(receive==null||pay==null||judge==null||allding==null){};
        dingdanAdapter allw=new dingdanAdapter(getActivity(),allding);
        dingdanAdapter judgew=new dingdanAdapter(getActivity(),judge);
        dingdanAdapter payw=new dingdanAdapter(getActivity(),pay);
        dingdanAdapter receivew=new dingdanAdapter(getActivity(),receive);
        all1.setAdapter(allw);
        all1.setLayoutManager(new LinearLayoutManager(getActivity()));
        waitJudge1.setAdapter(judgew);
        waitJudge1.setLayoutManager(new LinearLayoutManager(getActivity()));
        waitPay1.setAdapter(payw);
        waitPay1.setLayoutManager(new LinearLayoutManager(getActivity()));
        waitReceive1.setAdapter(receivew);
        waitReceive1.setLayoutManager(new LinearLayoutManager(getActivity()));

        usersTou=v.findViewById(R.id.userstou);
        userName=v.findViewById(R.id.username);
        introduce=v.findViewById(R.id.addintroduce);
        Glide.with(v.getContext()).load(((MainUi)this.getActivity()).getHeadIcon()).into(usersTou);
        name=((MainUi)this.getActivity()).getUsername();
        password=((MainUi)this.getActivity()).getPassword();
        userName.setText(name);
        usersTou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.all:
                if(all1.getVisibility()==View.VISIBLE){
                    all1.setVisibility(GONE);
                }else{
                all1.setVisibility(View.VISIBLE);
                waitPay1.setVisibility(GONE);
                waitJudge1.setVisibility(GONE);
                waitReceive1.setVisibility(GONE);}
                break;
            case R.id.waitjudge:
                if(waitJudge1.getVisibility()==View.VISIBLE){
                    waitJudge1.setVisibility(GONE);
                }else{
                all1.setVisibility(GONE);
                waitPay1.setVisibility(GONE);
                waitJudge1.setVisibility(View.VISIBLE);
                waitReceive1.setVisibility(GONE);}
                break;
            case R.id.waitpay:
                if(waitPay1.getVisibility()== View.VISIBLE){
                    waitPay1.setVisibility(GONE);
                }else{
                all1.setVisibility(GONE);
                waitPay1.setVisibility(View.VISIBLE);
                waitJudge1.setVisibility(GONE);
                waitReceive1.setVisibility(GONE);}
                break;
            case R.id.waitreceive:
                if(waitReceive1.getVisibility()==View.VISIBLE){
                    waitReceive1.setVisibility(GONE);
                }else {
                all1.setVisibility(GONE);
                waitPay1.setVisibility(GONE);
                waitJudge1.setVisibility(GONE);
                waitReceive1.setVisibility(View.VISIBLE);}
                break;
            default:
                break;
        }
    }
}
