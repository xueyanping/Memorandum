package com.xue.yado.memorandum.myView;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.xue.yado.memorandum.Constant;
import com.xue.yado.memorandum.R;
import com.xue.yado.memorandum.activity.AddMemorandumActivity;
import com.xue.yado.memorandum.adapter.TypeListAdapter;
import com.xue.yado.memorandum.entity.Memoire;
import com.xue.yado.memorandum.utils.AppCache;
import com.xue.yado.memorandum.utils.PopupWindowBackground;
import com.xue.yado.memorandum.utils.ScreenUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/3.
 */

public class CustomPopupWindow extends PopupWindow implements View.OnClickListener {

    private AppCompatActivity context;

    private Button quit;

    private ListView listView;

    private TypeListAdapter adapter;
    private Memoire memoire;

    View mPopView;
    public CustomPopupWindow(AppCompatActivity context, Memoire memoire) {
        super(context);
        this.context = context;
        this.memoire = memoire;
        initData();
        initViews();
        setPopupWindow();
    }


    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
//        WindowManager windowManager = context.getWindowManager();
//        int width = windowManager.getDefaultDisplay().getWidth();
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可点击
        //this.setAnimationStyle(R.style.popwindow_style);// 设置动画
        ColorDrawable dw = new ColorDrawable(-00000);// 设置背景透明
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        this.setOutsideTouchable(false);

    }

//    @Override
//    public void showAtLocation(View parent, int gravity, int x, int y) {
//                WindowManager windowManager = context.getWindowManager();
//               int width = windowManager.getDefaultDisplay().getWidth();
//                x = width/2-this.getWidth()/2;
//        super.showAtLocation(parent, gravity, x, y);
   // }

    private void initData() {
        List<String> list = new ArrayList<>();
        list.add(Constant.TYPE_00);
        list.add(Constant.TYPE_01);
        list.add(Constant.TYPE_02);
        list.add(Constant.TYPE_03);
        list.add(Constant.TYPE_04);
        AppCache.setTypeList(list);
    }

    public void initViews(){
        mPopView = LayoutInflater.from(context).inflate(R.layout.type_dialog_layout,null,false);
        quit = mPopView.findViewById(R.id.quit);
        quit.setOnClickListener(this);
        listView = mPopView.findViewById(R.id.type_list);
        adapter = new TypeListAdapter(context,memoire);
        listView.setAdapter(adapter);
        adapter.setData(AppCache.getTypeList());
        //Log.i("getView", "CustomPopupWindow111 "+memoire.getType());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for(int a = 0;a < AppCache.getTypeList().size();a++){
                    if(a==i){
                        View childAt = adapterView.getChildAt(a);
                        ImageView imageView = childAt.findViewById(R.id.type_list_adapter_check);
                        imageView.setImageResource(R.mipmap.single_check_ed);
                        if(memoire!= null){
                            memoire.setType(AppCache.getTypeList().get(i));
                            Log.i("getView", "CustomPopupWindow "+memoire.getType());
                        }
                    }else{
                        View childAt = adapterView.getChildAt(a);
                        ImageView imageView = childAt.findViewById(R.id.type_list_adapter_check);
                        imageView.setImageResource(R.mipmap.single_check);
                    }
                }
            }
        });
    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.quit){
            this.dismiss();
            PopupWindowBackground.setBackgroundAlpha(context,1.0f);
        }else{
            return;
        }
    }
}
