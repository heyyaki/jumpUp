package com.example.com.jumpupbitcoin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.com.jumpupbitcoin.setting.NetworkFragment;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    public Context mContext;
    public static int frag_num = 0;
    public static boolean thread_flag = true;

    private long pressedTime;

    public static HomeFragment homeFragment;
    public static UpFragment upFragment;
    public static CoinSchedule coin_shcedule_fragment;
    Intent intent;

    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;

    public static HashMap<Integer, String> map = new HashMap<Integer, String>();

    public static Vibrator mVibrator;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //FragmentManager fragmentManager = getSupportFragmentManager();
            //FragmentTransaction transaction = fragmentManager.beginTransaction();
            android.app.FragmentManager manager = getFragmentManager();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("현재 시세");
//                    mWebView.destroy();
                    homeFragment = new HomeFragment();
                    manager.beginTransaction().replace(R.id.content, homeFragment, homeFragment.getTag()).commitAllowingStateLoss();
                    frag_num = 1;
                    return true;
                case R.id.navigation_dashboard:
                    setTitle("급등 코인");
//                    mWebView.destroy();
                    upFragment = new UpFragment();
                    manager.beginTransaction().replace(R.id.content, upFragment, upFragment.getTag()).commitAllowingStateLoss();
                    frag_num = 2;
                    return true;

                case R.id.navigation_schedule:
                    setTitle("코인 일정");
//                    mWebView.destroy();
                    coin_shcedule_fragment = new CoinSchedule();
                    manager.beginTransaction().replace(R.id.content, coin_shcedule_fragment, coin_shcedule_fragment.getTag()).commitAllowingStateLoss();
                    frag_num = 3;
                    return true;

                case R.id.navigation_notifications:
                    setTitle("설정");
//                    mWebView.destroy();
                    NetworkFragment networkFragment = new NetworkFragment();
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
        mContext = this;

        final long FINISH_INTERVAL_TIME = 2000;

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);


        if (String.valueOf(pref.getAll().get("radio300")) == "true")
            NetworkFragment.bunbong = 1;
        else if (String.valueOf(pref.getAll().get("radio1")) == "true")
            NetworkFragment.bunbong = 2;
        else if (String.valueOf(pref.getAll().get("radio3")) == "true")
            NetworkFragment.bunbong = 6;
        else if (String.valueOf(pref.getAll().get("radio5")) == "true")
            NetworkFragment.bunbong = 10;
        else if (String.valueOf(pref.getAll().get("radio10")) == "true")
            NetworkFragment.bunbong = 20;
        else
            NetworkFragment.bunbong = 30;

        Client.price_per = Float.parseFloat((String) pref.getAll().get("edit_txt"));
        if (pref.getAll().get("edit_txt2").toString().equals("Disabled")) {
            Client.price_per_pre = 0;
            Client.pre_check = 0;
        }
        else {
            Client.price_per_pre = Float.parseFloat((String) pref.getAll().get("edit_txt2"));
            Client.pre_check = 1;
        }
        if (pref.getAll().get("edit_txt3").toString().equals("Disabled")) {
            Client.trade_per = 0;
            Client.trade_check = 0;
        }
        else {
            Client.trade_per = Float.parseFloat((String) pref.getAll().get("edit_txt3"));
            Client.trade_check = 1;
        }
        if (pref.getAll().get("edit_txt4").toString().equals("Disabled")) {
            Client.trade_per_pre = 0;
            Client.pre_trade_check = 0;
        }
        else {
            Client.trade_per_pre = Float.parseFloat((String) pref.getAll().get("edit_txt4"));
            Client.pre_trade_check = 1;
        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        intent = new Intent(getApplicationContext(), BackService.class);
        startService(intent); // 서비스 시작

        addMap();

//        mWebView = (WebView) findViewById(R.id.webview);
//        mWebView.setWebViewClient(new WebViewClient());
//        mWebView.loadUrl("https://www.coinmarketcal.com");

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

    public void addMap() {
        map.put(0, "비트코인");
        map.put(1, "에이다");
        map.put(2, "리플");
        map.put(3, "스테이터스네트워크토큰");
        map.put(4, "퀀텀");
        map.put(5, "이더리움");
        map.put(6, "머큐리");
        map.put(7, "네오");
        map.put(8, "스팀달러");
        map.put(9, "스팀");
        map.put(10, "스텔라루멘");
        map.put(11, "아인스타이늄");
        map.put(12, "비트코인 골드");
        map.put(13, "아더");
        map.put(14, "뉴이코미무브먼트");
        map.put(15, "블록틱스");
        map.put(16, "파워렛저");
        map.put(17, "비트코인캐시");
        map.put(18, "코모도");
        map.put(19, "스트라티스");
        map.put(20, "이더리움클래식");
        map.put(21, "오미세고");
        map.put(22, "그리스톨코인");
        map.put(23, "스토리지");
        map.put(24, "어거");
        map.put(25, "웨이브");
        map.put(26, "아크");
        map.put(27, "모네로");
        map.put(28, "라이트코인");
        map.put(29, "리스크");
        map.put(30, "버트코인");
        map.put(31, "피벡스");
        map.put(32, "메탈");
        map.put(33, "대쉬");
        map.put(34, "지캐시");
    }

}
