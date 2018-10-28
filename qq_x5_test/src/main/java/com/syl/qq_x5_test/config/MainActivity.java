package com.syl.qq_x5_test.config;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.syl.qq_x5_test.R;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * author   Bright
 * date     2018/10/27 9:40
 * desc
 * 使用腾讯X5 加载html界面,html页面中有定位功能,但是不知道为啥,targetSdkVersion >22即使在清单文件中申请了权限,在APP中添加了动态权限,也不能正常定位.
 * 只要targetSdkVersion 22就可以正常定位.不理解
 */
public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mWebView = findViewById(R.id.main_web_view);

        initData();
    }

    private void initData() {
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);


        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
            }

            @Override
            public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
                return super.onJsAlert(webView, s, s1, jsResult);
            }

            @Override
            public boolean onJsConfirm(WebView webView, String s, String s1, JsResult jsResult) {
                return super.onJsConfirm(webView, s, s1, jsResult);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
                return super.onShowFileChooser(webView, valueCallback, fileChooserParams);
            }

            @Override
            public void openFileChooser(ValueCallback<Uri> valueCallback, String s, String s1) {
                super.openFileChooser(valueCallback, s, s1);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
//                return super.shouldOverrideUrlLoading(webView, s);
                webView.loadUrl(s);
                return true;
            }
        });
        mWebView.loadUrl("http://xj.zhroot.com/MobileManage");
    }
}
