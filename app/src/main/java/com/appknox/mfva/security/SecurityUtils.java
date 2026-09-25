package com.appknox.mfva.security;

import android.content.Context;
import android.provider.Settings;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static boolean isAdbEnabled(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.ADB_ENABLED, 0) == 1;
    }

    public static boolean isDeveloperOptionsEnabled(Context context) {
        int devOptions = Settings.Global.getInt(
                context.getContentResolver(),
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0);
        int adbEnabled = Settings.Global.getInt(
                context.getContentResolver(),
                Settings.Global.ADB_ENABLED, 0);
        return devOptions == 1 || adbEnabled == 1;
    }
}
