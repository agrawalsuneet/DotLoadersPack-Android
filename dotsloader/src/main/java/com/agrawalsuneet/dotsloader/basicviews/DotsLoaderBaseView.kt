package com.agrawalsuneet.dotsloader.basicviews

import android.content.Context
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.utils.Helper

/**
 * Created by Suneet on 13/01/17.
 */
abstract class DotsLoaderBaseView : View, LoaderContract {

    var animDur = 500

    lateinit var dotsXCorArr: FloatArray

    protected var defaultCirclePaint: Paint? = null
    protected var selectedCirclePaint: Paint? = null

    protected lateinit var firstShadowPaint: Paint
    protected lateinit var secondShadowPaint: Paint

    protected var isShadowColorSet = false

    protected var shouldAnimate = true

    protected var selectedDotPos = 1

    protected var logTime = 0L

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun initAttributes(attrs: AttributeSet) {
        with(context.obtainStyledAttributes(attrs, R.styleable.DotsLoaderBaseView, 0, 0)) {
            defaultColor = getColor(R.styleable.DotsLoaderBaseView_loader_defaultColor,
                    ContextCompat.getColor(context, R.color.loader_defalut))
            selectedColor = getColor(R.styleable.DotsLoaderBaseView_loader_selectedColor,
                    ContextCompat.getColor(context, R.color.loader_selected))

            radius = getDimensionPixelSize(R.styleable.DotsLoaderBaseView_loader_circleRadius, 30)

            animDur = getInt(R.styleable.DotsLoaderBaseView_loader_animDur, 500)

            showRunningShadow = getBoolean(R.styleable.DotsLoaderBaseView_loader_showRunningShadow, true)

            firstShadowColor = getColor(R.styleable.DotsLoaderBaseView_loader_firstShadowColor, 0)
            secondShadowColor = getColor(R.styleable.DotsLoaderBaseView_loader_secondShadowColor, 0)

            recycle()
        }

    }

    protected abstract fun initCoordinates()

    //init paints for drawing dots
    fun initPaints() {
        defaultCirclePaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = defaultColor
        }

        selectedCirclePaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = selectedColor
        }
    }

    //init paints for drawing shadow dots
    fun initShadowPaints() {
        if (showRunningShadow) {
            if (!isShadowColorSet) {
                firstShadowColor = Helper.adjustAlpha(selectedColor, 0.7f)
                secondShadowColor = Helper.adjustAlpha(selectedColor, 0.5f)
                isShadowColorSet = true
            }

            firstShadowPaint = Paint().apply {
                isAntiAlias = true
                style = Paint.Style.FILL
                color = firstShadowColor
            }

            secondShadowPaint = Paint().apply {
                isAntiAlias = true
                style = Paint.Style.FILL
                color = secondShadowColor
            }
        }
    }

    fun startAnimation() {
        shouldAnimate = true
        invalidate()
    }

    fun stopAnimation() {
        shouldAnimate = false
        invalidate()
    }

    var defaultColor: Int = ContextCompat.getColor(context, R.color.loader_defalut)
        set(defaultColor) {
            field = defaultColor
            defaultCirclePaint?.let {
                it.color = defaultColor
            }
        }

    open var selectedColor: Int = ContextCompat.getColor(context, R.color.loader_selected)
        set(selectedColor) {
            field = selectedColor
            selectedCirclePaint?.let {
                it.color = selectedColor
                initShadowPaints()
            }
        }

    var radius: Int = 30
        set(radius) {
            field = radius
            initCoordinates()
        }

    var showRunningShadow: Boolean = true

    var firstShadowColor: Int = 0
        set(value) {
            field = value
            if (value != 0) {
                isShadowColorSet = true
                initShadowPaints()
            }
        }


    var secondShadowColor: Int = 0
        set(value) {
            field = value
            if (value != 0) {
                isShadowColorSet = true
                initShadowPaints()
            }
        }
}
