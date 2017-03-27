package com.lee.myapplication.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.lee.myapplication.Util;

/**
 * Created by alvinlee on 16/6/21.
 */
public class MyTextView extends TextView{


    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("lifa","in  MyTextView       .dispatchTouchEvent    : " + Util.getActionName(event));
        boolean temp = super.dispatchTouchEvent(event);
        Log.d("lifa","out MyTextView       .dispatchTouchEvent    : " + Util.getActionName(event) + " return : " + temp);
        return temp;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("lifa","in  MyTextView       .onTouchEvent          : " + Util.getActionName(event));
        boolean temp = super.onTouchEvent(event);
        Log.d("lifa","out MyTextView       .onTouchEvent          : " + Util.getActionName(event) + " return : " + temp);
        return temp;
    }

}
