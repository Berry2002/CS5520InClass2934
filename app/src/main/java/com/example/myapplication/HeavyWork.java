package com.example.myapplication;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HeavyWork implements Runnable {
    static final int COUNT = 9000000;
    private String taskDescription;
    private Handler messageQueue;

    public final static int STATUS_START = 0x001;
    public final static int STATUS_PROGRESS = 0x002;
    public final static int STATUS_STOP = 0x003;

    public final static String MIN_PROGRESS = "MIN";
    public final static String MAX_PROGRESS = "MAX";
    public final static String AVG_PROGRESS = "AVG";

    public HeavyWork(String message, Handler messageQueue) {
        this.taskDescription = message;
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        // start task
        Message startMessage = new Message();
        startMessage.what = STATUS_START;
        messageQueue.sendMessage(startMessage);

        for (int i = 0; i < 3; i++) {
            Message progressMessage = new Message();
            progressMessage.what = STATUS_PROGRESS;
            Bundle bundle = new Bundle();

            switch (i) {
                case 0:
                    bundle.putDouble(MIN_PROGRESS, get_min(InClass04.nums));
                    break;
                case 1:
                    bundle.putDouble(MAX_PROGRESS, get_max(InClass04.nums));
                    break;
                case 2:
                    bundle.putDouble(AVG_PROGRESS, compute_avg(InClass04.nums));
                    break;
            }
            progressMessage.setData(bundle);
            messageQueue.sendMessage(progressMessage);
        }


        // end task
        Message endMessage = new Message();
        endMessage.what = STATUS_STOP;
        messageQueue.sendMessage(endMessage);
    }


    private double get_min(List<Double> nums) {
        Collections.sort(nums);
        return nums.get(0);
    }

    private double get_max(List<Double> nums) {
        Collections.sort(nums, Collections.reverseOrder());
        return nums.get(0);
    }

    private double compute_avg(List<Double> nums) {
        double result = 0;
        for (double i : nums) {
            result += i;
        }
        return result / nums.size();
    }
    static ArrayList<Double> getArrayNumbers(int n){
        ArrayList<Double> returnArray = new ArrayList<>();

        for (int i=0; i<n; i++){
            returnArray.add(getNumber());
        }

        return returnArray;
    }

    static double getNumber(){
        double num = 0;
        Random ran = new Random();
        for(int i=0;i<COUNT; i++){
            num = num + (Math.random()*ran.nextDouble()*100+ran.nextInt(50))*1000;
        }
        return num / ((double) COUNT);
    }


    // public static void main(String[] args) {
    //     ArrayList<Double> arrayList = new ArrayList<>();
    //     arrayList = getArrayNumbers(200);
    //     for(double num: arrayList){
    //         System.out.println(num);
    //     }
    // }
}