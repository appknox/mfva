package com.appknox.mfva;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
        setContentView(R.layout.activity_register);
        getWindow().getDecorView().getRootView().setFilterTouchesWhenObscured(true);
        USER_NAME = (EditText) findViewById(R.id.editText3);
        USER_PASS = (EditText) findViewById(R.id.editText2);
        REG = (Button) findViewById(R.id.button);

        // Use the app's secure input method service for sensitive input (password)
        // to protect against keyloggers that may be present via third-party keyboards.
        USER_PASS.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showSecureKeyboard();
                }
            }
        });

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

    /**
     * Switches the active input method to the app-owned {@link SecureInputMethodService}
     * so that highly sensitive input (e.g. passwords) is not exposed to third-party
     * keyboards that could act as keyloggers.
     */
    private void showSecureKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            ComponentName componentName = new ComponentName(getPackageName(), SecureInputMethodService.class.getName());
            imm.setInputMethod(getWindow().getDecorView().getWindowToken(), componentName.flattenToShortString());
        }
    }
}
