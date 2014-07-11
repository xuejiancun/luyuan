package com.luyuan.mobile.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luyuan.mobile.R;
import com.luyuan.mobile.util.MD5Util;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.PushUtil;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends Activity implements View.OnClickListener {

    private int tabSeletedIndex;
    public static boolean isForeground = false;

    private ArrayList<LinearLayout> tabLayoutList = new ArrayList<LinearLayout>();
    private ArrayList<TextView> tabTextViewList = new ArrayList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        CookieSyncManager.createInstance(MainActivity.this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(MyGlobal.SERVER_URL_PREFIX, "ASP.NET_SessionId=" + MyGlobal.getUser().getSessionId());
        CookieSyncManager.getInstance().sync();

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);

        setContentView(R.layout.activity_main);

        initTab();

        FunctionFragment functionFragment = new FunctionFragment();
        replaceTabContent(functionFragment);
        changeTabSelectedStyle(2);

        // important!!! init jpush service
        registerMessageReceiver();
        // setTag();
        setAlias();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuItem actionItem = menu.add("Refresh");
//        actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        actionItem.setIcon(android.R.drawable.ic_notification_overlay);

        return true;
    }

    private void initTab() {
        LinearLayout layout_tab_home = (LinearLayout) findViewById(R.id.layout_tab_home);
        LinearLayout layout_tab_function = (LinearLayout) findViewById(R.id.layout_tab_function);
        LinearLayout layout_tab_explore = (LinearLayout) findViewById(R.id.layout_tab_explore);
        LinearLayout layout_tab_account = (LinearLayout) findViewById(R.id.layout_tab_account);

        layout_tab_home.setOnClickListener(this);
        layout_tab_function.setOnClickListener(this);
        layout_tab_explore.setOnClickListener(this);
        layout_tab_account.setOnClickListener(this);

        tabLayoutList.add(0, layout_tab_home);
        tabLayoutList.add(1, layout_tab_function);
        tabLayoutList.add(2, layout_tab_explore);
        tabLayoutList.add(3, layout_tab_account);

        TextView textview_tab_home = (TextView) findViewById(R.id.textview_tab_home);
        TextView textview_tab_function = (TextView) findViewById(R.id.textview_tab_function);
        TextView textview_tab_explore = (TextView) findViewById(R.id.textview_tab_explore);
        TextView textview_tab_account = (TextView) findViewById(R.id.textview_tab_account);

        tabTextViewList.add(0, textview_tab_home);
        tabTextViewList.add(1, textview_tab_function);
        tabTextViewList.add(2, textview_tab_explore);
        tabTextViewList.add(3, textview_tab_account);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_tab_home:
                if (tabSeletedIndex != 1) {
                    replaceTabContent(new HomeFragment());
                    changeTabSelectedStyle(1);
                }
                break;
            case R.id.layout_tab_function:
                if (tabSeletedIndex != 2) {
                    replaceTabContent(new FunctionFragment());
                    changeTabSelectedStyle(2);
                }
                break;
            case R.id.layout_tab_explore:
                if (tabSeletedIndex != 3) {
                    replaceTabContent(new ExploreFragment());
                    changeTabSelectedStyle(3);
                }
                break;
            case R.id.layout_tab_account:
                if (tabSeletedIndex != 4) {
                    replaceTabContent(new AccountFragment());
                    changeTabSelectedStyle(4);
                }
                break;
        }
    }

    private void replaceTabContent(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

    private void changeTabSelectedStyle(int index) {
        tabSeletedIndex = index;
        for (int i = 0; i < tabLayoutList.size(); i++) {
            if (i == index - 1) {
                tabLayoutList.get(i).setSelected(true);
                tabTextViewList.get(i).setTextColor(Color.parseColor(MyGlobal.COLOR_BOTTOM_TAB_SELECTED));
            } else {
                tabLayoutList.get(i).setSelected(false);
                tabTextViewList.get(i).setTextColor(Color.parseColor(MyGlobal.COLOR_BOTTOM_TAB_UNSELECTED));
            }
        }
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.luyuan.mobile.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!PushUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                setCostomMsg(showMsg.toString());
            }
        }
    }

    private void setCostomMsg(String msg) {
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    private void setTag() {
        String tag = MD5Util.encode(MyGlobal.getUser().getId());

        // ","隔开的多个 转换成 Set
        Set<String> tagSet = new LinkedHashSet<String>();
        if (!PushUtil.isValidTagAndAlias(tag)) {
            return;
        }
        tagSet.add(tag);

        //调用JPush API设置Tag
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));

    }

    private void setAlias() {
        String alias = MyGlobal.getUser().getId();

        //调用JPush API设置Alias
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, MD5Util.encode(alias)));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:
                    // set tag and alias success
                    // JPushInterface.resumePush(getApplicationContext());
                    break;

                case 6002:
                    // failed to set alias and tags due to timeout. Try again after 60s.
                    if (PushUtil.isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        // no network
                    }
                    break;

                default:
                    // failed with errorCode = code
            }

        }

    };

    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:
                    // JPushInterface.resumePush(getApplicationContext());
                    break;

                case 6002:
                    if (PushUtil.isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                    } else {
                    }
                    break;

                default:
            }
        }

    };

    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;

                case MSG_SET_TAGS:
                    JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
                    break;

                default:
            }
        }
    };

}
