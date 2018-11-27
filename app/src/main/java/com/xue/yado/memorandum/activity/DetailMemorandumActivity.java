package com.xue.yado.memorandum.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xue.yado.memorandum.R;
import com.xue.yado.memorandum.fragment.BaseFragment;
import com.xue.yado.memorandum.utils.AppCache;

import butterknife.BindView;

    public class DetailMemorandumActivity extends BaseActivity {

    @BindView(R.id.detail_toobbar)
    android.support.v7.widget.Toolbar detail_toobbar;

    @BindView(R.id.detail_biaoqian)
    ImageView detail_biaoqian;

    @BindView(R.id.modify_time)
    TextView modify_time;

    @BindView(R.id.detail_content)
    EditText detail_content;

    boolean isEmpty_search_text;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

        @Override
        public void setContentView() {
            super.setContentView();
            setContentView(R.layout.activity_detail_memorandum);
         }

        @Override
        public void initViews() {
            super.initViews();
            initToolBar(detail_toobbar,"备忘录",true);
            Intent intent = getIntent();
            isEmpty_search_text = intent.getBooleanExtra("isEmpty_search_text",false);
            position = intent.getIntExtra("position",0);
            if(isEmpty_search_text == false){
                detail_content.setText(AppCache.getSearchList().get(position).getContent());
                modify_time.setText(AppCache.getSearchList().get(position).getLastModifyDate());
            }else{
                detail_content.setText(AppCache.getMemireList().get(position).getContent());
                modify_time.setText(AppCache.getMemireList().get(position).getLastModifyDate());
            }
        }

    }
