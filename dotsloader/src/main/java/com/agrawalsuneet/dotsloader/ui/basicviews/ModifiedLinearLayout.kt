package com.agrawalsuneet.dotsloader.ui.basicviews

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import com.agrawalsuneet.dotsloader.R

/**
 * Created by suneet on 10/10/17.
 */

abstract class ModifiedLinearLayout : LinearLayout {

    var animDuration: Int = 500

    var interpolator : Interpolator = LinearInterpolator()

    constructor(context: Context?) : super(context) {
    }

    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }


    var dotsRadius: Int = 30
        get() = field
        set(value) {
            field = value
            initView()
        }

    var dotsDist: Int = 15
        get() = field
        set(value) {
            field = value
            initView()
        }

    var dotsColor: Int = resources.getColor(R.color.loader_defalut)
        get() = field
        set(value) {
            field = value
            initView()
        }

    abstract fun initView()

}
