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
import com.luyuan.pad.mberp.util.GlobalConstantValues;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ProductMainFragment extends Fragment implements View.OnClickListener {

    private ArrayList<LinearLayout> tabLayoutList = new ArrayList<LinearLayout>();

    private int seletedIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_main, null);

        initTab(view);

        String param = getArguments().getString(GlobalConstantValues.CAR_TYPE);
        if (param.equals(GlobalConstantValues.IMAGE_LUXURY)) {
            clickLuxuryTab();
        } else if (param.equals(GlobalConstantValues.IMAGE_SIMPLE)) {
            clickSimpleTab();
        } else if (param.equals(GlobalConstantValues.IMAGE_STANDARD)) {
            clickStandardTab();
        } else if (param.equals(GlobalConstantValues.IMAGE_BATTERY)) {
            clickBatteryTab();
        } else if (param.equals(GlobalConstantValues.IMAGE_REPLACEWALK)) {
            clickReplacewalkTab();
        } else if (param.equals(GlobalConstantValues.IMAGE_SPECIAL)) {
            clickSpecialTab();
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
                if (seletedIndex != 1) {
                    clickLuxuryTab();
                }
                break;
            case R.id.tab_layout_product_simple:
                if (seletedIndex != 2) {
                    clickSimpleTab();
                }
                break;
            case R.id.tab_layout_product_standard:
                if (seletedIndex != 3) {
                    clickStandardTab();
                }
                break;
            case R.id.tab_layout_product_battery:
                if (seletedIndex != 4) {
                    clickBatteryTab();
                }
                break;
            case R.id.tab_layout_product_replacewalk:
                if (seletedIndex != 5) {
                    clickReplacewalkTab();
                }
                break;
            case R.id.tab_layout_product_special:
                if (seletedIndex != 6) {
                    clickSpecialTab();
                }
                break;
        }
    }

    private void clickLuxuryTab() {
        ProductSubLuxuryFragment productSubLuxuryFragment = new ProductSubLuxuryFragment();
        rePlaceTabContent(productSubLuxuryFragment, GlobalConstantValues.IMAGE_LUXURY);
        changeTabBackStyle(tabLayoutList, 1);
    }

    private void clickSimpleTab() {
        ProductSubSimpleFragment productSubSimpleFragment = new ProductSubSimpleFragment();
        rePlaceTabContent(productSubSimpleFragment, GlobalConstantValues.IMAGE_SIMPLE);
        changeTabBackStyle(tabLayoutList, 2);
    }

    private void clickStandardTab() {
        ProductSubStandardFragment productSubStandardFragment = new ProductSubStandardFragment();
        rePlaceTabContent(productSubStandardFragment, GlobalConstantValues.IMAGE_STANDARD);
        changeTabBackStyle(tabLayoutList, 3);
    }

    private void clickBatteryTab() {
        ProductSubBatteryFragment productSubBatteryFragment = new ProductSubBatteryFragment();
        rePlaceTabContent(productSubBatteryFragment, GlobalConstantValues.IMAGE_BATTERY);
        changeTabBackStyle(tabLayoutList, 4);
    }

    private void clickReplacewalkTab() {
        ProductSubReplacewalkFragment productSubReplacewalkFragment = new ProductSubReplacewalkFragment();
        rePlaceTabContent(productSubReplacewalkFragment, GlobalConstantValues.IMAGE_REPLACEWALK);
        changeTabBackStyle(tabLayoutList, 5);
    }

    private void clickSpecialTab() {
        ProductSubSpecialFragment productSubSpecialFragment = new ProductSubSpecialFragment();
        rePlaceTabContent(productSubSpecialFragment, GlobalConstantValues.IMAGE_SPECIAL);
        changeTabBackStyle(tabLayoutList, 6);
    }

    private void rePlaceTabContent(Fragment fragment, String type) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString(GlobalConstantValues.CAR_TYPE, type);
        fragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

    private void changeTabBackStyle(ArrayList<LinearLayout> layoutList, int index) {
        seletedIndex = index;
        for (int i = 0; i < layoutList.size(); i++) {
            if (i == index - 1) {
                layoutList.get(i).setBackgroundColor(Color.parseColor(GlobalConstantValues.COLOR_TOP_TAB_SELECTED));
            } else {
                layoutList.get(i).setBackgroundColor(Color.parseColor(GlobalConstantValues.COLOR_TOP_TAB_UNSELECTED));
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}