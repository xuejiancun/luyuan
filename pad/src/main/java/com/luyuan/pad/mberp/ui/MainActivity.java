package com.luyuan.pad.mberp.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.luyuan.pad.mberp.R;


public class MainActivity extends FragmentActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    private PopularMainFragment popularMainFragment;
    private ProductHomeFragment productHomeFragment;
    private TechMainFragment techMainFragment;
    private LuyuanMainFragment luyuanMainFragment;

    private LinearLayout home_layout, popular_layout, product_layout, tech_layout, luyuan_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        setContentView(R.layout.activity_main);

        initView();
        initData();

        Intent intent = getIntent();
        String param = intent.getExtras().getString("com.luyuan.pad.mberp.home2main");

        if (param.equals("popular")) {
            clickPopularTab();
        } else if (param.equals("product")) {
            clickProductTab();
        } else if (param.equals("tech")) {
            clickTechTab();
        } else if (param.equals("luyuan")) {
            clickLuyuanTab();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action actionBar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onQueryTextChange(String newText) {
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
        return true;
    }


    private void initData() {
        home_layout.setOnClickListener(this);
        popular_layout.setOnClickListener(this);
        product_layout.setOnClickListener(this);
        tech_layout.setOnClickListener(this);
        luyuan_layout.setOnClickListener(this);
    }

    private void initView() {
        home_layout = (LinearLayout) findViewById(R.id.layout_home);
        popular_layout = (LinearLayout) findViewById(R.id.layout_popular);
        product_layout = (LinearLayout) findViewById(R.id.layout_product);
        tech_layout = (LinearLayout) findViewById(R.id.layout_tech);
        luyuan_layout = (LinearLayout) findViewById(R.id.layout_luyuan);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_home:
                clickHomeTab();
                break;
            case R.id.layout_popular:
                clickPopularTab();
                break;
            case R.id.layout_product:
                clickProductTab();
                break;
            case R.id.layout_tech:
                clickTechTab();
                break;
            case R.id.layout_luyuan:
                clickLuyuanTab();
                break;
        }
    }

    private void clickHomeTab() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    private void clickPopularTab() {
        popularMainFragment = new PopularMainFragment();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, popularMainFragment);
        fragmentTransaction.commit();

        focusOnPopularTab();
    }

    private void focusOnPopularTab() {
        home_layout.setSelected(false);
        popular_layout.setSelected(true);
        product_layout.setSelected(false);
        tech_layout.setSelected(false);
        luyuan_layout.setSelected(false);
    }

    private void clickProductTab() {
        productHomeFragment = new ProductHomeFragment();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, productHomeFragment);
        fragmentTransaction.commit();

        focusOnProductTab();
    }

    private void focusOnProductTab() {
        home_layout.setSelected(false);
        popular_layout.setSelected(false);
        product_layout.setSelected(true);
        tech_layout.setSelected(false);
        luyuan_layout.setSelected(false);
    }

    private void clickTechTab() {
        techMainFragment = new TechMainFragment();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, techMainFragment);
        fragmentTransaction.commit();

        focusOnTechTab();
    }

    private void focusOnTechTab() {
        home_layout.setSelected(false);
        popular_layout.setSelected(false);
        product_layout.setSelected(false);
        tech_layout.setSelected(true);
        luyuan_layout.setSelected(false);
    }

    private void clickLuyuanTab() {
        luyuanMainFragment = new LuyuanMainFragment();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, luyuanMainFragment);
        fragmentTransaction.commit();

        focusOnLuyuanTab();
    }

    private void focusOnLuyuanTab() {
        home_layout.setSelected(false);
        popular_layout.setSelected(false);
        product_layout.setSelected(false);
        tech_layout.setSelected(false);
        luyuan_layout.setSelected(true);
    }

}