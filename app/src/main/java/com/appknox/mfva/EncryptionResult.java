package com.appknox.mfva;

public class EncryptionResult {
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
