package com.luyuan.mobile.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.luyuan.mobile.R;

import java.io.IOException;
import java.io.InputStream;

public class WebViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_webview, null);

        WebView webView = (WebView) view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(final WebView view, String url) {
                if (url.contains(".css")) {
                    return getCssWebResourceResponseFromAsset();
                } else if (url.contains(".js")) {
                    return getCssWebResourceResponseFromAsset();
                } else {
                    return super.shouldInterceptRequest(view, url);
                }
            }

            /**
             * Return WebResourceResponse with CSS markup from an asset (e.g. "assets/style.css").
             */
            private WebResourceResponse getCssWebResourceResponseFromAsset() {
                try {
                    return getUtf8EncodedCssWebResourceResponse(getActivity().getAssets().open("style.css"));
                } catch (IOException e) {
                    return null;
                }
            }

            /**
             * Return WebResourceResponse with Javascript markup from an asset (e.g. "assets/jquery.js").
             */
            private WebResourceResponse getJavascriptWebResourceResponseFromAsset() {
                try {
                    return getUtf8EncodedCssWebResourceResponse(getActivity().getAssets().open("style.css"));
                } catch (IOException e) {
                    return null;
                }
            }

            private WebResourceResponse getUtf8EncodedCssWebResourceResponse(InputStream data) {
                return new WebResourceResponse("text/css", "UTF-8", data);
            }

        });

        webView.loadUrl("http://192.168.10.90/modules.An.SaleReport.Web/Billboard.aspx");

        return view;
    }

}