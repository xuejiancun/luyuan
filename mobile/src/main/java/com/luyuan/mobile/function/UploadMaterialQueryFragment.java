package com.luyuan.mobile.function;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.luyuan.mobile.R;

public class UploadMaterialQueryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_material_query, null);

        // cancel material
        ((Button) view.findViewById(R.id.button_cancel_upload_material_query)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO warning
            }
        });

        // back to new material fragment
        ((Button) view.findViewById(R.id.button_back_upload_material_query)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceTabContent(new UploadMaterialChannelFragment());
            }
        });

        return view;
    }

    private void replaceTabContent(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.frame_content_upload_material, fragment);
        fragmentTransaction.commit();
    }

}