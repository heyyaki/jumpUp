package com.example.com.jumpupbitcoin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.com.jumpupbitcoin.coinSchedule.CoinSchedule;
import com.example.com.jumpupbitcoin.jumpCoin.UpFragment;
import com.example.com.jumpupbitcoin.priceInfo.HomeFragment;
import com.example.com.jumpupbitcoin.setting.SettingFragment;


public class MainActivity extends AppCompatActivity {

    public static int frag_num = 0;
    public static boolean thread_flag = true;

    private long pressedTime;

    public static HomeFragment homeFragment;
    public static UpFragment upFragment;
    public static CoinSchedule coin_shcedule_fragment;
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
                    homeFragment = new HomeFragment();
                    manager.beginTransaction().replace(R.id.content, homeFragment, homeFragment.getTag()).commitAllowingStateLoss();
                    frag_num = 1;
                    return true;
                case R.id.navigation_dashboard:
                    setTitle("급등 코인");
                    upFragment = new UpFragment();
                    manager.beginTransaction().replace(R.id.content, upFragment, upFragment.getTag()).commitAllowingStateLoss();
                    frag_num = 2;
                    return true;

                case R.id.navigation_schedule:
                    setTitle("코인 일정");
                    coin_shcedule_fragment = new CoinSchedule();
                    manager.beginTransaction().replace(R.id.content, coin_shcedule_fragment, coin_shcedule_fragment.getTag()).commitAllowingStateLoss();
                    frag_num = 3;
                    return true;

                case R.id.navigation_notifications:
                    setTitle("설정");
                    final int bunbong = SharedPreferencesManager.getBunBong(getApplicationContext());
                    final float pricePer = SharedPreferencesManager.getPricePer(getApplicationContext());
                    final float pricePerPre = SharedPreferencesManager.getPricePerPre(getApplicationContext());
                    final float tradePer = SharedPreferencesManager.getTradePer(getApplicationContext());
                    final float tradePerPre = SharedPreferencesManager.getTradePerPre(getApplicationContext());

                    SettingFragment networkFragment = SettingFragment.newInstance(bunbong, pricePer, pricePerPre, tradePer, tradePerPre);
                    manager.beginTransaction().replace(R.id.content, networkFragment, networkFragment.getTag()).commitAllowingStateLoss();
                    frag_num = 4;
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = getApplicationContext();
        Client.bunbong = SharedPreferencesManager.getBunBong(getApplicationContext());
        Client.price_per = SharedPreferencesManager.getPricePer(context);
        Client.price_per_pre = SharedPreferencesManager.getPricePerPre(context);
        Client.trade_per = SharedPreferencesManager.getTradePer(context);
        Client.trade_per_pre = SharedPreferencesManager.getTradePerPre(context);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        intent = new Intent(context, BackService.class);
        startService(intent); // 서비스 시작

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
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
                Log.d("Thread Interrup", "Thread EXIT");

                Client.thread.interrupt();

                stopService(intent);
                finish(); // app 종료 시키기
            }
        }
    }
}
