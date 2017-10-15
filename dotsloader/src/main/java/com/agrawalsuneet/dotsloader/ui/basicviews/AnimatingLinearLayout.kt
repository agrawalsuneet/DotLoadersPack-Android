package com.agrawalsuneet.dotsloader.ui.basicviews

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import com.agrawalsuneet.dotsloader.R

/**
 * Created by suneet on 10/10/17.
 */

abstract class AnimatingLinearLayout : LinearLayout, LoaderContract {

    var animDuration: Int = 500

    var interpolator: Interpolator = LinearInterpolator()

    constructor(context: Context?) : super(context) {
    }

    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    override fun initAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AnimatingLinearLayout, 0, 0)

        this.dotsRadius = typedArray.getDimensionPixelSize(R.styleable.AnimatingLinearLayout_all_dotsRadius, 30)
        this.dotsDist = typedArray.getDimensionPixelSize(R.styleable.AnimatingLinearLayout_all_dotsDist, 15)
        this.dotsColor = typedArray.getColor(R.styleable.AnimatingLinearLayout_all_dotsColor,
                resources.getColor(R.color.loader_defalut))

        this.animDuration = typedArray.getInt(R.styleable.AnimatingLinearLayout_all_animDur, 500)

        this.interpolator = AnimationUtils.loadInterpolator(context,
                typedArray.getResourceId(R.styleable.AnimatingLinearLayout_all_interpolator,
                        android.R.anim.linear_interpolator))

        typedArray.recycle()
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
