package com.appknox.mfva;

import android.inputmethodservice.InputMethodService;
import android.view.View;

/**
 * In-app virtual keyboard. Bound only by the system because the service is
 * protected by android.permission.BIND_INPUT_METHOD, so third-party keyboards
 * are not the only input path available to the user.
 */
public class SecureInputMethodService extends InputMethodService {

    @Override
    public View onCreateInputView() {
        return getLayoutInflater().inflate(R.layout.secure_keyboard, null);
    }
}
