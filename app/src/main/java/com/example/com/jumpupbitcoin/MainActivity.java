package com.example.com.jumpupbitcoin;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.jumpupbitcoin.coinSchedule.CoinSchedule;
import com.example.com.jumpupbitcoin.downCoin.DownFragment;
import com.example.com.jumpupbitcoin.jumpCoin.UpFragment;
import com.example.com.jumpupbitcoin.priceInfo.HomeFragment;
import com.example.com.jumpupbitcoin.setting.SettingFragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SettingFragment.OnSettingFragment {

    public static int frag_num = 0;
    private long pressedTime;

    Intent intent;

    public static Vibrator mVibrator;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.app.FragmentManager manager = getFragmentManager();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("현재 시세");

                    final HomeFragment homeFragment = HomeFragment.newInstance((ArrayList<String>) BackService.mCalPrice.getPrice(), (ArrayList<String>) BackService.mCalPrice.getPer());
                    manager.beginTransaction().replace(R.id.content, homeFragment, homeFragment.getTag()).commitAllowingStateLoss();
                    frag_num = 1;
                    LoadSettingData();

                    BackService.mCalPrice.setOnChangedDataLister(new CalPrice.onChangeData() {
                        @Override
                        public void onDataChanged(List<String> priceList, List<String> perList) {
                            if (homeFragment.isVisible()) {
                                final TextView test_clock = (TextView) findViewById(R.id.textClock);
                                int colorFrom = getResources().getColor(R.color.weakblack);
                                int colorTo = getResources().getColor(R.color.black);
                                final ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                                colorAnimation.setDuration(1000); //You can manage the time of the blink with this parameter
                                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animator) {
                                        test_clock.setBackgroundColor((int) animator.getAnimatedValue());
                                    }
                                });

                                colorAnimation.start();

                                homeFragment.refresh((ArrayList<String>) priceList, (ArrayList<String>) perList);
                            }
                        }
                    });
                    return true;

                case R.id.navigation_dashboard:
                    setTitle("급등 코인");
                    final UpFragment upFragment = UpFragment.newInstance(((ArrayList<String>) BackService.mCalJump.getAlarmReg()), (ArrayList<String>) BackService.mCalJump.getLogList());
                    manager.beginTransaction().replace(R.id.content, upFragment, upFragment.getTag()).commitAllowingStateLoss();
                    frag_num = 2;

                    BackService.mCalJump.setOnChangedDataLister(new CalJump.onChangeData() {
                        @Override
                        public void onDataChanged(List<String> alarmReg, List<String> logList) {
                            if (!upFragment.isDetached()) {
                                upFragment.refresh((ArrayList<String>) alarmReg, (ArrayList<String>) logList);
                            }
                        }
                    });
                    return true;

                case R.id.navigation_dashboard_down:
                    setTitle("급락 코인");
                    final DownFragment downFragment = DownFragment.newInstance(((ArrayList<String>) BackService.mCalDown.getAlarmReg()), (ArrayList<String>) BackService.mCalDown.getLogList());
                    manager.beginTransaction().replace(R.id.content, downFragment, downFragment.getTag()).commitAllowingStateLoss();
                    frag_num = 3;

                    BackService.mCalDown.setOnChangedDataLister(new CalDown.onChangeData() {
                        @Override
                        public void onDataChanged(List<String> alarmReg, List<String> logList) {
                            if (!downFragment.isDetached()) {
                                downFragment.refresh((ArrayList<String>) alarmReg, (ArrayList<String>) logList);
                            }
                        }
                    });
                    return true;

                case R.id.navigation_schedule:
                    setTitle("코인 일정");
                    CoinSchedule coin_shcedule_fragment = new CoinSchedule();
                    manager.beginTransaction().replace(R.id.content, coin_shcedule_fragment, coin_shcedule_fragment.getTag()).commitAllowingStateLoss();
                    frag_num = 4;
                    return true;

                case R.id.navigation_notifications:
                    setTitle("설정");
                    final boolean isUpSettingEnabled = BackService.getSettingData().mUpSettingEnabled;
                    final int upCandle = BackService.getSettingData().mUpCandle;
                    final float pricePer = BackService.getSettingData().price_per;
                    final float pricePerPre = BackService.getSettingData().price_per_pre;
                    final float tradePer = BackService.getSettingData().trade_per;
                    final float tradePerPre = BackService.getSettingData().trade_per_pre;

                    final boolean isDownSettingEnabled = BackService.getSettingData().mDownSettingEnabled;
                    final int downCandle = BackService.getSettingData().mDownCandle;
                    final float downPricePer = BackService.getSettingData().down_price_per;
                    final float downPricePerPre = BackService.getSettingData().down_price_per_pre;
                    final float downTradePer = BackService.getSettingData().down_trade_per;
                    final float downTradePerPre = BackService.getSettingData().down_trade_per_pre;

                    SettingFragment settingFragment = SettingFragment.newInstance(
                            isUpSettingEnabled, upCandle, pricePer, pricePerPre, tradePer, tradePerPre,
                            isDownSettingEnabled, downCandle, downPricePer, downPricePerPre, downTradePer, downTradePerPre);
                    manager.beginTransaction().replace(R.id.content, settingFragment, settingFragment.getTag()).commitAllowingStateLoss();
                    frag_num = 5;
                    return true;
            }
            return false;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

        LoadSettingData();

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        intent = new Intent(getApplicationContext(), BackService.class);
        startService(intent);

        MobileAds.initialize(this, "ca-app-pub-9946826173060023~4419923481");
        getAD();

    }

    private void LoadSettingData() {
        final boolean isUpSettingEnabled = SharedPreferencesManager.getUpSettingEnabled(getApplicationContext());
        final int upCandle = SharedPreferencesManager.getUpCandle(getApplicationContext());
        final float pricePer = SharedPreferencesManager.getPricePer(getApplicationContext());
        final float pricePerPre = SharedPreferencesManager.getPricePerPre(getApplicationContext());
        final float tradePer = SharedPreferencesManager.getTradePer(getApplicationContext());
        final float tradePerPre = SharedPreferencesManager.getTradePerPre(getApplicationContext());

        final boolean isDownSettingEnabled = SharedPreferencesManager.getDownSettingEnabled(getApplicationContext());
        final int downCandle = SharedPreferencesManager.getDownCandle(getApplicationContext());
        final float downPricePer = SharedPreferencesManager.getDownPricePer(getApplicationContext());
        final float downPricePerPre = SharedPreferencesManager.getDownPricePerPre(getApplicationContext());
        final float downTradePer = SharedPreferencesManager.getDownTradePer(getApplicationContext());
        final float downTradePerPre = SharedPreferencesManager.getDownTradePerPre(getApplicationContext());

        BackService.setUpSetting(isUpSettingEnabled);
        BackService.setUpCandle(upCandle);
        BackService.setPricePer(pricePer);
        BackService.setPricePerPer(pricePerPre);
        BackService.setTradePer(tradePer);
        BackService.setTradePerPre(tradePerPre);

        BackService.setDownSetting(isDownSettingEnabled);
        BackService.setDownCandle(downCandle);
        BackService.setDownPricePer(downPricePer);
        BackService.setDownPricePerPer(downPricePerPre);
        BackService.setDownTradePer(downTradePer);
        BackService.setDownTradePerPre(downTradePerPre);
    }

    @Override
    public void onBackPressed() {
        if (pressedTime == 0) {
            Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        } else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if (seconds > 2000) {
                Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
                pressedTime = 0;
            } else {
                super.onBackPressed();

                stopService(intent);

                finish(); // app 종료 시키기
            }
        }
    }
//
    private void getAD() {
        final InterstitialAd ad = new InterstitialAd(this);
        ad.setAdUnitId(getString(R.string.ad_id));

        ad.loadAd(new AdRequest.Builder().build());

        ad.setAdListener(new AdListener() {
            @Override public void onAdLoaded() {
                if (ad.isLoaded()) {
                    ad.show();
                }
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }

    @Override
    public void onVibrationSelected(int vibration) {
        SharedPreferencesManager.setVibration(getApplicationContext(), vibration);
        BackService.setVibration(vibration);
    }

    @Override
    public void onUpSettingEnabled(boolean isEnabled) {
        SharedPreferencesManager.setUpSettingEnabled(getApplicationContext(), isEnabled);
        BackService.setUpSetting(isEnabled);
    }

    @Override
    public void onUpCandleButtonClicked(int candle) {
        SharedPreferencesManager.setUpCandle(getApplicationContext(), candle);
        BackService.setUpCandle(candle);
    }

    @Override
    public void onUpPrePriceEditted(float prePrice) {
        SharedPreferencesManager.setPricePer(getApplicationContext(), prePrice);
        BackService.setPricePer(prePrice);
    }

    @Override
    public void onUpPrePrePriceEditted(float prePrePrice) {
        SharedPreferencesManager.setPricePerPre(getApplicationContext(), prePrePrice);
        BackService.setPricePerPer(prePrePrice);
    }

    @Override
    public void onUpPreTradeEditted(float preTrade) {
        SharedPreferencesManager.setTradePer(getApplicationContext(), preTrade);
        BackService.setTradePer(preTrade);
    }

    @Override
    public void onUpPrePreTradeEditted(float prePreTrade) {
        SharedPreferencesManager.setTradePerPre(getApplicationContext(), prePreTrade);
        BackService.setTradePerPre(prePreTrade);
    }

    @Override
    public void onDownSettingEnabled(boolean isEnabled) {
        Log.d("MY_LOG", "onDownSettingEnabled : " + isEnabled);
        SharedPreferencesManager.setDownSettingEnabled(getApplicationContext(), isEnabled);
        BackService.setDownSetting(isEnabled);
    }

    @Override
    public void onDownCandleButtonClicked(int candle) {
        SharedPreferencesManager.setDownCandle(getApplicationContext(), candle);
        BackService.setDownCandle(candle);
    }

    @Override
    public void onDownPrePriceEditted(float prePrice) {
        SharedPreferencesManager.setDownPricePer(getApplicationContext(), prePrice);
        BackService.setDownPricePer(prePrice);
    }

    @Override
    public void onDownPrePrePriceEditted(float prePrePrice) {
        SharedPreferencesManager.setDownPricePerPre(getApplicationContext(), prePrePrice);
        BackService.setDownPricePerPer(prePrePrice);
    }

    @Override
    public void onDownPreTradeEditted(float preTrade) {
        SharedPreferencesManager.setDownTradePer(getApplicationContext(), preTrade);
        BackService.setDownTradePer(preTrade);
    }

    @Override
    public void onDownPrePreTradeEditted(float prePreTrade) {
        SharedPreferencesManager.setDownTradePerPre(getApplicationContext(), prePreTrade);
        BackService.setDownTradePerPre(prePreTrade);
    }
}