package com.example.fruitshop.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.fruitshop.Fragment.share;
import com.example.fruitshop.Fragment.shouYe;
import com.example.fruitshop.Fragment.users;
import com.example.fruitshop.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a26665203 on 2018/1/19 0019.
 */

public class MainUi extends AppCompatActivity {
    ViewPager viewPager;
    List<android.support.v4.app.Fragment> list=new ArrayList<>();
    MenuItem item;
    BottomNavigationView navigationItemView;
    String uid;
    String headIcon;
    String username;
    String password;
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.mainui);
        initView();



    }
    public String getUid(){
        return uid;
    }
    public String getHeadIcon(){return headIcon;}
    public String getUsername(){return  username;}
    public String getPassword(){return password;}


    private void initView() {
        uid=getIntent().getStringExtra("uid");
        headIcon=getIntent().getStringExtra("headIcon");
        username=getIntent().getStringExtra("username");
        password=getIntent().getStringExtra("password");
        System.out.println(uid);
        viewPager= (ViewPager) findViewById(R.id.viewpage);
        navigationItemView= (BottomNavigationView) findViewById(R.id.itemView);
        list.add(new shouYe());
        list.add(new share());
        list.add(new users());
        navigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.share:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.users:
                        viewPager.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(item!=null){
                    item.setChecked(false);
                }else
                    navigationItemView.getMenu().getItem(0).setChecked(false);
                item=navigationItemView.getMenu().getItem(position);
                item.setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}
