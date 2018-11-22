package com.xue.yado.memorandum;

import android.app.Application;

import com.xue.yado.memorandum.utils.AppCache;

import org.litepal.LitePal;

/**
 * Created by Administrator on 2018/11/7.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCache.init(this);
        LitePal.initialize(this);
    }
}
