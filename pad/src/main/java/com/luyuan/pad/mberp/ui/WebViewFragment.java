package com.luyuan.pad.mberp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.util.GlobalConstantValues;

public class WebViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, null);

        WebView webView = (WebView) view.findViewById(R.id.webview);
        webView.loadUrl(getArguments().getString(GlobalConstantValues.PARAM_URL));

        return view;
    }

}