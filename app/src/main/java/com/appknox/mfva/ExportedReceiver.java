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
        if (action == null) {
            Log.w(TAG, "Received intent with null action.");
            return;
        }

        switch (action) {
            case Intent.ACTION_BOOT_COMPLETED:
            case Intent.ACTION_INPUT_METHOD_CHANGED:
                StringBuilder sb = new StringBuilder();
                sb.append("Action: " + action + "\n");
                sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
                String log = sb.toString();
                Log.d(TAG, log);
                Toast.makeText(context, log, Toast.LENGTH_LONG).show();
                break;
            default:
                Log.w(TAG, "Received unexpected action: " + action);
                break;
        }
    }
}