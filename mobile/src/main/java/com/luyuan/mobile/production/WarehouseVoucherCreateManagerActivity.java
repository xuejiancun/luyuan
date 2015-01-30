package com.luyuan.mobile.production;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.SearchView;

import com.luyuan.mobile.R;
import com.luyuan.mobile.function.WarehousePurVoucherSearchFragment;
import com.luyuan.mobile.ui.MainActivity;
import com.luyuan.mobile.util.MyGlobal;


public class WarehouseVoucherCreateManagerActivity extends Activity implements SearchView.OnQueryTextListener {

    private String tab = "home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.function_voucher_create);

        setContentView(R.layout.warehouse_voucher_activity);

        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("tab") != null) {
            tab = intent.getStringExtra("tab");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

         SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.hint_enter_purchase_order_no));
        searchView.setOnQueryTextListener(this);

        return true;
    }

    public boolean onQueryTextChange(String newText) {
        return true;
    }

   long lastClick ;
    public boolean onQueryTextSubmit(String query) {
         if (System.currentTimeMillis() - lastClick <= 1000)
    {
       // showToast("点那么快干什么!!!");
        return false;
    }
    lastClick = System.currentTimeMillis();
        rePlaceTabContentForSearch(query);
        return true;
    }

    private void rePlaceTabContentForSearch(String api) {
        WarehousePurVoucherSearchFragment warehousePurVoucherSearchFragment = new WarehousePurVoucherSearchFragment();
         FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

         Bundle args = new Bundle();
        args.putString("api", api);
        warehousePurVoucherSearchFragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, warehousePurVoucherSearchFragment);
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
