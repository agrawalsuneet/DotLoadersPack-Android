package com.agrawalsuneet.dotsloader.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.util.AttributeSet

import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.utils.Helper

/**
 * Created by ballu on 04/07/17.
 */

class CircularDotsLoader : DotsLoader {

    private val mNoOfDots = 8
    private val SIN_45 = 0.7071f

    protected var dotsYCorArr: FloatArray? = null

    private var firstShadowPaint: Paint? = null
    private var secondShadowPaint: Paint? = null

    private var isShadowColorSet = false

    constructor(context: Context) : super(context) {
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

    override fun initAttributes(attrs: AttributeSet) {
        super.initAttributes(attrs)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularDotsLoader, 0, 0)

        this.bigCircleRadius = typedArray.getDimensionPixelSize(R.styleable.CircularDotsLoader_loader_bigCircleRadius, 60)
        this.showRunningShadow = typedArray.getBoolean(R.styleable.CircularDotsLoader_loader_showRunningShadow, true)

        this.firstShadowColor = typedArray.getColor(R.styleable.CircularDotsLoader_loader_firstShadowColor, 0)
        this.secondShadowColor = typedArray.getColor(R.styleable.CircularDotsLoader_loader_secondShadowColor, 0)

        if (firstShadowColor != 0 && secondShadowColor != 0) {
            isShadowColorSet = true
        }

        typedArray.recycle()
    }

    override fun initCordinates() {
        val sin45Radius = SIN_45 * this.bigCircleRadius

        dotsXCorArr = FloatArray(mNoOfDots)
        dotsYCorArr = FloatArray(mNoOfDots)

        for (i in 0..mNoOfDots - 1) {
            dotsYCorArr!![i] = (this.bigCircleRadius + radius).toFloat()
            dotsXCorArr!![i] = dotsYCorArr!![i]
        }

        dotsXCorArr!![1] = dotsXCorArr!![1] + sin45Radius
        dotsXCorArr!![2] = dotsXCorArr!![2] + this.bigCircleRadius
        dotsXCorArr!![3] = dotsXCorArr!![3] + sin45Radius

        dotsXCorArr!![5] = dotsXCorArr!![5] - sin45Radius
        dotsXCorArr!![6] = dotsXCorArr!![6] - this.bigCircleRadius
        dotsXCorArr!![7] = dotsXCorArr!![7] - sin45Radius

        dotsYCorArr!![0] = dotsYCorArr!![0] - this.bigCircleRadius
        dotsYCorArr!![1] = dotsYCorArr!![1] - sin45Radius
        dotsYCorArr!![3] = dotsYCorArr!![3] + sin45Radius

        dotsYCorArr!![4] = dotsYCorArr!![4] + this.bigCircleRadius
        dotsYCorArr!![5] = dotsYCorArr!![5] + sin45Radius
        dotsYCorArr!![7] = dotsYCorArr!![7] - sin45Radius
    }

    //init paints for drawing dots
    override fun initPaints() {
        defaultCirclePaint = Paint()
        defaultCirclePaint!!.isAntiAlias = true
        defaultCirclePaint!!.style = Paint.Style.FILL
        defaultCirclePaint!!.color = defaultColor

        selectedCirclePaint = Paint()
        selectedCirclePaint!!.isAntiAlias = true
        selectedCirclePaint!!.style = Paint.Style.FILL
        selectedCirclePaint!!.color = selectedColor
    }

    private fun initShadowPaints() {
        if (showRunningShadow) {
            if (!isShadowColorSet) {
                firstShadowColor = Helper.adjustAlpha(selectedColor, 0.7f)
                secondShadowColor = Helper.adjustAlpha(selectedColor, 0.5f)
                isShadowColorSet = true
            }

            firstShadowPaint = Paint()
            firstShadowPaint!!.isAntiAlias = true
            firstShadowPaint!!.style = Paint.Style.FILL
            firstShadowPaint!!.color = firstShadowColor

            secondShadowPaint = Paint()
            secondShadowPaint!!.isAntiAlias = true
            secondShadowPaint!!.style = Paint.Style.FILL
            secondShadowPaint!!.color = secondShadowColor
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        calWidth = 2 * this.bigCircleRadius + 2 * radius
        calHeight = calWidth

        setMeasuredDimension(calWidth, calHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawCircle(canvas)

        if (shouldAnimate) {
            Handler().postDelayed({
                if (System.currentTimeMillis() - logTime >= animDur) {

                    selectedDotPos = selectedDotPos + 1

                    if (selectedDotPos > mNoOfDots) {
                        selectedDotPos = 1
                    }

                    invalidate()
                    logTime = System.currentTimeMillis()
                }
            }, animDur.toLong())
        }
    }

    private fun drawCircle(canvas: Canvas) {
        val firstShadowPos = if (selectedDotPos == 1) 8 else selectedDotPos - 1
        val secondShadowPos = if (firstShadowPos == 1) 8 else firstShadowPos - 1

        for (i in 0..mNoOfDots - 1) {
            //boolean isSelected = (i + 1 == selectedDotPos);
            //canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], radius, isSelected ? selectedCirclePaint : defaultCirclePaint);

            if (i + 1 == selectedDotPos) {
                canvas.drawCircle(dotsXCorArr!![i], dotsYCorArr!![i], radius.toFloat(), selectedCirclePaint!!)
            } else if (this.showRunningShadow && i + 1 == firstShadowPos) {
                canvas.drawCircle(dotsXCorArr!![i], dotsYCorArr!![i], radius.toFloat(), firstShadowPaint!!)
            } else if (this.showRunningShadow && i + 1 == secondShadowPos) {
                canvas.drawCircle(dotsXCorArr!![i], dotsYCorArr!![i], radius.toFloat(), secondShadowPaint!!)
            } else {
                canvas.drawCircle(dotsXCorArr!![i], dotsYCorArr!![i], radius.toFloat(), defaultCirclePaint!!)
            }

        }
    }


    override var selectedColor: Int
        get() = super.selectedColor
        set(selectedColor) {
            super.selectedColor = selectedColor
            initShadowPaints()
        }

    var bigCircleRadius: Int = 60
        get() = field
        set(bigCircleRadius) {
            field = bigCircleRadius
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
