package com.lee.myapplication.patterns;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lee.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alvinlee on 16/6/17.
 */
public class SynProAndConsu extends AppCompatActivity {

    Button mStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_and_consumer);
        initView();
    }

    private void initView() {
        mStart = (Button) findViewById(R.id.start);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NewSync().testMethod();
                //doPrepare();
            }
        });
    }

    private void doPrepare() {
        //共享资源
        Plate plate = new Plate();

        //添加生产者
        for (int i = 0; i < 100; i++) {
            new Thread(new Creator2(plate, String.valueOf(i))).start();
        }

        //添加消费者
        for (int i = 0; i < 100; i++) {
            new Thread(new Consumer2(plate, String.valueOf(i))).start();
        }
    }

    class Plate {
        private List<Object> eggs = new ArrayList<Object>();

        public Object getEgg(String name) {
            Object egg = eggs.get(0);
            eggs.remove(0);
            System.out.println(name + " GETEgg" + " 当前蛋的数量: " + getEggNum());
            return egg;
        }

        public void addEgg(Object egg, String name) {
            eggs.add(egg);
            System.out.println(name + " ADDEgg" + " 当前蛋的数量: " + getEggNum());
        }

        public int getEggNum() {
            return eggs.size();
        }
    }

    class Creator2 implements Runnable {
        private Plate plate;
        private String name;

        public Creator2(Plate plate, String name) {
            this.plate = plate;
            this.name = name;
        }

        @Override
        public void run() {
            synchronized (plate) {
                //设置盘子装蛋最大数
                while (plate.getEggNum() >= 5) {
                    try {
                        // 线程进入wait，其上的锁就会暂时得到释放，
                        // 不然其他线程不能进行加锁
                        plate.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //唤醒后，再次得到资源锁，且条件满足就可以放心地生蛋啦
                Object egg = new Object();
                plate.addEgg(egg, name);
                plate.notify();
            }
        }
    }

    class Consumer2 implements Runnable {
        private Plate plate;
        private String name;

        public Consumer2(Plate plate, String name) {
            this.plate = plate;
            this.name = name;
        }

        @Override
        public void run() {
            synchronized (plate) {
                //如果盘子中没有蛋
                while (plate.getEggNum() < 1) {
                    try {
                        //线程进入wait，那么其上的锁就会暂时得到释放，
                        plate.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 唤醒后，再次得到资源锁，且条件满足就可以放心地取蛋了
                plate.getEgg(name);
                plate.notify();
            }
        }
    }


}
