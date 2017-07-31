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

    private var mBigCircleRadius = 60

    private var showRunningShadow = true
    protected var dotsYCorArr: FloatArray? = null

    private var firstShadowPaint: Paint? = null
    private var secondShadowPaint: Paint? = null
    private var firstShadowColor = 0
    private var secondShadowColor = 0

    private var isShadowColorSet = false

    constructor(context: Context) : super(context) {
        initCordinates()
        initPaints()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttributes(attrs)
        initCordinates()
        initPaints()
        setShadowProperty()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttributes(attrs)
        initCordinates()
        initPaints()
        setShadowProperty()
    }

    override fun initAttributes(attrs: AttributeSet) {
        super.initAttributes(attrs)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularDotsLoader, 0, 0)

        this.mBigCircleRadius = typedArray.getDimensionPixelSize(R.styleable.CircularDotsLoader_loader_bigCircleRadius, 60)
        this.showRunningShadow = typedArray.getBoolean(R.styleable.CircularDotsLoader_loader_showRunningShadow, true)

        this.firstShadowColor = typedArray.getColor(R.styleable.CircularDotsLoader_loader_firstShadowColor, 0)
        this.secondShadowColor = typedArray.getColor(R.styleable.CircularDotsLoader_loader_secondShadowColor, 0)

        if (firstShadowColor != 0 && secondShadowColor != 0) {
            isShadowColorSet = true
        }

        typedArray.recycle()
    }

    override fun initCordinates() {
        val sin45Radius = SIN_45 * mBigCircleRadius

        dotsXCorArr = FloatArray(mNoOfDots)
        dotsYCorArr = FloatArray(mNoOfDots)

        for (i in 0..mNoOfDots - 1) {
            dotsYCorArr!![i] = (mBigCircleRadius + mRadius).toFloat()
            dotsXCorArr!![i] = dotsYCorArr!![i]
        }

        dotsXCorArr!![1] = dotsXCorArr!![1] + sin45Radius
        dotsXCorArr!![2] = dotsXCorArr!![2] + mBigCircleRadius
        dotsXCorArr!![3] = dotsXCorArr!![3] + sin45Radius

        dotsXCorArr!![5] = dotsXCorArr!![5] - sin45Radius
        dotsXCorArr!![6] = dotsXCorArr!![6] - mBigCircleRadius
        dotsXCorArr!![7] = dotsXCorArr!![7] - sin45Radius

        dotsYCorArr!![0] = dotsYCorArr!![0] - mBigCircleRadius
        dotsYCorArr!![1] = dotsYCorArr!![1] - sin45Radius
        dotsYCorArr!![3] = dotsYCorArr!![3] + sin45Radius

        dotsYCorArr!![4] = dotsYCorArr!![4] + mBigCircleRadius
        dotsYCorArr!![5] = dotsYCorArr!![5] + sin45Radius
        dotsYCorArr!![7] = dotsYCorArr!![7] - sin45Radius
    }

    //init paints for drawing dots
    override fun initPaints() {
        defaultCirclePaint = Paint()
        defaultCirclePaint!!.isAntiAlias = true
        defaultCirclePaint!!.style = Paint.Style.FILL
        defaultCirclePaint!!.color = mDefaultColor

        selectedCirclePaint = Paint()
        selectedCirclePaint!!.isAntiAlias = true
        selectedCirclePaint!!.style = Paint.Style.FILL
        selectedCirclePaint!!.color = mSelectedColor
    }

    private fun setShadowProperty() {
        if (showRunningShadow) {
            if (!isShadowColorSet) {
                firstShadowColor = Helper.adjustAlpha(mSelectedColor, 0.7f)
                secondShadowColor = Helper.adjustAlpha(mSelectedColor, 0.5f)
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

        calWidth = 2 * mBigCircleRadius + 2 * mRadius
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
            //canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], mRadius, isSelected ? selectedCirclePaint : defaultCirclePaint);

            if (i + 1 == selectedDotPos) {
                canvas.drawCircle(dotsXCorArr!![i], dotsYCorArr!![i], mRadius.toFloat(), selectedCirclePaint!!)
            } else if (showRunningShadow && i + 1 == firstShadowPos) {
                canvas.drawCircle(dotsXCorArr!![i], dotsYCorArr!![i], mRadius.toFloat(), firstShadowPaint!!)
            } else if (showRunningShadow && i + 1 == secondShadowPos) {
                canvas.drawCircle(dotsXCorArr!![i], dotsYCorArr!![i], mRadius.toFloat(), secondShadowPaint!!)
            } else {
                canvas.drawCircle(dotsXCorArr!![i], dotsYCorArr!![i], mRadius.toFloat(), defaultCirclePaint!!)
            }

        }
    }

    var bigCircleRadius: Int
        get() = mBigCircleRadius
        set(bigCircleRadius) {
            this.mBigCircleRadius = bigCircleRadius
            initCordinates()
        }

    override var selectedColor: Int
        get() = super.selectedColor
        set(selectedColor) {
            super.selectedColor = selectedColor
            setShadowProperty()
        }



    var isShowRunningShadow: Boolean
        get() = showRunningShadow
        set(showRunningShadow) {
            this.showRunningShadow = showRunningShadow
            setShadowProperty()
        }

    fun getFirstShadowColor(): Int {
        return firstShadowColor
    }

    fun setFirstShadowColor(firstShadowColor: Int) {
        this.firstShadowColor = firstShadowColor
        isShadowColorSet = true
        setShadowProperty()
    }

    fun getSecondShadowColor(): Int {
        return secondShadowColor
    }

    fun setSecondShadowColor(secondShadowColor: Int) {
        this.secondShadowColor = secondShadowColor
        isShadowColorSet = true
        setShadowProperty()
    }
}
