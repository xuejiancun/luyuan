package com.luyuan.mobile.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.luyuan.mobile.R;
import com.luyuan.mobile.function.WarehouseVoucherManagerActivity;

public class FunctionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.function_list_item, null);

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout_function_item);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), WarehouseVoucherManagerActivity.class);
                startActivity(intent);
            }
        });

        return view;


    }
}
