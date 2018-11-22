package com.xue.yado.memorandum.activity;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.xue.yado.memorandum.R;

import butterknife.BindView;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.floatingActionsMenu)
    FloatingActionsMenu floatingActionsMenu;

    @BindView(R.id.paizhao)
    FloatingActionButton paizhao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floatactionbutton_menu_layout);
    }


}
