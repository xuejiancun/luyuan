package com.luyuan.mobile.function;

import android.annotation.SuppressLint;
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

import java.io.IOException;
import java.io.InputStream;

public class SalesTacticalReport extends Activity {

	WebView webview;

	@SuppressLint({ "JavascriptInterface", "NewApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.sales_tactical_report);

		setContentView(R.layout.fragment_webview);

		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.addJavascriptInterface(this, "android");
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public WebResourceResponse shouldInterceptRequest(final WebView view, String url) {
				if (url.contains("jquery-ui.css")) {
					return getCssWebResourceResponseFromAsset("jquery-ui.css");
				} else if (url.contains("jquery.mobile-1.0a1.min.css")) {
					return getCssWebResourceResponseFromAsset("jquery.mobile-1.0a1.min.css");
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

		webview.loadUrl("https://erp.luyuan.cn/modules/An.SaleReport.Web/Tactical.aspx");
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			webview.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	class myWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

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