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

        // Validate the intent action against expected system broadcasts
        if (!"android.intent.action.BOOT_COMPLETED".equals(action) &&
                !"android.intent.action.INPUT_METHOD_CHANGED".equals(action)) {
            Log.w(TAG, "Received unexpected action: " + action + ". Ignoring intent.");
            return; // Discard unexpected intents
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        String log = sb.toString();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, log);
        }
        Toast.makeText(context, log, Toast.LENGTH_LONG).show();
    }
}