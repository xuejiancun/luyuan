package com.luyuan.pad.mberp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.util.GlobalConstantValues;

public class HomeActivity extends Activity {

    private static final int MAX_PROGRESS = 100;

    private int mProgress;
    ProgressDialog mProgressDialog;
    private Handler mProgressHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();

        setContentView(R.layout.activity_home);

        // Check version: version + url
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.luyuan.pad.mberp", 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        mProgressDialog = new ProgressDialog(HomeActivity.this);
        mProgressDialog.setIconAttribute(android.R.attr.alertDialogIcon);
        mProgressDialog.setTitle(R.string.dialog_hint);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax(MAX_PROGRESS);
        mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                getText(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    /* User clicked Yes so do some stuff */
                    }
                }
        );
        mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                getText(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    /* User clicked No so do some stuff */
                    }
                }
        );

        mProgressHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (mProgress >= MAX_PROGRESS) {
                    mProgressDialog.dismiss();
                } else {
                    mProgress++;
                    mProgressDialog.incrementProgressBy(1);
                    mProgressHandler.sendEmptyMessageDelayed(0, 100);
                }
            }
        };

        mProgressDialog.show();
        mProgress = 0;
        mProgressDialog.setProgress(0);
        mProgressHandler.sendEmptyMessage(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    public void onClickPopular(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(GlobalConstantValues.INTENT_HOME_TO_MAIN, GlobalConstantValues.TAB_POPULAR_CAR);
        startActivity(intent);
    }

    public void onClickProduct(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(GlobalConstantValues.INTENT_HOME_TO_MAIN, GlobalConstantValues.TAB_PRODUCT_APPRECIATE);
        startActivity(intent);
    }

    public void onClickTab(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(GlobalConstantValues.INTENT_HOME_TO_MAIN, GlobalConstantValues.TAB_TECH_EMBODIED);
        startActivity(intent);
    }

    public void onClickLuyuan(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(GlobalConstantValues.INTENT_HOME_TO_MAIN, GlobalConstantValues.TAB_LUYUAN_CULTURE);
        startActivity(intent);
    }

}