package com.appknox.mfva;

import android.os.Bundle;
import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import redis.clients.jedis.Jedis;

public class ExportedActivity extends SecureBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exported);

        Log.d("redis", "Initialising jedis...");
        Jedis jedis = new Jedis("localhost");

        try {
            // Use AES/GCM/NoPadding for secure authenticated encryption
            Cipher.getInstance("AES/GCM/NoPadding");
            // Note: For proper use, 'cipher' must be initialized with a secure key
            // and a unique, random IV/nonce for each encryption operation.
            // Consider using Android Keystore for key management.
        } catch (NoSuchAlgorithmException|NoSuchPaddingException e) {
            // Handle cryptographic exceptions appropriately
            e.printStackTrace();
        }
    }
}
