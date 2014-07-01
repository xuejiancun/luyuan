package com.luyuan.mobile.function;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.luyuan.mobile.R;

import java.io.IOException;
import java.io.InputStream;

public class SalesBillboardReport extends Activity {

    WebView webview;

    @SuppressLint({"JavascriptInterface", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_webview);

        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.addJavascriptInterface(this, "android");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(final WebView view, String url) {
                if (url.contains("jquery.mobile-1.3.2.min.css")) {
                    return getCssWebResourceResponseFromAsset("jquery.mobile-1.3.2.min.css");
                } else if (url.contains("jquery-1.9.1.min.js")) {
                    return getJsWebResourceResponseFromAsset("jquery-1.9.1.min.js");
                } else if (url.contains("jquery.mobile-1.3.2.min.js")) {
                    return getJsWebResourceResponseFromAsset("jquery.mobile-1.3.2.min.js");
                } else {
                    return super.shouldInterceptRequest(view, url);
                }
            }

            private WebResourceResponse getCssWebResourceResponseFromAsset(String filename) {
                InputStream input = null;
                try {
                    input = getAssets().open(filename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return new WebResourceResponse("text/css", "UTF-8", input);
            }

            private WebResourceResponse getJsWebResourceResponseFromAsset(String filename) {
                InputStream input = null;
                try {
                    input = getAssets().open(filename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return new WebResourceResponse("text/javascript", "UTF-8", input);
            }

        });

        webview.loadUrl("https://erp.luyuan.cn/modules/An.SaleReport.Web/Billboard.aspx");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @JavascriptInterface
    public void destroy() {
        super.finish();
    }

    @JavascriptInterface
    public void redirect(String url) {
        webview.loadUrl(url);
    }

    @JavascriptInterface
    public void show(String msg) {
        new AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton("ȷ��", null)
                .create()
                .show();
    }

}
