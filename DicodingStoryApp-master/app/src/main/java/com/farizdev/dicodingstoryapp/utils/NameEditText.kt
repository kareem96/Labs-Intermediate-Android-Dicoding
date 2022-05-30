package com.farizdev.dicodingstoryapp.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.farizdev.dicodingstoryapp.R

class  NameEditText : AppCompatEditText {
    private lateinit var inactiveBackground: Drawable
    private lateinit var activeBackground: Drawable
    private lateinit var nameImage: Drawable

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
        activeBackground = ContextCompat.getDrawable(context, R.drawable.bg_valid) as Drawable
        inactiveBackground = ContextCompat.getDrawable(context, R.drawable.bg_inactive) as Drawable
        nameImage = ContextCompat.getDrawable(context, R.drawable.ic_baseline_person) as Drawable

        background = inactiveBackground

        setButtonDrawables()
    }

    private fun setButtonDrawables(
        start: Drawable? = nameImage,
        top: Drawable? = null,
        end: Drawable? = null,
        bottom: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = resources.getString(R.string.name)
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        background = if (focused) activeBackground else inactiveBackground
    }
}