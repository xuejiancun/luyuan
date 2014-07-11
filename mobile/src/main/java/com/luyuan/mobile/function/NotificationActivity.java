package com.luyuan.mobile.function;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.luyuan.mobile.R;

import cn.jpush.android.api.JPushInterface;

public class NotificationActivity extends Activity {

    private String key = "";
    private String title = "";
    private String content = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.notification_push);

        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            content = bundle.getString(JPushInterface.EXTRA_ALERT);
            key = bundle.getString(JPushInterface.EXTRA_EXTRA);

        }

        setContentView(R.layout.notification_activity);

        Fragment fragment = null;
        if (key.contains("schedule")) {
            fragment = new NotificationScheduleFragment();
        } else if (key.contains("update")) {
            fragment = new NotificationUpdateFragment();
        } else if (key.contains("notice")) {
            fragment = new NotificationNewsFragment();
        } else {
            // TODO default
            return;
        }

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.frame_content_notification, fragment);
        fragmentTransaction.commit();
    }

}
