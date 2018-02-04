package com.example.com.jumpupbitcoin;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CrawlringPrice implements Runnable {
    final String url = "http://122.40.239.103:8080/crawlring/index.html";
    private final int SLEEP_TIME = 5 * 1000;

    private Handler mHandler;

    CrawlringPrice(Handler handler){
        mHandler = handler;
    }

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Document doc = null;
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements now_doc_price = doc.select("div.now_price");
            for (Element e : now_doc_price) {
                Log.d("now_price", e.text());
                // TODO 현재가격 분석모듈
                Message msg = Message.obtain(mHandler, 0);
                msg.obj = e.text();
                mHandler.sendMessage(msg);
            }

            Elements start_price = doc.select("div.start_price");
            for (Element e : start_price) {
                //Log.d("start_price", e.text());
                // TODO 현재 퍼센트 분석모듈
                Message msg = Message.obtain(mHandler, 0);
                msg.obj = e.text();
                mHandler.sendMessage(msg);
            }
        }
    }
}
