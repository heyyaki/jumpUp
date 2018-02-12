package com.example.com.jumpupbitcoin;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class BackService extends Service {

    final String TAG = "BackService";

    public static NotificationCompat.Builder builder;
    private Thread mJumpThread, mPriceThread;
    private CrawlringJump mCrawlringJump = new CrawlringJump();
    private CrawlringPrice mCrawlringPrice = new CrawlringPrice();


    public class aidlServiceBinder extends Binder {
        public BackService getService() {
            return BackService.this;
        }
    }

    public void registerOnReciveJumpData(CrawlringJump.JumpDataReceiver callback) {
        mCrawlringJump.registerOnReciveJumpData(callback);
    }

    public void registerOnRecivePriceData(CrawlringPrice.PriceDataReceiver callback) {
        mCrawlringPrice.registerOnRecivePriceData(callback);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return new aidlServiceBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

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

        mJumpThread = new Thread(mCrawlringJump);
        mJumpThread.start();

        mPriceThread = new Thread(mCrawlringPrice);
        mPriceThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy service");

        super.onDestroy();
        mJumpThread.interrupt();
        mPriceThread.interrupt();
    }
}
