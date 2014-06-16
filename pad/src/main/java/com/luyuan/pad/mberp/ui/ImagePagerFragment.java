package com.luyuan.pad.mberp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.model.ImagePager;
import com.luyuan.pad.mberp.model.ImageSlide;
import com.luyuan.pad.mberp.util.DepthPageTransformer;
import com.luyuan.pad.mberp.util.GlobalConstantValues;
import com.luyuan.pad.mberp.util.GsonRequest;
import com.luyuan.pad.mberp.util.RequestManager;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ImagePagerFragment extends Fragment {

    private ViewGroup rootView;

    private int imageNum;
    private String api;
    private ArrayList<String> urls;

    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_image_pager, null);

        if (GlobalConstantValues.checkNetworkConnection(getActivity())) {
            api = getArguments().getString(GlobalConstantValues.PARAM_API_URL);

            GsonRequest gsonObjRequest = new GsonRequest<ImagePager>(Request.Method.GET, api,
                    ImagePager.class, new Response.Listener<ImagePager>() {
                @Override
                public void onResponse(ImagePager response) {
                    if (response != null && response.getSuccess().equals("true")) {
                        urls = getImageUrls(response);
                        imageNum = urls.size();

                        pager = (ViewPager) ImagePagerFragment.this.rootView.findViewById(R.id.image_pager);
                        pagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
                        pager.setAdapter(pagerAdapter);
                        pager.setPageTransformer(true, new DepthPageTransformer());

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Dialog alertDialog = new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.fetch_data_error)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create();
                    alertDialog.show();
                }
            }
            );

            RequestManager.getRequestQueue().add(gsonObjRequest);
        }

        return rootView;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ImageSlideFragment.create(position, urls.get(position));
        }

        @Override
        public int getCount() {
            return imageNum;
        }
    }

    public ArrayList<String> getImageUrls(ImagePager imagePager) {
        ArrayList<String> result = new ArrayList<String>();
        for (ImageSlide imageSlide : imagePager.getImageSlides()) {
            result.add(imageSlide.getUrl());
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