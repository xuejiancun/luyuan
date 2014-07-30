package com.luyuan.mobile.function;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.luyuan.mobile.R;
import com.luyuan.mobile.ui.MainActivity;
import com.luyuan.mobile.util.MyGlobal;

public class MarketResearchActivity extends Activity {

    private String tab = "home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.function_market_research);

        setContentView(R.layout.market_research_activity);

        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("tab") != null) {
            tab = intent.getStringExtra("tab");
        }

        Fragment fragment = new MarketResearch1Fragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
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
}
