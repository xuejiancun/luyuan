package com.luyuan.mobile.function;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.luyuan.mobile.R;
import com.luyuan.mobile.util.RequestManager;

import cn.jpush.android.api.JPushInterface;

// 消息推送 主控制页面，具体显示页面分成四个：
// 1. 系统更新
// 2. 日程管理
// 3. 问卷审批
// 4. 培训通知
public class NotificationActivity extends Activity {

    private String key = "";
    private String title = "";
    private String content = "";
    private String function = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);

        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            key = (bundle.getString(JPushInterface.EXTRA_EXTRA) != null) ? bundle.getString(JPushInterface.EXTRA_EXTRA) : key;
            title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            content = bundle.getString(JPushInterface.EXTRA_ALERT);
            function = (bundle.getString("function") != null ? bundle.getString("function") : function);
        }

        setContentView(R.layout.notification_activity);

        Fragment fragment = null;
        if (function.equals("check_version")) {
            fragment = new NotificationUpdateFragment();
            actionBar.setTitle(R.string.function_check_version);
        } else if (key.contains("schedule")) {
            fragment = new NotificationScheduleFragment();
            actionBar.setTitle(R.string.schedule_remind);
        } else if (key.contains("update")) {
            fragment = new NotificationUpdateFragment();
            actionBar.setTitle(R.string.version_remind);
        } else if (key.contains("approve")) {
            fragment = new NotificationApproveFragment();
            actionBar.setTitle(R.string.approve_remind);
        } else if (key.contains("training")) {
            fragment = new NotificationTrainingFragment();
            actionBar.setTitle(R.string.training_remind);
        } else if (key.contains("notification")) {
            fragment = new NotificationNotificationFragment();
            actionBar.setTitle(R.string.notification_remind);
        } else if (key.contains("warrant")) {
            fragment = new NotificationWarrantFragment();
            actionBar.setTitle(R.string.warrant_remind);
        } else if (key.contains("notice")) {
            fragment = new NotificationNoticeFragment();
            actionBar.setTitle(R.string.notice_remind);
        }

        // 根据参数不同，显示对应的推送通知页面
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("content", content);
        fragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onStop() {
        super.onStop();
        RequestManager.getRequestQueue().cancelAll(this);
    }

}
