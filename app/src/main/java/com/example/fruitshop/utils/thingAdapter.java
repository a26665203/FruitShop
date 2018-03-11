package com.example.fruitshop.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fruitshop.Fragment.diancan;
import com.example.fruitshop.R;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by a26665203 on 2018/2/6 0006.
 */

public class thingAdapter extends RecyclerView.Adapter<thingAdapter.viewHolder> {
    com.alibaba.fastjson.JSONArray list;
    diancan dian;
    onClick click;
    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View o= LayoutInflater.from(dian.getActivity()).inflate(R.layout.thing,parent,false);
        viewHolder i=new viewHolder(o);
        return i;
    }
    public interface onClick{
        void onClick(TextView view);
    }
    public void setOnclickListener( onClick l){
        this.click=l;

    }

    @Override
    public void onBindViewHolder(final thingAdapter.viewHolder holder, int position) {
        holder.thingName.setText(list.getJSONObject(position).getString("name"));
        holder.thingprice.setText(list.getJSONObject(position).getString("price"));
        Glide.with(dian.getActivity()).load(list.getJSONObject(position).getString("img")).into(holder.image);
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onClick(holder.thingprice);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public thingAdapter(com.alibaba.fastjson.JSONArray list, diancan dian){
        this.list=list;
        this.dian=dian;
    }
    class viewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView thingName;
        TextView thingprice;
        ImageView add;
        public viewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.shopthing);
            thingName=itemView.findViewById(R.id.thingname);
            thingprice=itemView.findViewById(R.id.thingPrice);
            add=itemView.findViewById(R.id.add);
        }
    }
}
