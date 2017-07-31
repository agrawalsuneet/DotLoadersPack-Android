package com.agrawalsuneet.dotsloader.ui

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

import com.agrawalsuneet.dotsloader.R
import java.lang.reflect.Array

/**
 * Created by Suneet on 13/01/17.
 */
abstract class DotsLoader : View {

    protected var mDefaultColor = resources.getColor(R.color.loader_defalut)
    protected var mSelectedColor = resources.getColor(R.color.loader_selected)

    protected var mRadius = 30

    var animDur = 500

    var dotsXCorArr: FloatArray? = null

    protected var defaultCirclePaint: Paint? = null
    protected var selectedCirclePaint: Paint? = null

    protected var calWidth: Int = 0
    protected var calHeight: Int = 0

    protected var shouldAnimate = true

    protected var selectedDotPos = 1

    protected var logTime: Long = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    protected open fun initAttributes(attrs: AttributeSet) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DotsLoader, 0, 0)

        this.mDefaultColor = typedArray.getColor(R.styleable.DotsLoader_loader_defaultColor,
                resources.getColor(R.color.loader_defalut))
        this.mSelectedColor = typedArray.getColor(R.styleable.DotsLoader_loader_selectedColor,
                resources.getColor(R.color.loader_selected))

        this.mRadius = typedArray.getDimensionPixelSize(R.styleable.DotsLoader_loader_circleRadius, 30)

        this.animDur = typedArray.getInt(R.styleable.DotsLoader_loader_animDur, 500)

        typedArray.recycle()
    }

    protected abstract fun initCordinates()

    protected abstract fun initPaints()

    fun startAnimation() {
        shouldAnimate = true
        invalidate()
    }

    fun stopAnimation() {
        shouldAnimate = false
        invalidate()
    }

    var defaultColor: Int
        get() = mDefaultColor
        set(defaultColor) {
            this.mDefaultColor = defaultColor
            defaultCirclePaint!!.color = defaultColor
        }

    open var selectedColor: Int
        get() = mSelectedColor
        set(selectedColor) {
            this.mSelectedColor = selectedColor
            selectedCirclePaint!!.color = selectedColor
        }

    var radius: Int
        get() = mRadius
        set(radius) {
            this.mRadius = radius
            initCordinates()
        }
}
