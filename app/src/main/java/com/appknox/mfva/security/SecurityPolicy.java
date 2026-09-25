package com.appknox.mfva.security;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public final class SecurityPolicy {

    private SecurityPolicy() {
    }

    public static void handleAdbEnabled(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("Security Alert")
                .setMessage("USB debugging is enabled. Please disable it to continue using the app.")
                .setCancelable(false)
                .setPositiveButton("Exit App", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        activity.finishAffinity();
                        System.exit(0);
                    }
                })
                .show();
    }

    public static void handleDeveloperOptionsEnabled(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("Security Alert")
                .setMessage("Developer Options are enabled on this device. For your security, the app cannot proceed.")
                .setCancelable(false)
                .setPositiveButton("Exit App", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        activity.finishAffinity();
                        System.exit(0);
                    }
                })
                .show();
    }
}
