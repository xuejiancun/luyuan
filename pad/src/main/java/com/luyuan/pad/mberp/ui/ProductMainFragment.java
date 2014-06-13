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

        String param = getArguments().getString(GlobalConstantValues.PARAM_CAR_TYPE);
        clickTab(param);

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

        // Plz do not change the order
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
                    clickTab(GlobalConstantValues.TAB_LUXURY_CAR);
                }
                break;
            case R.id.tab_layout_product_simple:
                if (seletedIndex != 2) {
                    clickTab(GlobalConstantValues.TAB_SIMPLE_CAR);
                }
                break;
            case R.id.tab_layout_product_standard:
                if (seletedIndex != 3) {
                    clickTab(GlobalConstantValues.TAB_STANDARD_CAR);
                }
                break;
            case R.id.tab_layout_product_battery:
                if (seletedIndex != 4) {
                    clickTab(GlobalConstantValues.TAB_BATTERY_CAR);
                }
                break;
            case R.id.tab_layout_product_replacewalk:
                if (seletedIndex != 5) {
                    clickTab(GlobalConstantValues.TAB_REPLACEWALK_CAR);
                }
                break;
            case R.id.tab_layout_product_special:
                if (seletedIndex != 6) {
                    clickTab(GlobalConstantValues.TAB_SPECIAL_CAR);
                }
                break;
        }
    }

    private void clickTab(String type) {
        ProductSubFragment productSubFragment = new ProductSubFragment();
        rePlaceTabContent(productSubFragment, type);
        changeTabBackStyle(tabLayoutList, getIndex(type));
    }

    private void rePlaceTabContent(Fragment fragment, String type) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString(GlobalConstantValues.PARAM_API_URL, getApiUrl(type));
        fragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

    private int getIndex(String type) {
        int result = 1;
        if (type.equals(GlobalConstantValues.TAB_LUXURY_CAR)) {
            result = 1;
        } else if (type.equals(GlobalConstantValues.TAB_SIMPLE_CAR)) {
            result = 2;
        } else if (type.equals(GlobalConstantValues.TAB_STANDARD_CAR)) {
            result = 3;
        } else if (type.equals(GlobalConstantValues.TAB_BATTERY_CAR)) {
            result = 4;
        } else if (type.equals(GlobalConstantValues.TAB_REPLACEWALK_CAR)) {
            result = 5;
        } else if (type.equals(GlobalConstantValues.TAB_SPECIAL_CAR)) {
            result = 6;
        }
        return result;
    }

    private String getApiUrl(String type) {
        String result = GlobalConstantValues.API_PRODUCT_THUMB_LUXURY;
        if (type.equals(GlobalConstantValues.TAB_LUXURY_CAR)) {
            result = GlobalConstantValues.API_PRODUCT_THUMB_LUXURY;
        } else if (type.equals(GlobalConstantValues.TAB_SIMPLE_CAR)) {
            result = GlobalConstantValues.API_PRODUCT_THUMB_SIMPLE;
        } else if (type.equals(GlobalConstantValues.TAB_STANDARD_CAR)) {
            result = GlobalConstantValues.API_PRODUCT_THUMB_STANDARD;
        } else if (type.equals(GlobalConstantValues.TAB_BATTERY_CAR)) {
            result = GlobalConstantValues.API_PRODUCT_THUMB_BATTERY;
        } else if (type.equals(GlobalConstantValues.TAB_REPLACEWALK_CAR)) {
            result = GlobalConstantValues.API_PRODUCT_THUMB_REPLACEWALK;
        } else if (type.equals(GlobalConstantValues.TAB_SPECIAL_CAR)) {
            result = GlobalConstantValues.API_PRODUCT_THUMB_SPECIAL;
        }
        return result;
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