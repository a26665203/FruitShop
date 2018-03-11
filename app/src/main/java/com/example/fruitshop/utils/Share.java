package com.example.fruitshop.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by a26665203 on 2018/1/19 0019.
 */
//封装sharePreference
public class Share {
    private final static String name="message";
    public static void putString(Context u,String key,String value){
        SharedPreferences.Editor w=u.getSharedPreferences(name,Context.MODE_PRIVATE).edit();
        w.putString(key,value);
        w.commit();
    }
    public static void putInt(Context u,String key,int value){
        SharedPreferences.Editor w=u.getSharedPreferences(name,Context.MODE_PRIVATE).edit();
        w.putInt(key,value);
        w.commit();
    }
    public static String getString(Context u,String key,String deafaul){
        SharedPreferences w=u.getSharedPreferences(name,Context.MODE_PRIVATE);
        return w.getString(key,deafaul);

    }
    public static int getInt(Context u,String key,int deafaul){
        SharedPreferences w=u.getSharedPreferences(name,Context.MODE_PRIVATE);
        return w.getInt(key,deafaul);

    }
    public static void delete(Context u,String key){
        SharedPreferences.Editor w=u.getSharedPreferences(name,Context.MODE_PRIVATE).edit();
        w.remove(key);
        w.commit();
    }
    public static boolean getBoolean(Context u,String key,boolean deafaul){
        SharedPreferences w=u.getSharedPreferences(name,Context.MODE_PRIVATE);
        return w.getBoolean(key,deafaul);

    }
    public static void putBoolean(Context u,String key,boolean k){
        SharedPreferences.Editor w=u.getSharedPreferences(name,Context.MODE_PRIVATE).edit();
        w.putBoolean(key,k);
        w.commit();
    }
    public static void clearAll(Context u){
        SharedPreferences.Editor w=u.getSharedPreferences(name,Context.MODE_PRIVATE).edit();
        w.clear();
        w.commit();
    }
}
