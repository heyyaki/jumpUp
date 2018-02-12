package ga.zua.coin.jumpupbitcoin;

import android.os.Message;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class CrawlringJump implements Runnable {

    private final String url_up_coin = "http://13.125.173.93:8080/crawlring/coin_up.html";
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

                    Thread.sleep(SLEEP_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

