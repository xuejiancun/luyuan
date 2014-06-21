package com.luyuan.mobile.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.luyuan.mobile.R;
import com.luyuan.mobile.util.GlobalConstantValues;
import com.luyuan.mobile.util.MD5Util;
import com.luyuan.mobile.util.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity implements View.OnTouchListener {

    ScrollView scrollView;

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

        StringBuffer url = new StringBuffer(GlobalConstantValues.API_LOGIN);
        url.append("&Sid=" + sob + "&LoginName=" + username + "&sha1=" + MD5Util.encode(password + username + "089"));

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET,
                url.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String state = "";
                        try {
                            state = response.getString("return_");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (state.equals("ok")) {
                            // choose job dialog, if only one item, do not show, choose it by default.
                            Dialog dialog = new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle(R.string.dialog_choose_job)
                                    .setSingleChoiceItems(new CharSequence[]{"A", "B", "C"}, 0, null)
                                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton(R.string.cancel, null)
                                    .create();

                            dialog.show();
                        } else {
                            Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);
                            findViewById(R.id.edittext_password).startAnimation(shake);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );

        RequestManager.getRequestQueue().add(jsonObjRequest);

    }

    public void reset(View view) {

    }

}
