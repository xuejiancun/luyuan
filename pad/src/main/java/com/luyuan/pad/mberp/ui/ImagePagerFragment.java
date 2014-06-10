package com.luyuan.pad.mberp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.util.DepthPageTransformer;
import com.luyuan.pad.mberp.util.GlobalConstantValues;

import java.lang.reflect.Field;


public class ImagePagerFragment extends Fragment {

    private String imageType;
    private int imageNum;

    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_image_pager, null);

        Bundle args = getArguments();
        if (args != null) {
            imageType = args.getString(GlobalConstantValues.PARAM_IMAGE_TYPE);
            imageNum = args.getInt(GlobalConstantValues.PARAM_IMAGE_NUM);
        }

        pager = (ViewPager) rootView.findViewById(R.id.image_pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setPageTransformer(true, new DepthPageTransformer());

        return rootView;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ImageSlideFragment.create(position, imageType);
        }

        @Override
        public int getCount() {
            return imageNum;
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