package com.appknox.mfva

import android.inputmethodservice.InputMethodService
import android.view.View

class SecureInputMethodService : InputMethodService() {
    override fun onCreateInputView(): View {
        return layoutInflater.inflate(R.layout.secure_keyboard, null)
    }
}
