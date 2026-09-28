package com.appknox.mfva;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends SecureBaseActivity {

    EditText USER_NAME,USER_PASS;
    String user_name,user_pass;
    Button REG;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        View rootView = getWindow().getDecorView().getRootView();
        rootView.setFilterTouchesWhenObscured(true);
        USER_NAME = (EditText) findViewById(R.id.editText3);
        USER_PASS = (EditText) findViewById(R.id.editText2);
        REG = (Button) findViewById(R.id.button);
        
        USER_NAME.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showSecureKeyboard();
                }
            }
        });
        
        USER_PASS.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
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

    private void showSecureKeyboard() {
        InputMethodManager imm = getSystemService(Context.INPUT_METHOD_SERVICE) instanceof InputMethodManager 
            ? (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE) 
            : null;
        if (imm != null) {
            imm.showInputMethodPicker();
        }
    }
}
