package com.lee.myapplication.patterns;

import java.util.ArrayList;

/**
 * Created by alvinlee on 16/6/17.
 */
public class NewSync {
    class Plate {
        private ArrayList<Object> eggs = new ArrayList<Object>();
        public Object getEgg(String name) {
            Object egg = eggs.get(0);
            eggs.remove(0);
            System.out.println(name + " GETEgg" + " 当前蛋的数量: " + getSize());
            return egg;
        }
        public void addegg(Object egg, String name) {
            eggs.add(egg);
            System.out.println(name + " ADDEgg" + " 当前蛋的数量: " + getSize());
        }
        public int getSize() {
            return eggs.size();
        }
    }
    class Creator implements Runnable {
        private Plate plate;
        private String name;
        public Creator(Plate plate, String name) {
            this.plate = plate;
            this.name = name;
        }
        @Override
        public void run() {
            synchronized (plate) {
                while (plate.getSize() >= 10) {
                    try {
                        plate.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                plate.addegg(new Object(), name);
                plate.notify();
            }
        }
    }
    class Consumer implements Runnable {
        private Plate plate;
        private String name;
        public Consumer(Plate plate, String name) {
            this.plate = plate;
            this.name = name;
        }
        @Override
        public void run() {
            synchronized (plate) {
                while (plate.getSize() < 1) {
                    try {
                        plate.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                plate.getEgg(name);
                plate.notify();
            }
        }
    }
    public void testMethod() {
        Plate plate = new Plate();
        for (int i = 0; i < 100; i++) {
            new Thread(new Consumer(plate, String.valueOf(i))).start();
        }
        for (int i = 0; i < 100; i++) {
            new Thread(new Creator(plate, String.valueOf(i))).start();
        }
    }
}
