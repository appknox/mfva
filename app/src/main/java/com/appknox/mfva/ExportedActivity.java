package com.appknox.mfva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;



public class ExportedActivity extends AppCompatActivity {

    private static final String KEY_ALIAS = "secure_app_key";
    private static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exported);

        try {
            // 1. Generate or retrieve a secure key from Android Keystore
            SecretKey secureKey = getOrCreateSecretKey();

            // 2. Initialize Cipher with AES/GCM/NoPadding
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            // For encryption, init with the secure key. The IV will be generated automatically
            // if the key was created with setRandomizedEncryptionRequired(true).
            cipher.init(Cipher.ENCRYPT_MODE, secureKey);

            // Now 'cipher' is securely initialized and ready for use.
            // The IV generated during encryption can be retrieved via cipher.getIV()
            // and must be stored alongside the ciphertext for decryption.

        } catch (Exception e) {
            // Handle cryptographic exceptions appropriately, e.g., log and inform user
            e.printStackTrace();
        }
    }

    /**
     * Helper method to get or create a secure AES key using Android Keystore.
     * The key is configured for AES/GCM with automatic IV generation.
     */
    private SecretKey getOrCreateSecretKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        if (!keyStore.containsAlias(KEY_ALIAS)) {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setRandomizedEncryptionRequired(true)
                    .build();
            keyGenerator.init(keyGenParameterSpec);
            return keyGenerator.generateKey();
        } else {
            KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry)
                    keyStore.getEntry(KEY_ALIAS, null);
            return secretKeyEntry.getSecretKey();
        }
    }
}
