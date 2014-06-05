package com.luyuan.pad.mberp.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.luyuan.pad.mberp.R;

public class ProductMainFragment extends Fragment implements View.OnClickListener {

    private ProductSubFirstFragment productSubFirstFragment;
    private SettingFragment settingFragment;

    private LinearLayout tab_luxury_layout, tab_simple_layout, tab_standard_layout,
            tab_battery_layout, tab_replacewalk_layout, tab_special_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_main, container, false);

        initView(view);
        initData();

        String param = getArguments().toString();
        if (param.equals("luxury")) {
            clickLuxuryTab();
        } else if (param.equals("battery")) {
            clickBatteryTab();
        } else {

        }

        return view;
    }

    private void initData() {
        tab_luxury_layout.setOnClickListener(this);
        tab_simple_layout.setOnClickListener(this);
        tab_standard_layout.setOnClickListener(this);
        tab_battery_layout.setOnClickListener(this);
        tab_replacewalk_layout.setOnClickListener(this);
        tab_special_layout.setOnClickListener(this);
    }

    private void initView(View view) {
        tab_luxury_layout = (LinearLayout) view.findViewById(R.id.tab_layout_product_luxury);
        tab_simple_layout = (LinearLayout) view.findViewById(R.id.tab_layout_product_simple);
        tab_standard_layout = (LinearLayout) view.findViewById(R.id.tab_layout_product_standard);
        tab_battery_layout = (LinearLayout) view.findViewById(R.id.tab_layout_product_battery);
        tab_replacewalk_layout = (LinearLayout) view.findViewById(R.id.tab_layout_product_replacewalk);
        tab_special_layout = (LinearLayout) view.findViewById(R.id.tab_layout_product_special);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_layout_product_luxury:
                clickLuxuryTab();
                break;
            case R.id.tab_layout_product_simple:
                clickBatteryTab();
                break;
            case R.id.tab_layout_product_standard:
                // clickLuxuryTab();
                break;
            case R.id.tab_layout_product_battery:
                // clickLuxuryTab();
                break;
            case R.id.tab_layout_product_replacewalk:
                // clickLuxuryTab();
                break;
            case R.id.tab_layout_product_special:
                // clickLuxuryTab();
                break;
        }
    }

    private void clickLuxuryTab() {
        productSubFirstFragment = new ProductSubFirstFragment();
        FragmentTransaction fragmentTransaction = this.getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, productSubFirstFragment);
        fragmentTransaction.commit();

        focusOnLuxuryTab();
    }

    private void focusOnLuxuryTab() {
        tab_luxury_layout.setBackgroundColor(Color.parseColor("#4C7F20"));
        tab_simple_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_standard_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_special_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_replacewalk_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_special_layout.setBackgroundColor(Color.parseColor("#99C741"));
    }

    private void clickBatteryTab() {
        settingFragment = new SettingFragment();
        FragmentTransaction fragmentTransaction = this.getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, settingFragment);
        fragmentTransaction.commit();

        focusOnSimpleTab();
    }

    private void focusOnSimpleTab() {
        tab_luxury_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_simple_layout.setBackgroundColor(Color.parseColor("#4C7F20"));
        tab_standard_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_special_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_replacewalk_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_special_layout.setBackgroundColor(Color.parseColor("#99C741"));
    }

}