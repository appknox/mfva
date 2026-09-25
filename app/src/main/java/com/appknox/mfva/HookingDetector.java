package com.appknox.mfva;

import android.content.Context;
import android.os.Process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public final class HookingDetector {

    private static final List<String> HOOK_PACKAGES = Arrays.asList(
            "de.robv.android.xposed.installer",
            "com.saurik.substrate",
            "com.topjohnwu.magisk"
    );

    private static final List<String> MAPS_MARKERS = Arrays.asList(
            "XposedBridge.jar", "com.saurik.substrate", "frida"
    );

    private HookingDetector() {
    }

    public static boolean isMapsContaminated() {
        String mapsFile = "/proc/" + Process.myPid() + "/maps";
        try (BufferedReader reader = new BufferedReader(new FileReader(mapsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String lowerLine = line.toLowerCase();
                for (String marker : MAPS_MARKERS) {
                    if (lowerLine.contains(marker.toLowerCase())) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isHookingPackageInstalled(Context context) {
        for (String pkg : HOOK_PACKAGES) {
            try {
                context.getPackageManager().getApplicationInfo(pkg, 0);
                return true;
            } catch (Exception e) {
                // package not found, continue
            }
        }
        return false;
    }

    public static boolean isFridaServerListening() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("127.0.0.1", 27042), 50);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isHooked(Context context) {
        return isMapsContaminated() || isHookingPackageInstalled(context) || isFridaServerListening();
    }
}
