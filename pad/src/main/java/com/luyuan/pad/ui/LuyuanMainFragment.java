package com.luyuan.pad.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.luyuan.pad.R;
import com.luyuan.pad.util.GlobalConstantValues;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class LuyuanMainFragment extends Fragment implements View.OnClickListener {

    private int seletedIndex;

    private ArrayList<LinearLayout> tabLayoutList = new ArrayList<LinearLayout>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_luyuan_main, null);

        initTab(view);
        clickGoLuyuanTab();

        return view;
    }

    private void initTab(View view) {
        LinearLayout tab_goluyuan_layout = (LinearLayout) view.findViewById(R.id.tab_layout_luyuan_goluyuan);
        LinearLayout tab_histroy_layout = (LinearLayout) view.findViewById(R.id.tab_layout_luyuan_history);
        LinearLayout tab_honor_layout = (LinearLayout) view.findViewById(R.id.tab_layout_luyuan_honor);
        LinearLayout tab_news_layout = (LinearLayout) view.findViewById(R.id.tab_layout_luyuan_news);

        tab_goluyuan_layout.setOnClickListener(this);
        tab_histroy_layout.setOnClickListener(this);
        tab_honor_layout.setOnClickListener(this);
        tab_news_layout.setOnClickListener(this);

        // Do not change the order
        tabLayoutList.add(tab_goluyuan_layout);
        tabLayoutList.add(tab_histroy_layout);
        tabLayoutList.add(tab_honor_layout);
        tabLayoutList.add(tab_news_layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_layout_luyuan_goluyuan:
                if (seletedIndex != 1) {
                    clickGoLuyuanTab();
                }
                break;
            case R.id.tab_layout_luyuan_history:
                if (seletedIndex != 2) {
                    clickHistroyTab();
                }
                break;
            case R.id.tab_layout_luyuan_honor:
                if (seletedIndex != 3) {
                    clickHonorTab();
                }
                break;
            case R.id.tab_layout_luyuan_news:
                if (seletedIndex != 4) {
                    clickNewsTab();
                }
                break;
        }
    }

    private void clickGoLuyuanTab() {
        WebViewFragment webViewFragment = new WebViewFragment();
        rePlaceTabContentForWebView(webViewFragment, GlobalConstantValues.URL_ABOUT_LUYUAN);
        changeTabBackStyle(tabLayoutList, 1);
    }

    private void clickHistroyTab() {
        WebViewFragment webViewFragment = new WebViewFragment();
        rePlaceTabContentForWebView(webViewFragment, GlobalConstantValues.URL_BRAND_HISTROY);
        changeTabBackStyle(tabLayoutList, 2);
    }

    private void clickHonorTab() {
        ImagePagerFragment imagePagerFragment = new ImagePagerFragment();
        rePlaceTabContentForSlide(imagePagerFragment, GlobalConstantValues.API_BRAND_HONOR);
        changeTabBackStyle(tabLayoutList, 3);
    }

    private void clickNewsTab() {
        WebViewFragment webViewFragment = new WebViewFragment();
        rePlaceTabContentForWebView(webViewFragment, GlobalConstantValues.URL_LUYUAN_NEWS);
        changeTabBackStyle(tabLayoutList, 4);
    }

    private void rePlaceTabContentForSlide(Fragment fragment, String api) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString(GlobalConstantValues.PARAM_API_URL, api);
        fragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

    private void rePlaceTabContentForWebView(Fragment fragment, String url) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString(GlobalConstantValues.PARAM_WEBVIEW_URL, url);
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