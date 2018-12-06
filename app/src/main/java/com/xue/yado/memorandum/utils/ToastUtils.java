package com.xue.yado.memorandum.utils;

import android.content.Context;
import android.widget.Toast;



/**
 * Created by Administrator on 2018/11/30.
 */

public class ToastUtils {

    public static void toast(Context context,String string){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }


}
