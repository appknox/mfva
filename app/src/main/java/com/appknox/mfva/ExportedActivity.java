package com.appknox.mfva;

import android.os.Bundle;

import javax.crypto.SecretKey;

public class ExportedActivity extends SecureBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exported);

        try {
            SecretKey secureKey = SecureCryptoManager.getOrCreateSecureKey();
            SecureCryptoManager.encryptData(new byte[0], secureKey);
        } catch (Exception e) {
            // Android Keystore is unavailable in this environment.
        }
    }
}
