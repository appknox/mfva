package com.appknox.mfva;

import android.os.Bundle;
import android.view.WindowManager;
import android.support.v7.app.AppCompatActivity;

public class SecureBaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set FLAG_SECURE before calling super.onCreate() and setContentView()
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
        );
        super.onCreate(savedInstanceState);
    }
}
