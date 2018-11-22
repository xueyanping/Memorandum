package com.xue.yado.memorandum.myView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/11/8.
 */

public class MyRecyclerView extends RecyclerView {

    private int mFirstY;
    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context,AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context,AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
      //  if(e.getAction()==MotionEvent.ACTION_DOWN)mFirstY= (int) e.getY();
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public int getmFirstY() {
        return mFirstY;
    }

    public void setmFirstY(int mFirstY) {
        this.mFirstY = mFirstY;
    }
}
