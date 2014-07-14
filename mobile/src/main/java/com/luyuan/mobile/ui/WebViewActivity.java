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
        if (function.equals("payroll")) {
            result = getText(R.string.function_payroll_query).toString();
        } else if (function.equals("billboard")) {
            result = getText(R.string.function_billboard_report).toString();
        } else if (function.equals("personal")) {
            result = getText(R.string.function_personal_report).toString();
        } else if (function.equals("strategy")) {
            result = getText(R.string.function_strategy_report).toString();
        } else if (function.equals("tactical")) {
            result = getText(R.string.function_tactical_report).toString();
        } else if (function.equals("train")) {
            result = getText(R.string.function_train_manager).toString();
        } else if (function.equals("research")) {
            result = getText(R.string.function_market_research_web).toString();
        } else if (function.equals("login_histroy")) {
            result = getText(R.string.function_login_histroy).toString();
        } else if (function.equals("change_password")) {
            result = getText(R.string.function_change_password).toString();
        } else if (function.equals("notification_history")) {
            result = getText(R.string.function_notification_history).toString();
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
        } else if (function.equals("train")) {
            result = MyGlobal.WEBVIEW_URL_TRAIN;
        } else if (function.equals("research")) {
            result = MyGlobal.WEBVIEW_URL_MARKET_RESEARCH;
        } else if (function.equals("login_histroy")) {
            result = MyGlobal.WEBVIEW_URL_LOGIN_HISTORY;
        } else if (function.equals("change_password")) {
            result = MyGlobal.WEBVIEW_URL_CHANGE_PASSWORD;
        } else if (function.equals("notification_history")) {
            result = MyGlobal.WEBVIEW_URL_NOTIFICATION_HISTORY;
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