package com.agrawalsuneet.dotsloader.loaders

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.basicviews.DotsLoaderBaseView

/**
 * Created by ballu on 04/07/17.
 */

class LinearDotsLoader : DotsLoaderBaseView {

    var isSingleDir = true

    private var diffRadius: Int = 0
    private var isFwdDir = true

    constructor(context: Context) : super(context) {
        initCoordinates()
        initPaints()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttributes(attrs)
        initCoordinates()
        initPaints()
        initShadowPaints()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttributes(attrs)
        initCoordinates()
        initPaints()
        initShadowPaints()
    }

    override fun initAttributes(attrs: AttributeSet) {
        super.initAttributes(attrs)

        with(context.obtainStyledAttributes(attrs, R.styleable.LinearDotsLoader, 0, 0)){
            noOfDots = getInt(R.styleable.LinearDotsLoader_loader_noOfDots, 3)

            selRadius = getDimensionPixelSize(R.styleable.LinearDotsLoader_loader_selectedRadius, radius + 10)

            dotsDistance = getDimensionPixelSize(R.styleable.LinearDotsLoader_loader_dotsDist, 15)

            isSingleDir = getBoolean(R.styleable.LinearDotsLoader_loader_isSingleDir, false)
            expandOnSelect = getBoolean(R.styleable.LinearDotsLoader_loader_expandOnSelect, false)

            recycle()  
        }       
    }

    override fun initCoordinates() {
        diffRadius = this.selRadius - radius

        dotsXCorArr = FloatArray(this.noOfDots)

        //init X cordinates for all dots
        for (i in 0 until this.noOfDots) {
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


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCircle(canvas)

        if (shouldAnimate) {
            postDelayed({
                if (System.currentTimeMillis() - logTime >= animDur) {

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

                    invalidate()
                    logTime = System.currentTimeMillis()
                }
            }, animDur.toLong())
        }
    }

    private fun drawCircle(canvas: Canvas) {
        for (i in 0 until this.noOfDots) {

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

            when {
                i + 1 == selectedDotPos -> canvas.drawCircle(
                        xCor,
                        (if (expandOnSelect) this.selRadius else radius).toFloat(),
                        (if (expandOnSelect) this.selRadius else radius).toFloat(),
                        selectedCirclePaint!!)
                showRunningShadow && i + 1 == firstShadowPos -> canvas.drawCircle(
                        xCor,
                        (if (expandOnSelect) this.selRadius else radius).toFloat(),
                        radius.toFloat(),
                        firstShadowPaint)
                showRunningShadow && i + 1 == secondShadowPos -> canvas.drawCircle(
                        xCor,
                        (if (expandOnSelect) this.selRadius else radius).toFloat(),
                        radius.toFloat(),
                        secondShadowPaint)
                else -> canvas.drawCircle(
                        xCor,
                        (if (expandOnSelect) this.selRadius else radius).toFloat(),
                        radius.toFloat(),
                        defaultCirclePaint!!)
            }
        }
    }

    var dotsDistance: Int = 15
        set(value) {
            field = value
            initCoordinates()
        }

    var noOfDots: Int = 3
        set(noOfDots) {
            field = noOfDots
            initCoordinates()
        }

    var selRadius: Int = 38
        set(selRadius) {
            field = selRadius
            initCoordinates()
        }

    var expandOnSelect: Boolean = false
        set(expandOnSelect) {
            field = expandOnSelect
            initCoordinates()
        }

}
