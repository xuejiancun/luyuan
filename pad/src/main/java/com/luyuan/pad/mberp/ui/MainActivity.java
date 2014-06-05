package com.luyuan.pad.mberp.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;
import android.widget.Toast;

import com.luyuan.pad.mberp.R;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    private ArrayList<LinearLayout> tabLayoutList = new ArrayList<LinearLayout>();
    private ArrayList<TextView> tabTextViewList = new ArrayList<TextView>();

    private final String selectedTabColor = "#339900";
    private final String unselectedTabColor = "#FFFFFF";

    private int seletedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        setContentView(R.layout.activity_main);

        initTab();

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
        PopularMainFragment popularMainFragment = new PopularMainFragment();
        rePlaceTabContent(popularMainFragment);
        changeTabBackStyle(tabLayoutList, tabTextViewList, 2);
    }

    private void clickProductTab() {
        ProductHomeFragment productHomeFragment = new ProductHomeFragment();
        rePlaceTabContent(productHomeFragment);
        changeTabBackStyle(tabLayoutList, tabTextViewList, 3);
    }

    private void clickTechTab() {
        TechMainFragment techMainFragment = new TechMainFragment();
        rePlaceTabContent(techMainFragment);
        changeTabBackStyle(tabLayoutList, tabTextViewList, 4);
    }

    private void clickLuyuanTab() {
        LuyuanMainFragment luyuanMainFragment = new LuyuanMainFragment();
        rePlaceTabContent(luyuanMainFragment);
        changeTabBackStyle(tabLayoutList, tabTextViewList, 5);
    }

    private void rePlaceTabContent(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

    private void changeTabBackStyle(ArrayList<LinearLayout> layoutList, ArrayList<TextView> textviewList, int index) {
        seletedIndex = index;
        for (int i = 0; i < layoutList.size(); i++) {
            if (i == index - 1) {
                layoutList.get(i).setSelected(true);
                textviewList.get(i).setTextColor(Color.parseColor(selectedTabColor));
            } else {
                layoutList.get(i).setSelected(false);
                textviewList.get(i).setTextColor(Color.parseColor(unselectedTabColor));
            }
        }
    }

}