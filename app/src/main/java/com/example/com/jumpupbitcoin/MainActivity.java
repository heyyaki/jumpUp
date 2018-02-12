package com.example.com.jumpupbitcoin;

import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.com.jumpupbitcoin.coinSchedule.CoinSchedule;
import com.example.com.jumpupbitcoin.downCoin.DownFragment;
import com.example.com.jumpupbitcoin.jumpCoin.UpFragment;
import com.example.com.jumpupbitcoin.priceInfo.HomeFragment;
import com.example.com.jumpupbitcoin.setting.SettingData;
import com.example.com.jumpupbitcoin.setting.SettingFragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SettingFragment.OnSettingFragment {
    private String TAG = "MainActivity";

    private SettingData mSettingData = new SettingData();
    public CalJump mCalJump = new CalJump(mSettingData);
    public CalDown mCalDown = new CalDown(mSettingData);
    public CalPrice mCalPrice = new CalPrice();

    private long pressedTime;
    public static int frag_num = 0;
    public static Vibrator mVibrator;

    private BackService mService;
    private boolean mIsBound = false;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");

            BackService.aidlServiceBinder binder = (BackService.aidlServiceBinder) service;
            mService = binder.getService();
            mService.registerOnReciveJumpData(new CrawlringJump.JumpDataReceiver() {
                @Override
                public void onReceiveJumpData(Message msg) {
                    if (msg.what == 0) {
                        // 데이터 초기화 요청
                        //mCalJump.clearData();
                        //mCalDown.clearData();
                    } else if (msg.what == 1) {
                        // 급등계산 모듈
                        Document document = (Document) msg.obj;

                        // TODO 분봉 넣어야댐
                        if (mSettingData.mIsUpSettingEnabled)
                            mCalJump.upCatch(document);
                        if (mSettingData.mIsDownSettingEnabled)
                            mCalDown.downCatch(document);
                    }
                }

                @Override
                public boolean isUpSettingEnabled() {
                    return mSettingData.mIsUpSettingEnabled;
                }

                @Override
                public boolean isDownSettingEnabled() {
                    return mSettingData.mIsDownSettingEnabled;
                }
            });

            mService.registerOnRecivePriceData(new CrawlringPrice.PriceDataReceiver() {
                @Override
                public void onReceivePriceData(Message msg) {
                    if (msg.what == 0) {
                        // 현재가격 분석모듈
                        String now_price = (String) msg.obj;
                        mCalPrice.Calc(now_price);
                    } else if (msg.what == 1) {
                        String start_coin = (String) msg.obj;
                        mCalPrice.per_Calc(start_coin);
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");

            mService = null;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

        initSettingData();

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        startService();

//        MobileAds.initialize(this, "ca-app-pub-9946826173060023~4419923481");
//        getAD();
    }

    public void startService() {
        Intent service = new Intent(this, BackService.class);
        startService(service);
        bindService(service, mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    private void stopService() {
        if (mIsBound) {
            unbindService(mConnection);
            mIsBound = false;
        }
        stopService(new Intent(MainActivity.this, BackService.class));
    }

    private void initSettingData() {
        mSettingData.mVibration = SharedPreferencesManager.getVibration(getApplicationContext());

        mSettingData.mIsUpSettingEnabled = SharedPreferencesManager.getUpSettingEnabled(getApplicationContext());
        mSettingData.mUpCandle = SharedPreferencesManager.getUpCandle(getApplicationContext());
        mSettingData.price_per = SharedPreferencesManager.getPricePer(getApplicationContext());
        mSettingData.price_per_pre = SharedPreferencesManager.getPricePerPre(getApplicationContext());
        mSettingData.trade_per = SharedPreferencesManager.getTradePer(getApplicationContext());
        mSettingData.trade_per_pre = SharedPreferencesManager.getTradePerPre(getApplicationContext());

        mSettingData.mIsDownSettingEnabled = SharedPreferencesManager.getDownSettingEnabled(getApplicationContext());
        mSettingData.mDownCandle = SharedPreferencesManager.getDownCandle(getApplicationContext());
        mSettingData.down_price_per = SharedPreferencesManager.getDownPricePer(getApplicationContext());
        mSettingData.down_price_per_pre = SharedPreferencesManager.getDownPricePerPre(getApplicationContext());
        mSettingData.down_trade_per = SharedPreferencesManager.getDownTradePer(getApplicationContext());
        mSettingData.down_trade_per_pre = SharedPreferencesManager.getDownTradePerPre(getApplicationContext());
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
                stopService();
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
            @Override
            public void onAdLoaded() {
                if (ad.isLoaded()) {
                    ad.show();
                }
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            final FragmentManager fragmentManager = getFragmentManager();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("현재 시세");

                    final ArrayList<String> priceList = (ArrayList<String>) mCalPrice.getPrice();
                    final ArrayList<String> perList = (ArrayList<String>) mCalPrice.getPer();

                    final HomeFragment homeFragment = HomeFragment.newInstance(priceList, perList);
                    fragmentManager.beginTransaction().replace(R.id.content, homeFragment, homeFragment.getTag()).commitAllowingStateLoss();
                    frag_num = 1;
                    mCalPrice.setOnChangedDataLister(new CalPrice.onChangeData() {
                        @Override
                        public void onDataChanged(final List<String> priceList, final List<String> perList) {
                            if (!homeFragment.isVisible()) {
                                return;
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    homeFragment.blinkAnimateTextClock();

                                    if (priceList.size() == 0 || perList.size() == 0 || priceList.size() != perList.size()) {
                                        homeFragment.showNoItemView();
                                    } else {
                                        homeFragment.refreshListView(priceList, perList);
                                    }
                                }
                            });
                        }
                    });
                    return true;

                case R.id.navigation_dashboard:
                    setTitle("급등 코인");
                    final UpFragment upFragment = UpFragment.newInstance(((ArrayList<String>) mCalJump.getAlarmReg()), (ArrayList<String>) mCalJump.getLogList());
                    fragmentManager.beginTransaction().replace(R.id.content, upFragment, upFragment.getTag()).commitAllowingStateLoss();
                    frag_num = 2;

                    mCalJump.setOnChangedDataLister(new CalJump.onChangeData() {
                        @Override
                        public void onDataChanged(List<String> alarmReg, List<String> logList) {
//                            Log.d("MainActivity", "onDataChanged(jump), size of alarmReg : " + alarmReg.size() + ", size of logList : " + upFragment.getAlarmReg().size()+ ", vib : " + mSettingData.mVibration);

//                            if (mSettingData.mVibration != Const.VIBRATION_DISABLED && !equalLists(alarmReg, upFragment.getAlarmReg())) {
//                                Log.d("MainActivity", "onDataChanged(jump), vibrate!!!");
//                                mVibrator.vibrate(Const.vibPattern, -1);
//                            }

                            if (!upFragment.isDetached()) {
                                upFragment.refresh((ArrayList<String>) alarmReg, (ArrayList<String>) logList);
                            }
                        }
                    });
                    return true;

                case R.id.navigation_dashboard_down:
                    setTitle("급락 코인");
                    final DownFragment downFragment = DownFragment.newInstance(((ArrayList<String>) mCalDown.getAlarmReg()), (ArrayList<String>) mCalDown.getLogList());
                    fragmentManager.beginTransaction().replace(R.id.content, downFragment, downFragment.getTag()).commitAllowingStateLoss();
                    frag_num = 3;

                    mCalDown.setOnChangedDataLister(new CalDown.onChangeData() {
                        @Override
                        public void onDataChanged(List<String> alarmReg, List<String> logList) {
//                            Log.d("MainActivity", "onDataChanged(down), size of alarmReg : " + alarmReg.size() + ", size of logList : " + downFragment.getAlarmReg().size() + ", vib : " + mSettingData.mVibration);

//                            if (mSettingData.mVibration != Const.VIBRATION_DISABLED && !equalLists(alarmReg, downFragment.getAlarmReg())) {
//                                Log.d("MainActivity", "onDataChanged(down), vibrate!!!");
//                                mVibrator.vibrate(Const.vibPattern, -1);
//                            }

                            if (!downFragment.isDetached()) {
                                downFragment.refresh((ArrayList<String>) alarmReg, (ArrayList<String>) logList);
                            }
                        }
                    });
                    return true;

                case R.id.navigation_schedule:
                    setTitle("코인 일정");
                    CoinSchedule coin_shcedule_fragment = new CoinSchedule();
                    fragmentManager.beginTransaction().replace(R.id.content, coin_shcedule_fragment, coin_shcedule_fragment.getTag()).commitAllowingStateLoss();
                    frag_num = 4;
                    return true;

                case R.id.navigation_notifications:
                    setTitle("설정");
                    final int vibrationSettingEnabled = mSettingData.mVibration;
                    final boolean isUpSettingEnabled = mSettingData.mIsUpSettingEnabled;
                    final int upCandle = mSettingData.mUpCandle;
                    final float pricePer = mSettingData.price_per;
                    final float pricePerPre = mSettingData.price_per_pre;
                    final float tradePer = mSettingData.trade_per;
                    final float tradePerPre = mSettingData.trade_per_pre;

                    final boolean isDownSettingEnabled = mSettingData.mIsDownSettingEnabled;
                    final int downCandle = mSettingData.mDownCandle;
                    final float downPricePer = mSettingData.down_price_per;
                    final float downPricePerPre = mSettingData.down_price_per_pre;
                    final float downTradePer = mSettingData.down_trade_per;
                    final float downTradePerPre = mSettingData.down_trade_per_pre;

                    SettingFragment settingFragment = SettingFragment.newInstance(
                            vibrationSettingEnabled,
                            isUpSettingEnabled, upCandle, pricePer, pricePerPre, tradePer, tradePerPre,
                            isDownSettingEnabled, downCandle, downPricePer, downPricePerPre, downTradePer, downTradePerPre);
                    fragmentManager.beginTransaction().replace(R.id.content, settingFragment, settingFragment.getTag()).commitAllowingStateLoss();
                    frag_num = 5;
                    return true;
            }
            return false;
        }
    };

    private boolean equalLists(List<String> a, List<String> b) {
        if (a == null && b == null) return true;
        if ((a == null && b != null) || (a != null && b == null) || (a.size() != b.size())) {
            return false;
        }

        Collections.sort(a);
        Collections.sort(b);
        return a.equals(b);
    }

    protected void onDestroy() {
        super.onDestroy();
        stopService();
    }

    @Override
    public void onVibrationSelected(int vibration) {
        SharedPreferencesManager.setVibration(getApplicationContext(), vibration);
        mSettingData.mVibration = vibration;
    }

    @Override
    public void onUpSettingEnabled(boolean isEnabled) {
        SharedPreferencesManager.setUpSettingEnabled(getApplicationContext(), isEnabled);
        mSettingData.mIsUpSettingEnabled = isEnabled;
    }

    @Override
    public void onUpCandleButtonClicked(int candle) {
        SharedPreferencesManager.setUpCandle(getApplicationContext(), candle);
        mSettingData.mUpCandle = candle;
    }

    @Override
    public void onUpPrePriceEditted(float prePrice) {
        SharedPreferencesManager.setPricePer(getApplicationContext(), prePrice);
        mSettingData.price_per = prePrice;
    }

    @Override
    public void onUpPrePrePriceEditted(float prePrePrice) {
        SharedPreferencesManager.setPricePerPre(getApplicationContext(), prePrePrice);
        mSettingData.price_per_pre = prePrePrice;
    }

    @Override
    public void onUpPreTradeEditted(float preTrade) {
        SharedPreferencesManager.setTradePer(getApplicationContext(), preTrade);
        mSettingData.trade_per = preTrade;
    }

    @Override
    public void onUpPrePreTradeEditted(float prePreTrade) {
        SharedPreferencesManager.setTradePerPre(getApplicationContext(), prePreTrade);
        mSettingData.trade_per_pre = prePreTrade;
    }

    @Override
    public void onDownSettingEnabled(boolean isEnabled) {
        Log.d("MY_LOG", "onDownSettingEnabled : " + isEnabled);
        SharedPreferencesManager.setDownSettingEnabled(getApplicationContext(), isEnabled);
        mSettingData.mIsDownSettingEnabled = isEnabled;
    }

    @Override
    public void onDownCandleButtonClicked(int candle) {
        SharedPreferencesManager.setDownCandle(getApplicationContext(), candle);
        mSettingData.mDownCandle = candle;
    }

    @Override
    public void onDownPrePriceEditted(float prePrice) {
        SharedPreferencesManager.setDownPricePer(getApplicationContext(), prePrice);
        mSettingData.down_price_per = prePrice;
    }

    @Override
    public void onDownPrePrePriceEditted(float prePrePrice) {
        SharedPreferencesManager.setDownPricePerPre(getApplicationContext(), prePrePrice);
        mSettingData.down_price_per_pre = prePrePrice;
    }

    @Override
    public void onDownPreTradeEditted(float preTrade) {
        SharedPreferencesManager.setDownTradePer(getApplicationContext(), preTrade);
        mSettingData.down_trade_per = preTrade;
    }

    @Override
    public void onDownPrePreTradeEditted(float prePreTrade) {
        SharedPreferencesManager.setDownTradePerPre(getApplicationContext(), prePreTrade);
        mSettingData.down_trade_per_pre = prePreTrade;
    }
}