package com.xue.yado.memorandum.adapter;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.xue.yado.memorandum.R;
import com.xue.yado.memorandum.activity.AddMemorandumActivity;
import com.xue.yado.memorandum.entity.Memoire;
import com.xue.yado.memorandum.service.MainRecyclerItemClickListener;
import com.xue.yado.memorandum.service.MemorandumDataChangedListener;

import com.xue.yado.memorandum.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/11/7.
 */

public class GridRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private MainRecyclerItemClickListener listener;
    private MemorandumDataChangedListener memorandumDataChangedListener;
    public static final int NO_DATA = 0x1001;
    public static final int HAS_DATA = 0x1002;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private List<Memoire> dataList = new ArrayList<>();
    public GridRecyclerAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        layoutManager = recyclerView.getLayoutManager();
    }

    @Override
    public int getItemViewType(int position) {
        if(dataList.size() <= 0){
            return NO_DATA;
        }else{
            return HAS_DATA;
        }
        // return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        int size = dataList.size() <= 0 ? 1 : dataList.size();
        return size;
    }

    public void setData(List<Memoire> dataList){
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
        Log.i("setData", "setData: "+dataList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(layoutManager instanceof GridLayoutManager){
                ((GridLayoutManager)layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return setSpanSize();
                    }
                });
            }
        if(viewType == NO_DATA){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_memorandum_layout,parent,false);
            //隐藏MainActivity中的FloatActionButton
            memorandumDataChangedListener.hideFloatActionButton();
            return new NoDataViewHolder(view);
            }else{
                 view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_item_layout,parent,false);
                //显示MainActivity中的FloatActionButton
             memorandumDataChangedListener.showFloatActionButton();
                 return new ContentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == HAS_DATA){
            TextView content = holder.itemView.findViewById(R.id.recyclerView_content);
            TextView date = holder.itemView.findViewById(R.id.recyclerView_date);
            content.setText(dataList.get(position).getContent());
            date.setText(dataList.get(position).getLastModifyDate());
            GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            // 设置成长宽均为屏幕宽度的一半（正方形）
            params.height = ScreenUtils.getScreenWidth()/2 -ScreenUtils.dp2px(8);
            params.width = ScreenUtils.getScreenWidth()/2  -ScreenUtils.dp2px(8);
//            if(position % 2 == 0){
//                 params.leftMargin = ScreenUtils.dp2px(5);
//                 params.rightMargin = ScreenUtils.dp2px(1);
//            }else{
//                params.leftMargin = ScreenUtils.dp2px(1);
//                params.rightMargin = ScreenUtils.dp2px(5);
//            }
//            if(position == 0 || position ==1){
//                params.topMargin = ScreenUtils.dp2px(0);
//            }else{
//                params.topMargin = ScreenUtils.dp2px(2);
//            }
            holder.itemView.setLayoutParams(params);
        }

        if(getItemViewType(position) == NO_DATA){
            FloatingActionButton no_data_flaotButton = holder.itemView.findViewById(R.id.no_data_flaotButton);
            no_data_flaotButton.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View view) {
                holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(),AddMemorandumActivity.class));
        }
    });
        }


    }



    class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ContentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.itemClick(this.getAdapterPosition());
        }
    }

    class NoDataViewHolder extends RecyclerView.ViewHolder {

        public NoDataViewHolder(View itemView) {
            super(itemView);

        }
    }


    public int setSpanSize(){
        if(dataList.size()<=0){
            return 2;
        }else{
            return 1;
        }
    }

    public void setRecyclerItemClickListener(MainRecyclerItemClickListener listener){
        this.listener = listener;
    }
    public void setMemorandumDataChangedListener(MemorandumDataChangedListener listener){
        this.memorandumDataChangedListener = listener;
    }
}


