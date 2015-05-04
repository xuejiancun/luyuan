package com.luyuan.mobile.ui;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.luyuan.mobile.R;
import com.luyuan.mobile.util.MyGlobal;

// 主页Tab
public class HomeFragment extends Fragment {
    private ProgressDialog dialog;
    private MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         mainActivity =    (MainActivity) inflater.getContext();
        View layout =  inflater.inflate(R.layout.webview_fragment, null);
        WebView webview = (WebView)  layout.findViewById(R.id.webview);
        if (MyGlobal.checkNetworkConnection(mainActivity)) {
            WebSettings settings = webview.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            settings.setDomStorageEnabled(true);
            settings.setAllowFileAccess(true);
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            //  webview.addJavascriptInterface(this, "android");
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public WebResourceResponse shouldInterceptRequest(final WebView view, String url) {
                    return super.shouldInterceptRequest(view, url);
                }
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    dialog = new ProgressDialog(mainActivity);
                    dialog.setMessage(getText(R.string.loading));
                    dialog.setCancelable(true);
                    dialog.show();
                }
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    dialog.dismiss();
                }
            });
            webview.loadUrl(MyGlobal.chart);
        }
        return layout;
    }

}