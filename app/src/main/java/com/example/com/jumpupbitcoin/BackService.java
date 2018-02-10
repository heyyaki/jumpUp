package com.example.com.jumpupbitcoin;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.jsoup.nodes.Document;


public class BackService extends Service {

    public static NotificationCompat.Builder builder;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        builder = new NotificationCompat.Builder(this);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        //notificationIntent.putExtra("notificationId", 9999); //전달할 값
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.drawable.ic_app)
                .setContentTitle("코인을 감시중입니다...")
                .setContentText("Jump Up Coin")
                .setTicker("Jump Up Service Running")
                .setContentIntent(contentIntent);
        Notification notification = builder.build();
        startForeground(1, notification);

        Log.d("Back Service","onStartCommand");

        mJumpThread = new Thread(new CrawlringJump(mJumpHandler));
        mJumpThread.start();

        mPriceThread = new Thread(new CrawlringPrice(mPriceHandler));
        mPriceThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    private Thread mJumpThread;
    private Thread mPriceThread;

    public static CalJump mCalJump = new CalJump();
    public static CalDown mCalDown = new CalDown();
    public static CalPrice mCalPrice = new CalPrice();

    final private Handler mJumpHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                // 데이터 초기화 요청
                //mCalJump.clearData();
                //mCalDown.clearData();
            } else if (msg.what == 1) {
                // 급등계산 모듈
                Document document = (Document) msg.obj;

                // TODO 분봉 넣어야댐
                mCalJump.upCatch(document);
                mCalDown.downCatch(document);
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

//    private void pre_Setting(){
//        final int bunbong = SharedPreferencesManager.getBunBong(getApplicationContext());
//        final float pricePer = SharedPreferencesManager.getPricePer(getApplicationContext());
//        final float pricePerPre = SharedPreferencesManager.getPricePerPre(getApplicationContext());
//        final float tradePer = SharedPreferencesManager.getTradePer(getApplicationContext());
//        final float tradePerPre = SharedPreferencesManager.getTradePerPre(getApplicationContext());
//
//        final float downPricePer = SharedPreferencesManager.getDownPricePer(getApplicationContext());
//        final float downPricePerPre = SharedPreferencesManager.getDownPricePerPre(getApplicationContext());
//        final float downTradePer = SharedPreferencesManager.getDownTradePer(getApplicationContext());
//        final float downTradePerPre = SharedPreferencesManager.getDownTradePerPre(getApplicationContext());
//
//        mCalJump.setBunBong(bunbong);
//        mCalJump.setPricePer(pricePer);
//        mCalJump.setPricePerPer(pricePerPre);
//        mCalJump.setTradePer(tradePer);
//        mCalJump.setTradePerPre(tradePerPre);
//
//        mCalDown.setBunBong(bunbong);
//        mCalDown.setDownPricePer(downPricePer);
//        mCalDown.setDownPricePerPer(downPricePerPre);
//        mCalDown.setDownTradePer(downTradePer);
//        mCalDown.setDownTradePerPre(downTradePerPre);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("Thread Interrup", "Thread EXIT");
        mJumpThread.interrupt();
        mPriceThread.interrupt();

    }
}
