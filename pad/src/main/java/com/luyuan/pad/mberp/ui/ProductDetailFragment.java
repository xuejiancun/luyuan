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

public class ProductDetailFragment extends Fragment implements View.OnClickListener {

    private ProductDetailAppearanceFragment productDetailAppearanceFragment;

    private LinearLayout tab_car_appearance_layout, tab_car_detail_layout, tab_optional_color_layout, tab_car_tech_layout,
            tab_car_equipment_layout, tab_back_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, null);

        initTab(view);

        clickCarAppearanceTab();

        return view;
    }

    private void initTab(View view) {
        tab_car_appearance_layout = (LinearLayout) view.findViewById(R.id.tab_car_appearance_layout);
        tab_car_detail_layout = (LinearLayout) view.findViewById(R.id.tab_car_detail_layout);
        tab_optional_color_layout = (LinearLayout) view.findViewById(R.id.tab_optional_color_layout);
        tab_car_equipment_layout = (LinearLayout) view.findViewById(R.id.tab_car_equipment_layout);
        tab_car_tech_layout = (LinearLayout) view.findViewById(R.id.tab_car_equipment_layout);
        tab_back_layout = (LinearLayout) view.findViewById(R.id.tab_back_layout);

        tab_car_appearance_layout.setOnClickListener(this);
        tab_car_detail_layout.setOnClickListener(this);
        tab_optional_color_layout.setOnClickListener(this);
        tab_car_equipment_layout.setOnClickListener(this);
        tab_car_tech_layout.setOnClickListener(this);
        tab_back_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_car_appearance_layout:
                clickCarAppearanceTab();
                break;
            case R.id.tab_car_detail_layout:
                // clickCarAppearanceTab();
                break;
            case R.id.tab_optional_color_layout:
                // clickCarAppearanceTab();
                break;
            case R.id.tab_car_equipment_layout:
                // clickCarAppearanceTab();
                break;
            case R.id.tab_car_tech_layout:
                // clickCarAppearanceTab();
                break;
            case R.id.tab_back_layout:
                clickBackTab();
                break;
        }
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

    private void clickCarAppearanceTab() {
        productDetailAppearanceFragment = new ProductDetailAppearanceFragment();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, productDetailAppearanceFragment);
        fragmentTransaction.commit();

        focusOnCarAppearanceTab();
    }

    private void focusOnCarAppearanceTab() {
        tab_car_appearance_layout.setBackgroundColor(Color.parseColor("#4C7F20"));
        tab_car_detail_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_optional_color_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_car_equipment_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_car_tech_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_back_layout.setBackgroundColor(Color.parseColor("#99C741"));
    }

}