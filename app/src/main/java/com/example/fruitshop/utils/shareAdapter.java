package com.example.fruitshop.utils;

import android.content.Context;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fruitshop.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by a26665203 on 2018/2/24 0024.
 */

public class shareAdapter extends RecyclerView.Adapter<shareAdapter.shareViewHolder> {
    com.alibaba.fastjson.JSONArray list;
    Context context;
    List<Integer> randomHeight;
    @Override
    public shareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.share2,parent,false);
        shareViewHolder shareViewHolder=new shareViewHolder(view);
        return shareViewHolder;
    }
    public shareAdapter(Context context,com.alibaba.fastjson.JSONArray list){
        this.context=context;
        this.list=list;
        randomHeight=new ArrayList<>();
        for(int k=0;k<list.size();k++){
            randomHeight.add((int)(500+Math.random()*300));
        }
    }

    @Override
    public void onBindViewHolder(shareViewHolder holder, int position) {
        com.alibaba.fastjson.JSONObject object=list.getJSONObject(position);
        String image=object.getString("image");
        String text=object.getString("text");
        Glide.with(context).load(image).into(holder.imageView);
        holder.textView.setText(text);
       ViewGroup.LayoutParams layoutParams=holder.imageView.getLayoutParams();
        layoutParams.height=randomHeight.get(position);
        holder.imageView.setLayoutParams(layoutParams);



    }

    class shareViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;

        public shareViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.sharephoto1);
            textView=itemView.findViewById(R.id.shareIntroduce);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
