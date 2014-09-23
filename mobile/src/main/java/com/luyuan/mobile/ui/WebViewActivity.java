package com.luyuan.mobile.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.luyuan.mobile.R;
import com.luyuan.mobile.util.MyGlobal;

import java.io.IOException;
import java.io.InputStream;

// Web页面嵌套进浏览器功能
public class WebViewActivity extends Activity {

    private WebView webview;
    private String function = "";
    private String tab = "";
    private ProgressDialog dialog;

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

        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("function") != null) {
            function = intent.getStringExtra("function");
        }
        if (intent != null && intent.getStringExtra("tab") != null) {
            tab = intent.getStringExtra("tab");
        }

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getFunctionTitle(function));

        setContentView(R.layout.webview_fragment);

        if (MyGlobal.checkNetworkConnection(WebViewActivity.this)) {

            webview = (WebView) findViewById(R.id.webview);
            WebSettings settings = webview.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            settings.setDomStorageEnabled(true);
            settings.setAllowFileAccess(true);
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            webview.addJavascriptInterface(this, "android");
            webview.setWebViewClient(new WebViewClient() {
                // 本地加载资源文件
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

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    dialog = new ProgressDialog(WebViewActivity.this);
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

            webview.loadUrl(getFunctionUrl(function));
        }

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
        if (function.equals("report_billboard")) {
            result = getText(R.string.function_report_billboard).toString();
        } else if (function.equals("report_personal")) {
            result = getText(R.string.function_report_personal).toString();
        } else if (function.equals("report_strategy")) {
            result = getText(R.string.function_report_strategy).toString();
        } else if (function.equals("report_tactical")) {
            result = getText(R.string.function_report_tactical).toString();
        } else if (function.equals("query_payroll")) {
            result = getText(R.string.function_query_payroll).toString();
        } else if (function.equals("query_training")) {
            result = getText(R.string.function_query_training).toString();
        } else if (function.equals("query_manual")) {
            result = getText(R.string.function_query_manual).toString();
        } else if (function.equals("query_authorization")) {
            result = getText(R.string.function_query_authorization).toString();
        } else if (function.equals("market_research")) {
            result = getText(R.string.function_market_research).toString();
        } else if (function.equals("market_strategy")) {
            result = getText(R.string.function_market_strategy).toString();
        } else if (function.equals("research_verify")) {
            result = getText(R.string.function_research_verify).toString();
        } else if (function.equals("verification_detail")) {
            result = getText(R.string.function_verification_detail).toString();
        } else if (function.equals("market_paper_1")) {
            result = getText(R.string.function_market_paper_1).toString();
        } else if (function.equals("market_paper_2")) {
            result = getText(R.string.function_market_paper_2).toString();
        } else if (function.equals("market_paper_3")) {
            result = getText(R.string.function_market_paper_3).toString();
        } else if (function.equals("follow_someone")) {
            result = getText(R.string.function_follow_someone).toString();
        } else if (function.equals("change_password")) {
            result = getText(R.string.function_change_password).toString();
        } else if (function.equals("notification_history")) {
            result = getText(R.string.function_notification_history).toString();
        } else if (function.equals("query_warrant")) {
            result = getText(R.string.function_query_warrant).toString();
        }

        return result;
    }

    public String getFunctionUrl(String function) {
        String result = "";
        if (function.equals("report_billboard")) {
            result = MyGlobal.WEBVIEW_URL_BILLBOARD;
        } else if (function.equals("report_personal")) {
            result = MyGlobal.WEBVIEW_URL_PERSONAL;
        } else if (function.equals("report_strategy")) {
            result = MyGlobal.WEBVIEW_URL_STRATEGY;
        } else if (function.equals("report_tactical")) {
            result = MyGlobal.WEBVIEW_URL_TACTICAL;
        } else if (function.equals("query_payroll")) {
            result = MyGlobal.WEBVIEW_URL_QUERY_PAYROLL;
        } else if (function.equals("query_training")) {
            result = MyGlobal.WEBVIEW_URL_TRAIN;
        } else if (function.equals("query_manual")) {
            result = MyGlobal.WEBVIEW_URL_MANUAL;
        } else if (function.equals("query_authorization")) {
            result = MyGlobal.WEBVIEW_URL_QUERY_AUTH;
        } else if (function.equals("market_research")) {
            result = MyGlobal.WEBVIEW_URL_MARKET_RESEARCH;
        } else if (function.equals("market_strategy")) {
            result = MyGlobal.WEBVIEW_URL_MARKET_STRATEGY;
        } else if (function.equals("research_verify")) {
            result = MyGlobal.WEBVIEW_URL_RESEARCH_VERIFY;
        } else if (function.equals("verification_detail")) {
            result = MyGlobal.WEBVIEW_URL_VERIFICATION_DETAIL;
        } else if (function.equals("market_paper_1")) {
            result = MyGlobal.WEBVIEW_URL_MARKET_PAPER_1;
        } else if (function.equals("market_paper_2")) {
            result = MyGlobal.WEBVIEW_URL_MARKET_PAPER_2;
        } else if (function.equals("market_paper_3")) {
            result = MyGlobal.WEBVIEW_URL_MARKET_PAPER_3;
        } else if (function.equals("follow_someone")) {
            result = MyGlobal.WEBVIEW_URL_FOLLOWING_SOMEONE;
        } else if (function.equals("change_password")) {
            result = MyGlobal.WEBVIEW_URL_CHANGE_PASSWORD;
        } else if (function.equals("notification_history")) {
            result = MyGlobal.WEBVIEW_URL_NOTIFICATION_HISTORY;
        } else if (function.equals("query_warrant")) {
            result = MyGlobal.WEBVIEW_URL_QUERY_WARRANT;
        }

        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("stId", MyGlobal.getUser().getStId());
            intent.putExtra("tab", tab);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}