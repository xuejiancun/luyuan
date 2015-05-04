package com.luyuan.mobile.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.luyuan.mobile.R;
import com.luyuan.mobile.util.MyGlobal;

/**
 * 手势设置主界面
 */
public class SettingActivity extends Activity {
    private static ImageView imageView;
    private String tab = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.lock_setting);


        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("tab") != null) {
            tab = intent.getStringExtra("tab");
        }

            final ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(R.string.back);

         imageView  =(ImageView)  findViewById(R.id.setting_icon);
         loadStatus();
    }
    //设置密码监听
    public void Btn1OnClick(View view){
        //1.得到当前按钮的状态
        String status = getStatus();
        //2.off-->on
        if("off".equals(status)){
            //先改变图片为on
            imageView.setImageResource(R.drawable.checkbox_setting_on);
            //跳转到设置密码界面
            startSetLockPattern();
        }else if("on".equals(status)){  //3.on-->off
            //设置status为off
            changeStatus("off");
            //改变图片为off
            imageView.setImageResource(R.drawable.checkbox_setting_off);
        }
    }
    //打开页面的时候开关默认状态
    public void loadStatus(){
        //获取图片view
        // inflater.inflate
        SharedPreferences sharedPreferences = getSharedPreferences("lockInfo", Context.MODE_APPEND);
        //本地获取数据判断
        String status =sharedPreferences.getString("status", "");//status:on;off 开关
        if("on".equals(status)&&null!=imageView){  //2.用户已经开启，设置图片为on
            imageView.setImageResource(R.drawable.checkbox_setting_on);
        }else{
            imageView.setImageResource(R.drawable.checkbox_setting_off);
        }
    }
    //改变开关的状态
    public void changeStatus(String status){
        SharedPreferences sharedPreferences = getSharedPreferences("lockInfo", Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("status", status);
        editor.commit();
    }

    //设置手势密码
    private void startSetLockPattern() {
        Intent intent = new Intent(SettingActivity.this, GestureEditActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStatus();
    }

//得到当前按钮的状态
    public String getStatus(){
        SharedPreferences sharedPreferences = getSharedPreferences("lockInfo", Context.MODE_APPEND);
        String status =sharedPreferences.getString("status", "");//status:on;off 开关
        return status;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
           intent.putExtra("stId", MyGlobal.getUser().getStId());
           intent.putExtra("tab", tab);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void Btn2OnClick(View view){
       //判断是否开启了手势密码
      String status =   getStatus();
        if("off".equals(status)){
            Toast toast=Toast.makeText(getApplicationContext(), "手势密码未设置", Toast.LENGTH_SHORT);
            toast.show();
        }else if("on".equals(status)){
            startVerifyLockPattern();
        }



       //1.true跳转到重置密码界面

       //2.false 提示用户未开启手势密码


    }


    private void startVerifyLockPattern() {
        Intent intent = new Intent(SettingActivity.this, GestureVerifyActivity.class);
        //标示是重置手势操作
        intent.putExtra("option", "reset");
        startActivity(intent);


         //打开设置手势密码界面
         // startSetLockPattern();

    }












}
