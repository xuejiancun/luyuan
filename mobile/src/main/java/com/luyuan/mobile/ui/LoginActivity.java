package com.luyuan.mobile.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.JobData;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MD5Util;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

public class LoginActivity extends Activity implements View.OnTouchListener {

    ScrollView scrollView;

    int jobIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        scrollView = (ScrollView) findViewById(R.id.scrollview_login_page);

        findViewById(R.id.edittext_sob).setOnTouchListener(this);
        findViewById(R.id.edittext_username).setOnTouchListener(this);
        findViewById(R.id.edittext_password).setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        changeScrollView();
        return false;
    }

    private void changeScrollView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, scrollView.getHeight());
            }
        }, 300);
    }

    public void login(View view) {
        String sob = ((EditText) findViewById(R.id.edittext_sob)).getText().toString().trim();
        String username = ((EditText) findViewById(R.id.edittext_username)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.edittext_password)).getText().toString().trim();

        StringBuffer url = new StringBuffer(MyGlobal.API_FETCH_LOGIN);
        url.append("&sob=" + sob + "&username=" + username + "&password=" + MD5Util.encode(password + username + "089"));

        if (MyGlobal.checkNetworkConnection(this)) {

            GsonRequest gsonObjRequest = new GsonRequest<JobData>(Request.Method.GET, url.toString(),
                    JobData.class, new Response.Listener<JobData>() {
                @Override
                public void onResponse(JobData response) {
                    if (response != null && response.getSuccess().equals("true")) {
                        CharSequence[] jobList = new CharSequence[]{};

                        int count = response.getJobInfos().size();
                        for (int i = 0; i < count; i++) {
                            jobList[i] = response.getJobInfos().get(i).getJobName();
                        }

                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle(R.string.dialog_choose_job)
                                .setSingleChoiceItems(jobList, 0, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        jobIndex = which;
                                        Toast.makeText(LoginActivity.this, "choose" + jobIndex, Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.putExtra("", "");
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton(R.string.cancel, null)
                                .create()
                                .show();
                    } else if (response != null && response.getSuccess().equals("false_username_error")) {
                        Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);
                        findViewById(R.id.edittext_username).startAnimation(shake);
                    } else if (response != null && response.getSuccess().equals("false_password_error")) {
                        Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);
                        findViewById(R.id.edittext_password).startAnimation(shake);
                    } else {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setMessage(R.string.fetch_data_error)
                                .setTitle(R.string.dialog_hint)
                                .setPositiveButton(R.string.dialog_confirm, null)
                                .create()
                                .show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage(R.string.fetch_data_error)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                }
            }
            );

            RequestManager.getRequestQueue().add(gsonObjRequest);
            gsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                    MyGlobal.CONNECTION_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }

    }

    public void reset(View view) {

    }

}
