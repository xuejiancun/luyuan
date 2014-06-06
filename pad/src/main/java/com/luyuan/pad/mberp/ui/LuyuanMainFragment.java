package com.luyuan.pad.mberp.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.luyuan.pad.mberp.R;

import java.lang.reflect.Field;

public class LuyuanMainFragment extends Fragment implements View.OnClickListener {

    private LuyuanSubFirstFragment luyuanSubFirstFragment;
    private LuyuanSubSecondFragment luyuanSubSecondFragment;
    private LuyuanSubThirdFragment luyuanSubThirdFragment;
    private LuyuanSubFourthFragment luyuanSubFourthFragment;

    private LinearLayout tab_goluyuan_layout, tab_histroy_layout, tab_honor_layout, tab_news_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_luyuan_main, null);

        initView(view);
        initData();

        clickGoLuyuanTab();

        return view;
    }

    private void initData() {
        tab_goluyuan_layout.setOnClickListener(this);
        tab_histroy_layout.setOnClickListener(this);
        tab_honor_layout.setOnClickListener(this);
        tab_news_layout.setOnClickListener(this);
    }

    private void initView(View view) {
        tab_goluyuan_layout = (LinearLayout) view.findViewById(R.id.tab_layout_luyuan_goluyuan);
        tab_histroy_layout = (LinearLayout) view.findViewById(R.id.tab_layout_luyuan_history);
        tab_honor_layout = (LinearLayout) view.findViewById(R.id.tab_layout_luyuan_honor);
        tab_news_layout = (LinearLayout) view.findViewById(R.id.tab_layout_luyuan_news);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_layout_luyuan_goluyuan:
                clickGoLuyuanTab();
                break;
            case R.id.tab_layout_luyuan_history:
                clickHistroyTab();
                break;
            case R.id.tab_layout_luyuan_honor:
                clickHonorTab();
                break;
            case R.id.tab_layout_luyuan_news:
                clickNewsTab();
                break;
        }
    }

    private void clickGoLuyuanTab() {
        luyuanSubFirstFragment = new LuyuanSubFirstFragment();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, luyuanSubFirstFragment);
        fragmentTransaction.commit();

        focusOnGoLuyuanTab();
    }

    private void focusOnGoLuyuanTab() {
        tab_goluyuan_layout.setBackgroundColor(Color.parseColor("#4C7F20"));
        tab_histroy_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_honor_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_news_layout.setBackgroundColor(Color.parseColor("#99C741"));
    }

    private void clickHistroyTab() {
        luyuanSubSecondFragment = new LuyuanSubSecondFragment();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, luyuanSubSecondFragment);
        fragmentTransaction.commit();

        focusOnHistroyTab();
    }

    private void focusOnHistroyTab() {
        tab_goluyuan_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_histroy_layout.setBackgroundColor(Color.parseColor("#4C7F20"));
        tab_honor_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_news_layout.setBackgroundColor(Color.parseColor("#99C741"));
    }

    private void clickHonorTab() {
        luyuanSubThirdFragment = new LuyuanSubThirdFragment();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, luyuanSubThirdFragment);
        fragmentTransaction.commit();

        focusOnHonorTab();
    }

    private void focusOnHonorTab() {
        tab_goluyuan_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_histroy_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_honor_layout.setBackgroundColor(Color.parseColor("#4C7F20"));
        tab_news_layout.setBackgroundColor(Color.parseColor("#99C741"));
    }

    private void clickNewsTab() {
        luyuanSubFourthFragment = new LuyuanSubFourthFragment();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, luyuanSubFourthFragment);
        fragmentTransaction.commit();

        focusOnNewsTab();
    }

    private void focusOnNewsTab() {
        tab_goluyuan_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_histroy_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_honor_layout.setBackgroundColor(Color.parseColor("#99C741"));
        tab_news_layout.setBackgroundColor(Color.parseColor("#4C7F20"));
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