package com.appknox.mfva;

import android.inputmethodservice.InputMethodService;
import android.view.View;

public class SecureInputMethodService extends InputMethodService {

    @Override
    public View onCreateInputView() {
        return getLayoutInflater().inflate(R.layout.secure_keyboard, null);
    }
}
