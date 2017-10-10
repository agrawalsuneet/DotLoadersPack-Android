package com.agrawalsuneet.dotsloader.ui.basicviews

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout

/**
 * Created by suneet on 10/10/17.
 */

open class ModifiedLinearLayout : LinearLayout {

    var animDuration: Int = 500

    var interpolator : Interpolator = LinearInterpolator()

    constructor(context: Context?) : super(context) {
    }

    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

}
