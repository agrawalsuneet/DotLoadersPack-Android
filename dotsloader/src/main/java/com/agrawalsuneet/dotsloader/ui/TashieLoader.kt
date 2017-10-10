package com.agrawalsuneet.dotsloader.ui

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.LinearLayout
import com.agrawalsuneet.dotsloader.ui.basicviews.LoaderContract
import com.agrawalsuneet.dotsloader.ui.basicviews.ModifiedLinearLayout

/**
 * Created by suneet on 10/10/17.
 */
class TashieLoader : ModifiedLinearLayout, LoaderContract{


    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs) {
        initAttributes(attrs)
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttributes(attrs)
        initView()
    }


    override fun initAttributes(attrs: AttributeSet) {

    }

    private fun initView() {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


    }

}