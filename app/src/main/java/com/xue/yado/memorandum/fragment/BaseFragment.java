package com.xue.yado.memorandum.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xue.yado.memorandum.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/11/19.
 */

public class BaseFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(setLayoutView(),container,false);
        ButterKnife.bind(this,view);
        initViews();
        setListener();
        initEvent();
        return view;
    }

    public void initViews() {}
    public void initEvent(){}
    public void setData(){}


    public void setListener(){}



    public int setLayoutView(){
        return 0;
    }
}
