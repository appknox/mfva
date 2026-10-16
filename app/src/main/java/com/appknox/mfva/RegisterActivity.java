package com.appknox.mfva;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    EditText USER_NAME,USER_PASS;
    String user_name,user_pass;
    Button REG;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
            android.view.WindowManager.LayoutParams.FLAG_SECURE,
            android.view.WindowManager.LayoutParams.FLAG_SECURE
        );
        setContentView(R.layout.activity_register);
        warnIfThirdPartyImeActive();
        getWindow().getDecorView().getRootView().setFilterTouchesWhenObscured(true);
        USER_NAME = (EditText) findViewById(R.id.editText3);
        USER_PASS = (EditText) findViewById(R.id.editText2);
        REG = (Button) findViewById(R.id.button);
        REG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_name = USER_NAME.getText().toString();
                user_pass = USER_PASS.getText().toString();
                DatabaseOps DB = new DatabaseOps(ctx);
                DB.putInformation(DB,user_name,user_pass);
                finish();

            }
        });
    }

    private void warnIfThirdPartyImeActive() {
        String currentIme = Settings.Secure.getString(
            getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD
        );
        if (currentIme == null) {
            return;
        }
        String activePackage = currentIme.split("/")[0];
        boolean isSystemKeyboard = activePackage.equals("com.android.inputmethod.latin") || 
                                   activePackage.equals("com.google.android.inputmethod.latin");
        if (!isSystemKeyboard) {
            new AlertDialog.Builder(this)
                .setTitle("Security Warning")
                .setMessage("A third-party keyboard is active. Sensitive data may be at risk.")
                .setPositiveButton("Continue", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
        }
    }
}
