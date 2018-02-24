package ga.zua.coin.jumpupbitcoin;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
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

import ga.zua.coin.jumpupbitcoin.coinSchedule.CoinSchedule;
import ga.zua.coin.jumpupbitcoin.downCoin.DownFragment;
import ga.zua.coin.jumpupbitcoin.jumpCoin.UpFragment;
import ga.zua.coin.jumpupbitcoin.priceInfo.HomeFragment;
import ga.zua.coin.jumpupbitcoin.setting.SettingData;
import ga.zua.coin.jumpupbitcoin.setting.SettingFragment;
import ga.zua.coin.jumpupbitcoin.setting.getMarketVersion;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyCloseAd;
import com.fsn.cauly.CaulyCloseAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SettingFragment.OnSettingFragment, DownFragment.DownFragmentListener, UpFragment.UpFragmentListener, CaulyCloseAdListener {
    private String TAG = "MainActivity";

    public static Context mContext;

    private SettingData mSettingData = new SettingData();
    public CalJump mCalJump = new CalJump(mSettingData);
    public CalDown mCalDown = new CalDown(mSettingData);
    public CalPrice mCalPrice = new CalPrice();

    private CrawlringPrice mCrawlringPrice = new CrawlringPrice();
    private Thread mPriceThread;

    private DownFragment mDownFragment;
    private UpFragment mUpFragment;
    private HomeFragment mHomeFragment;

    private long pressedTime;
    public static Vibrator mVibrator;

    private BackService mService;
    private boolean mIsBound = false;

    public static int mUpMinCandle;
    public static int mDownMinCandle;

    private static final String APP_CODE = "hY6BdBKU"; // 광고 요청을 코드
    CaulyCloseAd mCloseAd ;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            mUpMinCandle = mSettingData.mUpCandle;
            mDownMinCandle = mSettingData.mDownCandle;

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
                        if (mSettingData.mIsUpSettingEnabled && mSettingData.price_per!=-1)
                            mCalJump.upCatch(document);

                    } else if (msg.what == 2) {
                        Document document = (Document) msg.obj;
                        if (mSettingData.mIsDownSettingEnabled && mSettingData.down_price_per!=-1)
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

        mContext=getApplicationContext();
        getMarketVersion mv = (getMarketVersion) new getMarketVersion().execute();
        version_Check(mv.func_version_check());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

        initSettingData();

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        startService();

        try {
            mCalJump.setOnChangedDataLister(new CalJump.onChangeData() {
                @Override
                public void onDataChanged(final List<String> alarmReg, final List<String> logList) {
                    Log.d("MainActivity", "onDataChanged Up, alarmReg : " + alarmReg.toString() + ", logList : " + logList.toString());
                    mService.notification(alarmReg, true);

                    if (mUpFragment == null) {
                        Log.d("MainActivity", "onDataChanged Up, mUpFragment is null");
                        return;
                    }
                    if (!mUpFragment.isVisible()) {
                        Log.d("MainActivity", "onDataChanged Up, !mUpFragment.isVisible()");
                        return;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("MainActivity", "onDataChanged Up, refresh GUI");
                            mUpFragment.refresh(alarmReg, logList);
                        }
                    });
                }
            });

            mCalDown.setOnChangedDataLister(new CalDown.onChangeData() {
                @Override
                public void onDataChanged(final List<String> alarmReg, final List<String> logList) {
                    Log.d("MainActivity", "onDataChanged Down, alarmReg : " + alarmReg.toString() + ", logList : " + logList.toString());
                    mService.notification(alarmReg, false);
                    if (mDownFragment == null) {
                        Log.d("MainActivity", "onDataChanged Down, mDownFragment is null");
                        return;
                    }
                    if (!mDownFragment.isVisible()) {
                        Log.d("MainActivity", "onDataChanged Down, !mDownFragment.isVisible()");
                        return;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("MainActivity", "onDataChanged Down, refresh GUI");
                            mDownFragment.refresh(alarmReg, logList);
                        }
                    });
                }
            });

            mCalPrice.setOnChangedDataLister(new CalPrice.onChangeData() {
                @Override
                public void onDataChanged(final List<String> priceList, final List<String> perList, final List<String> tradeList, final List<String> premeumList) {
                    Log.d("MainActivity", "onDataChanged price, priceList : " + priceList.toString() + ", perList : " + perList.toString() + ".tradeList : " + tradeList.toString() + ".tradeList : " + premeumList.toString());

                    if (mHomeFragment == null) {
                        Log.d("MainActivity", "onDataChanged price, mHomeFragment is null");
                        return;
                    }

                    if (!mHomeFragment.isVisible()) {
                        Log.d("MainActivity", "onDataChanged price, !mHomeFragment.isVisible()");
                        return;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("MainActivity", "onDataChanged price, refresh GUI");

                            mHomeFragment.blinkAnimateTextClock();

                            if (priceList.size() == 0 || perList.size() == 0 || tradeList.size() == 0 || premeumList.size() == 0 || priceList.size() != perList.size()) {
                                mHomeFragment.showNoItemView();
                            } else {
                                mHomeFragment.refreshListView(priceList, perList, tradeList, premeumList);
                            }
                        }
                    });
                }
            });
        }
        catch (Exception e) {
            Log.d("Exception", String.valueOf(e));
        }
        registerOnRecivePriceData(new CrawlringPrice.PriceDataReceiver() {
            @Override
            public void onReceivePriceData(Message msg) {
                if (msg.what == 0) {
                    // 현재가격 분석모듈
                    String now_price = (String) msg.obj;
                    mCalPrice.Calc(now_price);
                } else if (msg.what == 1) {
                    String start_coin = (String) msg.obj;
                    mCalPrice.per_Calc(start_coin);
                } else if (msg.what == 2) {
                    String trade_price = (String) msg.obj;
                    mCalPrice.trade_Calc(trade_price);
                } else if (msg.what == 3) {
                    String premeum_price = (String) msg.obj;
                    mCalPrice.premeum_Calc(premeum_price);
                }
            }
        });

//        MobileAds.initialize(this, "ca-app-pub-9946826173060023~4419923481");
//        getAD();

        //CloseAd 초기화
        CaulyAdInfo closeAdInfo = new CaulyAdInfoBuilder(APP_CODE).build();
        mCloseAd = new CaulyCloseAd();
        mCloseAd.setButtonText("취소", "종료");
        mCloseAd.setDescriptionText("종료하시겠습니까?");
        mCloseAd.setAdInfo(closeAdInfo);
        mCloseAd.setCloseAdListener(this); // CaulyCloseAdListener 등록
        mCloseAd.disableBackKey();

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
        pressedTime = System.currentTimeMillis();
        if (mCloseAd.isModuleLoaded())
        {
            mCloseAd.show(this);
        }
        else
        {
            showDefaultClosePopup();
        }
    }

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
                    final ArrayList<String> tradeList = (ArrayList<String>) mCalPrice.getTrade();
                    final ArrayList<String> premeumList = (ArrayList<String>) mCalPrice.getPremeum();
                    mHomeFragment = HomeFragment.newInstance(priceList, perList, tradeList, premeumList);
                    fragmentManager.beginTransaction().replace(R.id.content, mHomeFragment, mHomeFragment.getTag()).commitAllowingStateLoss();
                    return true;

                case R.id.navigation_dashboard:
                    setTitle("급등 코인");
                    mUpFragment = UpFragment.newInstance(((ArrayList<String>) mCalJump.getAlarmReg()), (ArrayList<String>) mCalJump.getLogList());
                    fragmentManager.beginTransaction().replace(R.id.content, mUpFragment, mUpFragment.getTag()).commitAllowingStateLoss();
                    return true;

                case R.id.navigation_dashboard_down:
                    setTitle("급락 코인");
                    mDownFragment = DownFragment.newInstance(((ArrayList<String>) mCalDown.getAlarmReg()), (ArrayList<String>) mCalDown.getLogList());
                    fragmentManager.beginTransaction().replace(R.id.content, mDownFragment, mDownFragment.getTag()).commitAllowingStateLoss();
                    return true;

                case R.id.navigation_schedule:
                    setTitle("코인 일정");
                    CoinSchedule coin_shcedule_fragment = new CoinSchedule();
                    fragmentManager.beginTransaction().replace(R.id.content, coin_shcedule_fragment, coin_shcedule_fragment.getTag()).commitAllowingStateLoss();
                    return true;

                case R.id.navigation_notifications:
                    setTitle("설정");
                    SettingFragment settingFragment = SettingFragment.newInstance(
                            mSettingData.mVibration,
                            mSettingData.mIsUpSettingEnabled, mSettingData.mUpCandle, mSettingData.price_per, mSettingData.price_per_pre, mSettingData.trade_per, mSettingData.trade_per_pre,
                            mSettingData.mIsDownSettingEnabled, mSettingData.mDownCandle, mSettingData.down_price_per, mSettingData.down_price_per_pre, mSettingData.down_trade_per, mSettingData.down_trade_per_pre);
                    fragmentManager.beginTransaction().replace(R.id.content, settingFragment, settingFragment.getTag()).commitAllowingStateLoss();
                    return true;
            }
            return false;
        }
    };

    public void registerOnRecivePriceData(CrawlringPrice.PriceDataReceiver callback) {
        mCrawlringPrice.registerOnRecivePriceData(callback);
    }


    protected void onDestroy() {
        super.onDestroy();
        stopService();
    }

    // Setting Listener
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
        mUpMinCandle=candle;
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
        SharedPreferencesManager.setDownSettingEnabled(getApplicationContext(), isEnabled);
        mSettingData.mIsDownSettingEnabled = isEnabled;
    }

    @Override
    public void onDownCandleButtonClicked(int candle) {
        SharedPreferencesManager.setDownCandle(getApplicationContext(), candle);
        mSettingData.mDownCandle = candle;
        mDownMinCandle=candle;
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

    // UpFragment Listener
    @Override
    public void onClearUpLogData() {
        mCalJump.clearLogData();
    }

    // DownFragment Listener
    @Override
    public void onClearDownLogData() {
        mCalDown.clearLogData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCloseAd != null)
            mCloseAd.resume(this);
    }

    private void version_Check(boolean version_check)
    {
        AlertDialog.Builder mDialog;
        mDialog = new AlertDialog.Builder(this);
        if (!version_check) {
            mDialog.setMessage("업데이트 버전이 있습니다. \n최신 버전으로 업데이트 후 사용해주세요. \n감사합니다.")
                    .setCancelable(false)
                    .setPositiveButton("업데이트",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    Intent marketLaunch = new Intent(Intent.ACTION_VIEW);
                                    marketLaunch.setData(Uri.parse("https://play.google.com/store/apps/details?id=ga.zua.coin.jumpupbitcoin"));
                                    MainActivity.mContext.startActivity(marketLaunch);
                                    finish();
                                }
                            })
                    .setNegativeButton("종료",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    stopService();
                                    finish();
                                }
                            });

            AlertDialog alert = mDialog.create();
            alert.setTitle("업데이트 안내");
            alert.show();
        }
    }

    private void showDefaultClosePopup()
    {
        new AlertDialog.Builder(this).setTitle("").setMessage("종료 하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stopService();
                        mPriceThread.interrupt();
                        finish();
                    }
                })
                .setNegativeButton("아니요",null)
                .show();
    }

    @Override
    public void onReceiveCloseAd(CaulyCloseAd caulyCloseAd, boolean b) {

    }

    @Override
    public void onShowedCloseAd(CaulyCloseAd caulyCloseAd, boolean b) {

    }

    @Override
    public void onFailedToReceiveCloseAd(CaulyCloseAd caulyCloseAd, int i, String s) {

    }

    @Override
    public void onLeftClicked(CaulyCloseAd caulyCloseAd) {

    }

    @Override
    public void onRightClicked(CaulyCloseAd caulyCloseAd) {
        moveTaskToBack(true);
        stopService();
        finish(); // app 종료 시키기
    }

    @Override
    public void onLeaveCloseAd(CaulyCloseAd caulyCloseAd) {
    }

    @Override
    protected void onStop(){
        Log.d(TAG, "Pause Button Touch");
        mPriceThread.interrupt();
        super.onStop();
    }

    @Override
    protected void onStart(){
        Log.d(TAG, "Start Button Touch");
        mPriceThread = new Thread(mCrawlringPrice);
        mPriceThread.start();
        super.onStart();
    }
}
