package com.appknox.mfva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class ExportedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exported);

        try {
            Cipher.getInstance("DES/ECB/ZeroBytePadding", "BC");
        } catch (NoSuchAlgorithmException|NoSuchProviderException|NoSuchPaddingException e) {
            // pass
        }
    }
}
