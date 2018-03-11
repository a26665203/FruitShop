package com.example.fruitshop.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fruitshop.R;

/**
 * Created by a26665203 on 2018/2/5 0005.
 */

public class pingjia extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle){
        View view=inflater.inflate(R.layout.pingjia,null);
        Button jude=view.findViewById(R.id.ping);
        jude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"评价成功",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
