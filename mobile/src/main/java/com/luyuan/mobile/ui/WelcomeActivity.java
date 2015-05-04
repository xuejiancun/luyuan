package com.luyuan.mobile.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.function.NotificationActivity;
import com.luyuan.mobile.model.VersionData;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

import cn.jpush.android.api.JPushInterface;

// 欢迎页面
public class WelcomeActivity extends Activity {
    private int current_code;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        new Handler().postDelayed(new Runnable() {
            public void run() {

                try {
                    PackageInfo packageInfo = WelcomeActivity.this.getPackageManager().getPackageInfo(WelcomeActivity.this.getPackageName(), 0);
                    current_code = packageInfo.versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                dialog = new ProgressDialog(WelcomeActivity.this);
                dialog.setMessage(getText(R.string.check_update));
                dialog.setCancelable(false);
                dialog.show();

                GsonRequest gsonObjRequest = new GsonRequest<VersionData>(Request.Method.GET, MyGlobal.API_CHECK_VERSION_NEW + "&versionCode=" + current_code ,
                        VersionData.class, new Response.Listener<VersionData>() {
                    @Override
                    public void onResponse(VersionData response) {
                        int latestCode = response.getCode();
                        int need = response.getNeed();

                        if (response != null && response.getSuccess().equals("true") && latestCode > current_code) {
                            if (need == 0) {
                                Dialog alertDialog = new AlertDialog.Builder(WelcomeActivity.this)
                                        .setMessage(R.string.dialog_hint_new_version)
                                        .setTitle(R.string.dialog_hint)
                                        .setCancelable(false)
                                        .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(WelcomeActivity.this, NotificationActivity.class);
                                                intent.putExtra("function", "check_version");
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .create();
                                alertDialog.show();
                            } else if (need == 1) {
                                Intent intent = new Intent(WelcomeActivity.this, NotificationActivity.class);
                                intent.putExtra("function", "check_version");
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            SharedPreferences sharedPreferences = getSharedPreferences("lockInfo", Context.MODE_APPEND);
                            String status =sharedPreferences.getString("status", "");//status:on;off 开关
                            //判断用户是否启用手势解锁
                            if("on".equals(status)){
                                //true 跳转到核对密码界面  跳转信息:当前操作option：login
                                Intent intent = new Intent(WelcomeActivity.this, GestureVerifyActivity.class);
                                intent.putExtra("option","login");
                                startActivity(intent);
                            }else{
                                //false 正常登陆
                                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }

                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                );

                RequestManager.getRequestQueue().add(gsonObjRequest);
            }

        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        RequestManager.getRequestQueue().cancelAll(this);

        try{
            dialog.dismiss();
        }catch (Exception e) {
        }
    }

}
