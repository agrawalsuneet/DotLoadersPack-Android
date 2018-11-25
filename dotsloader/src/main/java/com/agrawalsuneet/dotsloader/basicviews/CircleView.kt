package com.agrawalsuneet.dotsloader.basicviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.agrawalsuneet.dotsloader.R

/**
 * Created by ballu on 13/08/17.
 */

class CircleView : View {

    var circleRadius: Int = 30
    var strokeWidth: Int = 0

    var circleColor: Int = 0
    var drawOnlyStroke: Boolean = false

    private var xyCordinates: Float = 0.0f

    private val paint: Paint = Paint()

    constructor(context: Context, circleRadius: Int, circleColor: Int) : super(context) {
        this.circleRadius = circleRadius
        this.circleColor = circleColor

        initValues()
    }

    constructor(context: Context, circleRadius: Int, circleColor: Int, drawOnlyStroke: Boolean, strokeWidth: Int) : super(context) {
        this.circleRadius = circleRadius
        this.circleColor = circleColor

        this.drawOnlyStroke = drawOnlyStroke
        this.strokeWidth = strokeWidth

        initValues()
    }

    constructor(context: Context) : super(context) {
        initValues()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttributes(attrs)
        initValues()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttributes(attrs)
        initValues()
    }


    fun initAttributes(attrs: AttributeSet) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView, 0, 0)

        this.circleRadius = typedArray.getDimensionPixelSize(R.styleable.CircleView_circleRadius, 30)
        this.circleColor = typedArray.getColor(R.styleable.CircleView_circleColor, 0)

        this.drawOnlyStroke = typedArray.getBoolean(R.styleable.CircleView_circleDrawOnlystroke, false)

        if (drawOnlyStroke) {
            this.strokeWidth = typedArray.getDimensionPixelSize(R.styleable.CircleView_circleStrokeWidth, 0)
        }

        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthHeight = (2 * (circleRadius)) + strokeWidth

        setMeasuredDimension(widthHeight, widthHeight)
    }

    private fun initValues() {
        paint.isAntiAlias = true

        if (drawOnlyStroke) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = strokeWidth.toFloat()
        } else {
            paint.style = Paint.Style.FILL
        }
        paint.color = circleColor

        //adding half of strokeWidth because
        //the stroke will be half inside the drawing circle and half outside
        xyCordinates = (circleRadius + (strokeWidth / 2)).toFloat()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(xyCordinates, xyCordinates, circleRadius.toFloat(), paint)
    }


}