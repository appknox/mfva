package com.appknox.mfva;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
        
        // Validate the intent action - only process expected system broadcasts
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            // Only process if the action is BOOT_COMPLETED
            // Discard any unexpected extras
            if (intent.getExtras() != null && !intent.getExtras().isEmpty()) {
                Log.w(TAG, "Received BOOT_COMPLETED with unexpected extras. Discarding.");
                intent.replaceExtras(new Bundle());
            }
            // Existing logic for BOOT_COMPLETED
            if (BuildConfig.DEBUG) {
                StringBuilder sb = new StringBuilder();
                sb.append("Action: " + intent.getAction() + "\n");
                String log = sb.toString();
                Log.d(TAG, log);
            }
            Toast.makeText(context, "Received intent", Toast.LENGTH_LONG).show();
        } else if (Intent.ACTION_INPUT_METHOD_CHANGED.equals(action)) {
            // Only process if the action is INPUT_METHOD_CHANGED
            // Discard any unexpected extras
            if (intent.getExtras() != null && !intent.getExtras().isEmpty()) {
                Log.w(TAG, "Received INPUT_METHOD_CHANGED with unexpected extras. Discarding.");
                intent.replaceExtras(new Bundle());
            }
            // Existing logic for INPUT_METHOD_CHANGED
            if (BuildConfig.DEBUG) {
                StringBuilder sb = new StringBuilder();
                sb.append("Action: " + intent.getAction() + "\n");
                String log = sb.toString();
                Log.d(TAG, log);
            }
            Toast.makeText(context, "Received intent", Toast.LENGTH_LONG).show();
        } else {
            // Log or ignore unexpected actions from external sources
            Log.w(TAG, "Received unexpected action: " + action + ". Ignoring.");
            return;
        }
    }
}