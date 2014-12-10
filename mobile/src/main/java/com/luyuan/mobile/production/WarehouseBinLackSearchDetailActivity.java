package com.luyuan.mobile.production;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Window;

import com.luyuan.mobile.R;
import com.luyuan.mobile.ui.MainActivity;
import com.luyuan.mobile.util.MyGlobal;

public class WarehouseBinLackSearchDetailActivity extends Activity {

    private String tab = "home";
    private String time_s, time_e, wbcode, date_s, date_e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.function_warehouse_whbinlackinfo_search);

        setContentView(R.layout.warehouse_voucher_activity);

        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("tab") != null) {
            tab = intent.getStringExtra("tab");
        }
        WarehouseBinLackSearchDetailFragment warehouseBinLackSearchDetailFragment = new
                WarehouseBinLackSearchDetailFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (intent != null && intent.getStringExtra("wbcode") != null) {
            wbcode = intent.getStringExtra("wbcode");
        }
        if (intent != null && intent.getStringExtra("time_s") != null) {
            time_s = intent.getStringExtra("time_s");
        }
        if (intent != null && intent.getStringExtra("time_e") != null) {
            time_e = intent.getStringExtra("time_e");
        }
        if (intent != null && intent.getStringExtra("date_s") != null) {
            date_s = intent.getStringExtra("date_s");
        }
        if (intent != null && intent.getStringExtra("date_e") != null) {
            date_e = intent.getStringExtra("date_e");
        }
        Bundle args = new Bundle();
        Bundle data = new Bundle();
        data.putString("time_s", time_s);
        data.putString("time_e", time_e);
        data.putString("date_s", date_s);
        data.putString("date_e", date_e);
        data.putString("wbcode", wbcode);
        args.putBundle("data", data);
        warehouseBinLackSearchDetailFragment.setArguments(args);
        fragmentTransaction.replace(R.id.frame_content, warehouseBinLackSearchDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(getApplicationContext(), WarehouseBinManagerActivity.class);
        //intent.putExtra("stId", MyGlobal.getUser().getStId());
        // intent.putExtra("tab", "home");
        startActivity(intent);
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent = new Intent(getApplicationContext(), WarehouseBinManagerActivity.class);
            //intent.putExtra("stId", MyGlobal.getUser().getStId());
            // intent.putExtra("tab", "home");
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
