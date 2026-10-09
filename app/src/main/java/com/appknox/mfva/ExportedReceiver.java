package com.appknox.mfva;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by viren on 26/5/17.
 */

public class ExportedReceiver extends BroadcastReceiver {
    private static final String TAG = "ExportedReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            // Log this unexpected event if necessary, then return.
            return;
        }

        String action = intent.getAction();
        if (!action.equals(Intent.ACTION_BOOT_COMPLETED) &&
            !action.equals(Intent.ACTION_INPUT_METHOD_CHANGED)) {
            // This intent is not one of the expected system broadcasts.
            // Log this unexpected invocation if necessary, then return.
            return;
        }

        // If the intent is one of the expected system broadcasts,
        // proceed with processing. Always sanitize any extras if they are used.
        // For system broadcasts, extras are typically not user-controlled,
        // but defensive programming is recommended.
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Received intent in debug mode.");
        }
        Toast.makeText(context, "Intent received.", Toast.LENGTH_SHORT).show();
    }
}