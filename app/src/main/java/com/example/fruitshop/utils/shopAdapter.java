package com.example.fruitshop.utils;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fruitshop.Fragment.shouYe;
import com.example.fruitshop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by a26665203 on 2018/1/27 0027.
 */

public class shopAdapter extends RecyclerView.Adapter<shopAdapter.MyViewHolder>{
    shouYe u;
    com.alibaba.fastjson.JSONArray shop;
    OnItemClickListener click;
    public shopAdapter(shouYe i, com.alibaba.fastjson.JSONArray k){
        this.u=i;
        this.shop=k;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(u.getActivity()).inflate(R.layout.item,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(v);
        return viewHolder;
    }
    public interface OnItemClickListener{
        void onClick(int k);

    }
    public void setItemClickListener(OnItemClickListener u){
        this.click=u;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

            holder.name.setText(shop.getJSONObject(position).getString("name"));
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click.onClick(position);
                }
            });
            holder.introduce.setText(shop.getJSONObject(position).getString("introduce"));
            holder.introduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click.onClick(position);
                }
            });
            Glide.with(u.getActivity()).load(shop.getJSONObject(position).getString("img")).into(holder.imageView);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click.onClick(position);
                }
            });



    }

    @Override
    public int getItemCount() {
        return shop.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name;
        TextView introduce;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iam);
            name=itemView.findViewById(R.id.shopname);
            introduce=itemView.findViewById(R.id.introduce);
        }
    }
}
