package com.luyuan.mobile.function;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.Window;
import android.widget.SearchView;

import com.luyuan.mobile.R;
import com.luyuan.mobile.util.MyGlobal;

public class WarehouseVoucherManagerActivity extends Activity implements SearchView.OnQueryTextListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.function_voucher_manager);

        setContentView(R.layout.activity_warehouse_voucher_manager);
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

    public boolean onQueryTextSubmit(String query) {
        rePlaceTabContentForSearch(MyGlobal.API_WAREHOUSE_VOUCHER_SEARCH + "&query=" + query);

        return true;
    }

    private void rePlaceTabContentForSearch(String api) {
        WarehouseVoucherSearchFragment warehouseVoucherSearchFragment = new WarehouseVoucherSearchFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString("api", api);
        warehouseVoucherSearchFragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content_warehouse_voucher_manager, warehouseVoucherSearchFragment);
        fragmentTransaction.commit();
    }

}
