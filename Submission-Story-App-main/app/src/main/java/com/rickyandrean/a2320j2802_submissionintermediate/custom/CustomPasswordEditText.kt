package com.rickyandrean.a2320j2802_submissionintermediate.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.rickyandrean.a2320j2802_submissionintermediate.R

class CustomPasswordEditText : AppCompatEditText {
    private lateinit var inactiveBackground: Drawable
    private lateinit var validBackground: Drawable
    private lateinit var invalidBackground: Drawable
    private lateinit var passwordImage: Drawable
    var valid: Boolean = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        // Image drawable initialization
        validBackground =
            ContextCompat.getDrawable(context, R.drawable.bg_valid) as Drawable
        invalidBackground =
            ContextCompat.getDrawable(context, R.drawable.bg_invalid) as Drawable
        inactiveBackground =
            ContextCompat.getDrawable(context, R.drawable.bg_inactive) as Drawable
        passwordImage = ContextCompat.getDrawable(context, R.drawable.ic_key) as Drawable

        // Determine background
        background = inactiveBackground

        // Hide password text
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        // To show the key icon for the first time
        setButtonDrawables()

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                validatePassword(s?.toString())
            }
        })
    }

    private fun setButtonDrawables(
        start: Drawable? = passwordImage,
        top: Drawable? = null,
        end: Drawable? = null,
        bottom: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom)
    }

    private fun validatePassword(text: String?) {
        if (text != null) {
            valid = text.length >= 6
        }

        background = if (valid) validBackground else invalidBackground
        if (!valid) error = resources.getString(R.string.invalid_password)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = resources.getString(R.string.password)
        typeface = ResourcesCompat.getFont(context, R.font.roboto_medium)
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)

        setButtonDrawables()
        background = if (focused) {
            if (valid) validBackground else invalidBackground
        } else {
            inactiveBackground
        }
    }
}