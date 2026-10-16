package com.appknox.mfva;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;

public class SecureInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private KeyboardView keyboardView;
    private Keyboard keyboard;

    @Override
    public View onCreateInputView() {
        View view = getLayoutInflater().inflate(R.layout.secure_keyboard, null);
        keyboardView = (KeyboardView) view.findViewById(R.id.keyboard_view);
        keyboard = new Keyboard(this, R.xml.input_method_config);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
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
        if (primaryCode == Keyboard.KEYCODE_DELETE) {
            getCurrentInputConnection().deleteSurroundingText(1, 0);
        } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {
            // Handle shift key
        } else {
            char c = (char) primaryCode;
            getCurrentInputConnection().commitText(String.valueOf(c), 1);
        }
    }

    @Override
    public void onText(CharSequence text) {
        getCurrentInputConnection().commitText(text, 1);
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
