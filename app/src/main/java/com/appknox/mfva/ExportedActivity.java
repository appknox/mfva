package com.appknox.mfva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class ExportedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exported);

        Log.d("redis", "Initialising jedis...");

        try {
            Cipher.getInstance("AES/GCM/NoPadding");
        } catch (NoSuchAlgorithmException|NoSuchPaddingException e) {
            // pass
        }
    }
}
