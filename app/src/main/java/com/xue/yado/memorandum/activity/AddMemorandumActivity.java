package com.xue.yado.memorandum.activity;

import android.content.Intent;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.xue.yado.memorandum.Constant;
import com.xue.yado.memorandum.R;
import com.xue.yado.memorandum.utils.AppCache;
import com.xue.yado.memorandum.utils.TimerUtil;

import java.util.Date;

import butterknife.BindView;

public class AddMemorandumActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.new_memorandum_toobbar)
    android.support.v7.widget.Toolbar new_memorandum_toobbar;

    @BindView(R.id.add_biaoqian)
    ImageView add_biaoqian;

    @BindView(R.id.create_time)
    TextView create_time;

    @BindView(R.id.new_content)
    EditText new_content;

    @BindView(R.id.floatingActionsMenu)
    FloatingActionsMenu floatingActionsMenu;

    @BindView(R.id.tixing)
    FloatingActionButton tixing;

    @BindView(R.id.daiban)
    FloatingActionButton daiban;

    @BindView(R.id.paizhao)
    FloatingActionButton paizhao;

    @BindView(R.id.tupian)
    FloatingActionButton tupian;

    boolean isEmpty_search_text;
    int position;
    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.new_memorandum_fragment_layout);
    }

    @Override
    public void initViews() {
        super.initViews();
        initToolBar(new_memorandum_toobbar,getString(R.string.addNewMemorandum_toolbar),true,true, Constant.RIGHT_TYPE_1);
        Intent intent = getIntent();
        isEmpty_search_text = intent.getBooleanExtra("isEmpty_search_text",false);
        position = intent.getIntExtra("position",0);
        if(isEmpty_search_text == false){
            new_content.setText(AppCache.getSearchList().get(position).getContent());
            create_time.setText(AppCache.getSearchList().get(position).getLastModifyDate());
        }else{
            new_content.setText(AppCache.getMemireList().get(position).getContent());
            create_time.setText(AppCache.getMemireList().get(position).getLastModifyDate());
        }
    }

    @Override
    public void initEvent() {
        super.initEvent();
        create_time.setText(TimerUtil.getYear_Month_Day_Hour_Minute(new Date()));
        tixing.setOnClickListener(this);
        daiban.setOnClickListener(this);
        tupian.setOnClickListener(this);
        paizhao.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.tixing:
               Toast.makeText(this, "tixing", Toast.LENGTH_SHORT).show();
               break;
           case R.id.daiban:
               Toast.makeText(this, "daiban", Toast.LENGTH_SHORT).show();
               break;
           case R.id.tupian:
               Toast.makeText(this, "tupian", Toast.LENGTH_SHORT).show();
               break;
           case R.id.paizhao:
               Toast.makeText(this, "paizhao", Toast.LENGTH_SHORT).show();
               break;
       }
}
}
