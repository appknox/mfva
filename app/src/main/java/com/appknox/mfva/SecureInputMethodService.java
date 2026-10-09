package com.appknox.mfva;

import android.inputmethod.InputMethodService;
import android.inputmethod.EditorInfo;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class SecureInputMethodService extends InputMethodService {

    @Override
    public View onCreateInputView() {
        return super.onCreateInputView();
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
    }

    @Override
    public void onFinishInput() {
        super.onFinishInput();
    }
}
