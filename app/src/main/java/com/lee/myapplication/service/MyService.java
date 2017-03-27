package com.lee.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by alvinlee on 16/6/29.
 */
public class MyService extends Service{

    private MyBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        Log.d("lifa","onCreate, process id is " + android.os.Process.myPid());
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d("lifa","onStart");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("lifa","onStartCommand");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("lifa","onStartCommand I'm working background!");
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("lifa","onBind");
        return iMyAidlInterface;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("lifa","onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d("lifa","onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d("lifa","onDestroy");
        super.onDestroy();
    }

    class MyBinder extends Binder {
        public void doSomething() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d("lifa","onUnbind I'am working background!");
                }
            }).start();
        }
    }

    IMyAidlInterface.Stub iMyAidlInterface = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int plus(int a, int b) throws RemoteException {
            int result = a + b;
            Log.d("lifa","IMyAidlInterface.Stub plus result is " + result);
            return result;
        }

        @Override
        public String toUpperCase(String str) throws RemoteException {
            if (str != null) {
                String temp = str.toUpperCase();
                Log.d("lifa","IMyAidlInterface.Stub toUpperCase result is " + temp);
                return temp;
            }
            return null;
        }
    };

}
