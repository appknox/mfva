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
        String message = "Received Intent. Action: " + (action != null ? action : "null");
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message);
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}