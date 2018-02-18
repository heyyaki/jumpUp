package ga.zua.coin.jumpupbitcoin;

import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CrawlringPrice implements Runnable {
    final String url = "http://jumpupcoin.hopto.org:8080/crawlring/index.html";
    private final int SLEEP_TIME = 5 * 1000;

    private PriceDataReceiver mCallback = null;

    public void registerOnRecivePriceData(PriceDataReceiver callback) {
        mCallback = callback;
    }

    public interface PriceDataReceiver {
        void onReceivePriceData(Message msg);
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (mCallback != null) {
                    Document doc = null;
                    try {
                        doc = Jsoup.connect(url).get();

                        Elements now_doc_price = doc.select("div.now_price");
                        for (Element e : now_doc_price) {
                            Log.d("now_price", e.text());
                            // TODO 현재가격 분석모듈
                            Message msg = Message.obtain();
                            msg.what = 0;
                            msg.obj = e.text();
                            mCallback.onReceivePriceData(msg);
                        }

                        Elements start_price = doc.select("div.now_per");
                        for (Element e : start_price) {
                            //Log.d("start_price", e.text());
                            // TODO 현재 퍼센트 분석모듈
                            Message msg = Message.obtain();
                            msg.what = 1;
                            msg.obj = e.text();
                            mCallback.onReceivePriceData(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Thread.sleep(SLEEP_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
