package com.lee.myapplication.stopthread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lee.myapplication.R;

/**
 * Created by alvinlee on 16/7/4.
 */
public class StopThreadActivity extends AppCompatActivity {

    Button mStart;
    Button mStop;
    StopThreadDemo thread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_stop_thread);

        thread = new StopThreadDemo();

        mStart = (Button) findViewById(R.id.start_thread);
        mStop = (Button) findViewById(R.id.stop_thread);

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.start();
            }
        });

        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.interrupt();
            }
        });
    }

    class StopThreadDemo extends Thread {

        private volatile boolean isInterrupt = false;

        @Override
        public void interrupt() {
            isInterrupt = true;
            super.interrupt();
        }

        @Override
        public void run() {
            while (!isInterrupt) {
                Log.d("lifa","Thread running!");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d("lifa","Thread interrupt, then stop, isInterupted: " + this.isInterrupted());
                }
            }

            Log.d("lifa","Thread run end, then stop!");
        }
    }
}
