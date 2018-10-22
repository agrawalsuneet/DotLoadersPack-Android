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

    private val paint: Paint = Paint()

    constructor(context: Context, circleRadius: Int, circleColor: Int) : super(context) {
        this.circleRadius = circleRadius
        this.circleColor = circleColor
    }

    constructor(context: Context, circleRadius: Int, circleColor: Int, drawOnlyStroke: Boolean, strokeWidth: Int) : super(context) {
        this.circleRadius = circleRadius
        this.circleColor = circleColor

        this.drawOnlyStroke = drawOnlyStroke
        this.strokeWidth = strokeWidth
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttributes(attrs)
    }

    fun initAttributes(attrs: AttributeSet) {
        with(context.obtainStyledAttributes(attrs, R.styleable.CircleView, 0, 0)){
            circleRadius = getDimensionPixelSize(R.styleable.CircleView_circleRadius, 30)
            circleColor = getColor(R.styleable.CircleView_circleColor, 0)

            drawOnlyStroke = getBoolean(R.styleable.CircleView_circleDrawOnlystroke, false)

            if (drawOnlyStroke) {
                strokeWidth = getDimensionPixelSize(R.styleable.CircleView_circleStrokeWidth, 0)
            }

            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        
        val widthHeight = (2 * (circleRadius)) + strokeWidth

        setMeasuredDimension(widthHeight, widthHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

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
        val xyCoordinates = (circleRadius + (strokeWidth / 2)).toFloat()

        canvas.drawCircle(xyCoordinates, xyCoordinates, circleRadius.toFloat(), paint)
    }


}