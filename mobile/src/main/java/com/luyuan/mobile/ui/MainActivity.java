package com.luyuan.mobile.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luyuan.mobile.R;
import com.luyuan.mobile.util.GlobalConstantValues;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    private int seletedIndex;

    private ArrayList<LinearLayout> tabLayoutList = new ArrayList<LinearLayout>();
    private ArrayList<TextView> tabTextViewList = new ArrayList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);

        setContentView(R.layout.activity_main);

        initTab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem actionItem = menu.add("Refresh");
        actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        actionItem.setIcon(R.drawable.ic_action_refresh);

        return true;
    }

    private void initTab() {
        LinearLayout layout_tab_home = (LinearLayout) findViewById(R.id.layout_tab_home);
        LinearLayout layout_tab_function = (LinearLayout) findViewById(R.id.layout_tab_function);
        LinearLayout layout_tab_explore = (LinearLayout) findViewById(R.id.layout_tab_explore);
        LinearLayout layout_tab_account = (LinearLayout) findViewById(R.id.layout_tab_account);

        layout_tab_home.setOnClickListener(this);
        layout_tab_function.setOnClickListener(this);
        layout_tab_explore.setOnClickListener(this);
        layout_tab_account.setOnClickListener(this);

        // do not change the order
        tabLayoutList.add(layout_tab_home);
        tabLayoutList.add(layout_tab_function);
        tabLayoutList.add(layout_tab_explore);
        tabLayoutList.add(layout_tab_account);

        TextView textview_tab_home = (TextView) findViewById(R.id.textview_tab_home);
        TextView textview_tab_function = (TextView) findViewById(R.id.textview_tab_function);
        TextView textview_tab_explore = (TextView) findViewById(R.id.textview_tab_explore);
        TextView textview_tab_account = (TextView) findViewById(R.id.textview_tab_account);

        // do not change the order
        tabTextViewList.add(textview_tab_home);
        tabTextViewList.add(textview_tab_function);
        tabTextViewList.add(textview_tab_explore);
        tabTextViewList.add(textview_tab_account);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_tab_home:
                if (seletedIndex != 1) {
                    // TODO
                    changeTabBackStyle(1);
                }
                break;
            case R.id.layout_tab_function:
                if (seletedIndex != 2) {
                    // TODO
                    changeTabBackStyle(2);
                }
                break;
            case R.id.layout_tab_explore:
                if (seletedIndex != 3) {
                    // TODO
                    changeTabBackStyle(3);
                }
                break;
            case R.id.layout_tab_account:
                if (seletedIndex != 4) {
                    // TODO
                    changeTabBackStyle(4);
                }
                break;
        }
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
