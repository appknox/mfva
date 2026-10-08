package com.appknox.mfva;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class SecureCryptoManager {
    private static final String ANDROID_KEYSTORE = "AndroidKeyStore";
    private static final String KEYSTORE_ALIAS = "SecureCryptoKey";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128; // bits
    private static final int GCM_IV_LENGTH = 12; // bytes (96 bits)

    /**
     * Encryption result containing ciphertext and IV.
     */
    public static class EncryptionResult {
        private final byte[] ciphertext;
        private final byte[] iv;

        public EncryptionResult(byte[] ciphertext, byte[] iv) {
            this.ciphertext = ciphertext;
            this.iv = iv;
        }

        public byte[] getCiphertext() {
            return ciphertext;
        }

        public byte[] getIv() {
            return iv;
        }
    }

    /**
     * Gets or creates a secure key stored in the Android Keystore.
     *
     * @return A SecretKey for AES/GCM encryption
     * @throws GeneralSecurityException if key generation fails
     */
    public SecretKey getOrCreateSecureKey() throws GeneralSecurityException {
        KeyStore keyStore = KeyStore.getInstance(ANDROID_KEYSTORE);
        keyStore.load(null);

        if (keyStore.containsAlias(KEYSTORE_ALIAS)) {
            return (SecretKey) keyStore.getKey(KEYSTORE_ALIAS, null);
        }

        return generateSecureKey();
    }

    /**
     * Generates a new AES key stored securely in the Android Keystore.
     *
     * @return A newly generated SecretKey
     * @throws GeneralSecurityException if key generation fails
     */
    private SecretKey generateSecureKey() throws GeneralSecurityException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(
                    KEYSTORE_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setKeySize(256)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .build();
            keyGenerator.init(spec);
        }

        return keyGenerator.generateKey();
    }

    /**
     * Encrypts data using AES/GCM with the secure key.
     *
     * @param plaintext The data to encrypt
     * @param key The SecretKey for encryption
     * @return An EncryptionResult containing ciphertext and IV
     * @throws GeneralSecurityException if encryption fails
     */
    public EncryptionResult encryptData(byte[] plaintext, SecretKey key)
            throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] iv = cipher.getIV();
        byte[] ciphertext = cipher.doFinal(plaintext);

        return new EncryptionResult(ciphertext, iv);
    }

    /**
     * Decrypts data using AES/GCM with the secure key.
     *
     * @param ciphertext The encrypted data
     * @param iv The initialization vector used during encryption
     * @param key The SecretKey for decryption
     * @return The decrypted plaintext
     * @throws GeneralSecurityException if decryption fails
     */
    public byte[] decryptData(byte[] ciphertext, byte[] iv, SecretKey key)
            throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);

        return cipher.doFinal(ciphertext);
    }

    /**
     * Deletes the secure key from the Android Keystore.
     * This should be called when the key is no longer needed.
     *
     * @throws GeneralSecurityException if key deletion fails
     */
    public void deleteSecureKey() throws GeneralSecurityException {
        KeyStore keyStore = KeyStore.getInstance(ANDROID_KEYSTORE);
        keyStore.load(null);
        keyStore.deleteEntry(KEYSTORE_ALIAS);
    }
}
