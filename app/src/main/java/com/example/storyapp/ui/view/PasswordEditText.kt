package com.example.storyapp.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class PasswordEditText: AppCompatEditText, View.OnTouchListener {

    constructor(context: Context) : super(context) {
        init()
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().length < 8) setError("Minimal Character Is 8")
                else setError(null)

            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Password"
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return true
    }

}