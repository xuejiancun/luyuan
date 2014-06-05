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

import java.util.ArrayList;

public class ProductMainFragment extends Fragment implements View.OnClickListener {

    private ProductSubLuxuryFragment productSubLuxuryFragment;
    private ProductSubSimpleFragment productSubSimpleFragment;
    private ProductSubStandardFragment productSubStandardFragment;
    private ProductSubBatteryFragment productSubBatteryFragment;
    private ProductSubReplacewalkFragment productSubReplacewalkFragment;
    private ProductSubSpecialFragment productSubSpecialFragment;

    private ArrayList<LinearLayout> tabLayoutList = new ArrayList<LinearLayout>();

    private final String selectedTabColor = "#4C7F20";
    private final String unselectedTabColor = "#99C741";

    private int seletedIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_main, container, false);

        initTab(view);

        String param = getArguments().getString("type");
        if (param.equals("luxury")) {
            if (seletedIndex != 1) {
                clickLuxuryTab();
            }
        } else if (param.equals("simple")) {
            if (seletedIndex != 2) {
                clickSimpleTab();
            }
        } else if (param.equals("standard")) {
            if (seletedIndex != 3) {
                clickStandardTab();
            }
        } else if (param.equals("battery")) {
            if (seletedIndex != 4) {
                clickBatteryTab();
            }
        } else if (param.equals("replacewalk")) {
            if (seletedIndex != 5) {
                clickReplacewalkTab();
            }
        } else if (param.equals("special")) {
            if (seletedIndex != 5) {
                clickSpecialTab();
            }
        } else {
            clickLuxuryTab();
        }

        return view;
    }

    private void initTab(View view) {
        LinearLayout tab_luxury_layout = (LinearLayout) view.findViewById(R.id.tab_layout_product_luxury);
        LinearLayout tab_simple_layout = (LinearLayout) view.findViewById(R.id.tab_layout_product_simple);
        LinearLayout tab_standard_layout = (LinearLayout) view.findViewById(R.id.tab_layout_product_standard);
        LinearLayout tab_battery_layout = (LinearLayout) view.findViewById(R.id.tab_layout_product_battery);
        LinearLayout tab_replacewalk_layout = (LinearLayout) view.findViewById(R.id.tab_layout_product_replacewalk);
        LinearLayout tab_special_layout = (LinearLayout) view.findViewById(R.id.tab_layout_product_special);

        tab_luxury_layout.setOnClickListener(this);
        tab_simple_layout.setOnClickListener(this);
        tab_standard_layout.setOnClickListener(this);
        tab_battery_layout.setOnClickListener(this);
        tab_replacewalk_layout.setOnClickListener(this);
        tab_special_layout.setOnClickListener(this);

        tabLayoutList.add(tab_luxury_layout);
        tabLayoutList.add(tab_simple_layout);
        tabLayoutList.add(tab_standard_layout);
        tabLayoutList.add(tab_battery_layout);
        tabLayoutList.add(tab_replacewalk_layout);
        tabLayoutList.add(tab_special_layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_layout_product_luxury:
                clickLuxuryTab();
                break;
            case R.id.tab_layout_product_simple:
                clickSimpleTab();
                break;
            case R.id.tab_layout_product_standard:
                clickStandardTab();
                break;
            case R.id.tab_layout_product_battery:
                clickBatteryTab();
                break;
            case R.id.tab_layout_product_replacewalk:
                clickReplacewalkTab();
                break;
            case R.id.tab_layout_product_special:
                clickSpecialTab();
                break;
        }
    }

    private void clickLuxuryTab() {
        if (productSubLuxuryFragment == null) {
            productSubLuxuryFragment = new ProductSubLuxuryFragment();
        }
        rePlaceTabContent(productSubLuxuryFragment, "luxury");
        changeTabBackStyle(tabLayoutList, 1);
    }

    private void clickSimpleTab() {
        if (productSubSimpleFragment == null) {
            productSubSimpleFragment = new ProductSubSimpleFragment();
        }
        rePlaceTabContent(productSubSimpleFragment, "simple");
        changeTabBackStyle(tabLayoutList, 2);
    }

    private void clickStandardTab() {
        if (productSubStandardFragment == null) {
            productSubStandardFragment = new ProductSubStandardFragment();
        }
        rePlaceTabContent(productSubStandardFragment, "standard");
        changeTabBackStyle(tabLayoutList, 3);
    }

    private void clickBatteryTab() {
        if (productSubBatteryFragment == null) {
            productSubBatteryFragment = new ProductSubBatteryFragment();
        }
        rePlaceTabContent(productSubBatteryFragment, "battery");
        changeTabBackStyle(tabLayoutList, 4);
    }

    private void clickReplacewalkTab() {
        if (productSubReplacewalkFragment == null) {
            productSubReplacewalkFragment = new ProductSubReplacewalkFragment();
        }
        rePlaceTabContent(productSubReplacewalkFragment, "replacewalk");
        changeTabBackStyle(tabLayoutList, 5);
    }

    private void clickSpecialTab() {
        if (productSubSpecialFragment == null) {
            productSubSpecialFragment = new ProductSubSpecialFragment();
        }
        rePlaceTabContent(productSubSpecialFragment, "special");
        changeTabBackStyle(tabLayoutList, 6);
    }

    private void rePlaceTabContent(Fragment fragment, String type) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

    private void changeTabBackStyle(ArrayList<LinearLayout> layoutList, int index) {
        seletedIndex = index;
        for (int i = 0; i < layoutList.size(); i++) {
            if (i == index - 1) {
                layoutList.get(i).setBackgroundColor(Color.parseColor(selectedTabColor));
            } else {
                layoutList.get(i).setBackgroundColor(Color.parseColor(unselectedTabColor));
            }
        }
    }

}