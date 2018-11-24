package com.agrawalsuneet.dotsloader.loaders

import android.content.Context
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
import com.agrawalsuneet.dotsloader.contracts.LoaderContract

/**
 * Created by agrawalsuneet on 9/1/18.
 */

class AllianceLoader : LinearLayout, LoaderContract {


    var dotsRadius: Int = 50
    var strokeWidth: Int = 0

    var drawOnlyStroke: Boolean = false

    var distanceMultiplier: Int = 4
        set(value) {
            if (value < 1) {
                field = 1
            } else {
                field = value
            }
        }

    var firsDotColor: Int = resources.getColor(android.R.color.holo_red_dark)
    var secondDotColor: Int = resources.getColor(android.R.color.holo_green_dark)
    var thirdDotColor: Int = resources.getColor(R.color.loader_selected)

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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AllianceLoader, 0, 0)

        this.dotsRadius = typedArray.getDimensionPixelSize(R.styleable.AllianceLoader_alliance_dotsRadius, 50)

        this.distanceMultiplier = typedArray.getInteger(R.styleable.AllianceLoader_alliance_distanceMultiplier, 4)

        this.firsDotColor = typedArray.getColor(R.styleable.AllianceLoader_alliance_firstDotsColor,
                resources.getColor(R.color.loader_selected))

        this.secondDotColor = typedArray.getColor(R.styleable.AllianceLoader_alliance_secondDotsColor,
                resources.getColor(R.color.loader_selected))

        this.thirdDotColor = typedArray.getColor(R.styleable.AllianceLoader_alliance_thirdDotsColor,
                resources.getColor(R.color.loader_selected))

        this.drawOnlyStroke = typedArray.getBoolean(R.styleable.AllianceLoader_alliance_drawOnlyStroke, false)

        if (drawOnlyStroke) {
            this.strokeWidth = typedArray.getDimensionPixelSize(R.styleable.AllianceLoader_alliance_strokeWidth, 20)
        }

        this.animDuration = typedArray.getInt(R.styleable.AllianceLoader_alliance_animDuration, 500)

        typedArray.recycle()
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

        this.gravity = Gravity.CENTER_HORIZONTAL

        relativeLayout = RelativeLayout(context)
        relativeLayout.gravity = Gravity.CENTER_HORIZONTAL


        if (calWidthHeight == 0) {
            calWidthHeight = (2 * dotsRadius * distanceMultiplier) + strokeWidth
        }

        firstCircle = CircleView(context, dotsRadius, firsDotColor, drawOnlyStroke, strokeWidth)
        val firstParam = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        firstParam.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
        firstParam.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)

        relativeLayout.addView(firstCircle, firstParam)

        secondCircle = CircleView(context, dotsRadius, secondDotColor, drawOnlyStroke, strokeWidth)
        val secondParam = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        secondParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        secondParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)

        relativeLayout.addView(secondCircle, secondParam)

        thirdCircle = CircleView(context, dotsRadius, thirdDotColor, drawOnlyStroke, strokeWidth)
        val thirdParam = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        thirdParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        thirdParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)

        relativeLayout.addView(thirdCircle, thirdParam)

        val relParam = RelativeLayout.LayoutParams(calWidthHeight, calWidthHeight)
        this.addView(relativeLayout, relParam)

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

        val firstPosArray = ArrayList<Pair<Float, Float>>()
        firstPosArray.add(Pair(0.0f, 0.0f))
        firstPosArray.add(Pair(halfDistance, fullDistance))
        firstPosArray.add(Pair(-halfDistance, fullDistance))

        posArrayList.add(firstPosArray)

        val secondPosArray = ArrayList<Pair<Float, Float>>()

        secondPosArray.add(Pair(0.0f, 0.0f))
        secondPosArray.add(Pair(-fullDistance, 0.0f))
        secondPosArray.add(Pair(-halfDistance, -fullDistance))

        posArrayList.add(secondPosArray)

        val thirdPosArray = ArrayList<Pair<Float, Float>>()

        thirdPosArray.add(Pair(0.0f, 0.0f))
        thirdPosArray.add(Pair(halfDistance, -fullDistance))
        thirdPosArray.add(Pair(fullDistance, 0.0f))

        posArrayList.add(thirdPosArray)
    }

    private fun startLoading() {

        val firstCircleAnim = getTranslateAnim(1)
        firstCircle.startAnimation(firstCircleAnim)

        val secondCircleAnim = getTranslateAnim(2)
        secondCircle.startAnimation(secondCircleAnim)

        val thirdCircleAnim = getTranslateAnim(3)

        thirdCircleAnim.setAnimationListener(object : Animation.AnimationListener {

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

        thirdCircle.startAnimation(thirdCircleAnim)
    }

    private fun getTranslateAnim(circleCount: Int): TranslateAnimation {

        var nextStep = step + 1
        if (nextStep > 2) {
            nextStep = 0
        }

        val fromXPos = posArrayList.get(circleCount - 1).get(step).first
        val fromYPos = posArrayList.get(circleCount - 1).get(step).second

        val toXPos = posArrayList.get(circleCount - 1).get(nextStep).first
        val toYPos = posArrayList.get(circleCount - 1).get(nextStep).second

        val transAnim = TranslateAnimation(fromXPos, toXPos, fromYPos, toYPos)
        transAnim.duration = animDuration.toLong()
        transAnim.fillAfter = true
        transAnim.interpolator = AccelerateDecelerateInterpolator()
        transAnim.repeatCount = 0

        return transAnim
    }
}