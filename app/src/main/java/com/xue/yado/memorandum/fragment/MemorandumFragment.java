package com.xue.yado.memorandum.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.xue.yado.memorandum.R;
import com.xue.yado.memorandum.activity.AddMemorandumActivity;
import com.xue.yado.memorandum.adapter.GridRecyclerAdapter;
import com.xue.yado.memorandum.entity.Memoire;
import com.xue.yado.memorandum.myView.GridRecyclerItemDecoration;
import com.xue.yado.memorandum.service.MainRecyclerItemClickListener;
import com.xue.yado.memorandum.utils.AppCache;

import java.util.Date;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/11/19.
 */

public class MemorandumFragment extends BaseFragment {

        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;

        @BindView(R.id.flaotButton)
        FloatingActionButton floatButton;

        @BindView(R.id.appbar)
        AppBarLayout appBarLayout;

        GridRecyclerAdapter adapter;

    @Override
    public int setLayoutView() {
        return R.layout.memorandum_fragment_layout;
    }

    @Override
    public void initViews() {
        super.initViews();
        Bundle bundle = getArguments();
        if(bundle != null){
            bundle.getString("tag");
         }
        setData();
         if(AppCache.getMemireList().size()<=0){
            appBarLayout.setVisibility(View.GONE);
             floatButton.setVisibility(View.GONE);
         }
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        if(AppCache.getMemireList().size()<=0){
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return 2;
                }
            });
        }
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new GridRecyclerAdapter(recyclerView);
        GridRecyclerItemDecoration gridRecyclerItemDecoration = new GridRecyclerItemDecoration();
        recyclerView.addItemDecoration(gridRecyclerItemDecoration);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initEvent() {
        adapter.setRecyclerItemClickListener(new MainRecyclerItemClickListener() {
            @Override
            public void itemClick(int position) {
                Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();
            }
        });

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddMemorandumActivity.class));
            }
        });

    }

    @Override
    public void setData() {
      //  super.setData();
        Memoire memoire = new Memoire("标题","内容",new Date(),new Date(),"类型0");
//        Memoire memoire2 = new Memoire("标题2","内容2",new Date(),new Date(),"类型2");
        AppCache.getMemireList().add(memoire);
//        AppCache.getMemireList().add(memoire2);
//        Memoire memoire3 = new Memoire("标题3","内容2",new Date(),new Date(),"类型3");
//        AppCache.getMemireList().add(memoire3);
//        Memoire memoire4 = new Memoire("标题3","内容2",new Date(),new Date(),"类型3");
//        AppCache.getMemireList().add(memoire4);
//        Memoire memoire5 = new Memoire("标题3","内容2",new Date(),new Date(),"类型3");
//        AppCache.getMemireList().add(memoire5);
//        Memoire memoire6 = new Memoire("标题3","内容2",new Date(),new Date(),"类型3");
//        AppCache.getMemireList().add(memoire6);
//        Memoire memoire7 = new Memoire("标题3","内容2",new Date(),new Date(),"类型3");
//        AppCache.getMemireList().add(memoire7);
//        Memoire memoire8 = new Memoire("标题3","内容2",new Date(),new Date(),"类型3");
//        AppCache.getMemireList().add(memoire8);
//        Memoire memoire9 = new Memoire("标题3","内容2",new Date(),new Date(),"类型3");
//        AppCache.getMemireList().add(memoire9);
//        Memoire memoire13 = new Memoire("标题3","内容2",new Date(),new Date(),"类型3");
//        AppCache.getMemireList().add(memoire13);
//        Memoire memoire14 = new Memoire("标题3","内容2",new Date(),new Date(),"类型3");
//        AppCache.getMemireList().add(memoire14);
//        Memoire memoire15 = new Memoire("标题3","内容2",new Date(),new Date(),"类型3");
//        AppCache.getMemireList().add(memoire15);
//        Memoire memoire16 = new Memoire("标题3","内容2",new Date(),new Date(),"类型3");
//        AppCache.getMemireList().add(memoire16);
//        Memoire memoire17 = new Memoire("标题3","内容2",new Date(),new Date(),"类型3");
//        AppCache.getMemireList().add(memoire17);
//        Memoire memoire18 = new Memoire("标题3","内容2",new Date(),new Date(),"类型3");
//        AppCache.getMemireList().add(memoire18);
//        Memoire memoire19 = new Memoire("标题3","内容2",new Date(),new Date(),"类型3");
//        AppCache.getMemireList().add(memoire19);
    }
}
