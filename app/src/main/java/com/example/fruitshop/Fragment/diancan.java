package com.example.fruitshop.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.fruitshop.R;
import com.example.fruitshop.StaticClass.staticClass;
import com.example.fruitshop.ui.DtealActvity;
import com.example.fruitshop.ui.addAddress;
import com.example.fruitshop.utils.MyHandler;
import com.example.fruitshop.utils.addressAdapter;
import com.example.fruitshop.utils.thingAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by a26665203 on 2018/2/5 0005.
 */

public class diancan extends Fragment {
    String uid;
    RecyclerView recyclerView;
    thingAdapter thingAdapter;
    PopupWindow popupWindow;
    String address;
    RecyclerView recyclerView1;
    TextView price;
    View v;
    double k;
    Button buy;
    Activity activity;
    diancan wo;
    WindowManager windowManager;
    addressAdapter adapter;
    com.alibaba.fastjson.JSONArray list;
@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle){
    View i=inflater.inflate(R.layout.diancan,null);
    wo=this;
    v=this.getActivity().getLayoutInflater().inflate(R.layout.alladdress,null);
    try {
        initView(i);
    } catch (JSONException e) {
        e.printStackTrace();
    }
    return i;
}
    public void initView(View view) throws JSONException {
        uid=((DtealActvity)this.getActivity()).getUid();
        activity=this.getActivity();
        windowManager=this.getActivity().getWindowManager();
        View i=LayoutInflater.from(getActivity()).inflate(R.layout.popup2,null);
        thingAdapter=new thingAdapter(((DtealActvity)this.getActivity()).getShoplist(),this);
        thingAdapter.setOnclickListener(new thingAdapter.onClick() {
            @Override
            public void onClick(TextView view) {
                k=Double.valueOf(price.getText().toString())+Double.valueOf(view.getText().toString());
                String l=String.valueOf(k);
                price.setText(l);
                if(k>=10){
                    buy.setBackgroundColor(Color.BLUE);
                    buy.setText("下单");
                    buy.setClickable(true);
                    buy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow=new PopupWindow(v, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                            recyclerView1=v.findViewById(R.id.alladdress);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String addw=staticClass.ADDRESSGET+uid;
                                    Request request=new Request.Builder().url(addw).build();
                                    try {
                                        Response  response=staticClass.client.newCall(request).execute();
                                        String body=response.body().string();
                                        System.out.println("wiii"+body);
                                        com.alibaba.fastjson.JSONObject object=JSON.parseObject(body);
                                        com.alibaba.fastjson.JSONArray list1=object.getJSONArray("result");;
                                        list=list1;
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }).start();
                            while(list==null){};
                            try {

                                adapter=new addressAdapter(list,view.getContext());
                                adapter.setOnItemClickListener(new addressAdapter.onItemClickListener() {
                                    @Override
                                    public void onClick(int position, List<Boolean> ischecked,int w) {
                                        ischecked.set(position,true);
                                        ischecked.set(w,false);
                                        adapter.notifyDataSetChanged();


                                    }
                                });
                                recyclerView1.setAdapter(adapter);
                                recyclerView1.setLayoutManager(new LinearLayoutManager(view.getContext()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Button add=v.findViewById(R.id.adds);
                            add.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent=new Intent(view.getContext(),addAddress.class);
                                    intent.putExtra("uid",uid);
                                    startActivity(intent);

                                }
                            });
                            Button sure=v.findViewById(R.id.sure);
                            sure.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    popupWindow.dismiss();
                                    Toast.makeText(view.getContext(),"下单成功",Toast.LENGTH_SHORT).show();

                                }
                            });
                            Button cancel=v.findViewById(R.id.cancel2);
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    popupWindow.dismiss();
                                }
                            });
                            DisplayMetrics i=new DisplayMetrics();
                            windowManager.getDefaultDisplay().getMetrics(i);
                            int width=i.widthPixels/2;
                            int height=i.heightPixels/2;
                            popupWindow.showAtLocation(view, Gravity.CENTER,width,height);


                        }
                    });
                }
            }
        });
        recyclerView=view.findViewById(R.id.list_1);
        recyclerView.setAdapter(thingAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        price=view.findViewById(R.id.price);
        price.setText("0");
        buy=view.findViewById(R.id.buy);

    }}

