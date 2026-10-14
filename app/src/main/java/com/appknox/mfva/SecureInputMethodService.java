package com.appknox.mfva;

import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;

public class SecureInputMethodService extends InputMethodService {

    @Override
    public View onCreateInputView() {
        View view = getLayoutInflater().inflate(R.layout.secure_keyboard, null);
        
        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection != null) {
            bindKeyboardButtons(view, inputConnection);
        }
        
        return view;
    }

    private void bindKeyboardButtons(View view, final InputConnection inputConnection) {
        // Bind number keys
        int[] numberKeyIds = {
            R.id.key_0, R.id.key_1, R.id.key_2, R.id.key_3, R.id.key_4,
            R.id.key_5, R.id.key_6, R.id.key_7, R.id.key_8, R.id.key_9
        };
        
        for (int keyId : numberKeyIds) {
            Button button = (Button) view.findViewById(keyId);
            if (button != null) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button btn = (Button) v;
                        inputConnection.commitText(btn.getText(), 1);
                    }
                });
            }
        }
        
        // Bind delete key
        Button deleteButton = (Button) view.findViewById(R.id.key_delete);
        if (deleteButton != null) {
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inputConnection.deleteSurroundingText(1, 0);
                }
            });
        }
        
        // Bind space key
        Button spaceButton = (Button) view.findViewById(R.id.key_space);
        if (spaceButton != null) {
            spaceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inputConnection.commitText(" ", 1);
                }
            });
        }
    }
}
