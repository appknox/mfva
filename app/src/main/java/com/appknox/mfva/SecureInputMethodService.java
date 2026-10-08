package com.appknox.mfva;

import android.inputmethodservice.InputMethodService;
import android.view.View;

public class SecureInputMethodService extends InputMethodService {
    public SecureInputMethodService() {
    }

    @Override
    public View onCreateInputView() {
        View view = getLayoutInflater().inflate(R.layout.secure_keyboard, null);
        return view;
    }
}
