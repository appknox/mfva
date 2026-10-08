package com.appknox.mfva;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by viren on 26/5/17.
 */

public class ExportedReceiver extends BroadcastReceiver {
    private static final String TAG = "ExportedReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            Log.w(TAG, "Received null intent or intent with null action.");
            return;
        }

        String action = intent.getAction();
        switch (action) {
            case Intent.ACTION_BOOT_COMPLETED:
                Log.i(TAG, "Received BOOT_COMPLETED broadcast.");
                // Add specific logic for boot completed, if any.
                // Ensure any data extracted from the intent is validated and sanitized.
                break;
            case Intent.ACTION_INPUT_METHOD_CHANGED:
                Log.i(TAG, "Received INPUT_METHOD_CHANGED broadcast.");
                // Add specific logic for input method changed, if any.
                // Ensure any data extracted from the intent is validated and sanitized.
                break;
            default:
                Log.w(TAG, "Received unexpected action: " + action + ". Ignoring.");
                // Do not process unexpected actions.
                break;
        }
        // Always validate and sanitize any extras if they are used.
        // For example:
        // String someExtra = intent.getStringExtra("some_key");
        // if (someExtra != null) {
        // // Sanitize someExtra before use
        // }
    }
}