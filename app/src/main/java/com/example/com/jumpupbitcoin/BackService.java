package com.example.com.jumpupbitcoin;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.com.jumpupbitcoin.jumpCoin.UpFragment;
import com.example.com.jumpupbitcoin.priceInfo.HomeFragment;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BackService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Back Service","onStartCommand");
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Back Service","onStartCommand");

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1, new Notification());

        Log.d("Back Service","onStartCommand");
        mJumpThread = new Thread(new CrawlringJump(mJumpHandler));
        mJumpThread.start();

        mPriceThread = new Thread(new CrawlringPrice(mPriceHandler));
        mPriceThread.start();

        final HomeFragment homeFragment = HomeFragment.newInstance((ArrayList<String>) mCalPrice.getPrice(), (ArrayList<String>)mCalPrice.getPer());
        mCalPrice.setOnChangedDataLister(new CalPrice.onChangeData() {
            @Override
            public void onDataChanged(List<String> priceList, List<String> perList) {
                if(!homeFragment.isDetached()){
                    homeFragment.refresh((ArrayList<String>) priceList, (ArrayList<String>) perList);
                }
            }
        });

        final UpFragment upFragment = UpFragment.newInstance(((ArrayList<String>) mCalJump.getAlarmReg()), (ArrayList<String>) mCalJump.getLogList());
        mCalJump.setOnChangedDataLister(new CalJump.onChangeData() {
            @Override
            public void onDataChanged(List<String> alarmReg, List<String> logList) {
                if(!upFragment.isDetached()){
                    upFragment.refresh((ArrayList<String>) alarmReg, (ArrayList<String>) logList);
                }
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    private Thread mJumpThread;
    private Thread mPriceThread;

    private CalJump mCalJump = new CalJump();
    private CalPrice mCalPrice = new CalPrice();

    final private Handler mJumpHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                // 데이터 초기화 요청
                mCalJump.clearData();
            } else if (msg.what == 1) {
                // 급등계산 모듈
                Document document = (Document) msg.obj;

                // TODO 분봉 넣어야댐
                mCalJump.upCatch(document);
            }
        }
    };


    final private Handler mPriceHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                // 현재가격 분석모듈
                String now_price = (String) msg.obj;
                mCalPrice.Calc(now_price);
            } else if (msg.what == 1) {
                String start_coin = (String) msg.obj;
                mCalPrice.per_Calc(start_coin);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("Thread Interrup", "Thread EXIT");
        mJumpThread.interrupt();
        mPriceThread.interrupt();

    }
}
