package com.appknox.mfva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import java.security.SecureRandom;



public class ExportedActivity extends SecureBaseActivity {

    private SecretKey generateSecureAesKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, new SecureRandom());
        return keyGen.generateKey();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exported);

        Log.d("redis", "Initialising jedis...");

        try {
            SecretKey secureKey = generateSecureAesKey();
            Cipher secureCipher = Cipher.getInstance("AES/GCM/NoPadding");
            secureCipher.init(Cipher.ENCRYPT_MODE, secureKey);
        } catch (Exception e) {
            Log.e("ExportedActivity", "Error initializing secure cipher", e);
        }
    }
}
