package com.xue.yado.memorandum.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.xue.yado.memorandum.R;
import com.xue.yado.memorandum.utils.PermissionReq;


import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/11/7.
 */

public class BaseActivity extends AppCompatActivity {

    private String[] PERMISSIONARRAY = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private boolean isShowRight;
    private int rightType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        ButterKnife.bind(this);
        PermissionReq.with(this).permissions(PERMISSIONARRAY).result(new PermissionReq.Result() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied() {
                Toast.makeText(BaseActivity.this, "permission is denied", Toast.LENGTH_SHORT).show();
            }
        }).request();


    }

    public void setContentView() {}

    public void initViews() {}
    public void initEvent(){}
    public void setData(){}

    
    public void setListener(){}

    public void initToolBar(Toolbar toolbar, String name, boolean showHomeAsUp) {
        initToolBar(toolbar, name, showHomeAsUp, false);
    }

    public void initToolBar(Toolbar toolbar, String name, boolean showHomeAsUp, boolean isShowRight) {
        initToolBar(toolbar, name, showHomeAsUp, isShowRight, 0);
    }

    public void initToolBar(Toolbar toolbar, String name, boolean showHomeAsUp, boolean isShowRight, int rightType) {
        this.isShowRight = isShowRight;
        this.rightType = rightType;
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem;
        if(isShowRight){
            getMenuInflater().inflate(R.menu.toolbar_save,menu);
            menuItem = menu.findItem(R.id.toolBar_save);
            switch (rightType){
                case 1:
                 menuItem.setTitle("save");
                    break;
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionReq.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
