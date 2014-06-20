package com.luyuan.pad.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.luyuan.pad.R;
import com.luyuan.pad.util.GlobalConstantValues;

public class WebViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_webview, null);

        WebView webView = (WebView) view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(getArguments().getString(GlobalConstantValues.PARAM_WEBVIEW_URL));

        return view;
    }

}