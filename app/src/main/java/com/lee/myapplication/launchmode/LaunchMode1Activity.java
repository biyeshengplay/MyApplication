package com.lee.myapplication.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lee.myapplication.R;

/**
 * Created by alvinlee on 16/7/11.
 */
public class LaunchMode1Activity extends AppCompatActivity {

    TextView mDescribe;

    Button mAction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mode1);
        initView();
    }

    private void initView() {
        mDescribe = (TextView) findViewById(R.id.tv_describe);
        mDescribe.setText("Activity: " + this.toString() + "\nTASK: " + this.getTaskId());
        mAction = (Button) findViewById(R.id.btn_action);
        mAction.setText("Start " + LaunchMode2Activity.class.getSimpleName());
        mAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaunchMode1Activity.this, LaunchMode2Activity.class);
                startActivity(intent);
            }
        });
    }

}
