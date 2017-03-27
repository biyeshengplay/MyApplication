package com.lee.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lee.myapplication.customview.CustomViewActivity;
import com.lee.myapplication.groupcount.GroupCountActivity;
import com.lee.myapplication.launchmode.LaunchMode1Activity;
import com.lee.myapplication.patterns.SynProAndConsu;
import com.lee.myapplication.service.ServiceActivity;
import com.lee.myapplication.spiner.SpinerActivity;
import com.lee.myapplication.stopthread.StopThreadActivity;
import com.lee.myapplication.storage.JsonStorageActivity;
import com.lee.myapplication.touchevent.OntouchActivity;

public class MainActivity extends AppCompatActivity{

    Button mProducer;
    Button mOntouch;
    Button mService;
    Button mStopThread;
    Button mLaunchMode;
    Button mSpiner;
    Button mCustomView;
    Button mGroupCount;
    Button mJsonStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mProducer = (Button) findViewById(R.id.producer_consumer);
        mOntouch = (Button) findViewById(R.id.ontouch_event);
        mService = (Button) findViewById(R.id.service_demo);
        mStopThread = (Button) findViewById(R.id.stop_thread);
        mLaunchMode = (Button) findViewById(R.id.launch_mode);
        mSpiner = (Button) findViewById(R.id.widget_spiner);
        mCustomView = (Button) findViewById(R.id.custom_view);
        mGroupCount = (Button) findViewById(R.id.viewgroup_count);
        mJsonStorage = (Button) findViewById(R.id.json_storage);
        mProducer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SynProAndConsu.class);
                startActivity(intent);
            }
        });

        mOntouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OntouchActivity.class);
                startActivity(intent);
            }
        });

        mService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
                startActivity(intent);
            }
        });

        mStopThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StopThreadActivity.class);
                startActivity(intent);
            }
        });

        mLaunchMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LaunchMode1Activity.class);
                startActivity(intent);
            }
        });

        mSpiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SpinerActivity.class);
                startActivity(intent);
            }
        });

        mCustomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomViewActivity.class);
                startActivity(intent);
            }
        });

        mGroupCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GroupCountActivity.class);
                startActivity(intent);
            }
        });

        mJsonStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JsonStorageActivity.class);
                startActivity(intent);
            }
        });
    }

}
