package com.xue.yado.memorandum.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.xue.yado.memorandum.Constant;
import com.xue.yado.memorandum.R;

import com.xue.yado.memorandum.utils.ScreenUtils;
import com.xue.yado.memorandum.utils.TimerUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import id.zelory.compressor.Compressor;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

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

    private String path;

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.new_memorandum_fragment_layout);
    }

    @Override
    public void initViews() {
        super.initViews();
        initToolBar(new_memorandum_toobbar,getString(R.string.addNewMemorandum_toolbar),true,true, Constant.RIGHT_TYPE_1);
    }

    @Override
    public void initEvent() {
        super.initEvent();
        create_time.setText(TimerUtil.getYear_Month_Day_Hour_Minute(new Date()));
    }

    @Override
    public void setListener() {
        super.setListener();
        tixing.setOnClickListener(this);
        daiban.setOnClickListener(this);
        tupian.setOnClickListener(this);
        paizhao.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.tixing:
               break;
           case R.id.daiban:
               Toast.makeText(this, "daiban", Toast.LENGTH_SHORT).show();
               break;
           case R.id.tupian:
               Toast.makeText(this, "tupian", Toast.LENGTH_SHORT).show();
               break;
           case R.id.paizhao:
               //takePhote();
               setImageIntoEditText();
               setSpanClickable();
               break;
       }
}

    public void takePhote(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.path = Environment.getExternalStorageDirectory().getAbsolutePath();
        this.path = this.path+"/abc/"+System.currentTimeMillis();
        Log.i( "takePhote: ","path=="+this.path);
        File file = new File(this.path);
        file.getParentFile().mkdirs();
        Uri uri = FileProvider.getUriForFile(this,"com.xue.yado.memorandum.provider",file);
        Log.i( "takePhote: ","uri=="+uri);
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //指定图片的输出地址
        i.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(i,1);
    }



public void setImageIntoEditText(){
    String path = "/storage/emulated/0/DCIM/Camera/IMG_20181126_113150.jpg";
    Bitmap loadedImage = null;
    try {
         loadedImage = new Compressor(this)
                .setMaxWidth(500)
                .setMaxHeight(800)
                .setQuality(80)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .compressToBitmap(new File(path));
    } catch (IOException e) {
        e.printStackTrace();
    }
    Log.i( "setImageIntoEditText: ","loadedImage.getWidth()=="+loadedImage.getWidth());
                    Log.i( "setImageIntoEditText: ","loadedImage.getWidth()=="+loadedImage.getHeight());
                    ImageSpan imageSpan = new ImageSpan(AddMemorandumActivity.this, loadedImage);
                    SpannableString spannableString = new SpannableString(path);
                    spannableString.setSpan(imageSpan, 0, path.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    // 将选择的图片追加到EditText中光标所在位置
                    int index = new_content.getSelectionStart();
                    // 获取光标所在位置
                    Editable edit_text = new_content.getEditableText();
                    if (index < 0 || index >= edit_text.length()) {
                        edit_text.append(spannableString);
                    } else {
                        edit_text.insert(index, spannableString);
                    }
                    edit_text.append("\n");
}

    public void setSpanClickable() {
        Spanned s = new_content.getText();
        //setMovementMethod很重要，不然ClickableSpan无法获取点击事件。
        new_content.setMovementMethod(LinkMovementMethod.getInstance());
        ImageSpan[] imageSpans = s.getSpans(0, s.length(), ImageSpan.class);
        for (ImageSpan span : imageSpans) {
           // final String image_src = span.getSource();
            final int start = s.getSpanStart(span);
            final int end = s.getSpanEnd(span);
            ClickableSpan click_span = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    new_content.setCursorVisible(false);
                    Toast.makeText(AddMemorandumActivity.this,
                            "Image Clicked ", Toast.LENGTH_SHORT)
                            .show();
                }
            };

            ClickableSpan[] click_spans = s.getSpans(start, end,ClickableSpan.class);
            if (click_spans.length != 0) {
                // remove all click spans
                for (ClickableSpan c_span : click_spans) {
                    ((Spannable) s).removeSpan(c_span);
                }
            }

            ((Spannable) s).setSpan(click_span, start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(requestCode == 1){
        if(resultCode == RESULT_OK){
            setImageIntoEditText();
            Log.i( "onActivityResult: ","拍照");
        }
    }
}
}
