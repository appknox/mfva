package com.appknox.mfva;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class SecureInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private KeyboardView mKeyboardView;

    @Override
    public View onCreateInputView() {
        View view = getLayoutInflater().inflate(R.layout.secure_keyboard, null);
        mKeyboardView = (KeyboardView) view.findViewById(R.id.keyboard_view);
        mKeyboardView.setKeyboard(new Keyboard(this, R.xml.input_method_config));
        mKeyboardView.setOnKeyboardActionListener(this);
        return view;
    }

    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection == null) {
            return;
        }

        if (primaryCode == Keyboard.KEYCODE_DELETE) {
            inputConnection.deleteSurroundingText(1, 0);
        } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {
            // Shift handling could go here
        } else {
            char code = (char) primaryCode;
            inputConnection.commitText(String.valueOf(code), 1);
        }
    }

    @Override
    public void onText(CharSequence text) {
        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection != null) {
            inputConnection.commitText(text, 1);
        }
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeUp() {
    }
}
