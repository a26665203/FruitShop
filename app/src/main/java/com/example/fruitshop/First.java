package com.example.fruitshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.fruitshop.StaticClass.staticClass;
import com.example.fruitshop.ui.MainUi;
import com.example.fruitshop.ui.Register;
import com.example.fruitshop.utils.Share;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by a26665203 on 2018/1/19 0019.
 */

public class First extends AppCompatActivity implements View.OnClickListener{
    private EditText password;
    private EditText name;
    private CheckBox rememeber;
    private Button register;
    private Button login;
    private boolean isRemember=false;
    private String uid="";
    private String headIcon="";
    private String usersname="";
    private String password1="";
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.denglu);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initView();

    }
    //初始化组件
    public void initView(){
        isRemember=Share.getBoolean(this,"remember",false);
        login= (Button) findViewById(R.id.login);
        register= (Button) findViewById(R.id.register);
        rememeber= (CheckBox) findViewById(R.id.remember);
        name= (EditText) findViewById(R.id.name);
        password= (EditText) findViewById(R.id.password);
        if(isRemember){
            name.setText(Share.getString(this,"name",""));
            password.setText(Share.getString(this,"password",""));
        }
        Share.clearAll(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        rememeber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rememeber.isChecked())
                    isRemember=true;
                else
                    isRemember=false;
            }
        });
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.login:
                if(isRemember){
                    Share.putString(this,"name",name.getText().toString());
                    Share.putString(this,"password",password.getText().toString());
                }
                Share.putBoolean(this,"remember",isRemember);
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       String uri=staticClass.LOGIN+"?Name="+name.getText().toString()+"&Password="+password.getText().toString();
                       Request request=new Request.Builder().url(uri).build();
                       staticClass.client.newCall(request).enqueue(new Callback() {
                           @Override
                           public void onFailure(Call call, IOException e) {

                           }

                           @Override
                           public void onResponse(Call call, Response response) throws IOException {
                               String res=response.body().string();
                               com.alibaba.fastjson.JSONObject object= JSON.parseObject(res);
                               String result=object.getString("reultCode");
                               com.alibaba.fastjson.JSONObject i=object.getJSONObject("result");
                               uid=i.getString("uID");;
                               headIcon=i.getString("headIcon");
                               usersname=i.getString("name");
                               password1=i.getString("password");
                               if(result.equals("fail")){

                                   runOnUiThread(new Runnable() {
                                       @Override
                                       public void run() {
                                           Toast.makeText(First.this,"账户名或密码不正确",Toast.LENGTH_SHORT).show();
                                           name.setText("");
                                           password.setText("");
                                       }
                                   });

                               }else
                               {
                                   runOnUiThread(new Runnable() {
                                       @Override
                                       public void run() {
                                           runOnUiThread(new Runnable() {
                                               @Override
                                               public void run() {
                                                   Toast.makeText(First.this,"登陆成功",Toast.LENGTH_SHORT).show();
                                               }
                                           });


                                       }
                                   });
                                   Intent k=new Intent(First.this,MainUi.class);
                                   k.putExtra("uid",uid);
                                   k.putExtra("headIcon",headIcon);
                                   k.putExtra("username",usersname);
                                   k.putExtra("password",password1);

                                   startActivity(k);
                               }


                           }
                       });
                   }
               }).start();
                break;
            case R.id.register:
                Intent intent=new Intent(this, Register.class);
                startActivity(intent);
                break;


        }
    }
}
