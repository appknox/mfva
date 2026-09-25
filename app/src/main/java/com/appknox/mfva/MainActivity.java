package com.appknox.mfva;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class MainActivity extends AppCompatActivity {
    private static final java.security.SecureRandom secureRandom = new java.security.SecureRandom();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set FLAG_SECURE to prevent screen capture and recording
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
        );
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().getRootView().setFilterTouchesWhenObscured(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        final Context context = this;

        Button buttonApi = (Button) findViewById(R.id.button_api_requests);
        buttonApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ApiRequestsActivity.class);
                startActivity(intent);
            }
        });

        Button buttonWebView = (Button) findViewById(R.id.button_webview);
        buttonWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
		        startActivity(intent);
            }
        });

        Button buttonSharedPref = (Button) findViewById(R.id.button_shared_pref);
        buttonSharedPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", 0);
                SharedPreferences.Editor editor = pref.edit();

                String[] keys = new String[] {
                        "java.class.version",
                        "java.home",
                        "java.library.path",
                        "java.vendor",
                        "java.vendor.url",
                        "java.specification.name",
                        "java.vm.name",
                        "java.vm.version",
                        "java.vm.specification.name",
                        "java.vm.specification.version",
                        "os.arch",
                        "os.name",
                        "os.version",
                };
                java.security.SecureRandom secureRandom = new java.security.SecureRandom();
                String key = keys[secureRandom.nextInt(keys.length)];
                editor.putString(key, System.getProperty(key));
                editor.commit();

                Snackbar.make(v, key + ": " + pref.getString(key, null), Snackbar.LENGTH_SHORT).show();
            }
        });

        Button buttonDebugLog = (Button) findViewById(R.id.button_debug_log);
        buttonDebugLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] quotes = new String[] {
                        "Any given program, when running correctly, is obsolete.",
                        "Disco is to music what Etch-A-Sketch is to art.",
                        "Computer Science is merely the post-Turing decline in formal systems theory.",
                        "Sometimes the only solution is to find a new problem.",
                        "There are 10 kinds of people. Those who know binary and those who don't.",
                        "Whom computers would destroy, they must first drive mad.",
                        "I program, therefore I am.",
                        "If Java had true garbage collection, most programs would delete themselves upon execution.",
                        "Hardware, n.: The parts of a computer system that can be kicked.",
                        "There are two ways to write error-free programs. Only the third one works.",
                        "Why isn't \"palindrome\" spelled the same way backwards?",
                        "A language that doesn't affect the way you think about programming is not worth knowing.",
                        "All programmers are playwrights and all computers are lousy actors.",
                        "Every program has two purposes ― one for which it was written and another for which it wasn't.",
                        "Every program is a part of some other program, and rarely fits.",
                };
                String quote = quotes[secureRandom.nextInt(quotes.length)];
                Log.d("YOLO", quote);
                Snackbar.make(v, quote, Snackbar.LENGTH_SHORT).show();
            }
        });

        Button buttonCalculateChecksum = (Button) findViewById(R.id.button_calculate_checksum);
        buttonCalculateChecksum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quote = "The beauty of me is that I’m very rich. - Donald Trump";
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    byte[] hash = md.digest(quote.getBytes());
                    StringBuilder hashStr = new StringBuilder(hash.length * 2);
                    for(byte b: hash)
                        hashStr.append(String.format("%02x", b));
                    Snackbar.make(v, "MD5: [" + hashStr + "] : " + quote, Snackbar.LENGTH_SHORT).show();
                } catch (NoSuchAlgorithmException e) {
                    Snackbar.make(v, e.toString(), Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonEncrypt = (Button) findViewById(R.id.button_encrypt);
        buttonEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String quote = "Even if you're not doing anything wrong, you are being watched and recorded. - Edward Snowden";

                    byte[] plaintext = quote.getBytes();
                    char[] password = "Gangnam!".toCharArray();

                    byte[] salt = SecureCryptoManager.generateSecureRandom(16);
                    SecretKey secretKey = SecureCryptoManager.deriveKeyFromPassword(password, salt);
                    SecureCryptoManager.EncryptionResult encryptionResult = SecureCryptoManager.encryptData(plaintext, secretKey);

                    byte[] combinedEncryptedData = new byte[salt.length + encryptionResult.getIv().length + encryptionResult.getCiphertext().length];
                    System.arraycopy(salt, 0, combinedEncryptedData, 0, salt.length);
                    System.arraycopy(encryptionResult.getIv(), 0, combinedEncryptedData, salt.length, encryptionResult.getIv().length);
                    System.arraycopy(encryptionResult.getCiphertext(), 0, combinedEncryptedData, salt.length + encryptionResult.getIv().length, encryptionResult.getCiphertext().length);

                    String encryptedTextBase64 = android.util.Base64.encodeToString(combinedEncryptedData, android.util.Base64.DEFAULT);

                    Snackbar.make(v, "Encrypted: " + encryptedTextBase64, Snackbar.LENGTH_SHORT).show();
                } catch (java.security.GeneralSecurityException e) {
                    Snackbar.make(v, e.toString(), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.about_message).setTitle(R.string.about_title);
                builder.create().show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
