package com.xue.yado.memorandum.executors;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.xue.yado.memorandum.R;
import com.xue.yado.memorandum.activity.MainActivity;
import com.xue.yado.memorandum.fragment.MemorandumFragment;

/**
 * Created by Administrator on 2018/11/19.
 */

public class NaviMenuExecutor {

    public static boolean onNavigationItemSelected(MenuItem item,Context context){
        switch (item.getItemId()){
            case R.id.my_navigation_0:

                break;
        }

        return false;
    }

}
