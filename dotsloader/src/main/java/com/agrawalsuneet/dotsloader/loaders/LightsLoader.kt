package com.agrawalsuneet.dotsloader.loaders

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.agrawalsuneet.dotsloader.basicviews.CircleView
import com.agrawalsuneet.dotsloader.contracts.LoaderContract

class LightsLoader : LinearLayout, LoaderContract {

    var noOfCircles: Int = 3
        set(value) {
            field = if (value < 1) 1 else value
        }

    var circleRadius: Int = 30
    var circleColor: Int = resources.getColor(android.R.color.holo_purple)

    private var calWidthHeight: Int = 0


    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttributes(attrs)
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttributes(attrs)
        initView()
    }

    override fun initAttributes(attrs: AttributeSet) {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (calWidthHeight == 0) {
            calWidthHeight = (2 * circleRadius * noOfCircles)
        }

        setMeasuredDimension(calWidthHeight, calWidthHeight)
    }


    private fun initView() {
        removeAllViews()
        removeAllViewsInLayout()

        orientation = LinearLayout.VERTICAL

        if (calWidthHeight == 0) {
            calWidthHeight = (2 * circleRadius * noOfCircles)
        }

        for (countI in 0 until noOfCircles) {
            val linearLayout = LinearLayout(context)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            linearLayout.layoutParams = params

            for (countJ in 0 until noOfCircles) {
                val circleView = CircleView(context, circleRadius, circleColor)
                linearLayout.addView(circleView)
            }

            addView(linearLayout)
        }
    }
}