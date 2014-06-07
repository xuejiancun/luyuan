package com.luyuan.pad.mberp.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.util.GlobalConstantValues;

public class HomeActivity extends Activity implements OnQueryTextListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    public boolean onQueryTextChange(String newText) {
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
        return true;
    }

    public void onClickPopular(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(GlobalConstantValues.INTENT_HOME_TO_MAIN, GlobalConstantValues.TAB_POPULAR);
        startActivity(intent);
    }

    public void onClickProduct(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(GlobalConstantValues.INTENT_HOME_TO_MAIN, GlobalConstantValues.TAB_PRODUCT);
        startActivity(intent);
    }

    public void onClickTab(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(GlobalConstantValues.INTENT_HOME_TO_MAIN, GlobalConstantValues.TAB_TECH);
        startActivity(intent);
    }

    public void onClickLuyuan(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(GlobalConstantValues.INTENT_HOME_TO_MAIN, GlobalConstantValues.TAB_LUYUAN);
        startActivity(intent);
    }

}