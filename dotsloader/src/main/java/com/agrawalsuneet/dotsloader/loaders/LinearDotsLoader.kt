package com.agrawalsuneet.dotsloader.loaders

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.contracts.DotsLoaderBaseView
import java.util.*

/**
 * Created by ballu on 04/07/17.
 */

class LinearDotsLoader : DotsLoaderBaseView {

    private var timer: Timer? = null

    var isSingleDir = true

    private var diffRadius: Int = 0
    private var isFwdDir = true

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

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LinearDotsLoader, 0, 0)

        this.noOfDots = typedArray.getInt(R.styleable.LinearDotsLoader_loader_noOfDots, 3)

        this.selRadius = typedArray.getDimensionPixelSize(R.styleable.LinearDotsLoader_loader_selectedRadius, radius + 10)

        this.dotsDistance = typedArray.getDimensionPixelSize(R.styleable.LinearDotsLoader_loader_dotsDist, 15)

        this.isSingleDir = typedArray.getBoolean(R.styleable.LinearDotsLoader_loader_isSingleDir, false)
        this.expandOnSelect = typedArray.getBoolean(R.styleable.LinearDotsLoader_loader_expandOnSelect, false)

        typedArray.recycle()
    }

    override fun initCordinates() {
        diffRadius = this.selRadius - radius

        dotsXCorArr = FloatArray(this.noOfDots)

        //init X cordinates for all dots
        for (i in 0..this.noOfDots - 1) {
            dotsXCorArr[i] = (i * dotsDistance + (i * 2 + 1) * radius).toFloat()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val calWidth: Int
        val calHeight: Int

        if (expandOnSelect) {
            calWidth = (2 * this.noOfDots * radius + (this.noOfDots - 1) * dotsDistance + 2 * diffRadius)
            calHeight = 2 * this.selRadius
        } else {
            calHeight = 2 * radius
            calWidth = (2 * this.noOfDots * radius + (this.noOfDots - 1) * dotsDistance)
        }
        setMeasuredDimension(calWidth, calHeight)
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
                if (isSingleDir) {
                    selectedDotPos++
                    if (selectedDotPos > noOfDots) {
                        selectedDotPos = 1
                    }
                } else {
                    if (isFwdDir) {
                        selectedDotPos++
                        if (selectedDotPos == noOfDots) {
                            isFwdDir = !isFwdDir
                        }
                    } else {
                        selectedDotPos--
                        if (selectedDotPos == 1) {
                            isFwdDir = !isFwdDir
                        }
                    }
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
        for (i in 0..this.noOfDots - 1) {

            var xCor = dotsXCorArr[i]
            if (expandOnSelect) {
                if (i + 1 == selectedDotPos) {
                    xCor += diffRadius.toFloat()
                } else if (i + 1 > selectedDotPos) {
                    xCor += (2 * diffRadius).toFloat()
                }
            }

            var firstShadowPos: Int
            var secondShadowPos: Int

            if ((isFwdDir && selectedDotPos > 1) || selectedDotPos == noOfDots) {
                firstShadowPos = selectedDotPos - 1
                secondShadowPos = firstShadowPos - 1
            } else {
                firstShadowPos = selectedDotPos + 1
                secondShadowPos = firstShadowPos + 1
            }

            if (i + 1 == selectedDotPos) {
                canvas.drawCircle(
                        xCor,
                        (if (expandOnSelect) this.selRadius else radius).toFloat(),
                        (if (expandOnSelect) this.selRadius else radius).toFloat(),
                        selectedCirclePaint)
            } else if (showRunningShadow && i + 1 == firstShadowPos) {
                canvas.drawCircle(
                        xCor,
                        (if (expandOnSelect) this.selRadius else radius).toFloat(),
                        radius.toFloat(),
                        firstShadowPaint)
            } else if (showRunningShadow && i + 1 == secondShadowPos) {
                canvas.drawCircle(
                        xCor,
                        (if (expandOnSelect) this.selRadius else radius).toFloat(),
                        radius.toFloat(),
                        secondShadowPaint)
            } else {
                canvas.drawCircle(
                        xCor,
                        (if (expandOnSelect) this.selRadius else radius).toFloat(),
                        radius.toFloat(),
                        defaultCirclePaint)
            }

        }
    }

    var dotsDistance: Int = 15
        get() = field
        set(value) {
            field = value
            initCordinates()
        }

    var noOfDots: Int = 3
        get() = field
        set(noOfDots) {
            field = noOfDots
            initCordinates()
        }

    var selRadius: Int = 38
        get() = field
        set(selRadius) {
            field = selRadius
            initCordinates()
        }

    var expandOnSelect: Boolean = false
        get() = field
        set(expandOnSelect) {
            field = expandOnSelect
            initCordinates()
        }

}
