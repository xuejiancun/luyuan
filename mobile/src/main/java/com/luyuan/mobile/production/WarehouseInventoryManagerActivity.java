package com.luyuan.mobile.production;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.luyuan.mobile.R;
import com.luyuan.mobile.ui.MainActivity;
import com.luyuan.mobile.util.MyGlobal;


public class WarehouseInventoryManagerActivity extends Activity  {

    private String tab = "home";
    private int  flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.function_whinventorybook_search);

        setContentView(R.layout.warehouse_voucher_activity);

        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("tab") != null) {
            tab = intent.getStringExtra("tab");
        }
	    WarehouseInventorySearchFragment warehouseInventorySearchFragment = new
			    WarehouseInventorySearchFragment();
	    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

	  //  Bundle args = new Bundle();
	    //args.putString("api", api);
	   // warehouseVoucherSearchFragment.setArguments(args);
        //getFragmentManager().beginTransaction().add(warehouseInventorySearchFragment,"1");
	    fragmentTransaction.replace(R.id.frame_content, warehouseInventorySearchFragment);
	    fragmentTransaction.commit();
    }


    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        if(getFragmentManager().getBackStackEntryCount()>0) {
            getFragmentManager().popBackStack();
            return true;
        }
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
