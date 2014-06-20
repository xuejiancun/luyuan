package com.luyuan.mobile.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

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

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                        if (!state.equals("ok")) {
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

        RequestManager.getRequestQueue().add(jsonObjRequest);

    }

    public void forgotPassword(View view) {

    }

}
