package org.hh.desikeyboard

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.text.TextUtils
import android.view.View

class DesiInputMethodService : InputMethodService(), KeyboardView.OnKeyboardActionListener {
    override fun onCreateInputView(): View {
        // get the KeyboardView and add our Keyboard layout to it
        val keyboardView = layoutInflater.inflate(R.layout.keyboard_view, null) as KeyboardView
        val keyboard = Keyboard(this, R.xml.number_pad)
        keyboardView.keyboard = keyboard
        keyboardView.setOnKeyboardActionListener(this)
        return keyboardView
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray) {
        val ic = currentInputConnection ?: return
        when (primaryCode) {
            Keyboard.KEYCODE_DELETE -> {
                val selectedText = ic.getSelectedText(0)
                if (TextUtils.isEmpty(selectedText)) {
                    // no selection, so delete previous character
                    ic.deleteSurroundingText(1, 0)
                } else {
                    // delete the selection
                    ic.commitText("", 1)
                }
            }

            else -> {
                val code = primaryCode.toChar()
                ic.commitText(code.toString(), 1)
            }
        }
    }

    override fun onPress(primaryCode: Int) {}
    override fun onRelease(primaryCode: Int) {}
    override fun onText(text: CharSequence) {}
    override fun swipeLeft() {}
    override fun swipeRight() {}
    override fun swipeDown() {}
    override fun swipeUp() {}
}