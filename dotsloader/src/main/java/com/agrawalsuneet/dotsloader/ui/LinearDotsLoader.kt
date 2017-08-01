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


    private var mNoOfDots = 3
    private var mDotsDist = 15

    protected var mSelRadius = 38
    protected var diffRadius: Int = 0

    private var mIsSingleDir = true

    private var isFwdDir = true

    protected var mExpandOnSelect = false

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
        this.mNoOfDots = typedArray.getInt(R.styleable.LinearDotsLoader_loader_noOfDots, 3)

        this.mSelRadius = typedArray.getDimensionPixelSize(R.styleable.LinearDotsLoader_loader_selectedRadius, radius + 10)

        this.mDotsDist = typedArray.getDimensionPixelSize(R.styleable.LinearDotsLoader_loader_dotsDist, 15)

        this.mIsSingleDir = typedArray.getBoolean(R.styleable.LinearDotsLoader_loader_isSingleDir, false)
        this.mExpandOnSelect = typedArray.getBoolean(R.styleable.LinearDotsLoader_loader_expandOnSelect, false)

        typedArray.recycle()

        initCordinates()
    }

    override fun initCordinates() {
        diffRadius = mSelRadius - radius

        dotsXCorArr = FloatArray(mNoOfDots)

        //init X cordinates for all dots
        for (i in 0..mNoOfDots - 1) {
            dotsXCorArr!![i] = (i * mDotsDist + (i * 2 + 1) * radius).toFloat()
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

        if (mExpandOnSelect) {
            calWidth = (2 * mNoOfDots * radius + (mNoOfDots - 1) * mDotsDist + 2 * diffRadius)
            calHeight = 2 * mSelRadius
        } else {
            calHeight = 2 * radius
            calWidth = (2 * mNoOfDots * radius + (mNoOfDots - 1) * mDotsDist)
        }
        setMeasuredDimension(calWidth, calHeight)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCircle(canvas)

        if (shouldAnimate) {
            Handler().postDelayed({
                if (System.currentTimeMillis() - logTime >= animDur) {

                    if (mIsSingleDir && selectedDotPos == mNoOfDots) {
                        selectedDotPos = 1
                    } else if (mIsSingleDir) {
                        selectedDotPos = selectedDotPos + 1
                    } else if (!mIsSingleDir && isFwdDir) {
                        selectedDotPos = selectedDotPos + 1
                        if (selectedDotPos == mNoOfDots) {
                            isFwdDir = !isFwdDir
                        }
                    } else if (!mIsSingleDir && !isFwdDir) {
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
        for (i in 0..mNoOfDots - 1) {
            /*canvas.drawCircle(dotsXCorArr[i], radius, radius,
                    i + 1 == selectedDotPos ? selectedCirclePaint : defaultCirclePaint);*/

            val isSelected = i + 1 == selectedDotPos

            var xCor = dotsXCorArr!![i]
            if (mExpandOnSelect) {
                if (i + 1 == selectedDotPos) {
                    xCor += diffRadius.toFloat()
                } else if (i + 1 > selectedDotPos) {
                    xCor += (2 * diffRadius).toFloat()
                }
            }

            canvas.drawCircle(
                    xCor,
                    (if (mExpandOnSelect) mSelRadius else radius).toFloat(),
                    (if (mExpandOnSelect && isSelected) mSelRadius else radius).toFloat(),
                    if (isSelected) selectedCirclePaint else defaultCirclePaint)
        }
    }

    var dotsDist: Int
        get() = mDotsDist
        set(dotsDist) {
            this.mDotsDist = dotsDist
            initCordinates()
        }

    var noOfDots: Int
        get() = mNoOfDots
        set(noOfDots) {
            this.mNoOfDots = noOfDots
            initCordinates()
        }

    var selRadius: Int
        get() = mSelRadius
        set(selRadius) {
            this.mSelRadius = selRadius
            initCordinates()
        }

    var isSingleDir: Boolean
        get() = mIsSingleDir
        set(isSingleDir) {
            this.mIsSingleDir = isSingleDir
        }

    var isExpandOnSelect: Boolean
        get() = mExpandOnSelect
        set(expandOnSelect) {
            this.mExpandOnSelect = expandOnSelect
            initCordinates()
        }

}
