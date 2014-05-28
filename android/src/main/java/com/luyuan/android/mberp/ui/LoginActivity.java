package com.luyuan.android.mberp.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.luyuan.android.mberp.R;
import com.luyuan.android.mberp.util.MD5Util;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {

    // private static final String TAG = LogUtils.makeLogTag(LoginActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        String sob = ((EditText) findViewById(R.id.edittext_sob)).getText().toString();
        String username = ((EditText) findViewById(R.id.edittext_username)).getText().toString();
        String password = ((EditText) findViewById(R.id.edittext_password)).getText().toString();

        StringBuffer url = new StringBuffer("http://192.168.10.100:8080/modules/An.Systems.Web/Ajax/Login.ashx?fn=login");
        url.append("&Sid=" + sob + "&LoginName=" + username + "&sha1=" + MD5Util.encode(password + username + "089"));

        RequestQueue requestQueue = Volley.newRequestQueue(this);

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
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );

        requestQueue.add(jsonObjRequest);

    }

    public void forgotPassword(View view) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://erp.luyuan.cn"));
        startActivity(intent);
    }

}
