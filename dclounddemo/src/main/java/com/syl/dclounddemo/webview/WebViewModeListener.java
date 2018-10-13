package com.syl.dclounddemo.webview;

import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import io.dcloud.common.DHInterface.ICore;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.DHInterface.IWebviewStateListener;
import io.dcloud.feature.internal.sdk.SDK;

/**
 * Created by Bright on 2018/10/14.
 *
 * @Describe
 * 1 创建一个继承自ICoreStatusListener的类，并实现其中的方法。
 * 2 调用SDK.createWebview方法创建并启动5+ Webview
 * 3 启动5+ 内核
 * @Called
 */
public class WebViewModeListener implements ICore.ICoreStatusListener {
    private static final String TAG = WebViewModeListener.class.getSimpleName();
    IWebview webview = null;
    LinearLayout btns = null;
    Activity activity = null;
    ViewGroup mRootView = null;

    private Map<String, String> mapCookie = new HashMap<>();

    public WebViewModeListener(Activity activity, ViewGroup rootView) {
        this.activity = activity;
        mRootView = rootView;
        btns = new LinearLayout(activity);
        mRootView.setBackgroundColor(0xffffffff);
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                webview.onRootViewGlobalLayout(mRootView);
            }
        });
    }

    @Override
    public void onCoreInitEnd(ICore coreHandler) {
        //设置单页面集成的appid
        String appid = "test1";
        // 单页面集成时要加载页面的路径，可以是本地文件路径也可以是网络路径
//        String url = "file:///android_asset/apps/H5Plugin/www/index.html";
        String url = "http://xj.zhroot.com/MobileManage";
        webview = SDK.createWebview(activity, url, appid, new IWebviewStateListener() {
            @Override
            public Object onCallBack(int pType, Object pArgs) {
                switch (pType) {
                    case IWebviewStateListener.ON_WEBVIEW_READY:
                        // 准备完毕之后添加webview到显示父View中，设置排版不显示状态，避免显示webview时，html内容排版错乱问题
                        ((IWebview) pArgs).obtainFrameView().obtainMainView().setVisibility(View.INVISIBLE);
                        SDK.attach(mRootView, ((IWebview) pArgs));
                        break;
                    case IWebviewStateListener.ON_PAGE_STARTED:

                        break;
                    case IWebviewStateListener.ON_PROGRESS_CHANGED:

                        break;
                    case IWebviewStateListener.ON_PAGE_FINISHED:
                        // 页面加载完毕，设置显示webview
                        webview.obtainFrameView().obtainMainView().setVisibility(View.VISIBLE);
                        break;
                }
                return null;
            }
        });

        //保存从服务器返回的cookie
        saveCookie(url);

        final WebView webviewInstance = webview.obtainWebview();
        // 监听返回键
        webviewInstance.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (webviewInstance.canGoBack()) {
                        webviewInstance.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onCoreReady(ICore coreHandler) {
        try {
            SDK.initSDK(coreHandler);
            SDK.requestAllFeature();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCoreStop() {
        // TODO Auto-generated method stub
        return false;
    }
    /**
     * 将服务器返回的Cookie保存到mapCookie,并解析出登录名mLoginUserName
     *
     * @param url
     */
    private void saveCookie(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(url);
//        Log.d(TAG, "cookie==" + cookie);

        String[] cookies = cookie.split(";");
        for (int i = 0; i < cookies.length; i++) {
            String str = cookies[i];
            String key = str.split("=")[0].trim();
            String value;
            if (str.split("=").length == 1) {
                value = "";
            } else {
                value = str.split("=")[1].trim();
            }
            mapCookie.put(key, value);
        }
        Set<Map.Entry<String, String>> entries = mapCookie.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            if ("usertel".equals(key)) {//loginUserName
                Log.d(TAG, "loginUserName==" + value);
            }
//            Log.d(TAG, "key==" + key+"---value==="+value);
        }
    }
}
