package com.xue.yado.memorandum.utils;

import android.content.Context;

import com.xue.yado.memorandum.MyApplication;
import com.xue.yado.memorandum.entity.Memoire;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/11/7.
 */

public class AppCache {

    private  Context mContext;
    private List<Memoire> memireList = new ArrayList();
    private List<Memoire> searchList = new ArrayList();
    private List<String> typeList = new ArrayList();

    public AppCache() {}

    private static class SingleTon{
        private static AppCache appCache = new AppCache();
    }

    public static AppCache getInstance(){
        return SingleTon.appCache;
    }

    public void onInit(MyApplication myApplication){
        mContext = myApplication.getApplicationContext();
        ScreenUtils.init(mContext);
        dbUtils.createDatabase();

    }

    public static void init(MyApplication myApplication) {
        getInstance().onInit(myApplication);
    }

    public static void setMemireList(List<Memoire> list){
        getInstance().memireList.clear();
        getInstance().memireList .addAll(list);
    }

    public static List<Memoire> getMemireList(){
        return getInstance().memireList;
    }

    public static void setSearchList(List<Memoire> list){
        getInstance().searchList = list;
    }

    public static List<Memoire> getSearchList(){
        return getInstance().searchList;
    }


    public static void setTypeList(List<String> list){
        getInstance().typeList.clear();
        getInstance().typeList.addAll(list);
    }

    public static List<String> getTypeList(){
        return getInstance().typeList;
    }
    public static void addTypeList(String type){
         getInstance().typeList.add(type);
    }
}
