package com.example.MireaProject.browser;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyClient extends WebViewClient {

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
