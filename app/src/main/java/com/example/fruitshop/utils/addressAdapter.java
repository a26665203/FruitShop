package com.example.fruitshop.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.fruitshop.Fragment.diancan;
import com.example.fruitshop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a26665203 on 2018/2/22 0022.
 */

public class addressAdapter extends RecyclerView.Adapter<addressAdapter.addressViewHolder> {
    com.alibaba.fastjson.JSONArray list=new com.alibaba.fastjson.JSONArray();
    LayoutInflater layoutinflate;
    List<String> addressName=new ArrayList<>();
    int location;
    onItemClickListener listener;

    List<Boolean> ischecked=new ArrayList<>(); //记录哪个被选
    public addressAdapter(com.alibaba.fastjson.JSONArray list, Context context) throws JSONException {
        this.list=list;
        addressName.add("广东海洋大学");
        addressName.add("广东海洋大学寸金学院");
        addressName.add("岭南师范学院");
        addressName.add("广东医科大学");
        addressName.add("后坛村");
        for(int k=0;k<list.size();k++){
            String string=list.getJSONObject(k).getString("default");
            if(string.equals("true")){
                ischecked.add(true);
                location=k;
            }else ischecked.add(false);
        }
        layoutinflate=LayoutInflater.from(context);
    }
    public interface onItemClickListener{
        void onClick(int position,List<Boolean> isChecked,int location);
    }
    public void setOnItemClickListener(onItemClickListener listener){
        this.listener=listener;
    }



    @Override
    public addressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=layoutinflate.inflate(R.layout.addresss,parent,false);
        addressViewHolder addressViewHolder=new addressViewHolder(v);
        return addressViewHolder;
    }

    @Override
    public void onBindViewHolder(addressViewHolder holder, final int position) {
        holder.address.setText(addressName.get(position));
        holder.address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(position,ischecked,location);
                location=position;
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(position,ischecked,location);
                location=position;
            }
        });
        holder.checkBox.setChecked(ischecked.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class addressViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        CheckBox checkBox;
        public addressViewHolder(View itemView) {
            super(itemView);
            address=itemView.findViewById(R.id.address);
            checkBox=itemView.findViewById(R.id.choose);
        }
    }
}
