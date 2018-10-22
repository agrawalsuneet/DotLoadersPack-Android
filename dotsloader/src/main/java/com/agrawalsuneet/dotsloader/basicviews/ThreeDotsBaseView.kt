package com.agrawalsuneet.dotsloader.basicviews

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import com.agrawalsuneet.dotsloader.R

/**
 * Created by suneet on 12/14/17.
 */
abstract class ThreeDotsBaseView : AnimatingLinearLayout {

    var firstDotColor: Int = ContextCompat.getColor(context, R.color.loader_defalut)

    var secondDotColor: Int = ContextCompat.getColor(context, R.color.loader_defalut)

    var thirdDotColor: Int = ContextCompat.getColor(context, R.color.loader_defalut)

    protected lateinit var firstCircle: CircleView
    protected lateinit var secondCircle: CircleView
    protected lateinit var thirdCircle: CircleView

    constructor(context: Context, dotsRadius: Int, dotsDist: Int,
                firstDotColor: Int, secondDotColor: Int, thirdDotColor: Int) : super(context) {
        this.dotsRadius = dotsRadius
        this.dotsDist = dotsDist
        this.firstDotColor = firstDotColor
        this.secondDotColor = secondDotColor
        this.thirdDotColor = thirdDotColor
        this.initView()
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}

