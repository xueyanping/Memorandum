package com.xue.yado.memorandum.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
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

import com.xue.yado.memorandum.utils.ImageUtils;
import com.xue.yado.memorandum.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
        /**
         * 动态获取 SpanSize
         */
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
//            content.setText(dataList.get(position).getContent());
            content.setText(initContent(dataList.get(position),holder.itemView.getContext(),holder.itemView));
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

    /**
     * 初始化内容，将图片路径转换成图片
     */
    public Spannable initContent(Memoire memoire, Context context,View view){
        String input = memoire.getContent().toString();
        //String regex = "<img src=\\".*?\\"\\/>";
        Pattern p = Pattern.compile("\\<img src=\".*?\"\\/>");
        Matcher m = p.matcher(input);
        //List<String> result = new ArrayList<String>();


        SpannableString spannable = new SpannableString(input);
        while(m.find()){
            //这里s保存的是整个式子，即<img src="xxx"/>，start和end保存的是下标
            String s = m.group();
            int start = m.start();
            int end = m.end();
            //path是去掉<img src=""/>的中间的图片路径
            String path = s.replaceAll("\\<img src=\"|\"\\/>","").trim();

            //利用spannableString和ImageSpan来替换掉这些图片
            //int width = ScreenUtils.getViewWidth(view);
            //int height = ScreenUtils.getViewHeight(view);
          //  Log.i("initContent: ","width=="+width+"height=="+height);
            try {
                Bitmap bitmap = ImageUtils.getSmallBitmap(path, 480, 480);
                ImageSpan imageSpan = new ImageSpan(context, bitmap);
                spannable.setSpan(imageSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
      return  spannable;
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


