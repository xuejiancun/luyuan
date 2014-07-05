package com.luyuan.mobile.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.luyuan.mobile.R;
import com.luyuan.mobile.util.MyGlobal;

import java.io.IOException;
import java.io.InputStream;

public class WebViewActivity extends Activity {

    private WebView webview;
    private String function = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        if (getIntent() == null || getIntent().getStringExtra("function") == null) {
            new AlertDialog.Builder(WebViewActivity.this)
                    .setMessage(R.string.app_param_error)
                    .setTitle(R.string.dialog_hint)
                    .setPositiveButton(R.string.dialog_confirm, null)
                    .create()
                    .show();
            return;
        }

        function = getIntent().getStringExtra("function");

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getFunctionTitle(function));

        setContentView(R.layout.webview_fragment);

        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setAllowFileAccess(true);
        webview.addJavascriptInterface(this, "android");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(final WebView view, String url) {
                if (url.contains("jquery-ui.css")) {
                    return getCssWebResourceResponseFromAsset("jquery-ui.css");
                } else if (url.contains("jquery.mobile-1.0a1.min.css")) {
                    return getCssWebResourceResponseFromAsset("jquery.mobile-1.0a1.min.css");
                } else if (url.contains("jquery.mobile-1.3.2.min.css")) {
                    return getCssWebResourceResponseFromAsset("jquery.mobile-1.3.2.min.css");
                } else if (url.contains("jquery-1.4.3.min.js")) {
                    return getJsWebResourceResponseFromAsset("jquery-1.4.3.min.js");
                } else if (url.contains("jquery-1.7.2.min.js")) {
                    return getJsWebResourceResponseFromAsset("jquery-1.7.2.min.js");
                } else if (url.contains("jquery.mobile-1.0a1.min.js")) {
                    return getJsWebResourceResponseFromAsset("jquery.mobile-1.0a1.min.js");
                } else if (url.contains("jquery-1.10.2.js")) {
                    return getJsWebResourceResponseFromAsset("jquery-1.10.2.js");
                } else if (url.contains("1.10.4jquery-ui.js")) {
                    return getJsWebResourceResponseFromAsset("1.10.4jquery-ui.js");
                } else if (url.contains("jquery-1.8.3.min.js")) {
                    return getJsWebResourceResponseFromAsset("jquery-1.8.3.min.js");
                } else if (url.contains("jquery.mobile-1.3.2.min.js")) {
                    return getJsWebResourceResponseFromAsset("jquery.mobile-1.3.2.min.js");
                } else if (url.contains("jquery-1.9.1.min.js")) {
                    return getJsWebResourceResponseFromAsset("jquery-1.9.1.min.js");
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

        webview.loadUrl(getFunctionUrl(function));
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
                .setPositiveButton(R.string.confirm, null)
                .create()
                .show();
    }

    public String getFunctionTitle(String function) {
        String result = "";
        if (function.equals("payroll")) {
            result = getText(R.string.payroll_query).toString();
        } else if (function.equals("billboard")) {
            result = getText(R.string.sales_billboard_report).toString();
        } else if (function.equals("personal")) {
            result = getText(R.string.sales_personal_report).toString();
        } else if (function.equals("strategy")) {
            result = getText(R.string.sales_strategy_report).toString();
        } else if (function.equals("tactical")) {
            result = getText(R.string.sales_tactical_report).toString();
        }

        return result;
    }

    public String getFunctionUrl(String function) {
        String result = "";
        if (function.equals("payroll")) {
            result = MyGlobal.WEBVIEW_URL_PAYROLL;
        } else if (function.equals("billboard")) {
            result = MyGlobal.WEBVIEW_URL_BILLBOARD;
        } else if (function.equals("personal")) {
            result = MyGlobal.WEBVIEW_URL_PERSONAL;
        } else if (function.equals("strategy")) {
            result = MyGlobal.WEBVIEW_URL_STRATEGY;
        } else if (function.equals("tactical")) {
            result = MyGlobal.WEBVIEW_URL_TACTICAL;
        }

        return result;
    }

}