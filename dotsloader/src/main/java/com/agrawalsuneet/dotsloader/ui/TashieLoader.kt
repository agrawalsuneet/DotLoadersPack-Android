package com.agrawalsuneet.dotsloader.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.ui.basicviews.CircleView
import com.agrawalsuneet.dotsloader.ui.basicviews.LoaderContract
import com.agrawalsuneet.dotsloader.ui.basicviews.ModifiedLinearLayout

/**
 * Created by suneet on 10/10/17.
 */
class TashieLoader : ModifiedLinearLayout, LoaderContract {

    var noOfDots: Int = 8

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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val calHeight = 2 * dotsRadius
        val calWidth = (2 * noOfDots * dotsRadius + (noOfDots - 1) * dotsDist)

        setMeasuredDimension(calWidth, calHeight)
    }

    override fun initView() {
        removeAllViews()

        for (i in 0 until noOfDots) {
            val circle = CircleView(context, dotsRadius, resources.getColor(R.color.loader_selected))
            var params = LinearLayout.LayoutParams(2 * dotsRadius, 2 * dotsRadius)

            if (i != noOfDots - 1) {
                params.rightMargin = 10
            }

            addView(circle, params)
        }
    }

}