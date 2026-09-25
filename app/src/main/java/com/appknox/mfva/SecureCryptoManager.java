package com.appknox.mfva;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class providing secure PBKDF2-based key derivation and
 * AES/GCM authenticated encryption.
 */
public class SecureCryptoManager {

    private static final String KEY_DERIVATION_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int KEY_LENGTH_BITS = 256;
    private static final int PBKDF2_ITERATIONS = 65536;
    private static final int GCM_IV_LENGTH_BYTES = 12;
    private static final int GCM_TAG_LENGTH_BITS = 128;

    /**
     * Generates a cryptographically secure random byte array of the
     * requested length, suitable for use as a salt or IV.
     */
    public byte[] generateSecureRandom(int lengthBytes) {
        byte[] randomBytes = new byte[lengthBytes];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        return randomBytes;
    }

    /**
     * Derives an AES {@link SecretKey} from the given password and salt
     * using PBKDF2 with HMAC-SHA256.
     */
    public SecretKey deriveKeyFromPassword(char[] password, byte[] salt) throws GeneralSecurityException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_DERIVATION_ALGORITHM);
        KeySpec spec = new PBEKeySpec(password, salt, PBKDF2_ITERATIONS, KEY_LENGTH_BITS);
        SecretKey derivedKey = factory.generateSecret(spec);
        return new SecretKeySpec(derivedKey.getEncoded(), KEY_ALGORITHM);
    }

    /**
     * Encrypts the given plaintext bytes with AES/GCM using a freshly
     * generated random IV, and returns both the ciphertext and IV.
     */
    public EncryptionResult encryptData(byte[] plaintext, SecretKey key) throws GeneralSecurityException {
        byte[] iv = generateSecureRandom(GCM_IV_LENGTH_BYTES);
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, gcmParameterSpec);
        byte[] ciphertext = cipher.doFinal(plaintext);
        return new EncryptionResult(ciphertext, iv);
    }

    /**
     * Holds the result of an AES/GCM encryption operation.
     */
    public static class EncryptionResult {
        public final byte[] ciphertext;
        public final byte[] iv;

        public EncryptionResult(byte[] ciphertext, byte[] iv) {
            this.ciphertext = ciphertext;
            this.iv = iv;
        }
    }
}
