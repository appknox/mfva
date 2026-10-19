package com.appknox.mfva;

import android.inputmethodservice.InputMethodService;
import android.view.View;

public class SecureInputMethodService extends InputMethodService {
    @Override
    public View onCreateInputView() {
        View keyboardView = getLayoutInflater().inflate(R.layout.secure_keyboard, null);
        // Bind keypad buttons directly to currentInputConnection.commitText(...)
        return keyboardView;
    }
}
