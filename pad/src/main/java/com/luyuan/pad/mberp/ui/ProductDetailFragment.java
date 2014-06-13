package com.luyuan.pad.mberp.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.model.CarAppearanceSlide;
import com.luyuan.pad.mberp.model.ProductDetailData;
import com.luyuan.pad.mberp.util.GlobalConstantValues;
import com.luyuan.pad.mberp.util.GsonRequest;
import com.luyuan.pad.mberp.util.RequestManager;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ProductDetailFragment extends Fragment implements View.OnClickListener {

    private ArrayList<LinearLayout> tabLayoutList = new ArrayList<LinearLayout>();

    private String model;
    private ProductDetailData productDetailData;
    private ArrayList<String> carAppearanceArrayList = new ArrayList<String>();

    private int seletedIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, null);

        initTab(view);

        Bundle args = getArguments();
        if (args != null) {
            model = args.getString(GlobalConstantValues.PARAM_CAR_MODEL);
            fetchProductDetailData();
        }

        clickCarAppearanceTab();

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
        rePlaceTabContentForSlide(imagePagerFragment, carAppearanceArrayList);
        changeTabBackStyle(tabLayoutList, 1);
    }

    private void clickCarDetailTab() {
//        ImagePagerFragment imagePagerFragment = new ImagePagerFragment();
//        rePlaceTabContentForSlide(imagePagerFragment, GlobalConstantValues.IMAGE_CAR_DETAIL, 7);
//        changeTabBackStyle(tabLayoutList, 2);
    }

    private void clickCarColorTab() {
//        ImagePagerFragment imagePagerFragment = new ImagePagerFragment();
//        rePlaceTabContentForSlide(imagePagerFragment, GlobalConstantValues.IMAGE_CAR_COLOR, 7);
//        changeTabBackStyle(tabLayoutList, 3);
    }

    private void clickCarTechTab() {
        ProductDetailTechFragment productDetailTechFragment = new ProductDetailTechFragment();
        rePlaceTabContent(productDetailTechFragment);
        changeTabBackStyle(tabLayoutList, 4);
    }

    private void clickCarEquipmentTab() {
        ProductDetailEquipmentFragment productDetailEquipmentFragment = new ProductDetailEquipmentFragment();
        rePlaceTabContent(productDetailEquipmentFragment);
        changeTabBackStyle(tabLayoutList, 5);
    }

    private void clickBackTab() {
        ProductMainFragment productMainFragment = new ProductMainFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString(GlobalConstantValues.PARAM_CAR_TYPE, getArguments().getString(GlobalConstantValues.PARAM_CAR_TYPE));
        productMainFragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, productMainFragment);
        fragmentTransaction.commit();
    }

    private void rePlaceTabContent(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

    private void rePlaceTabContentForSlide(Fragment fragment, ArrayList<String> urls) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        Bundle args = new Bundle();
       // args.putStringArrayList(GlobalConstantValues.PARAM_IMAGE_URLS, urls);
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

    public void fetchProductDetailData() {
        GsonRequest gsonObjRequest = new GsonRequest<ProductDetailData>(Request.Method.GET, GlobalConstantValues.API_PRODUCT_THUMB_LUXURY,
                ProductDetailData.class, new Response.Listener<ProductDetailData>() {
            @Override
            public void onResponse(ProductDetailData response) {
                productDetailData = response;
                getCarAppearanceArrayList();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );

        RequestManager.getRequestQueue().add(gsonObjRequest);
    }

    public ArrayList<String> getCarAppearanceArrayList() {
        ArrayList<String> result = new ArrayList<String>();
        for (CarAppearanceSlide carAppearanceSlide : productDetailData.getCarAppearanceSlides()) {
            result.add(carAppearanceSlide.getUrl());
        }
        return result;
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