package com.appknox.mfva;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.webkit.WebView;

import java.util.Objects;

/**
 * Created by viren on 23/5/17.
 */

public class WebViewActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Toolbar toolbar = findViewById(R.id.toolbar_api_requests);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        WebView webView = findViewById(R.id.activity_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://example.com");
    }
}
