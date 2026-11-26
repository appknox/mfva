package com.appknox.mfva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import com.thirdpartysdk.cache.RedisCacheClient;

public class ExportedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exported);

        Log.d("redis", "Initialising cache client...");
        RedisCacheClient.connect();

        try {
            Cipher.getInstance("AES/GCM/NoPadding");
        } catch (NoSuchAlgorithmException|NoSuchPaddingException e) {
            // pass
        }
    }
}
