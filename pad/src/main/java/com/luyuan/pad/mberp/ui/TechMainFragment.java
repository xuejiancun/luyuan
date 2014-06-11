package com.luyuan.pad.mberp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.model.TechImageData;
import com.luyuan.pad.mberp.model.TechImageSlide;
import com.luyuan.pad.mberp.util.GlobalConstantValues;
import com.luyuan.pad.mberp.util.GsonRequest;
import com.luyuan.pad.mberp.util.RequestManager;

import java.util.ArrayList;

public class TechMainFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tech_main, null);

        Button tech1 = (Button) view.findViewById(R.id.button_tech_main_page_tech1);
        Button tech2 = (Button) view.findViewById(R.id.button_tech_main_page_tech2);
        Button tech3 = (Button) view.findViewById(R.id.button_tech_main_page_tech3);
        Button tech4 = (Button) view.findViewById(R.id.button_tech_main_page_tech4);
        Button tech5 = (Button) view.findViewById(R.id.button_tech_main_page_tech5);
        Button tech6 = (Button) view.findViewById(R.id.button_tech_main_page_tech6);
        Button tech7 = (Button) view.findViewById(R.id.button_tech_main_page_tech7);
        Button tech8 = (Button) view.findViewById(R.id.button_tech_main_page_tech8);
        Button tech9 = (Button) view.findViewById(R.id.button_tech_main_page_tech9);
        Button tech10 = (Button) view.findViewById(R.id.button_tech_main_page_tech10);
        Button tech11 = (Button) view.findViewById(R.id.button_tech_main_page_tech11);
        Button tech12 = (Button) view.findViewById(R.id.button_tech_main_page_tech12);
        Button tech13 = (Button) view.findViewById(R.id.button_tech_main_page_tech13);
        Button tech14 = (Button) view.findViewById(R.id.button_tech_main_page_tech14);
        Button tech15 = (Button) view.findViewById(R.id.button_tech_main_page_tech15);
        Button tech16 = (Button) view.findViewById(R.id.button_tech_main_page_tech16);
        Button tech17 = (Button) view.findViewById(R.id.button_tech_main_page_tech17);
        Button tech18 = (Button) view.findViewById(R.id.button_tech_main_page_tech18);

        tech1.setOnClickListener(this);
        tech2.setOnClickListener(this);
        tech3.setOnClickListener(this);
        tech4.setOnClickListener(this);
        tech5.setOnClickListener(this);
        tech6.setOnClickListener(this);
        tech7.setOnClickListener(this);
        tech8.setOnClickListener(this);
        tech9.setOnClickListener(this);
        tech10.setOnClickListener(this);
        tech11.setOnClickListener(this);
        tech12.setOnClickListener(this);
        tech13.setOnClickListener(this);
        tech14.setOnClickListener(this);
        tech15.setOnClickListener(this);
        tech16.setOnClickListener(this);
        tech17.setOnClickListener(this);
        tech18.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_tech_main_page_tech1:
                GsonRequest gsonObjRequest = new GsonRequest<TechImageData>(Request.Method.GET, GlobalConstantValues.API_TECH_IMAGE,
                        TechImageData.class, new Response.Listener<TechImageData>() {
                    @Override
                    public void onResponse(TechImageData response) {
                        if (response != null && response.getSuccess().equals("true")) {
                            ImagePagerFragment imagePagerFragment = new ImagePagerFragment();
                            rePlaceTabContentForSlide(imagePagerFragment, getTechImageUrls(response));
                        } else {
                            // TODO show another fragment
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
                );

                RequestManager.getRequestQueue().add(gsonObjRequest);
                break;
        }
    }

    private void rePlaceTabContentForSlide(Fragment fragment, ArrayList<String> urls) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putStringArrayList(GlobalConstantValues.PARAM_IMAGE_URLS, urls);
        fragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

    public ArrayList<String> getTechImageUrls(TechImageData techImageData) {
        ArrayList<String> result = new ArrayList<String>();
        for (TechImageSlide techImageSlide : techImageData.getTechImageSlides()) {
            result.add(techImageSlide.getUrl());
        }
        return result;
    }

}