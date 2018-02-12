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

import com.example.com.jumpupbitcoin.setting.SettingData;

import org.jsoup.nodes.Document;


public class BackService extends Service {

    public static NotificationCompat.Builder builder;

    private static SettingData mSettingData = new SettingData();

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

        Log.d("Back Service", "onStartCommand");

        mJumpThread = new Thread(new CrawlringJump(mJumpHandler,mSettingData));
        mJumpThread.start();

        mPriceThread = new Thread(new CrawlringPrice(mPriceHandler));
        mPriceThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    private Thread mJumpThread;
    private Thread mPriceThread;


    public static CalJump mCalJump = new CalJump(mSettingData);
    public static CalDown mCalDown = new CalDown(mSettingData);
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
                if(mSettingData.mUpSettingEnabled)
                    mCalJump.upCatch(document);
                if(mSettingData.mDownSettingEnabled)
                    mCalDown.downCatch(document);
            }
        }
    };

    final private static Handler mPriceHandler = new Handler() {
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

    public static SettingData getSettingData() {
        return mSettingData;
    }

    public static void setVibration(int vibration) {
        mSettingData.vibration = vibration;
    }

    public static void setUpSetting(boolean upSetting) {
        mSettingData.mUpSettingEnabled = upSetting;
    }

    public static void setUpCandle(int candle) {
        mSettingData.mUpCandle = candle;
    }

    public static void setPricePer(float pricePer) {
        mSettingData.price_per = pricePer;
    }

    public static void setPricePerPer(float pricePerPer) {
        mSettingData.price_per_pre = pricePerPer;
    }

    public static void setTradePer(float tradePer) {
        mSettingData.trade_per = tradePer;
    }

    public static void setTradePerPre(float tradePerPre) {
        mSettingData.trade_per_pre = tradePerPre;
    }

    public static void setDownSetting(boolean downSetting) {
        mSettingData.mDownSettingEnabled = downSetting;
    }

    public static void setDownCandle(int candle) {
        mSettingData.mDownCandle = candle;
    }

    public static void setDownPricePer(float downPricePer) {
        mSettingData.down_price_per = downPricePer;
    }

    public static void setDownPricePerPer(float downPricePerPer) {
        mSettingData.down_price_per_pre = downPricePerPer;
    }

    public static void setDownTradePer(float downTradePer) {
        mSettingData.down_trade_per = downTradePer;
    }

    public static void setDownTradePerPre(float downTradePerPre) {
        mSettingData.down_trade_per_pre = downTradePerPre;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("Thread Interrup", "Thread EXIT");
        mJumpThread.interrupt();
        mPriceThread.interrupt();
    }
}
