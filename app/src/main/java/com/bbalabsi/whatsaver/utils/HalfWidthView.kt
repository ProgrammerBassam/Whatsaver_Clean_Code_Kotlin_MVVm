package com.bbalabsi.whatsaver.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View

class HalfWidthView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //We take the measured height and set half of it as height
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec / 4)
    }

}