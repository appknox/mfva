package com.appknox.mfva;

import android.os.Bundle;
import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class ExportedActivity extends SecureBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exported);

        try {
            Cipher.getInstance("AES/GCM/NoPadding");
        } catch (NoSuchAlgorithmException|NoSuchPaddingException e) {
            // pass
        }
    }
}
