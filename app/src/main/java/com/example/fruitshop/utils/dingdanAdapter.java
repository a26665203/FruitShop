package com.example.fruitshop.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.example.fruitshop.R;

import java.util.List;

/**
 * Created by a26665203 on 2018/3/7 0007.
 */

public class dingdanAdapter extends RecyclerView.Adapter<dingdanAdapter.danViewHolder> {
    Context context;
    JSONArray list;
    @Override
    public danViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.dingdan,null);
        danViewHolder viewHolder=new danViewHolder(view);
        return viewHolder;
    }
    public dingdanAdapter(Context context, JSONArray list){
        this.context=context;
        this.list=list;
    }

    @Override
    public void onBindViewHolder(danViewHolder holder, int position) {
        holder.textView.setText(list.getJSONObject(position).getString("num"));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class danViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public danViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.alldingdan);
        }
    }
}
