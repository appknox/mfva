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
        // Validate the intent action
        if (intent == null || intent.getAction() == null) {
            return; // Ignore null or malformed intents
        }

        String action = intent.getAction();
        switch (action) {
            case Intent.ACTION_BOOT_COMPLETED:
                // Process boot completed event
                //... ensure any extras are validated and sanitized
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "Received intent. Action: " + action);
                }
                break;
            case Intent.ACTION_INPUT_METHOD_CHANGED:
                // Process input method changed event
                //... ensure any extras are validated and sanitized
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "Received intent. Action: " + action);
                }
                break;
            default:
                // Log unexpected action and return
                return;
        }
        // Further processing of the intent, ensuring all data is validated
    }
}