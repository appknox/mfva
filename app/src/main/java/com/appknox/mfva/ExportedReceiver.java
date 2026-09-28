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
        Log.d(TAG, "Received intent in onReceive.");
        Toast.makeText(context, "Received intent", Toast.LENGTH_LONG).show();
    }
}