package com.appknox.mfva;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import java.security.KeyStore;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class SecureCryptoManager {
    private static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final String KEY_ALIAS = "secure_app_key";
    private static final int GCM_IV_LENGTH = 12; // 96 bits
    private static final int GCM_TAG_LENGTH = 16; // 128 bits
    private static final int PBKDF2_ITERATIONS = 100000;
    private static final int PBKDF2_KEY_SIZE = 256; // 256 bits for AES-256

    private final Context context;
    private final KeyStore keyStore;

    public SecureCryptoManager(Context context) throws Exception {
        this.context = context;
        this.keyStore = KeyStore.getInstance("AndroidKeyStore");
        this.keyStore.load(null);
    }

    public SecretKey generateSecureKey() throws Exception {
        if (keyStore.containsAlias(KEY_ALIAS)) {
            return (SecretKey) keyStore.getKey(KEY_ALIAS, null);
        }

        KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT
        ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(PBKDF2_KEY_SIZE)
                .setRandomizedEncryptionRequired(true)
                .build();

        keyGenerator.init(keyGenParameterSpec);
        return keyGenerator.generateKey();
    }

    public EncryptionResult encryptData(byte[] plaintext, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] iv = cipher.getIV();
        byte[] ciphertext = cipher.doFinal(plaintext);

        return new EncryptionResult(ciphertext, iv);
    }

    public byte[] decryptData(EncryptionResult encryptionResult, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, encryptionResult.getIv());
        cipher.init(Cipher.DECRYPT_MODE, key, spec);

        return cipher.doFinal(encryptionResult.getCiphertext());
    }

    public SecretKey deriveKeyFromPassword(char[] password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec spec = new PBEKeySpec(password, salt, PBKDF2_ITERATIONS, PBKDF2_KEY_SIZE);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), 0, tmp.getEncoded().length, "AES");
    }

    public byte[] generateSecureRandom(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return randomBytes;
    }
}
