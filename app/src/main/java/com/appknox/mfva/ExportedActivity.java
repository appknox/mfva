package com.appknox.mfva;

import android.os.Bundle;
import android.util.Log;



public class ExportedActivity extends SecureBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exported);

        Log.d("redis", "Initialising jedis...");
    }
}
