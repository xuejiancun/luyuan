package com.luyuan.pad.mberp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.util.GlobalConstantValues;

public class TechMainFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tech_main, null);

        Button tech1 = (Button) view.findViewById(R.id.button_tech_main_page_tech1);
        tech1.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_tech_main_page_tech1:
                ImagePagerFragment imagePagerFragment = new ImagePagerFragment();
                rePlaceTabContentForSlide(imagePagerFragment, GlobalConstantValues.IMAGE_COLOR, 7);
                break;
        }
    }

    private void rePlaceTabContentForSlide(Fragment fragment, String type, int num) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString(GlobalConstantValues.IMAGE_TYPE, type);
        args.putInt(GlobalConstantValues.IMAGE_NUM, num);
        fragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

}