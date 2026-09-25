package com.appknox.mfva;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.scottyab.rootbeer.RootBeer;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DeviceIntegrityChecker {

    private final Context context;

    public DeviceIntegrityChecker(Context context) {
        this.context = context;
    }

    public boolean isDeviceCompromised() {
        RootBeer rootBeer = new RootBeer(context);
        return rootBeer.isRooted()
                || isSystemPartitionWritable()
                || (Build.TAGS != null && Build.TAGS.contains("test-keys"))
                || isDangerousPackagePresent();
    }

    private boolean isSystemPartitionWritable() {
        try {
            File probe = new File("/system/rootcheck_probe_" + System.nanoTime());
            boolean created = probe.createNewFile();
            probe.delete();
            return created;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isDangerousPackagePresent() {
        List<String> dangerous = Arrays.asList(
                "com.topjohnwu.magisk", "org.lsposed.manager",
                "com.devadvance.rootcloak", "com.koushikdutta.superuser"
        );
        PackageManager pm = context.getPackageManager();
        for (String pkg : dangerous) {
            try {
                pm.getApplicationInfo(pkg, 0);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                // package not present, continue checking others
            }
        }
        return false;
    }
}
