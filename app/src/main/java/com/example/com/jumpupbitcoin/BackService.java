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

        addMap();

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

    private void addMap() {
        map.put(0, "비트코인");
        map.put(1, "에이다");
        map.put(2, "리플");
        map.put(3, "스테이터스네트워크토큰");
        map.put(4, "퀀텀");
        map.put(5, "이더리움");
        map.put(6, "머큐리");
        map.put(7, "네오");
        map.put(8, "스팀달러");
        map.put(9, "스팀");
        map.put(10, "스텔라루멘");
        map.put(11, "아인스타이늄");
        map.put(12, "비트코인 골드");
        map.put(13, "아더");
        map.put(14, "뉴이코미무브먼트");
        map.put(15, "블록틱스");
        map.put(16, "파워렛저");
        map.put(17, "비트코인캐시");
        map.put(18, "코모도");
        map.put(19, "스트라티스");
        map.put(20, "이더리움클래식");
        map.put(21, "오미세고");
        map.put(22, "그리스톨코인");
        map.put(23, "스토리지");
        map.put(24, "어거");
        map.put(25, "웨이브");
        map.put(26, "아크");
        map.put(27, "모네로");
        map.put(28, "라이트코인");
        map.put(29, "리스크");
        map.put(30, "버트코인");
        map.put(31, "피벡스");
        map.put(32, "메탈");
        map.put(33, "대쉬");
        map.put(34, "지캐시");
    }
}
