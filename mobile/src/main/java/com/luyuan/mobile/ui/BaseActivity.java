package com.luyuan.mobile.ui;

import android.app.Activity;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.Response;
import com.luyuan.mobile.model.SuccessData;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.HomeWatcher;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.OnHomePressedListener;
import com.luyuan.mobile.util.RequestManager;

import cn.jpush.android.api.JPushInterface;

public class BaseActivity extends Activity {

    @Override
    protected void onResume() {
        super.onResume();

        JPushInterface.onResume(this);

        HomeWatcher mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                MyGlobal.isBackground = true;
            }
            @Override
            public void onHomeLongPressed() {
            }
        });
        mHomeWatcher.startWatch();

        // 如果当前用户已经登陆，并且APP切换至后台运行。则切换回来的时候，判断服务器Session是否已过期
        if(!MyGlobal.getUser().getSessionId().isEmpty() && MyGlobal.isBackground){
            GsonRequest request = new GsonRequest<SuccessData>(Request.Method.GET, MyGlobal.API_CHECK_SESSION, SuccessData.class,
                new Response.Listener<SuccessData>() {
                    @Override
                    public void onResponse(SuccessData response) {
                        if (response == null || response.getSuccess().equals("false")) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                }, null);

            RequestManager.getRequestQueue().add(request);
            MyGlobal.isBackground = false;
        }
     }

}
