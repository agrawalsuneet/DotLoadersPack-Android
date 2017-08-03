package com.agrawalsuneet.dotsloader.ui

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.utils.Helper

/**
 * Created by Suneet on 13/01/17.
 */
abstract class DotsLoader : View {

    var animDur = 500

    lateinit var dotsXCorArr: FloatArray

    protected var defaultCirclePaint: Paint? = null
    protected var selectedCirclePaint: Paint? = null

    protected lateinit var firstShadowPaint: Paint
    protected lateinit var secondShadowPaint: Paint

    protected var isShadowColorSet = false

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

        this.defaultColor = typedArray.getColor(R.styleable.DotsLoader_loader_defaultColor,
                resources.getColor(R.color.loader_defalut))
        this.selectedColor = typedArray.getColor(R.styleable.DotsLoader_loader_selectedColor,
                resources.getColor(R.color.loader_selected))

        this.radius = typedArray.getDimensionPixelSize(R.styleable.DotsLoader_loader_circleRadius, 30)

        this.animDur = typedArray.getInt(R.styleable.DotsLoader_loader_animDur, 500)

        this.showRunningShadow = typedArray.getBoolean(R.styleable.DotsLoader_loader_showRunningShadow, true)

        this.firstShadowColor = typedArray.getColor(R.styleable.DotsLoader_loader_firstShadowColor, 0)
        this.secondShadowColor = typedArray.getColor(R.styleable.DotsLoader_loader_secondShadowColor, 0)

        if (firstShadowColor != 0 && secondShadowColor != 0) {
            isShadowColorSet = true
        }

        typedArray.recycle()
    }

    protected abstract fun initCordinates()

    //init paints for drawing dots
    fun initPaints() {
        defaultCirclePaint = Paint()
        defaultCirclePaint!!.isAntiAlias = true
        defaultCirclePaint!!.style = Paint.Style.FILL
        defaultCirclePaint!!.color = defaultColor

        selectedCirclePaint = Paint()
        selectedCirclePaint!!.isAntiAlias = true
        selectedCirclePaint!!.style = Paint.Style.FILL
        selectedCirclePaint!!.color = selectedColor
    }

    //init paints for drawing shadow dots
    fun initShadowPaints() {
        if (showRunningShadow) {
            if (!isShadowColorSet) {
                firstShadowColor = Helper.adjustAlpha(selectedColor, 0.7f)
                secondShadowColor = Helper.adjustAlpha(selectedColor, 0.5f)
                isShadowColorSet = true
            }

            firstShadowPaint = Paint()
            firstShadowPaint.isAntiAlias = true
            firstShadowPaint.style = Paint.Style.FILL
            firstShadowPaint.color = firstShadowColor

            secondShadowPaint = Paint()
            secondShadowPaint.isAntiAlias = true
            secondShadowPaint.style = Paint.Style.FILL
            secondShadowPaint.color = secondShadowColor
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

    var defaultColor: Int = resources.getColor(R.color.loader_defalut)
        get() = field
        set(defaultColor) {
            field = defaultColor
            if (defaultCirclePaint != null) {
                defaultCirclePaint!!.color = defaultColor
            }
        }

    open var selectedColor: Int = resources.getColor(R.color.loader_selected)
        get() = field
        set(selectedColor) {
            field = selectedColor
            if (selectedCirclePaint != null) {
                selectedCirclePaint!!.color = selectedColor
            }
            initShadowPaints()
        }

    var radius: Int = 30
        get() = field
        set(radius) {
            field = radius
            initCordinates()
        }

    var showRunningShadow: Boolean = true
        get() = field
        set(showRunningShadow) {
            field = showRunningShadow
            initShadowPaints()
        }

    var firstShadowColor: Int = 0
        get() = field
        set(value) {
            field = value
            isShadowColorSet = true
            initShadowPaints()
        }


    var secondShadowColor: Int = 0
        get() = field
        set(value) {
            field = value
            isShadowColorSet = true
            initShadowPaints()
        }
}
