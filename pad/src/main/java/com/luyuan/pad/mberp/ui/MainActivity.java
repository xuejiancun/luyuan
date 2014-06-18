package com.luyuan.pad.mberp.ui;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.util.GlobalConstantValues;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    private int seletedIndex;

    private ArrayList<LinearLayout> tabLayoutList = new ArrayList<LinearLayout>();
    private ArrayList<TextView> tabTextViewList = new ArrayList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        setContentView(R.layout.activity_main);

        initTab();

        if (getIntent() != null && getIntent().getStringExtra(GlobalConstantValues.INTENT_HOME_TO_MAIN) != null) {
            String param = getIntent().getStringExtra(GlobalConstantValues.INTENT_HOME_TO_MAIN);

            if (param.equals(GlobalConstantValues.TAB_POPULAR_CAR)) {
                clickPopularTab();
            } else if (param.equals(GlobalConstantValues.TAB_PRODUCT_APPRECIATE)) {
                clickProductTab();
            } else if (param.equals(GlobalConstantValues.TAB_TECH_EMBODIED)) {
                clickTechTab();
            } else if (param.equals(GlobalConstantValues.TAB_LUYUAN_CULTURE)) {
                clickLuyuanTab();
            }
        } else {
            Dialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage(R.string.app_param_error)
                    .setTitle(R.string.dialog_hint)
                    .setPositiveButton(R.string.dialog_confirm, null)
                    .create();
            alertDialog.show();
        }
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
        rePlaceTabContentForSearch(GlobalConstantValues.API_SEARCH_DATA + "&query=" + query);
        changeTabBackStyle(3);

        return true;
    }

    private void initTab() {
        LinearLayout home_layout = (LinearLayout) findViewById(R.id.layout_home);
        LinearLayout popular_layout = (LinearLayout) findViewById(R.id.layout_popular);
        LinearLayout product_layout = (LinearLayout) findViewById(R.id.layout_product);
        LinearLayout tech_layout = (LinearLayout) findViewById(R.id.layout_tech);
        LinearLayout luyuan_layout = (LinearLayout) findViewById(R.id.layout_luyuan);

        home_layout.setOnClickListener(this);
        popular_layout.setOnClickListener(this);
        product_layout.setOnClickListener(this);
        tech_layout.setOnClickListener(this);
        luyuan_layout.setOnClickListener(this);

        // Do not change the order
        tabLayoutList.add(home_layout);
        tabLayoutList.add(popular_layout);
        tabLayoutList.add(product_layout);
        tabLayoutList.add(tech_layout);
        tabLayoutList.add(luyuan_layout);

        TextView home_textview = (TextView) findViewById(R.id.textview_goto_home);
        TextView popular_textview = (TextView) findViewById(R.id.textview_goto_popular);
        TextView product_textview = (TextView) findViewById(R.id.textview_goto_product);
        TextView tech_textview = (TextView) findViewById(R.id.textview_goto_tech);
        TextView luyuan_textview = (TextView) findViewById(R.id.textview_goto_luyuan);

        // Do not change the order
        tabTextViewList.add(home_textview);
        tabTextViewList.add(popular_textview);
        tabTextViewList.add(product_textview);
        tabTextViewList.add(tech_textview);
        tabTextViewList.add(luyuan_textview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_home:
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_popular:
                if (seletedIndex != 2) {
                    clickPopularTab();
                }
                break;
            case R.id.layout_product:
                if (seletedIndex != 3) {
                    clickProductTab();
                }
                break;
            case R.id.layout_tech:
                if (seletedIndex != 4) {
                    clickTechTab();
                }
                break;
            case R.id.layout_luyuan:
                if (seletedIndex != 5) {
                    clickLuyuanTab();
                }
                break;
        }
    }

    private void clickPopularTab() {
        rePlaceTabContentForSlide(GlobalConstantValues.API_POPULAR_CAR);
        changeTabBackStyle(2);
    }

    private void clickProductTab() {
        ProductHomeFragment productHomeFragment = new ProductHomeFragment();
        rePlaceTabContent(productHomeFragment);
        changeTabBackStyle(3);
    }

    private void clickTechTab() {
        TechMainFragment techMainFragment = new TechMainFragment();
        rePlaceTabContent(techMainFragment);
        changeTabBackStyle(4);
    }

    private void clickLuyuanTab() {
        LuyuanMainFragment luyuanMainFragment = new LuyuanMainFragment();
        rePlaceTabContent(luyuanMainFragment);
        changeTabBackStyle(5);
    }

    private void rePlaceTabContent(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

    private void rePlaceTabContentForSearch(String api) {
        ProductMainFragment productMainFragment = new ProductMainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString(GlobalConstantValues.PARAM_API_URL, api);
        args.putString(GlobalConstantValues.PARAM_CAR_TYPE, GlobalConstantValues.TAB_QUERY_CAR);
        productMainFragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, productMainFragment);
        fragmentTransaction.commit();
    }

    private void rePlaceTabContentForSlide(String api) {
        ImagePagerFragment imagePagerFragment = new ImagePagerFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString(GlobalConstantValues.PARAM_API_URL, api);
        imagePagerFragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, imagePagerFragment);
        fragmentTransaction.commit();
    }

    private void changeTabBackStyle(int index) {
        seletedIndex = index;
        for (int i = 0; i < tabLayoutList.size(); i++) {
            if (i == index - 1) {
                tabLayoutList.get(i).setSelected(true);
                tabTextViewList.get(i).setTextColor(Color.parseColor(GlobalConstantValues.COLOR_BOTTOM_TAB_SELECTED));
            } else {
                tabLayoutList.get(i).setSelected(false);
                tabTextViewList.get(i).setTextColor(Color.parseColor(GlobalConstantValues.COLOR_BOTTOM_TAB_UNSELECTED));
            }
        }
    }

}