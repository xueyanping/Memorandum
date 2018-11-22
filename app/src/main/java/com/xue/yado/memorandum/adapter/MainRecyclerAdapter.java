package com.xue.yado.memorandum.adapter;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.xue.yado.memorandum.service.MainRecyclerItemClickListener;
import com.xue.yado.memorandum.R;
import com.xue.yado.memorandum.utils.AppCache;
import com.xue.yado.memorandum.utils.ScreenUtils;


/**
 * Created by Administrator on 2018/11/7.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MainRecyclerItemClickListener listener;
    private int HEADER = 1001;
    private int ITEM =1002;
//    private View headerView;
//    private View itemView;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == HEADER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_layout,parent,false);
            return new HeaderViewHolder(view);
        }

        if(viewType == ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_item_layout,parent,false);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            //设置成长宽均为屏幕宽度的一半（正方形）
            params.height = ScreenUtils.getScreenWidth()/2/*-ScreenUtils.dp2px(2)*/;
            params.width = ScreenUtils.getScreenWidth()/2-ScreenUtils.dp2px(2);
            view.setLayoutParams(params);
            return new ContentViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof HeaderViewHolder){
            EditText search_content = holder.itemView.findViewById(R.id.search_text);
        }

        if(holder instanceof ContentViewHolder){
            position--;
            TextView content = holder.itemView.findViewById(R.id.recyclerView_content);
            TextView date = holder.itemView.findViewById(R.id.recyclerView_date);
            content.setText(AppCache.getMemireList().get(position).getContent());
            date.setText(AppCache.getMemireList().get(position).getLastModifyDate());
        }


    }

//    public boolean haveHead(){
//        return headerView != null;
//    }
//
//    public boolean isHeader(int position){
//        return haveHead() && position == 0;
//    }


    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return HEADER;
        }else{
            return ITEM;
        }
      //  return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        int size = AppCache.getMemireList() == null ? 0 : AppCache.getMemireList().size();
        return size;
    }

    // 刷新数据


    class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public HeaderViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.itemClick(this.getAdapterPosition());
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


    public void setRecyclerItemClickListener(MainRecyclerItemClickListener listener){
        this.listener = listener;
    }
}
