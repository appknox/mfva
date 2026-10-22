package com.appknox.mfva;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import java.util.HashSet;
import java.util.Set;

public abstract class SecureBaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        warnIfThirdPartyImeActive();
    }

    private void warnIfThirdPartyImeActive() {
        String currentIme = Settings.Secure.getString(
            getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD
        );
        if (currentIme == null) {
            currentIme = "";
        }
        Set<String> systemPackages = new HashSet<>();
        systemPackages.add("com.android.inputmethod.latin");
        systemPackages.add("com.google.android.inputmethod.latin");
        systemPackages.add("com.samsung.android.honeyboard");
        
        String activePackage = currentIme.contains("/") ? 
            currentIme.substring(0, currentIme.indexOf("/")) : currentIme;
        
        if (!systemPackages.contains(activePackage)) {
            new AlertDialog.Builder(this)
                .setTitle("Security Warning")
                .setMessage("A third-party keyboard is active. Sensitive data may be at risk.")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int which) {
                        d.dismiss();
                    }
                })
                .show();
        }
    }
}
