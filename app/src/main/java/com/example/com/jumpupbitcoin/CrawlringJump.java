package com.example.com.jumpupbitcoin;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.com.jumpupbitcoin.setting.SettingData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CrawlringJump implements Runnable {

    private final String url_up_coin = "http://13.125.173.93:8080/crawlring/coin_up.html";
    //private final String url_up_coin = "http://122.40.239.103:8080/crawlring/up_coin.html";
    private final int SLEEP_TIME = 30 * 1000;

    private Handler mHandler;

    private SettingData mSettingData = new SettingData();

    CrawlringJump(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (mSettingData.price_per != -1) {
                    // TODO 데이터 초기화 요청
                    Message msg = Message.obtain(mHandler, 0);
                    mHandler.sendMessage(msg);

                    Document doc2 = null;
                    try {
                        doc2 = Jsoup.connect(url_up_coin).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // TODO 급등계산 모듈
                    Message msg2 = Message.obtain(mHandler, 1, doc2);
                    msg2.obj = doc2;

                    mHandler.sendMessage(msg2);
                    Thread.sleep(SLEEP_TIME);
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

