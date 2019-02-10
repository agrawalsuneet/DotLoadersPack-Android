package com.agrawalsuneet.dotsloader.basicviews

import android.content.Context
import android.util.AttributeSet
import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.contracts.CircularAbstractView

/**
 * Created by suneet on 12/29/17.
 */
class CircularLoaderBaseView : CircularAbstractView {

    constructor(context: Context) : super(context) {
        initCordinates()
        initPaints()
    }

    constructor(context: Context, dotsRadius: Int, bigCircleRadius: Int, dotsColor: Int) : super(context) {
        this.radius = dotsRadius
        this.bigCircleRadius = bigCircleRadius
        this.defaultColor = dotsColor

        initCordinates()
        initPaints()
    }

    constructor(context: Context, dotsRadius: Int, bigCircleRadius: Int, dotsColorsArray: IntArray) : super(context) {
        this.radius = dotsRadius
        this.bigCircleRadius = bigCircleRadius
        this.dotsColorsArray = dotsColorsArray
        this.useMultipleColors = true

        initCordinates()
        initPaints()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttributes(attrs)
        initCordinates()
        initPaints()
        initShadowPaints()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttributes(attrs)
        initCordinates()
        initPaints()
        initShadowPaints()
    }

}