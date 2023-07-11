package com.appknox.mfva;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.webkit.WebView;

/**
 * Created by viren on 23/5/17.
 */

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_api_requests);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = (WebView) findViewById(R.id.activity_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://example.com");
    }
}
