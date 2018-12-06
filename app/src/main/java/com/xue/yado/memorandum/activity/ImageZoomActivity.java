package com.xue.yado.memorandum.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.xue.yado.memorandum.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/11/27.
 */

public class ImageZoomActivity extends BaseActivity {

    @BindView(R.id.image_zoom)
    ImageView image_zoom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.image_zoom_layout);
    }

    @Override
    public void initViews() {
        super.initViews();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        String path = bundle.getString("filePath");
        image_zoom.setImageBitmap(BitmapFactory.decodeFile(path));
    }
}
