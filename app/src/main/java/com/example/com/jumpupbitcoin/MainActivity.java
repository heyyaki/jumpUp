        package com.example.com.jumpupbitcoin;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.os.Vibrator;
        import android.support.annotation.NonNull;
        import android.support.design.widget.BottomNavigationView;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.MenuItem;
        import android.widget.Toast;

        import com.example.com.jumpupbitcoin.coinSchedule.CoinSchedule;
        import com.example.com.jumpupbitcoin.downCoin.DownFragment;
        import com.example.com.jumpupbitcoin.jumpCoin.UpFragment;
        import com.example.com.jumpupbitcoin.priceInfo.HomeFragment;
        import com.example.com.jumpupbitcoin.setting.SettingFragment;

        import org.jsoup.nodes.Document;

        import java.util.ArrayList;
        import java.util.List;


public class MainActivity extends AppCompatActivity {

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

                    final HomeFragment homeFragment = HomeFragment.newInstance((ArrayList<String>) mCalPrice.getPrice(), (ArrayList<String>)mCalPrice.getPer());
                    manager.beginTransaction().replace(R.id.content, homeFragment, homeFragment.getTag()).commitAllowingStateLoss();
                    frag_num = 1;
                    pre_Setting();

                    mCalPrice.setOnChangedDataLister(new CalPrice.onChangeData() {
                        @Override
                        public void onDataChanged(List<String> priceList, List<String> perList) {
                            if(!homeFragment.isDetached()){
                                homeFragment.refresh((ArrayList<String>) priceList, (ArrayList<String>) perList);
                            }
                        }
                    });
                    return true;

                case R.id.navigation_dashboard:
                    setTitle("급등 코인");
                    final UpFragment upFragment = UpFragment.newInstance(((ArrayList<String>) mCalJump.getAlarmReg()), (ArrayList<String>) mCalJump.getLogList());
                    manager.beginTransaction().replace(R.id.content, upFragment, upFragment.getTag()).commitAllowingStateLoss();
                    frag_num = 2;
                    pre_Setting();

                    mCalJump.setOnChangedDataLister(new CalJump.onChangeData() {
                        @Override
                        public void onDataChanged(List<String> alarmReg, List<String> logList) {
                            if(!upFragment.isDetached()){
                                upFragment.refresh((ArrayList<String>) alarmReg, (ArrayList<String>) logList);
                            }
                        }
                    });
                    return true;

                case R.id.navigation_dashboard_down:
                    setTitle("급락 코인");
                    final DownFragment downFragment = DownFragment.newInstance(((ArrayList<String>) mCalDown.getAlarmReg()), (ArrayList<String>) mCalDown.getLogList());
                    manager.beginTransaction().replace(R.id.content, downFragment, downFragment.getTag()).commitAllowingStateLoss();
                    frag_num = 3;

                    mCalDown.setOnChangedDataLister(new CalDown.onChangeData() {
                        @Override
                        public void onDataChanged(List<String> alarmReg, List<String> logList) {
                            if(!downFragment.isDetached()){
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
                    final int bunbong = SharedPreferencesManager.getBunBong(getApplicationContext());
                    final float pricePer = SharedPreferencesManager.getPricePer(getApplicationContext());
                    final float pricePerPre = SharedPreferencesManager.getPricePerPre(getApplicationContext());
                    final float tradePer = SharedPreferencesManager.getTradePer(getApplicationContext());
                    final float tradePerPre = SharedPreferencesManager.getTradePerPre(getApplicationContext());

                    final float downPricePer = SharedPreferencesManager.getDownPricePer(getApplicationContext());
                    final float downPricePerPre = SharedPreferencesManager.getDownPricePerPre(getApplicationContext());
                    final float downTradePer = SharedPreferencesManager.getDownTradePer(getApplicationContext());
                    final float downTradePerPre = SharedPreferencesManager.getDownTradePerPre(getApplicationContext());

                    mCalJump.setBunBong(bunbong);
                    mCalJump.setPricePer(pricePer);
                    mCalJump.setPricePerPer(pricePerPre);
                    mCalJump.setTradePer(tradePer);
                    mCalJump.setTradePerPre(tradePerPre);

                    mCalDown.setBunBong(bunbong);
                    mCalDown.setDownPricePer(downPricePer);
                    mCalDown.setDownPricePerPer(downPricePerPre);
                    mCalDown.setDownTradePer(downTradePer);
                    mCalDown.setDownTradePerPre(downTradePerPre);

                    SettingFragment networkFragment = SettingFragment.newInstance(bunbong, pricePer, pricePerPre, tradePer, tradePerPre, downPricePer, downPricePerPre, downTradePer, downTradePerPre);
                    manager.beginTransaction().replace(R.id.content, networkFragment, networkFragment.getTag()).commitAllowingStateLoss();
                    frag_num = 5;
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        pre_Setting();

//        intent = new Intent(getApplicationContext(),BackService.class);
//        startService(intent);


        mJumpThread = new Thread(new CrawlringJump(mJumpHandler));
        mJumpThread.start();

        mPriceThread = new Thread(new CrawlringPrice(mPriceHandler));
        mPriceThread.start();
    }

    private Thread mJumpThread;
    private Thread mPriceThread;

    final private Handler mJumpHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                // 데이터 초기화 요청
                mCalJump.clearData();
                mCalDown.clearData();
            } else if (msg.what == 1) {
                // 급등계산 모듈
                Document document = (Document) msg.obj;

                // TODO 분봉 넣어야댐
                mCalJump.upCatch(document);
                mCalDown.downCatch(document);
            }
        }
    };


    final private Handler mPriceHandler = new Handler() {
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

    private CalJump mCalJump = new CalJump();
    private CalDown mCalDown = new CalDown();
    private CalPrice mCalPrice = new CalPrice();

    private void pre_Setting(){
        final int bunbong = SharedPreferencesManager.getBunBong(getApplicationContext());
        final float pricePer = SharedPreferencesManager.getPricePer(getApplicationContext());
        final float pricePerPre = SharedPreferencesManager.getPricePerPre(getApplicationContext());
        final float tradePer = SharedPreferencesManager.getTradePer(getApplicationContext());
        final float tradePerPre = SharedPreferencesManager.getTradePerPre(getApplicationContext());

        final float downPricePer = SharedPreferencesManager.getDownPricePer(getApplicationContext());
        final float downPricePerPre = SharedPreferencesManager.getDownPricePerPre(getApplicationContext());
        final float downTradePer = SharedPreferencesManager.getDownTradePer(getApplicationContext());
        final float downTradePerPre = SharedPreferencesManager.getDownTradePerPre(getApplicationContext());

        mCalJump.setBunBong(bunbong);
        mCalJump.setPricePer(pricePer);
        mCalJump.setPricePerPer(pricePerPre);
        mCalJump.setTradePer(tradePer);
        mCalJump.setTradePerPre(tradePerPre);

        mCalDown.setBunBong(bunbong);
        mCalDown.setDownPricePer(downPricePer);
        mCalDown.setDownPricePerPer(downPricePerPre);
        mCalDown.setDownTradePer(downTradePer);
        mCalDown.setDownTradePerPre(downTradePerPre);
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

//                stopService(intent);

                mJumpThread.interrupt();
                mPriceThread.interrupt();

                finish(); // app 종료 시키기
            }
        }
    }
}