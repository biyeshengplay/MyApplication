package com.lee.myapplication.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.lee.myapplication.Util;

/**
 * Created by alvinlee on 16/6/21.
 */
public class InnerLinearLayout extends LinearLayout{


    public InnerLinearLayout(Context context) {
        super(context);
    }

    public InnerLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InnerLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("lifa","in  InnerLinearLayout.dispatchTouchEvent    : " + Util.getActionName(ev));
        boolean temp = super.dispatchTouchEvent(ev);
        Log.d("lifa","out InnerLinearLayout.dispatchTouchEvent    : " + Util.getActionName(ev) + " return : " + temp);
        return temp;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("lifa","in  InnerLinearLayout.onInterceptTouchEvent : " + Util.getActionName(ev));
        boolean temp = super.onInterceptTouchEvent(ev);
        Log.d("lifa","out InnerLinearLayout.onInterceptTouchEvent : " + Util.getActionName(ev) + " return : " + temp);
        return temp;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("lifa","in  InnerLinearLayout.onTouchEvent          : " + Util.getActionName(event));
        boolean temp = super.onTouchEvent(event);
        Log.d("lifa","out InnerLinearLayout.onTouchEvent          : " + Util.getActionName(event) + " return : " + temp);
        return temp;
    }

}
