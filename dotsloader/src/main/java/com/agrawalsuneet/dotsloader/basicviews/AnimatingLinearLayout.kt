package com.agrawalsuneet.dotsloader.basicviews

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import com.agrawalsuneet.dotsloader.R

/**
 * Created by suneet on 10/10/17.
 */

abstract class AnimatingLinearLayout : LinearLayout, LoaderContract {

    open var animDuration: Int = 500

    open var interpolator: Interpolator = LinearInterpolator()

    var dotsRadius: Int = 30
        set(value) {
            field = value
            initView()
        }

    var dotsDist: Int = 15
        set(value) {
            field = value
            initView()
        }

    var dotsColor: Int = ContextCompat.getColor(context, R.color.loader_defalut)
        set(value) {
            field = value
            initView()
        }

    abstract fun initView()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

}
