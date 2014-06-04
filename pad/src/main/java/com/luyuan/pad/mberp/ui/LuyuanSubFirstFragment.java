package com.luyuan.pad.mberp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.luyuan.pad.mberp.R;

public class LuyuanSubFirstFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_luyuan_sub_first, null);

        WebView webView = (WebView) view.findViewById(R.id.webview_about_luyuan);
        webView.loadUrl("http://luyuan.cn/aboot_brand.html");

        return view;
    }
}