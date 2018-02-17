package ga.zua.coin.jumpupbitcoin;

import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import ga.zua.coin.jumpupbitcoin.setting.SettingData;
import ga.zua.coin.jumpupbitcoin.setting.SettingFragment;

public class CrawlringJump implements Runnable {


    //private final String url_up_coin = "http://122.40.239.103:8080/crawlring/up_coin.html";
    private final int SLEEP_TIME = 30 * 1000;


    private JumpDataReceiver mCallback = null;

    public void registerOnReciveJumpData(JumpDataReceiver callback) {
        mCallback = callback;
    }

    public interface JumpDataReceiver {
        void onReceiveJumpData(Message msg);

        boolean isUpSettingEnabled();

        boolean isDownSettingEnabled();
    }

    @Override
    public void run() {
        try {
            while (true) {

                String url_up_coin = "http://jumpupcoin.hopto.org:8080/crawlring/coin_per"+MainActivity.mUpMinCandle+".html";
                String url_down_coin = "http://jumpupcoin.hopto.org:8080/crawlring/coin_per"+MainActivity.mDownMinCandle+".html";

                if (mCallback != null && (mCallback.isDownSettingEnabled() || mCallback.isUpSettingEnabled())) {
                    Message msg = Message.obtain();
                    msg.what = 0;
                    mCallback.onReceiveJumpData(msg);

                    Document doc2 = null;
                    try {
                        doc2 = Jsoup.connect(url_up_coin).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Message msg2 = Message.obtain();
                    msg2.what = 1;
                    msg2.obj = doc2;
                    mCallback.onReceiveJumpData(msg2);

                    try {
                        doc2 = Jsoup.connect(url_down_coin).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Message msg3 = Message.obtain();
                    msg3.what = 2;
                    msg3.obj = doc2;
                    mCallback.onReceiveJumpData(msg3);


                    Thread.sleep(SLEEP_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

