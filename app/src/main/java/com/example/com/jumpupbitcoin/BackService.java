package com.example.com.jumpupbitcoin;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class BackService extends Service {

    Client cli = new Client();

    @Override
    public IBinder onBind(Intent intent) {
        // Service 객체와 (화면단 Activity 사이에서)
        // 통신(데이터를 주고받을) 때 사용하는 메서드
        // 데이터를 전달할 필요가 없으면 return null;
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행
        startForeground(1, new Notification());
        cli.execute();
/*
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        cli.execute();

                        Thread.sleep(1000);
                        Log.d("Sleep?", "Sleep Mode Acitive");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
*/

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
