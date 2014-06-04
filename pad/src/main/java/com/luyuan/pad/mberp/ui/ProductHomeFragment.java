package com.luyuan.pad.mberp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.luyuan.pad.mberp.R;

public class ProductHomeFragment extends Fragment implements View.OnClickListener {

    private ProductMainFragment productMainFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_home, container, false);

        Button luxury = (Button) view.findViewById(R.id.button_product_main_page_luxury);
        luxury.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_product_main_page_luxury:
                onClickLuxury();
                break;
            case R.id.button_product_main_page_battery:
                break;
            case R.id.button_product_main_page_replacewalk:
                break;
            case R.id.button_product_main_page_special:
                break;
            case R.id.button_product_main_page_standard:
                break;
            case R.id.button_product_main_page_simple:
                break;
        }
    }

    public void onClickLuxury() {
        productMainFragment = new ProductMainFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, productMainFragment);
        fragmentTransaction.commit();
    }

}