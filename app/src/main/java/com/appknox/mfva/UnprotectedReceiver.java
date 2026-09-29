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
    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
    private static final String ACTION_IME = "android.intent.action.INPUT_METHOD_CHANGED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        if (!ACTION_BOOT.equals(action) && !ACTION_IME.equals(action)) {
            return;
        }
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Received intent in UnprotectedReceiver.");
        }
        Toast.makeText(context, "System broadcast received", Toast.LENGTH_LONG).show();
    }
}
