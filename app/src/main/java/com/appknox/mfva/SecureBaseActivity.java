package com.appknox.mfva;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Applies {@link WindowManager.LayoutParams#FLAG_SECURE} before the window is drawn
 * so screenshots and screen recording cannot capture activity contents.
 */
public class SecureBaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        super.onCreate(savedInstanceState);
    }
}
