package com.luyuan.pad.mberp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.util.GlobalConstantValues;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();

        setContentView(R.layout.activity_home);
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