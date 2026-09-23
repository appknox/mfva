package com.appknox.mfva;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by viren on 26/5/17.
 *
 * This receiver must be declared in AndroidManifest.xml with
 * android:exported="false" so it can only be triggered by components within
 * this application (system broadcasts such as BOOT_COMPLETED are still
 * delivered to non-exported receivers). As defense-in-depth, in case the
 * manifest is ever misconfigured to re-export this component, the required
 * permission is also re-validated here before any broadcast is processed.
 */

public class ExportedReceiver extends BroadcastReceiver {
    private static final String TAG = "ExportedReceiver";
    private static final String REQUIRED_PERMISSION = "com.appknox.mfva.CustomPermission";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context.checkCallingOrSelfPermission(REQUIRED_PERMISSION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.w(TAG, "Rejecting broadcast: caller lacks required permission " + REQUIRED_PERMISSION);
            return;
        }

        String action = intent.getAction();

        // Validate the intent action to ensure it's one of the expected system broadcasts
        if (action == null ||
                (!action.equals(Intent.ACTION_BOOT_COMPLETED) &&
                        !action.equals(Intent.ACTION_INPUT_METHOD_CHANGED))) {
            // Log or handle unexpected actions if necessary, but do not process further
            Log.w(TAG, "Received unexpected or null action: " + action);
            return;
        }

        // For these system broadcasts, URI data is not typically relevant or expected.
        // Do not process or display intent.toUri(1) as it could be attacker-controlled.
        String logMessage = "Received system broadcast: " + action;
        Log.d(TAG, logMessage);
        Toast.makeText(context, logMessage, Toast.LENGTH_LONG).show();
    }
}