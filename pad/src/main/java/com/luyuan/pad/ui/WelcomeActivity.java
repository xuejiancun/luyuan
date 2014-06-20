package com.luyuan.pad.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.luyuan.pad.R;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }

}
