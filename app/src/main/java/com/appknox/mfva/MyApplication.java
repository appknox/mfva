package com.appknox.mfva;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.widget.Toast;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // ... other initialization

        if (HookingDetector.isHooked(getApplicationContext())) {
            // Log event to your backend analytics, then restrict or terminate
            throw new SecurityException("Hooking framework detected");
        }

        final Handler mainHandler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final DeviceIntegrityChecker checker = new DeviceIntegrityChecker(getApplicationContext());
                final boolean compromised = checker.isDeviceCompromised();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (compromised) {
                            handleRootedDevice();
                        }
                    }
                });
            }
        }).start();
    }

    private void handleRootedDevice() {
        // Implement your desired response here:
        // - Show a warning dialog
        // - Restrict functionality
        // - Exit the application
        // Example: Show a simple toast and exit
        Toast.makeText(
                getApplicationContext(),
                "This app cannot run on a rooted device.",
                Toast.LENGTH_LONG
        ).show();
        Process.killProcess(Process.myPid());
    }
}
