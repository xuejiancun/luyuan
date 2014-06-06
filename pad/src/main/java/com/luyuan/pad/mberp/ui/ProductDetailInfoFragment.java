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

import java.lang.reflect.Field;

public class ProductDetailInfoFragment extends Fragment {

    private static final int NUM_PAGES = 7;

    private ViewPager pager;

    private PagerAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_product_detail_appearance, container, false);

        pager = (ViewPager) rootView.findViewById(R.id.product_page_pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setPageTransformer(true, new DepthPageTransformer());

        return rootView;
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

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ImageSlideFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}