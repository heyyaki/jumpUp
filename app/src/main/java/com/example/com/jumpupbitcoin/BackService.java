package com.example.com.jumpupbitcoin;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BackService extends Service {

    final String TAG = "BackService";


    private Thread mJumpThread, mPriceThread;
    private CrawlringJump mCrawlringJump = new CrawlringJump();
    private CrawlringPrice mCrawlringPrice = new CrawlringPrice();

    private HashMap<Integer, String> map = new HashMap<>();




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

        List<String> alarmReg=null;
        notification(alarmReg, true);

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


    public void notification(List<String> alarmReg, boolean updown){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if(alarmReg!=null) {
            if(alarmReg.size()>0){
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                ArrayList<String> alarm_coin = new ArrayList<String>();
                for(String temp : alarmReg){
                    Log.d("alarmReg",temp);
                    String[] coin_arr = temp.split("_");
                    alarm_coin.add(Const.sCoinNames.get(Integer.parseInt(coin_arr[0]))+"       "+coin_arr[1]+"%");
                }
                if(updown) {
                    inboxStyle.setBigContentTitle("급등 코인 " + alarmReg.size() + "개");
                    inboxStyle.setSummaryText("검색된 급등 코인이 존재합니다");
                }else{
                    inboxStyle.setBigContentTitle("급락 코인 " + alarmReg.size() + "개");
                    inboxStyle.setSummaryText("검색된 급락 코인이 존재합니다");
                }
                for (String str : alarm_coin) {
                    inboxStyle.addLine(str);
                }
                builder.setSmallIcon(R.drawable.ic_app)
                        .setStyle(inboxStyle)
                        .setContentIntent(contentIntent);
            }
        }else{
            builder.setSmallIcon(R.drawable.ic_app)
                    .setContentTitle("코인을 감시중입니다...")
                    .setContentText("Jump Up Coin")
                    .setTicker("Jump Up Service Running")
                    .setContentIntent(contentIntent);
        }

        Notification notification = builder.build();
        startForeground(1, notification);

    }
}
