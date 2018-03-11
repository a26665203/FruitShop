package com.example.fruitshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;

import com.example.fruitshop.R;
import com.example.fruitshop.StaticClass.staticClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by a26665203 on 2018/2/22 0022.
 */

public class addAddress extends AppCompatActivity {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    MapView mapView;
    String uid;
    AMap aMap;
    Button add;
    EditText input;
    MyLocationStyle myLocationStyle;
    @Override
    public void onCreate(Bundle onsave){
        super.onCreate(onsave);
        setContentView(R.layout.map);
        mapView= (MapView) findViewById(R.id.map);
        mapView.onCreate(onsave);
        initView();
    }
    public void initView(){
        if(aMap==null){
            aMap=mapView.getMap();
        }
        Intent intent=getIntent();
        uid=intent.getStringExtra("uid");
        System.out.println(uid);
        input= (EditText) findViewById(R.id.detaladdress);
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(2000);
        myLocationStyle.showMyLocation(true);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
        add= (Button) findViewById(R.id.addw);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject object=new JSONObject();
                        RequestBody requestBody=RequestBody.create(JSON,object.toString());
                        try {
                            object.put("UID",uid);
                            object.put("Name",input.getText().toString());
                            object.put("isDefault","false");
                            System.out.println(object.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Request request=new Request.Builder().url(staticClass.ADDADDRESS).post(requestBody).build();

                        staticClass.client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String i=response.body().string();
                                System.out.println(i);

                            }
                        });

                    }
                }).start();
            }
        });
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }
    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }
}
