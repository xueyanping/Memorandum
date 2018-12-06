package com.xue.yado.memorandum.utils;

import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by Administrator on 2018/12/4.
 */

public class PopupWindowBackground {

    //PopupWindow的背景
    public static void setBackgroundAlpha(AppCompatActivity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        (activity).getWindow().setAttributes(lp);
    }



}
