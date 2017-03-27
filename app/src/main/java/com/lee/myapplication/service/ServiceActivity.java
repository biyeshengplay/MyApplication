package com.lee.myapplication.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lee.myapplication.R;

/**
 * Created by alvinlee on 16/6/29.
 */
public class ServiceActivity extends AppCompatActivity {

    Button mStartService;
    Button mStopService;
    Button mBindService;
    Button mUnbindService;
    Intent intent;

    private IMyAidlInterface mMyBinder;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = IMyAidlInterface.Stub.asInterface(service);
            try {
                mMyBinder.toUpperCase("hello aidl");
                mMyBinder.plus(13, 12);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("lifa","ServiceActivity onCreate, process id is " + android.os.Process.myPid());
        setContentView(R.layout.activity_service_demo);
        initView();
    }

    private void initView() {
        intent = new Intent(this, MyService.class);

        mStartService = (Button) findViewById(R.id.start_service);
        mStopService = (Button) findViewById(R.id.stop_service);
        mBindService = (Button) findViewById(R.id.bind_service);
        mUnbindService = (Button) findViewById(R.id.unbind_service);

        mStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
            }
        });

        mStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });

        mBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(intent, mConnection, BIND_AUTO_CREATE);
            }
        });

        mUnbindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(mConnection);
            }
        });
    }

}
