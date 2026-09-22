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

        switch (intent.getAction()) {
            case Intent.ACTION_BOOT_COMPLETED:
                Log.i(TAG, "Received BOOT_COMPLETED broadcast.");
                break;
            case Intent.ACTION_INPUT_METHOD_CHANGED:
                Log.i(TAG, "Received INPUT_METHOD_CHANGED broadcast.");
                break;
            default:
                Log.w(TAG, "Received unexpected action: " + intent.getAction());
                return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        String log = sb.toString();
        Log.d(TAG, log);
        Toast.makeText(context, log, Toast.LENGTH_LONG).show();
    }
}