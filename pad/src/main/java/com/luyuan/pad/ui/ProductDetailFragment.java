package com.luyuan.pad.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.luyuan.pad.R;
import com.luyuan.pad.util.GlobalConstantValues;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ProductDetailFragment extends Fragment implements View.OnClickListener {

    private String model = "";
    private String type = "";
    private int seletedIndex;

    private ArrayList<LinearLayout> tabLayoutList = new ArrayList<LinearLayout>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_product_detail, null);

        initTab(view);

        Bundle args = getArguments();
        if (args != null) {
            if (args.getString(GlobalConstantValues.PARAM_CAR_MODEL) != null) {
                model = args.getString(GlobalConstantValues.PARAM_CAR_MODEL);
            }
            if (args.getString(GlobalConstantValues.PARAM_CAR_TYPE) != null) {
                type = args.getString(GlobalConstantValues.PARAM_CAR_TYPE);
            }
            clickCarAppearanceTab();
        } else {
            Dialog alertDialog = new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.app_param_error)
                    .setTitle(R.string.dialog_hint)
                    .setPositiveButton(R.string.dialog_confirm, null)
                    .create();
            alertDialog.show();
        }

        return view;
    }

    private void initTab(View view) {
        LinearLayout tab_car_appearance_layout = (LinearLayout) view.findViewById(R.id.tab_car_appearance_layout);
        LinearLayout tab_car_detail_layout = (LinearLayout) view.findViewById(R.id.tab_car_detail_layout);
        LinearLayout tab_optional_color_layout = (LinearLayout) view.findViewById(R.id.tab_optional_color_layout);
        LinearLayout tab_car_tech_layout = (LinearLayout) view.findViewById(R.id.tab_car_tech_layout);
        LinearLayout tab_car_equipment_layout = (LinearLayout) view.findViewById(R.id.tab_car_equipment_layout);
        LinearLayout tab_back_layout = (LinearLayout) view.findViewById(R.id.tab_back_layout);

        tab_car_appearance_layout.setOnClickListener(this);
        tab_car_detail_layout.setOnClickListener(this);
        tab_optional_color_layout.setOnClickListener(this);
        tab_car_equipment_layout.setOnClickListener(this);
        tab_car_tech_layout.setOnClickListener(this);
        tab_back_layout.setOnClickListener(this);

        // Do not change the order
        tabLayoutList.add(tab_car_appearance_layout);
        tabLayoutList.add(tab_car_detail_layout);
        tabLayoutList.add(tab_optional_color_layout);
        tabLayoutList.add(tab_car_tech_layout);
        tabLayoutList.add(tab_car_equipment_layout);
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
            case R.id.tab_car_tech_layout:
                if (seletedIndex != 4) {
                    clickCarTechTab();
                }
                break;
            case R.id.tab_car_equipment_layout:
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
        ImagePagerFragment imagePagerFragment = new ImagePagerFragment();
        rePlaceTabContentForSlide(imagePagerFragment, GlobalConstantValues.API_CAR_APPEARANCE + "&model=" + model.trim());
        changeTabBackStyle(tabLayoutList, 1);
    }

    private void clickCarDetailTab() {
        ImagePagerFragment imagePagerFragment = new ImagePagerFragment();
        rePlaceTabContentForSlide(imagePagerFragment, GlobalConstantValues.API_CAR_DETAIL + "&model=" + model.trim());
        changeTabBackStyle(tabLayoutList, 2);
    }

    private void clickCarColorTab() {
        ImagePagerFragment imagePagerFragment = new ImagePagerFragment();
        rePlaceTabContentForSlide(imagePagerFragment, GlobalConstantValues.API_CAR_COLOR + "&model=" + model.trim());
        changeTabBackStyle(tabLayoutList, 3);
    }

    private void clickCarTechTab() {
        ProductDetailTechFragment productDetailTechFragment = new ProductDetailTechFragment();
        rePlaceTabContent(productDetailTechFragment, model);
        changeTabBackStyle(tabLayoutList, 4);
    }

    private void clickCarEquipmentTab() {
        ProductDetailEquipmentFragment productDetailEquipmentFragment = new ProductDetailEquipmentFragment();
        rePlaceTabContent(productDetailEquipmentFragment, model);
        changeTabBackStyle(tabLayoutList, 5);
    }

    private void clickBackTab() {
        ProductMainFragment productMainFragment = new ProductMainFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString(GlobalConstantValues.PARAM_CAR_TYPE, type);
        productMainFragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, productMainFragment);
        fragmentTransaction.commit();
    }

    private void rePlaceTabContent(Fragment fragment, String model) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString(GlobalConstantValues.PARAM_CAR_MODEL, model);
        fragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

    private void rePlaceTabContentForSlide(Fragment fragment, String api) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString(GlobalConstantValues.PARAM_API_URL, api);
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