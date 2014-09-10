package com.luyuan.mobile.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.FunctionData;
import com.luyuan.mobile.model.JobData;
import com.luyuan.mobile.model.User;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MD5Util;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

public class LoginActivity extends Activity {

    private int jobIndex;
    private JobData jobData;
    private ProgressDialog dialog;

    private String sob;
    private String username;
    private String password;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }

    public void login(View view) {
        // clear user data
        MyGlobal.setUser(new User());
        MyGlobal.setFunctionData(new FunctionData());

        sob = ((EditText) findViewById(R.id.edittext_sob)).getText().toString().trim();
        username = ((EditText) findViewById(R.id.edittext_username)).getText().toString().trim();
        password = ((EditText) findViewById(R.id.edittext_password)).getText().toString().trim();

//        username = "xuejiancun";
//        password = "123456";

//        username = "ceshi2";
//        password = "Xx8888..";

//        username = "123";
//        password = "Wlwdsa12";

//        username = "adminhh";
//        password = "654321";

        if (username.isEmpty()) {
            new AlertDialog.Builder(LoginActivity.this)
                    .setMessage(R.string.username_empty)
                    .setTitle(R.string.dialog_hint)
                    .setPositiveButton(R.string.dialog_confirm, null)
                    .create()
                    .show();

            return;
        }

        if (password.isEmpty()) {
            new AlertDialog.Builder(LoginActivity.this)
                    .setMessage(R.string.password_empty)
                    .setTitle(R.string.dialog_hint)
                    .setPositiveButton(R.string.dialog_confirm, null)
                    .create()
                    .show();

            return;
        }

        StringBuffer url = new StringBuffer(MyGlobal.API_FETCH_LOGIN);
        url.append("&sob=" + sob + "&username=" + username + "&password=" + MD5Util.encode(password + username + "089"));

        if (MyGlobal.checkNetworkConnection(this)) {

            dialog = new ProgressDialog(this);
            dialog.setMessage(getText(R.string.validating));
            dialog.setCancelable(true);
            dialog.show();

            GsonRequest gsonObjRequest = new GsonRequest<JobData>(Request.Method.GET, url.toString(),
                    JobData.class, new Response.Listener<JobData>() {

                @Override
                public void onResponse(JobData response) {
                    dialog.dismiss();

                    if (response != null && response.getSuccess().equals("true")) {
                        jobData = response;

                        User user = new User();
                        user.setId(jobData.getUserId());
                        user.setSob(sob);
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setSessionId(jobData.getSessionId());
                        user.setEmail(jobData.getEmail());
                        user.setContact(jobData.getContact());
                        MyGlobal.setUser(user);

                        int count = jobData.getJobInfos().size();
                        if (count == 1) {
                            jobIndex = 0;

                            MyGlobal.getUser().setStId(jobData.getJobInfos().get(jobIndex).getStId());
                            MyGlobal.getUser().setJob(jobData.getJobInfos().get(jobIndex).getDeptName() + jobData.getJobInfos().get(jobIndex).getJobName());
                            MyGlobal.getUser().setUnitId(jobData.getJobInfos().get(jobIndex).getUnitId());

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("tab", "home");
                            startActivity(intent);

                        } else if (count > 1) {
                            CharSequence[] jobList = new CharSequence[response.getJobInfos().size()];
                            for (int i = 0; i < count; i++) {
                                jobList[i] = jobData.getJobInfos().get(i).getJobName();
                            }

                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle(R.string.dialog_choose_job)
                                    .setSingleChoiceItems(jobList, 0, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            jobIndex = which;
                                        }
                                    })
                                    .setNegativeButton(R.string.cancel, null)
                                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            MyGlobal.getUser().setStId(jobData.getJobInfos().get(jobIndex).getStId());
                                            MyGlobal.getUser().setJob(jobData.getJobInfos().get(jobIndex).getDeptName());
                                            MyGlobal.getUser().setUnitId(jobData.getJobInfos().get(jobIndex).getUnitId());

                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.putExtra("tab", "home");
                                            startActivity(intent);
                                        }
                                    })
                                    .create()
                                    .show();
                        }


                    } else if (response != null && response.getSuccess().equals("false_username_error")) {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setMessage(R.string.username_error)
                                .setTitle(R.string.dialog_hint)
                                .setPositiveButton(R.string.dialog_confirm, null)
                                .create()
                                .show();
                    } else if (response != null && response.getSuccess().equals("false_password_error")) {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setMessage(R.string.password_error)
                                .setTitle(R.string.dialog_hint)
                                .setPositiveButton(R.string.dialog_confirm, null)
                                .create()
                                .show();
                    } else if (response != null && response.getSuccess().equals("false_account_10nlatter")) {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setMessage(R.string.account_10nlatter)
                                .setTitle(R.string.dialog_hint)
                                .setPositiveButton(R.string.dialog_confirm, null)
                                .create()
                                .show();
                    } else {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setMessage(R.string.interact_data_error)
                                .setTitle(R.string.dialog_hint)
                                .setPositiveButton(R.string.dialog_confirm, null)
                                .create()
                                .show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();

                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage(R.string.interact_data_error)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                }
            }
            );

            RequestManager.getRequestQueue().add(gsonObjRequest);
        }

    }

    public void reset(View view) {
        // TODO
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), R.string.press_again_exit_application, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
