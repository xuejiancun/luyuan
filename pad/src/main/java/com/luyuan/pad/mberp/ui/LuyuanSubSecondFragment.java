package com.luyuan.pad.mberp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.luyuan.pad.mberp.R;

public class LuyuanSubSecondFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_luyuan_sub_second, null);

        WebView webView = (WebView) view.findViewById(R.id.webview_band_history);
        webView.loadUrl("http://luyuan.cn/history/index.html");

        return view;
	}	
}