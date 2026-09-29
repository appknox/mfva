package com.appknox.mfva;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Source-level checks for the KnoxIQ remediations. These run on the JVM and
 * read the project files the scanner flagged.
 */
public class SecurityFixesTest {

    @Test
    public void manifestHardening() throws Exception {
        String manifest = read("AndroidManifest.xml");
        assertTrue(manifest.contains("android:allowBackup=\"false\""));
        assertTrue(manifest.contains("android:usesCleartextTraffic=\"false\""));
        assertTrue(manifest.contains("android:networkSecurityConfig=\"@xml/network_security_config\""));
        assertTrue(manifest.contains("android:protectionLevel=\"signature\""));
        assertTrue(manifest.contains("android:taskAffinity=\"\""));
        assertTrue(manifest.contains("android:launchMode=\"singleTask\""));
        assertTrue(manifest.contains("android:readPermission=\"com.appknox.mfva.CustomPermission\""));
        assertTrue(manifest.contains("android:writePermission=\"com.appknox.mfva.CustomPermission\""));
        assertTrue(manifest.contains("android.permission.BIND_INPUT_METHOD"));
        assertFalse(manifest.contains("android:allowBackup=\"true\""));
        assertFalse(manifest.contains("android:debuggable"));
        assertFalse(manifest.contains("grant-uri-permission"));
        assertFalse(manifest.contains("READ_CONTACTS"));
        assertFalse(manifest.contains("GET_ACCOUNTS"));
        assertFalse(manifest.contains("android.permission.VIBRATE"));
        assertTrue(manifest.contains("android:name=\".RegisterActivity\""));
        assertTrue(manifest.contains("android:exported=\"false\""));
        assertTrue(manifest.contains("android:name=\".ExportedService\""));
    }

    @Test
    public void insecureCryptoAndRandomRemoved() throws Exception {
        String main = read("java/com/appknox/mfva/MainActivity.java");
        String exported = read("java/com/appknox/mfva/ExportedActivity.java");
        String crypto = read("java/com/appknox/mfva/SecureCryptoManager.java");
        assertFalse(main.contains("Math.random"));
        assertFalse(main.contains("DES/ECB"));
        assertFalse(main.contains("Gangnam"));
        assertTrue(main.contains("SecureRandom"));
        assertTrue(main.contains("SecureCryptoManager"));
        assertFalse(exported.contains("Jedis"));
        assertFalse(exported.contains("DES/ECB"));
        assertTrue(crypto.contains("AES/GCM/NoPadding"));
        assertTrue(crypto.contains("AndroidKeyStore"));
    }

    @Test
    public void cleartextAndLoggingRemoved() throws Exception {
        String api = read("java/com/appknox/mfva/ApiRequestsActivity.java");
        String web = read("java/com/appknox/mfva/WebViewActivity.java");
        String exportedReceiver = read("java/com/appknox/mfva/ExportedReceiver.java");
        String unprotected = read("java/com/appknox/mfva/UnprotectedReceiver.java");
        assertFalse(api.contains("http://"));
        assertTrue(api.contains("https://vapi.appknox.io"));
        assertFalse(web.contains("http://"));
        assertTrue(web.contains("FLAG_SECURE") || web.contains("SecureBaseActivity"));
        assertFalse(exportedReceiver.contains("toUri"));
        assertFalse(unprotected.contains("toUri"));
        assertTrue(unprotected.contains("BuildConfig.DEBUG"));
        String network = read("res/xml/network_security_config.xml");
        assertTrue(network.contains("cleartextTrafficPermitted=\"false\""));
    }

    private static String read(String relativeUnderMain) throws Exception {
        File[] candidates = new File[] {
                new File("src/main/" + relativeUnderMain),
                new File("app/src/main/" + relativeUnderMain)
        };
        for (File candidate : candidates) {
            if (candidate.isFile()) {
                return slurp(candidate);
            }
        }
        throw new IllegalStateException("Missing source file: " + relativeUnderMain);
    }

    private static String slurp(File file) throws Exception {
        StringBuilder builder = new StringBuilder();
        Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
        try {
            char[] buffer = new char[4096];
            int count;
            while ((count = reader.read(buffer)) != -1) {
                builder.append(buffer, 0, count);
            }
        } finally {
            reader.close();
        }
        return builder.toString();
    }
}
