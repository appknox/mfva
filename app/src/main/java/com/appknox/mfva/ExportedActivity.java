package com.appknox.mfva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import redis.clients.jedis.Jedis;

public class ExportedActivity extends SecureBaseActivity {

    private static final String TAG = "ExportedActivity";
    private static final String KEY_ALIAS = "secure_app_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exported);

        Log.d("redis", "Initialising jedis...");
        Jedis jedis = new Jedis("localhost");

        try {
            SecretKey secretKey = getOrCreateSecretKey(KEY_ALIAS);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            // Generate a secure random IV/nonce for GCM (12 bytes for GCM)
            byte[] iv = new byte[12];
            new SecureRandom().nextBytes(iv);

            // Initialize cipher for encryption (128-bit tag length for GCM)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(128, iv));

            // The 'cipher' object is now securely initialized for encryption.
            // You would use cipher.doFinal() for actual data encryption.
            // For decryption, you would need to store and reuse the IV.

        } catch (NoSuchAlgorithmException|NoSuchProviderException|NoSuchPaddingException|InvalidAlgorithmParameterException|KeyStoreException|CertificateException|IOException e) {
            Log.e(TAG, "Error initializing secure crypto: " + e.getMessage(), e);
        }
    }

    private SecretKey getOrCreateSecretKey(String alias) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, KeyStoreException, CertificateException, IOException {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        if (!keyStore.containsAlias(alias)) {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keyGenerator.init(new KeyGenParameterSpec.Builder(
                    alias,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_GCM).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE).setRandomizedEncryptionRequired(true).build());
            return keyGenerator.generateKey();
        } else {
            KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(alias, null);
            return secretKeyEntry.getSecretKey();
        }
    }
}
