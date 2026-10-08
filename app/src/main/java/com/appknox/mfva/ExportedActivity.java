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

        Log.d("redis", "Initialising jedis...");

        try {
            // Implement secure AES/GCM/NoPadding cipher
            Cipher secureCipher = Cipher.getInstance("AES/GCM/NoPadding");
            // secureCipher.init(Cipher.ENCRYPT_MODE, secureKey, new GCMParameterSpec(128, iv));
            // Further secure crypto operations should follow, using a securely generated key and IV.
        } catch (NoSuchAlgorithmException|NoSuchPaddingException e) {
            // Handle exceptions appropriately, e.g., log the error
            e.printStackTrace();
        }
    }
}
