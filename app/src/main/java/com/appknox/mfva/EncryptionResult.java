package com.appknox.mfva;

/**
 * Data class to hold the result of encryption operations.
 * Contains the ciphertext and initialization vector (IV).
 */
public class EncryptionResult {
    private final byte[] ciphertext;
    private final byte[] iv;

    /**
     * Constructs an EncryptionResult with the given ciphertext and IV.
     *
     * @param ciphertext the encrypted data
     * @param iv the initialization vector used in encryption
     */
    public EncryptionResult(byte[] ciphertext, byte[] iv) {
        this.ciphertext = ciphertext;
        this.iv = iv;
    }

    /**
     * Gets the ciphertext.
     *
     * @return the encrypted data
     */
    public byte[] getCiphertext() {
        return ciphertext;
    }

    /**
     * Gets the initialization vector.
     *
     * @return the IV used in encryption
     */
    public byte[] getIv() {
        return iv;
    }
}
