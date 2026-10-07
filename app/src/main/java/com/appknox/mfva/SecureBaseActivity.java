package com.appknox.mfva;

import android.os.Bundle;
import android.view.WindowManager;
import android.support.v7.app.AppCompatActivity;

public class SecureBaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        );
    }
}
