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

        Button simple = (Button) view.findViewById(R.id.button_product_main_page_simple);
        simple.setOnClickListener(this);

        Button standard = (Button) view.findViewById(R.id.button_product_main_page_standard);
        standard.setOnClickListener(this);

        Button battery = (Button) view.findViewById(R.id.button_product_main_page_battery);
        battery.setOnClickListener(this);

        Button replacewalk = (Button) view.findViewById(R.id.button_product_main_page_replacewalk);
        replacewalk.setOnClickListener(this);

        Button special = (Button) view.findViewById(R.id.button_product_main_page_special);
        special.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_product_main_page_luxury:
                onClickChooseCar("luxury");
                break;
            case R.id.button_product_main_page_simple:
                onClickChooseCar("simple");
                break;
            case R.id.button_product_main_page_standard:
                onClickChooseCar("standard");
                break;
            case R.id.button_product_main_page_battery:
                onClickChooseCar("battery");
                break;
            case R.id.button_product_main_page_replacewalk:
                onClickChooseCar("replacewalk");
                break;
            case R.id.button_product_main_page_special:
                onClickChooseCar("special");
                break;
        }
    }

    public void onClickChooseCar(String type) {
        productMainFragment = new ProductMainFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString("type", type);
        productMainFragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, productMainFragment);
        fragmentTransaction.commit();
    }

}