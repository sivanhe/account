package com.account.demo;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {

    private static final String TAG = "WebActivity";
    private WebView webView;
    private WebSettings webSettings;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = findViewById(R.id.web_view);

        initWebView();
        webView.loadUrl("https://test.xworld.pro/usdtDemo");
    }

    @Override
    protected void onResume() {
        super.onResume();
        webSettings.setJavaScriptEnabled(true);
        evaluateJavascript("demo.onappear()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        webSettings.setJavaScriptEnabled(false);
        evaluateJavascript("demo.ondisappear()");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    private void initWebView() {
        webSettings = webView.getSettings();
        WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);

        webSettings.setDomStorageEnabled(true);
//        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(webChromeClient);

        webView.addJavascriptInterface(jSInterface, "demo_account");
    }

    private void evaluateJavascript(String script) {
        if (isLoading) return;
        webView.evaluateJavascript(script, null);
    }

    private final WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "shouldOverrideUrlLoading url: " + url);
            if (url.startsWith("xworld://")) {
                if (AccountUtils.openOtherApp(url, WebActivity.this)) {
                    return true;
                }
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            Log.d(TAG, "shouldOverrideUrlLoading request: " + url);
            if (url.startsWith("xworld://")) {
                if (AccountUtils.openOtherApp(url, WebActivity.this)) {
                    return true;
                }
            }
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            isLoading = true;

        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request,
                                    WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d(TAG, "onPageFinished url: " + url);
            isLoading = false;
        }
    };

    private final WebChromeClient webChromeClient = new WebChromeClient() {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onShowFileChooser(WebView webView,
                                         ValueCallback<Uri[]> filePathCallback,
                                         FileChooserParams fileChooserParams) {
            return true;
        }
    };

    private final Object jSInterface = new Object() {

        @JavascriptInterface
        public boolean isAppInstalled(String pkg) {
            return AccountUtils.isAppInstalled(WebActivity.this, pkg);
        }

        @JavascriptInterface
        public void startXWorldPay(String mchId, String orderId, String appName) {
            AccountUtils.startPay(WebActivity.this, mchId, orderId, appName);
        }

        @JavascriptInterface
        public void startXWorldBind(String mchId, String userId) {
            AccountUtils.startBind(WebActivity.this, mchId, userId);
        }
    };
}