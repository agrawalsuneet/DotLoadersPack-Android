package com.agrawalsuneet.dotsloader.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.util.AttributeSet

import com.agrawalsuneet.dotsloader.R

/**
 * Created by ballu on 04/07/17.
 */

class LinearDotsLoader : DotsLoader {

    var isSingleDir = true

    private var diffRadius: Int = 0
    private var isFwdDir = true

    constructor(context: Context) : super(context) {
        initCordinates()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttributes(attrs)
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

        initCordinates()
    }

    override fun initCordinates() {
        diffRadius = this.selRadius - radius

        dotsXCorArr = FloatArray(this.noOfDots)

        //init X cordinates for all dots
        for (i in 0..this.noOfDots - 1) {
            dotsXCorArr!![i] = (i * dotsDistance + (i * 2 + 1) * radius).toFloat()
        }

        //init paints for drawing dots
        defaultCirclePaint = Paint()
        defaultCirclePaint!!.isAntiAlias = true
        defaultCirclePaint!!.style = Paint.Style.FILL
        defaultCirclePaint!!.color = defaultColor

        selectedCirclePaint = Paint()
        selectedCirclePaint!!.isAntiAlias = true
        selectedCirclePaint!!.style = Paint.Style.FILL
        selectedCirclePaint!!.color = selectedColor
    }

    override fun initPaints() {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

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
            Handler().postDelayed({
                if (System.currentTimeMillis() - logTime >= animDur) {

                    if (this.isSingleDir && selectedDotPos == this.noOfDots) {
                        selectedDotPos = 1
                    } else if (this.isSingleDir) {
                        selectedDotPos = selectedDotPos + 1
                    } else if (!this.isSingleDir && isFwdDir) {
                        selectedDotPos = selectedDotPos + 1
                        if (selectedDotPos == this.noOfDots) {
                            isFwdDir = !isFwdDir
                        }
                    } else if (!this.isSingleDir && !isFwdDir) {
                        selectedDotPos = selectedDotPos - 1
                        if (selectedDotPos == 1) {
                            isFwdDir = !isFwdDir
                        }
                    }

                    invalidate()
                    logTime = System.currentTimeMillis()
                }
            }, animDur.toLong())
        }
    }

    private fun drawCircle(canvas: Canvas) {
        for (i in 0..this.noOfDots - 1) {
            /*canvas.drawCircle(dotsXCorArr[i], radius, radius,
                    i + 1 == selectedDotPos ? selectedCirclePaint : defaultCirclePaint);*/

            val isSelected = i + 1 == selectedDotPos

            var xCor = dotsXCorArr!![i]
            if (expandOnSelect) {
                if (i + 1 == selectedDotPos) {
                    xCor += diffRadius.toFloat()
                } else if (i + 1 > selectedDotPos) {
                    xCor += (2 * diffRadius).toFloat()
                }
            }

            canvas.drawCircle(
                    xCor,
                    (if (expandOnSelect) this.selRadius else radius).toFloat(),
                    (if (expandOnSelect && isSelected) this.selRadius else radius).toFloat(),
                    if (isSelected) selectedCirclePaint else defaultCirclePaint)
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