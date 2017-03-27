package com.lee.myapplication.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lee.myapplication.R;

/**
 * Created by alvinlee on 16/7/26.
 */
public class CustomViewActivity extends AppCompatActivity{

    private CustomView mCustomView;
    private int mRadius;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        System.out.println("onCreate");
        initView();
    }

    private void initView() {
        mCustomView = (CustomView) findViewById(R.id.custom_view_view);
        countNum();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void countNum() {
        new Thread() {
            @Override
            public void run() {
                int index = 1;
                int i = 1;
                for(; index <= 2333; i++) {
                    if (i % 2 == 0 || i % 3 == 0) {
                        System.out.println("num = " + i + " index = " + index);
                        index++;
                    }
                }
//                while(index <= 2333) {
//                    if (i % 2 == 0 || i %3 == 0 ) {
//
//                    }
//                }
            }
        }.start();
    }
}
