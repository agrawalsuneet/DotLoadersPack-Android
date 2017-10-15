package com.agrawalsuneet.dotsloader.ui

import android.content.Context
import android.graphics.Canvas
import android.os.Handler
import android.util.AttributeSet
import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.ui.basicviews.DotsLoaderBaseView

/**
 * Created by ballu on 04/07/17.
 */

class CircularDotsLoader : DotsLoaderBaseView {

    private val mNoOfDots = 8
    private val SIN_45 = 0.7071f

    lateinit var dotsYCorArr: FloatArray

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

        typedArray.recycle()
    }

    override fun initCordinates() {
        val sin45Radius = SIN_45 * this.bigCircleRadius

        dotsXCorArr = FloatArray(mNoOfDots)
        dotsYCorArr = FloatArray(mNoOfDots)

        for (i in 0..mNoOfDots - 1) {
            dotsYCorArr[i] = (this.bigCircleRadius + radius).toFloat()
            dotsXCorArr[i] = dotsYCorArr[i]
        }

        dotsXCorArr[1] = dotsXCorArr[1] + sin45Radius
        dotsXCorArr[2] = dotsXCorArr[2] + this.bigCircleRadius
        dotsXCorArr[3] = dotsXCorArr[3] + sin45Radius

        dotsXCorArr[5] = dotsXCorArr[5] - sin45Radius
        dotsXCorArr[6] = dotsXCorArr[6] - this.bigCircleRadius
        dotsXCorArr[7] = dotsXCorArr[7] - sin45Radius

        dotsYCorArr[0] = dotsYCorArr[0] - this.bigCircleRadius
        dotsYCorArr[1] = dotsYCorArr[1] - sin45Radius
        dotsYCorArr[3] = dotsYCorArr[3] + sin45Radius

        dotsYCorArr[4] = dotsYCorArr[4] + this.bigCircleRadius
        dotsYCorArr[5] = dotsYCorArr[5] + sin45Radius
        dotsYCorArr[7] = dotsYCorArr[7] - sin45Radius
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

                    selectedDotPos++

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
                canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], radius.toFloat(), selectedCirclePaint)
            } else if (this.showRunningShadow && i + 1 == firstShadowPos) {
                canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], radius.toFloat(), firstShadowPaint)
            } else if (this.showRunningShadow && i + 1 == secondShadowPos) {
                canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], radius.toFloat(), secondShadowPaint)
            } else {
                canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], radius.toFloat(), defaultCirclePaint)
            }

        }
    }

    var bigCircleRadius: Int = 60
        get() = field
        set(bigCircleRadius) {
            field = bigCircleRadius
            initCordinates()
        }


}
