package com.example.com.jumpupbitcoin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by COM on 2018-02-12.
 */

public class SplashActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
