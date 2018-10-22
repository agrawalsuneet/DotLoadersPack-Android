package com.agrawalsuneet.dotsloader.loaders

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.basicviews.CircleView
import com.agrawalsuneet.dotsloader.basicviews.LoaderContract

/**
 * Created by agrawalsuneet on 9/1/18.
 */

class AllianceLoader : LinearLayout, LoaderContract {

    var dotsRadius: Int = 50
    var strokeWidth: Int = 0

    var drawOnlyStroke: Boolean = false

    var distanceMultiplier: Int = 4
        set(value) {
            field = if (value < 1) {
                1
            } else {
                value
            }
        }

    var firsDotColor: Int = ContextCompat.getColor(context, android.R.color.holo_red_dark)
    var secondDotColor: Int = ContextCompat.getColor(context, android.R.color.holo_green_dark)
    var thirdDotColor: Int = ContextCompat.getColor(context, R.color.loader_selected)

    var animDuration: Int = 500

    private var step: Int = 0

    private var calWidthHeight: Int = 0
    private lateinit var firstCircle: CircleView
    private lateinit var secondCircle: CircleView
    private lateinit var thirdCircle: CircleView
    private lateinit var relativeLayout: RelativeLayout


    private var posArrayList: ArrayList<ArrayList<Pair<Float, Float>>> = ArrayList()

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttributes(attrs)
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttributes(attrs)
        initView()
    }

    constructor(context: Context?, dotsRadius: Int, distanceMultiplier: Int, drawOnlyStroke: Boolean, strokeWidth: Int, firsDotColor: Int, secondDotColor: Int, thirdDotColor: Int) : super(context) {
        this.dotsRadius = dotsRadius
        this.distanceMultiplier = distanceMultiplier
        this.drawOnlyStroke = drawOnlyStroke
        this.strokeWidth = strokeWidth
        this.firsDotColor = firsDotColor
        this.secondDotColor = secondDotColor
        this.thirdDotColor = thirdDotColor

        initView()
    }

    override fun initAttributes(attrs: AttributeSet) {
        with(context.obtainStyledAttributes(attrs, R.styleable.AllianceLoader, 0, 0)) {
            dotsRadius = getDimensionPixelSize(R.styleable.AllianceLoader_alliance_dotsRadius, 50)

            distanceMultiplier = getInteger(R.styleable.AllianceLoader_alliance_distanceMultiplier, 4)

            firsDotColor = getColor(R.styleable.AllianceLoader_alliance_firstDotsColor,
                    ContextCompat.getColor(context, R.color.loader_selected))

            secondDotColor = getColor(R.styleable.AllianceLoader_alliance_secondDotsColor,
                    ContextCompat.getColor(context, R.color.loader_selected))

            thirdDotColor = getColor(R.styleable.AllianceLoader_alliance_thirdDotsColor,
                    ContextCompat.getColor(context, R.color.loader_selected))

            drawOnlyStroke = getBoolean(R.styleable.AllianceLoader_alliance_drawOnlyStroke, false)

            if (drawOnlyStroke) {
                strokeWidth = getDimensionPixelSize(R.styleable.AllianceLoader_alliance_strokeWidth, 20)
            }

            animDuration = getInt(R.styleable.AllianceLoader_alliance_animDuration, 500)

            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (calWidthHeight == 0) {
            calWidthHeight = (2 * dotsRadius * distanceMultiplier) + strokeWidth
        }

        setMeasuredDimension(calWidthHeight, calWidthHeight)
    }

    private fun initView() {
        removeAllViews()
        removeAllViewsInLayout()

        gravity = Gravity.CENTER_HORIZONTAL

        relativeLayout = RelativeLayout(context).apply {
            Gravity.CENTER_HORIZONTAL
        }

        if (calWidthHeight == 0) {
            calWidthHeight = (2 * dotsRadius * distanceMultiplier) + strokeWidth
        }

        firstCircle = CircleView(context, dotsRadius, firsDotColor, drawOnlyStroke, strokeWidth)
        val firstParam = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
            addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
            addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)
        }

        relativeLayout.addView(firstCircle, firstParam)

        secondCircle = CircleView(context, dotsRadius, secondDotColor, drawOnlyStroke, strokeWidth)
        val secondParam = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
            addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
        }

        relativeLayout.addView(secondCircle, secondParam)

        thirdCircle = CircleView(context, dotsRadius, thirdDotColor, drawOnlyStroke, strokeWidth)
        val thirdParam = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
            addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
        }

        relativeLayout.addView(thirdCircle, thirdParam)

        val relParam = RelativeLayout.LayoutParams(calWidthHeight, calWidthHeight)
        addView(relativeLayout, relParam)

        initInitialValues()

        val loaderView = this

        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                startLoading()

                val vto = loaderView.viewTreeObserver
                vto.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun initInitialValues() {

        val fullDistance = (calWidthHeight - ((2 * dotsRadius) + strokeWidth)).toFloat()
        val halfDistance = fullDistance / 2

        val firstPosArray = ArrayList<Pair<Float, Float>>().apply {
            add(Pair(0.0f, 0.0f))
            add(Pair(halfDistance, fullDistance))
            add(Pair(-halfDistance, fullDistance))
        }

        posArrayList.add(firstPosArray)

        val secondPosArray = ArrayList<Pair<Float, Float>>().apply {
            add(Pair(0.0f, 0.0f))
            add(Pair(-fullDistance, 0.0f))
            add(Pair(-halfDistance, -fullDistance))
        }

        posArrayList.add(secondPosArray)

        val thirdPosArray = ArrayList<Pair<Float, Float>>().apply {
            add(Pair(0.0f, 0.0f))
            add(Pair(halfDistance, -fullDistance))
            add(Pair(fullDistance, 0.0f))
        }

        posArrayList.add(thirdPosArray)
    }

    private fun startLoading() {

        firstCircle.startAnimation(getTranslateAnim(1))
        secondCircle.startAnimation(getTranslateAnim(2))

        val thirdCircleAnim = getTranslateAnim(3).apply {
            setAnimationListener(object : Animation.AnimationListener {

                override fun onAnimationEnd(p0: Animation?) {
                    step++
                    if (step > 2) {
                        step = 0
                    }
                    startLoading()
                }

                override fun onAnimationRepeat(p0: Animation?) {
                }

                override fun onAnimationStart(p0: Animation?) {
                }

            })
        }

        thirdCircle.startAnimation(thirdCircleAnim)
    }

    private fun getTranslateAnim(circleCount: Int): TranslateAnimation {

        var nextStep = step + 1

        if (nextStep > 2) {
            nextStep = 0
        }

        val fromXPos = posArrayList[circleCount - 1][step].first
        val fromYPos = posArrayList[circleCount - 1][step].second

        val toXPos = posArrayList[circleCount - 1][nextStep].first
        val toYPos = posArrayList[circleCount - 1][nextStep].second

        return TranslateAnimation(fromXPos, toXPos, fromYPos, toYPos).apply {
            duration = animDuration.toLong()
            fillAfter = true
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = 0
        }
    }
}