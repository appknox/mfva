package com.appknox.mfva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class ExportedActivity extends SecureBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exported);

        if (BuildConfig.DEBUG) {
            Log.d("redis", "Initialising jedis...");
        }

        try {
            // Generate a secure AES key (for demonstration; ideally use Android Keystore)
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256); // 256-bit AES key
            SecretKey secureKey = keyGen.generateKey();

            // Generate a cryptographically secure random IV (nonce) for GCM
            byte[] iv = new byte[12]; // 96-bit IV for GCM
            new SecureRandom().nextBytes(iv);

            // Initialize Cipher with AES/GCM/NoPadding
            Cipher secureCipher = Cipher.getInstance("AES/GCM/NoPadding");
            // GCMParameterSpec requires tag length in bits (128 bits = 16 bytes)
            GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
            secureCipher.init(Cipher.ENCRYPT_MODE, secureKey, gcmSpec);
        } catch (NoSuchAlgorithmException|NoSuchPaddingException e) {
            // pass
        } catch (Exception e) {
            // Handle cryptographic exceptions appropriately, e.g., log and inform user
            e.printStackTrace();
        }
    }
}
