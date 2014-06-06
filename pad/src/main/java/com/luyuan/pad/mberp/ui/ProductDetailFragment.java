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

public class ProductDetailFragment extends Fragment implements View.OnClickListener {

    private ProductDetailAppearanceFragment productDetailAppearanceFragment;
    private ProductDetailInfoFragment productDetailInfoFragment;
    private ProductDetailColorFragment productDetailColorFragment;
    private ProductDetailTechFragment productDetailTechFragment;
    private ProductDetailEquipmentFragment productDetailEquipmentFragment;

    private ArrayList<LinearLayout> tabLayoutList = new ArrayList<LinearLayout>();

    private final String selectedTabColor = "#4C7F20";
    private final String unselectedTabColor = "#99C741";

    private int seletedIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, null);

        initTab(view);

        clickCarAppearanceTab();

        return view;
    }

    private void initTab(View view) {
        LinearLayout tab_car_appearance_layout = (LinearLayout) view.findViewById(R.id.tab_car_appearance_layout);
        LinearLayout tab_car_detail_layout = (LinearLayout) view.findViewById(R.id.tab_car_detail_layout);
        LinearLayout tab_optional_color_layout = (LinearLayout) view.findViewById(R.id.tab_optional_color_layout);
        LinearLayout tab_car_equipment_layout = (LinearLayout) view.findViewById(R.id.tab_car_equipment_layout);
        LinearLayout tab_car_tech_layout = (LinearLayout) view.findViewById(R.id.tab_car_equipment_layout);
        LinearLayout tab_back_layout = (LinearLayout) view.findViewById(R.id.tab_back_layout);

        tab_car_appearance_layout.setOnClickListener(this);
        tab_car_detail_layout.setOnClickListener(this);
        tab_optional_color_layout.setOnClickListener(this);
        tab_car_equipment_layout.setOnClickListener(this);
        tab_car_tech_layout.setOnClickListener(this);
        tab_back_layout.setOnClickListener(this);

        tabLayoutList.add(tab_car_appearance_layout);
        tabLayoutList.add(tab_car_detail_layout);
        tabLayoutList.add(tab_optional_color_layout);
        tabLayoutList.add(tab_car_equipment_layout);
        tabLayoutList.add(tab_car_tech_layout);
        tabLayoutList.add(tab_back_layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_car_appearance_layout:
                if (seletedIndex != 1) {
                    clickCarAppearanceTab();
                }
                break;
            case R.id.tab_car_detail_layout:
                if (seletedIndex != 2) {
                    clickCarDetailTab();
                }
                break;
            case R.id.tab_optional_color_layout:
                if (seletedIndex != 3) {
                    clickCarColorTab();
                }
                break;
            case R.id.tab_car_equipment_layout:
                if (seletedIndex != 4) {
                    clickCarTechTab();
                }
                break;
            case R.id.tab_car_tech_layout:
                if (seletedIndex != 5) {
                    clickCarEquipmentTab();
                }
                break;
            case R.id.tab_back_layout:
                if (seletedIndex != 6) {
                    clickBackTab();
                }
                break;
        }
    }

    private void clickCarAppearanceTab() {
        if (productDetailAppearanceFragment == null) {
            productDetailAppearanceFragment = new ProductDetailAppearanceFragment();
        }
        rePlaceTabContent(productDetailAppearanceFragment);
        changeTabBackStyle(tabLayoutList, 1);
    }

    private void clickCarDetailTab() {
        if (productDetailInfoFragment == null) {
            productDetailInfoFragment = new ProductDetailInfoFragment();
        }
        rePlaceTabContent(productDetailInfoFragment);
        changeTabBackStyle(tabLayoutList, 2);
    }

    private void clickCarColorTab() {
        if (productDetailColorFragment == null) {
            productDetailColorFragment = new ProductDetailColorFragment();
        }
        rePlaceTabContent(productDetailColorFragment);
        changeTabBackStyle(tabLayoutList, 3);
    }

    private void clickCarTechTab() {
        if (productDetailTechFragment == null) {
            productDetailTechFragment = new ProductDetailTechFragment();
        }
        rePlaceTabContent(productDetailTechFragment);
        changeTabBackStyle(tabLayoutList, 4);
    }

    private void clickCarEquipmentTab() {
        if (productDetailEquipmentFragment == null) {
            productDetailEquipmentFragment = new ProductDetailEquipmentFragment();
        }
        rePlaceTabContent(productDetailEquipmentFragment);
        changeTabBackStyle(tabLayoutList, 5);
    }

    private void clickBackTab() {
        ProductMainFragment productMainFragment = new ProductMainFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString("type", getArguments().getString("type"));
        productMainFragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, productMainFragment);
        fragmentTransaction.commit();
    }

    private void rePlaceTabContent(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
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