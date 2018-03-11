package com.example.fruitshop.ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.RenderProcessGoneDetail;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.fruitshop.R;
import com.example.fruitshop.StaticClass.staticClass;
import com.example.fruitshop.utils.Share;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by a26665203 on 2018/1/21 0021.
 */

public class Register extends AppCompatActivity implements View.OnClickListener{
    final int CHOOSE_PIC=1;
    ImageView touxiang;
    EditText name;
    EditText password;
    EditText rePasswrod;
    Button registe;
    Button fanhui;
    File head;

    @Override
    public void onCreate(Bundle onSave){
        super.onCreate(onSave);
        setContentView(R.layout.register);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initView();
    }
    public void initView(){
        touxiang= (ImageView) findViewById(R.id.touxiang);
        name= (EditText) findViewById(R.id.registname);
        password= (EditText) findViewById(R.id.registPassword);
        rePasswrod=(EditText)findViewById(R.id.registTwicePassword);
        registe= (Button) findViewById(R.id.register1);
        fanhui= (Button) findViewById(R.id.retur);
        touxiang.setOnClickListener(this);
        fanhui.setOnClickListener(this);
        registe.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.touxiang:
                View v=this.getLayoutInflater().inflate(R.layout.popup,null);
               final PopupWindow u=new PopupWindow(v, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                //获取屏幕宽和高
                WindowManager w=this.getWindowManager();
                DisplayMetrics p=new DisplayMetrics();
                w.getDefaultDisplay().getMetrics(p);
                int width=p.widthPixels;
                int height=p.heightPixels;
                u.showAtLocation(view,Gravity.CENTER,width,height);
                v.findViewById(R.id.choose).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if(ContextCompat.checkSelfPermission(Register.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(Register.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);}
                        else
                        openAlbum();
                        u.dismiss();
                    }
                });
               v.findViewById(R.id.cancel) .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()){
                            case R.id.cancel:
                                u.dismiss();
                                break;
                        }
                    }
                });
                break;
            case R.id.register1:
                if(password.getText().toString().equals("")||rePasswrod.getText().toString().equals("")||name.getText().toString().equals("")){
                    Toast.makeText(this,"必须完善你的信息",Toast.LENGTH_SHORT).show();
                }
                //判断两次密码输入是否相同;
               else if(password.getText().toString().equals(rePasswrod.getText().toString())){
                   new Thread(new Runnable() {
                       @Override
                       public void run() {
                           MediaType type=MediaType.parse("image/png");
                           MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
                           builder.addFormDataPart("file",head.getName(),RequestBody.create(type,head));
                           builder.addFormDataPart("Name",name.getText().toString());
                           builder.addFormDataPart("Password",password.getText().toString());
                           MultipartBody body=builder.build();
                           Request d=new Request.Builder().post(body).url(staticClass.REGISTE).build();
                           staticClass.client.newCall(d).enqueue(new Callback() {
                               @Override
                               public void onFailure(Call call, IOException e) {
                                   System.out.println("失败");

                               }

                               @Override
                               public void onResponse(Call call, Response response) throws IOException {
                                   String res=response.body().string();
                                   System.out.println("成功"+res);
                                   com.alibaba.fastjson.JSONObject object=JSON.parseObject(res);
                                   String result=object.getString("result");
                                   if(result.equals("注册成！")){
                                      runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Toast.makeText(Register.this,"注册成功",Toast.LENGTH_SHORT).show();
                                              finish();
                                          }
                                      });

                                   }else{
                                       runOnUiThread(new Runnable() {
                                           @Override
                                           public void run() {
                                               Toast.makeText(Register.this,"账户已存在",Toast.LENGTH_SHORT).show();
                                               name.setText("");
                                               password.setText("");
                                               rePasswrod.setText("");
                                           }
                                       });
                                   }






                               }
                           });

                       }
                   }).start();


                }
                else
                {Toast.makeText(this, "两次输入的密码不相同，请重新输入", Toast.LENGTH_SHORT).show();
                password.setText("");
                    rePasswrod.setText("");
                }
                break;
            case R.id.retur:
                finish();
                break;
            default:
                break;
        }

    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch(requestCode){
            case CHOOSE_PIC:
                if(resultCode==RESULT_OK){
                    if(Build.VERSION.SDK_INT>=19){
                        after(data);
                    }
                    else
                        before(data);
                }
                break;
        }

    }

    private void before(Intent data) {
        Uri uri=data.getData();
        String path=null;
        path=getImagePath(uri,null);
        setImage(path);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    //SDK版本大于19
    private void after(Intent data) {
        Uri uri=data.getData();
        String imagePath = null;    //图片路径
        //判断是否是Document封装的uri
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docid=DocumentsContract.getDocumentId(uri);
            System.out.println("曹年末"+docid);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docid.split(":")[1];
                System.out.println("你妹"+Long.valueOf(id));
                String selection = MediaStore.Images.Media._ID+ "="+id;   //选择条件
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }
            else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                String id=docid.split(":")[1];
                System.out.println("你ye"+Long.valueOf(id));
                Uri newuri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(id));
                imagePath=getImagePath(newuri,null);
            }

        }
        else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath=getImagePath(uri,null);
        }
        else
            System.out.println("你妹");
        setImage(imagePath);
        }

    private String getImagePath(Uri uri, String selection) {
        Cursor cursor= getContentResolver().query(uri,null,selection,null,null);
        String path=null;
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
        }
        return path;

    }
    private void setImage(String path){
        head=new File(path);
        Bitmap bit= BitmapFactory.decodeFile(path);
        touxiang.setImageBitmap(bit);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissons,int[] grantResult){
        switch(requestCode){
            case 10:if(grantResult.length>0&&grantResult[0]==PackageManager.PERMISSION_GRANTED){
                openAlbum();
                }else{
                Toast.makeText(this,"permission denied", Toast.LENGTH_SHORT).show();
                }break;
            default:;
                }
        }

    private void openAlbum() {
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PIC);
    }
}

