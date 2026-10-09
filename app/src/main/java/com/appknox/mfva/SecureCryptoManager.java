package com.appknox.mfva;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class SecureCryptoManager {
    private static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12; // 96 bits
    private static final int GCM_TAG_LENGTH = 128; // 128 bits
    private static final int PBKDF2_ITERATIONS = 100000;
    private static final int PBKDF2_KEY_SIZE = 256; // bits

    // Secure encryption using AES-GCM
    public static EncryptionResult encryptData(byte[] plaintext, SecretKey key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key); // IV is generated internally for GCM

        byte[] iv = cipher.getIV(); // Get the generated IV
        byte[] ciphertext = cipher.doFinal(plaintext);

        return new EncryptionResult(ciphertext, iv);
    }

    // Secure key derivation using PBKDF2
    public static SecretKey deriveKeyFromPassword(char[] password, byte[] salt) throws GeneralSecurityException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec spec = new PBEKeySpec(password, salt, PBKDF2_ITERATIONS, PBKDF2_KEY_SIZE);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), 0, tmp.getEncoded().length, "AES");
    }

    // Generate cryptographically secure random bytes
    public static byte[] generateSecureRandom(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return randomBytes;
    }

    // Helper class for encryption result
    public static class EncryptionResult {
        public final byte[] ciphertext;
        public final byte[] iv;

        public EncryptionResult(byte[] ciphertext, byte[] iv) {
            this.ciphertext = ciphertext;
            this.iv = iv;
        }
    }
}
