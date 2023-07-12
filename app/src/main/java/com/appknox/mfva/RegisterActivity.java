package com.appknox.mfva;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    EditText USER_NAME,USER_PASS;
    String user_name,user_pass;
    Button REG;
    final Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        USER_NAME = findViewById(R.id.editText3);
        USER_PASS = findViewById(R.id.editText2);
        REG = findViewById(R.id.button);
        REG.setOnClickListener(view -> {
            user_name = USER_NAME.getText().toString();
            user_pass = USER_PASS.getText().toString();
            DatabaseOps DB = new DatabaseOps(ctx);
            DB.putInformation(DB,user_name,user_pass);
            finish();

        });
    }
}
