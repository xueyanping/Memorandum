package com.xue.yado.memorandum.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.xue.yado.memorandum.R;
import com.xue.yado.memorandum.adapter.GridRecyclerAdapter;
import com.xue.yado.memorandum.entity.Memoire;
import com.xue.yado.memorandum.myView.GridRecyclerItemDecoration;
import com.xue.yado.memorandum.service.MainRecyclerItemClickListener;
import com.xue.yado.memorandum.service.MemorandumDataChangedListener;
import com.xue.yado.memorandum.utils.AppCache;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MemorandumDataChangedListener{

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    @BindView(R.id.toolbar_main)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.flaotButton)
    FloatingActionButton floatButton;

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.search_text)
    EditText search_text;

    @BindView(R.id.undo)
    ImageView undo;

    @BindView(R.id.searchViewLayout)
    RelativeLayout searchViewLayout;

    GridRecyclerAdapter adapter;


    String toolbar_title;


    @Override
    public void setContentView() {
       setContentView(R.layout.activity_main);
    }

    public void startAction(Context context,Class clazz,int position,boolean isEmpty_search_text){
        Intent intent = new Intent(context,clazz);
        intent.putExtra("position",position);
        intent.putExtra("isEmpty_search_text",isEmpty_search_text);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
       // showFloatActionButton();

    }

    @Override
    public void initViews() {
        toolbar_title = getString(R.string.main_title);
        initToolBar(toolbar,toolbar_title,false);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        //在布局左上角显示(系统默认)图标
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerToggle.syncState();
        setData();
    }

    @Override
    public void initEvent() {
        super.initEvent();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new GridRecyclerAdapter(recyclerView);
        GridRecyclerItemDecoration gridRecyclerItemDecoration = new GridRecyclerItemDecoration();
        recyclerView.addItemDecoration(gridRecyclerItemDecoration);
        recyclerView.setAdapter(adapter);
        adapter.setData(AppCache.getMemireList());
    }

    @Override
    public void setListener() {
        super.setListener();
        adapter.setMemorandumDataChangedListener(this);

        //侧滑菜点击选择监听
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.my_navigation_0:
                        toolbar_title = getString(R.string.main_title);
                        break;
                    case R.id.my_navigation_1:
                        toolbar_title = getString(R.string.tixing);
                        break;
                    case R.id.my_navigation_2:
                        toolbar_title = getString(R.string.daiban);
                        break;
                    case R.id.my_navigation_3:
                        toolbar_title = getString(R.string.biaoqain);
                        break;
                    case R.id.my_navigation_4:
                        toolbar_title = getString(R.string.shoucang);
                        break;
                }
                getSupportActionBar().setTitle(toolbar_title);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        //adapter点击选择监听
        adapter.setRecyclerItemClickListener(new MainRecyclerItemClickListener() {
            @Override
            public void itemClick(int position) {
                if(search_text.getText().toString().equals("")){
                    startAction(MainActivity.this,DetailMemorandumActivity.class,position,true);
                }else{
                    startAction(MainActivity.this,DetailMemorandumActivity.class,position,true);

                }

            }
        });
        //悬浮菜单点击监听
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddMemorandumActivity.class));
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_text.setText("");
            }
        });

        //搜索框实时监听输入文本变化
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("beforeTextChanged", "beforeTextChanged: "+charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String content = charSequence.toString();
                if(content.length()>0){
                    undo.setVisibility(View.VISIBLE);
                }else{
                    undo.setVisibility(View.GONE);
                }
                //若输入的内容为格空则不执行搜索
                if(content.startsWith(" ")){
                        return;
                }

                if(searchData(content) != null){
                    AppCache.getSearchList().clear();
                    AppCache.getSearchList().addAll(searchData(content));
                    adapter.setData(AppCache.getSearchList());
                    adapter.notifyDataSetChanged();
                }else{
                    adapter.setData(AppCache.getMemireList());
                    adapter.notifyDataSetChanged();
                }

                if(search_text.equals("")){
                    adapter.setData(AppCache.getMemireList());
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public List<Memoire> searchData(String string) {
        int i = 0;
        if(string.equals("")){
            return null;
        }
        List<Memoire> list = new ArrayList<>();
       for(i = 0;i<AppCache.getMemireList().size();i++){
           if(string.equals(AppCache.getMemireList().get(i).getContent().toString())){
               list.add(AppCache.getMemireList().get(i));
           }
       }

        return list;
    }



    @Override
    public void setData() {
        Memoire memoire = new Memoire("标题","内容",new Date(),new Date(),"类型0");
        Memoire memoire2 = new Memoire("标题2","内容2",new Date(),new Date(),"类型2");
        AppCache.getMemireList().add(memoire);
        AppCache.getMemireList().add(memoire2);
        Memoire memoire3 = new Memoire("标题3","内容2",new Date(),new Date(),"类型3");
        AppCache.getMemireList().add(memoire3);
        Memoire memoire4 = new Memoire("标题3","内容2",new Date(),new Date(),"类型3");
        AppCache.getMemireList().add(memoire4);
        Memoire memoire5 = new Memoire("标题3","内容2",new Date(),new Date(),"类型3");
        AppCache.getMemireList().add(memoire5);
        Memoire memoire6 = new Memoire("标题3","内容2",new Date(),new Date(),"类型3");
        AppCache.getMemireList().add(memoire6);

    }

    @Override
    public void hideFloatActionButton() {
        if(floatButton.getVisibility() == View.VISIBLE){
            floatButton.setVisibility(View.GONE);
        }else{
            return;
        }
    }

    @Override
    public void showFloatActionButton() {
        if(floatButton.getVisibility() == View.GONE){
            floatButton.setVisibility(View.VISIBLE);
        }else{
            return;
        }
    }

    @Override
    public void hideSearchView() {
        if(searchViewLayout.getVisibility() == View.VISIBLE){
            searchViewLayout.setVisibility(View.GONE);
        }else{
            return;
        }
    }

    @Override
    public void showSearchView() {
        if(searchViewLayout.getVisibility() == View.GONE){
           searchViewLayout.setVisibility(View.VISIBLE);
        }else{
            return;
        }
    }



}
