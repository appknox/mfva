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
            Log.w(TAG, "Received null intent or action.");
            return;
        }

        String action = intent.getAction();
        Log.d(TAG, "Received broadcast with action: " + action);

        // Validate expected actions
        switch (action) {
            case Intent.ACTION_BOOT_COMPLETED:
                // Handle boot completed logic
                Log.i(TAG, "Boot completed action received.");
                break;
            case Intent.ACTION_INPUT_METHOD_CHANGED:
                // Handle input method changed logic
                Log.i(TAG, "Input method changed action received.");
                break;
            default:
                // Log and ignore unexpected actions
                Log.w(TAG, "Received unexpected action: " + action);
                break;
        }
    }
}