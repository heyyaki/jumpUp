package ga.zua.coin.jumpupbitcoin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by COM on 2018-02-12.
 */


public class SplashActivity extends Activity {

    boolean interstitialCanceled;
    Timer waitTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobileAds.initialize(this, "ca-app-pub-9946826173060023~4419923481");

        final InterstitialAd ad = new InterstitialAd(this);
        ad.setAdUnitId(getString(R.string.ad_id));

        ad.loadAd(new AdRequest.Builder().build());

        ad.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (!interstitialCanceled) {
                    waitTimer.cancel();
                    ad.show();
                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                startMainActivity();
            }

            @Override
            public void onAdClosed() {
                startMainActivity();

            }
        });

        waitTimer = new Timer();
        waitTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                interstitialCanceled = true;
                SplashActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startMainActivity();
                    }
                });
            }
        }, 5000);
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
