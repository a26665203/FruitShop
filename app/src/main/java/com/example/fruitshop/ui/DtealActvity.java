package com.example.fruitshop.ui;

import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.example.fruitshop.Fragment.diancan;
import com.example.fruitshop.Fragment.pingjia;
import com.example.fruitshop.Fragment.shangjia;
import com.example.fruitshop.R;
import com.example.fruitshop.StaticClass.staticClass;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by a26665203 on 2018/1/31 0031.
 */
/*
商家商品列表界面Activity
 */
public class DtealActvity extends AppCompatActivity {
    String id;
    String uid;
    com.alibaba.fastjson.JSONArray shoplist;
    List<String> title=new ArrayList<>();
    List<Fragment> page=new ArrayList<>();
    TabLayout tabLayout;
    ViewPager viewPager;
    String name;
    String img;
    String introduce;
    Toolbar toolbar;
    TextView shopname;
    TextView shopintroduce;
    ImageView imageView;
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        //去标题
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.detal1);
        initView();






    }
    public void initView(){
        Intent i=getIntent();
        name=i.getStringExtra("name");
        img=i.getStringExtra("touxiang");
        id=i.getStringExtra("id");
        uid=i.getStringExtra("uid");
        final String u= staticClass.SHOPGET+id;
        System.out.println(u);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request=new Request.Builder().url(u).build();
                try {
                    Response response=staticClass.client.newCall(request).execute();

                    JSONObject object= JSON.parseObject(response.body().string());
                    shoplist=object.getJSONArray("result");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tabLayout= (TabLayout) findViewById(R.id.tablayout);
        title.add("点餐");
        title.add("评价");
        title.add("商家");
        page.add(new diancan());
        page.add(new pingjia());
        page.add(new shangjia());
        viewPager= (ViewPager) findViewById(R.id.viewpage1);
        toolbar= (Toolbar) findViewById(R.id.myToolbar);
        shopintroduce= (TextView) findViewById(R.id.shopIntroduce);
        shopname= (TextView) findViewById(R.id.shopNames);
        imageView= (ImageView) findViewById(R.id.shopimage);
        System.out.println(img);
        introduce=i.getStringExtra("introduce");
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){

        }
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        ActionBar w=getSupportActionBar();
        //显示返回按钮
        w.setDisplayHomeAsUpEnabled(true);
        //使返回按钮可点击
        w.setDefaultDisplayHomeAsUpEnabled(true);
        shopname.setText(name);
        shopintroduce.setText(introduce);
        Glide.with(this).load(img).into(imageView);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return page.get(position);
            }

            @Override
            public int getCount() {
                return page.size();
            }
            @Override
            public CharSequence getPageTitle(int position){
                return title.get(position);
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }
    public void setId(String id){
        this.id=id;
    }
    public String getId(String id){
        return  id;
    }
    public com.alibaba.fastjson.JSONArray getShoplist(){
        return shoplist;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return false;
            default:
               return super.onOptionsItemSelected(item);
        }
    }
    public String getUid(){
        return uid;
    }


}
