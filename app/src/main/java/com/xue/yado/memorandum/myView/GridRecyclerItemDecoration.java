package com.xue.yado.memorandum.myView;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xue.yado.memorandum.utils.ScreenUtils;

/**
 * recyclerView的网格布局自定义item间隔（上下左右，包括奇偶item）
 * Created by Administrator on 2018/11/15.
 */

public class GridRecyclerItemDecoration extends RecyclerView.ItemDecoration {
    private int top;
    private int bottom;
    private int left;
    private int right;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        final GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        final int childPosition = parent.getChildAdapterPosition(view);
        final int spanCount = layoutManager.getSpanCount();
        if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {
            //判断是否在第一排
            if (layoutManager.getSpanSizeLookup().getSpanGroupIndex(childPosition, spanCount) == 0) {
                //第一排的需要上面
                top = ScreenUtils.dp2px(0);
                outRect.top = top;
            }else{
                top = ScreenUtils.dp2px(2);
                outRect.top = top;
            }
            bottom = ScreenUtils.dp2px(0);
            outRect.bottom = bottom;
            //这里忽略和合并项的问题，只考虑占满和单一的问题
            if (lp.getSpanSize() == spanCount) {
                //占满一行
                left = right = ScreenUtils.dp2px(7);
                outRect.left = left;
                outRect.right = right;
            } else {
                if(childPosition%2==0){

                    left = ScreenUtils.dp2px(7);
                    right = ScreenUtils.dp2px(1);

                }else{
                    right = ScreenUtils.dp2px(7);
                    left = ScreenUtils.dp2px(1);
                }
                outRect.left = left;
                outRect.right = right;
//                outRect.left = (int) (((float) (spanCount - lp.getSpanIndex())) / spanCount * left);
//                outRect.right = (int) (((float) right * (spanCount + 1) / spanCount) - outRect.left);
            }

//        } else {
//            if (layoutManager.getSpanSizeLookup().getSpanGroupIndex(childPosition, spanCount) == 0) {
//                //第一排的需要left
//                outRect.left = left;
//            }
//            outRect.right = right;
//            //这里忽略和合并项的问题，只考虑占满和单一的问题
//            if (lp.getSpanSize() == spanCount) {//占满
//                outRect.top = top;
//                outRect.bottom = bottom;
//            } else {
//                outRect.top = (int) (((float) (spanCount - lp.getSpanIndex())) / spanCount * top);
//                outRect.bottom = (int) (((float) bottom * (spanCount + 1) / spanCount) - outRect.top);
//            }
  }

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }
}
