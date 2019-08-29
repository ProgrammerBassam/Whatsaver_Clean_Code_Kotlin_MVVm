package com.bbalabsi.whatsaver.utils

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageView


class SquareImageView : AppCompatImageView {
    constructor(context: Context) : super(context)

    constructor(context: Context, @Nullable attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, @Nullable attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val size: Int
        if ((MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) xor (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY)) {
            if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY)
                size = width
            else
                size = height
        } else
            size = Math.min(width, height)
        setMeasuredDimension(size, size)
    }
}