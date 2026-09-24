package com.appknox.mfva;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by viren on 26/5/17.
 */

public class UnprotectedReceiver extends BroadcastReceiver {
    private static final String TAG = "UnprotectedReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        String log = sb.toString();
        if (BuildConfig.DEBUG) {
            StringBuilder debugSb = new StringBuilder();
            debugSb.append("Action: ").append(intent.getAction()).append("\n");
            String logMessage = debugSb.toString();
            Log.d(TAG, logMessage);
        }
        Toast.makeText(context, log, Toast.LENGTH_LONG).show();
    }
}