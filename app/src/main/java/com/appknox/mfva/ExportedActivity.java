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
            // Instantiate a secure cipher for AES in GCM mode with no padding.
            // This is a placeholder; actual encryption/decryption should use a dedicated manager.
            Cipher secureCipher = Cipher.getInstance("AES/GCM/NoPadding");
            // Further steps would involve generating a secure key (e.g., from Android Keystore),
            // generating a unique nonce/IV for each encryption, and using the cipher for authenticated encryption.
            // Refer to the 'SecureCryptoManager' example in the knowledge base for a complete implementation.
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            Log.e("ExportedActivity", "Error initializing secure cipher: " + e.getMessage());
            // Handle the exception appropriately, e.g., by preventing sensitive operations.
        }
    }
}
