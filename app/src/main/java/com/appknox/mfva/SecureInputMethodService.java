package com.appknox.mfva;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.view.inputmethod.InputConnection;

/**
 * In-app keyboard for sensitive fields. Keystrokes are committed to the focused
 * editor and are not logged or sent off-device.
 */
public class SecureInputMethodService extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener {

    @Override
    public View onCreateInputView() {
        KeyboardView keyboardView = (KeyboardView) getLayoutInflater()
                .inflate(R.layout.secure_keyboard, null);
        keyboardView.setKeyboard(new Keyboard(this, R.xml.secure_keyboard));
        keyboardView.setOnKeyboardActionListener(this);
        keyboardView.setPreviewEnabled(false);
        return keyboardView;
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection == null) {
            return;
        }
        if (primaryCode == Keyboard.KEYCODE_DELETE) {
            inputConnection.deleteSurroundingText(1, 0);
            return;
        }
        inputConnection.commitText(String.valueOf((char) primaryCode), 1);
    }

    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onText(CharSequence text) {
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
