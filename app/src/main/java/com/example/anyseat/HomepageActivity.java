package com.example.anyseat;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class HomepageActivity extends AppCompatActivity {

    private WebView webView;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        webView = (WebView)findViewById(R.id.homepage_webview);
        webView.setWebViewClient(new WebViewClient());
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //줌기능
        webView.getSettings().setBuiltInZoomControls(true);

        webView.loadUrl("http://smobile.knu.ac.kr/");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}