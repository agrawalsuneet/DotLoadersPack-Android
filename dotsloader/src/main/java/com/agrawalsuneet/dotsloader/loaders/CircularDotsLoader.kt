package com.agrawalsuneet.dotsloader.loaders

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.contracts.CircularAbstractView
import java.util.*

/**
 * Created by ballu on 04/07/17.
 */

class CircularDotsLoader : CircularAbstractView {

    private var timer: Timer? = null

    constructor(context: Context) : super(context) {
        initCordinates()
        initPaints()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initCordinates()
        initPaints()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initCordinates()
        initPaints()
    }

    override fun initAttributes(attrs: AttributeSet) {
        super.initAttributes(attrs)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularDotsLoader, 0, 0)

        this.bigCircleRadius = typedArray.getDimensionPixelSize(R.styleable.CircularDotsLoader_loader_bigCircleRadius, 60)

        typedArray.recycle()
    }


    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)

        if (visibility != VISIBLE) {
            timer?.cancel()
        } else if (shouldAnimate) {
            scheduleTimer()
        }
    }

    private fun scheduleTimer() {
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                selectedDotPos++

                if (selectedDotPos > noOfDots) {
                    selectedDotPos = 1
                }

                (context as Activity).runOnUiThread { invalidate() }
            }
        }, 0, animDur.toLong())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawCircle(canvas)
    }

    private fun drawCircle(canvas: Canvas) {
        val firstShadowPos = if (selectedDotPos == 1) 8 else selectedDotPos - 1
        val secondShadowPos = if (firstShadowPos == 1) 8 else firstShadowPos - 1

        for (i in 0..noOfDots - 1) {

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
}
