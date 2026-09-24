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
        String action = intent.getAction();

        // Validate the intent action
        if (!"android.intent.action.BOOT_COMPLETED".equals(action) &&
                !"android.intent.action.INPUT_METHOD_CHANGED".equals(action)) {
            // Log a warning for unexpected actions and return immediately
            Log.w(TAG, "Received unexpected action: " + action);
            return;
        }

        Toast.makeText(context, "System event received: " + action, Toast.LENGTH_SHORT).show();
    }
}