package com.appknox.mfva;

import android.os.Bundle;
import android.util.Log;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import redis.clients.jedis.Jedis;

import java.security.KeyStore;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class ExportedActivity extends SecureBaseActivity {
    private static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final String KEY_ALIAS = "secure_app_key";
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exported);

        Log.d("redis", "Initialising jedis...");
        Jedis jedis = new Jedis("localhost");

        try {
            SecretKey secureKey = getOrCreateSecretKey();
            Cipher secureCipher = Cipher.getInstance(AES_TRANSFORMATION);
            // For encryption, generate a new IV
            byte[] iv = new byte[GCM_IV_LENGTH];
            new SecureRandom().nextBytes(iv);
            secureCipher.init(Cipher.ENCRYPT_MODE, secureKey, new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv));
            // Now 'secureCipher' is ready for use with AES/GCM
            // Example: byte[] encryptedData = secureCipher.doFinal(plaintext);
        } catch (Exception e) {
            // Handle cryptographic exceptions appropriately
            e.printStackTrace();
        }
    }

    private SecretKey getOrCreateSecretKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        if (!keyStore.containsAlias(KEY_ALIAS)) {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
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
            KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
            return secretKeyEntry.getSecretKey();
        }
    }
}
