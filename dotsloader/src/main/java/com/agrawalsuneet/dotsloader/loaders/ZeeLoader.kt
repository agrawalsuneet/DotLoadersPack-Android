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
 * Created by agrawalsuneet on 8/26/18.
 */

class ZeeLoader : LinearLayout, LoaderContract {

    var dotsRadius: Int = 50

    var distanceMultiplier: Int = 4
        set(value) {
            if (value < 1) {
                field = 1
            } else {
                field = value
            }
        }

    var firsDotColor: Int = ContextCompat.getColor(context, R.color.loader_selected)
    var secondDotColor: Int = ContextCompat.getColor(context, R.color.loader_selected)

    var animDuration: Int = 500

    private var step: Int = 0

    private var calWidthHeight: Int = 0
    private lateinit var firstCircle: CircleView
    private lateinit var secondCircle: CircleView
    private lateinit var relativeLayout: RelativeLayout

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

    constructor(context: Context, dotsRadius: Int, distanceMultiplier: Int, firsDotColor: Int, secondDotColor: Int) : super(context) {
        this.dotsRadius = dotsRadius
        this.distanceMultiplier = distanceMultiplier
        this.firsDotColor = firsDotColor
        this.secondDotColor = secondDotColor
        initView()
    }


    override fun initAttributes(attrs: AttributeSet) {
        with(context.obtainStyledAttributes(attrs, R.styleable.ZeeLoader, 0, 0)) {
            dotsRadius = getDimensionPixelSize(R.styleable.ZeeLoader_zee_dotsRadius, 50)

            distanceMultiplier = getInteger(R.styleable.ZeeLoader_zee_distanceMultiplier, 4)

            firsDotColor = getColor(R.styleable.ZeeLoader_zee_firstDotsColor,
                    ContextCompat.getColor(context, R.color.loader_selected))

            secondDotColor = getColor(R.styleable.ZeeLoader_zee_secondDotsColor,
                    ContextCompat.getColor(context, R.color.loader_selected))

            animDuration = getInt(R.styleable.ZeeLoader_zee_animDuration, 500)

            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (calWidthHeight == 0) {
            calWidthHeight = (2 * dotsRadius * distanceMultiplier)
        }

        setMeasuredDimension(calWidthHeight, calWidthHeight)
    }

    private fun initView() {
        removeAllViews()
        removeAllViewsInLayout()

        gravity = Gravity.CENTER_HORIZONTAL

        relativeLayout = RelativeLayout(context).apply {
            gravity = Gravity.CENTER_HORIZONTAL
        }

        if (calWidthHeight == 0) {
            calWidthHeight = (2 * dotsRadius * distanceMultiplier)
        }

        firstCircle = CircleView(context, dotsRadius, firsDotColor)
        val firstParam = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
            addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
            addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
        }

        relativeLayout.addView(firstCircle, firstParam)

        secondCircle = CircleView(context, dotsRadius, secondDotColor)
        val secondParam = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
            addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
        }

        relativeLayout.addView(secondCircle, secondParam)

        val relParam = RelativeLayout.LayoutParams(calWidthHeight, calWidthHeight)
        this.addView(relativeLayout, relParam)

        val loaderView = this

        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                startLoading()

                val vto = loaderView.viewTreeObserver
                vto.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun startLoading() {

        firstCircle.startAnimation(getTranslateAnim(1))

        val secondCircleAnim = getTranslateAnim(2)

        secondCircleAnim.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationEnd(p0: Animation?) {
                step++

                if (step > 3) {
                    step = 0
                }

                startLoading()
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        })

        secondCircle.startAnimation(secondCircleAnim)
    }

    private fun getTranslateAnim(circleCount: Int): TranslateAnimation {

        val circleDiameter = 2 * dotsRadius
        val finalDistance = ((distanceMultiplier - 1) * circleDiameter).toFloat()

        var fromXPos = 0.0f
        var fromYPos = 0.0f

        var toXPos = 0.0f
        var toYPos = 0.0f

        when (step) {
            0 -> {
                toXPos = if (circleCount == 1) {
                    finalDistance
                } else {
                    -1 * finalDistance
                }
            }
            1 -> {
                if (circleCount == 1) {
                    fromXPos = finalDistance
                    toYPos = finalDistance
                } else {
                    fromXPos = -1 * finalDistance
                    toYPos = -1 * finalDistance
                }
            }
            2 -> {
                if (circleCount == 1) {
                    toXPos = finalDistance
                    fromYPos = finalDistance
                    toYPos = fromYPos
                } else {
                    toXPos = -1 * finalDistance
                    fromYPos = -1 * finalDistance
                    toYPos = fromYPos
                }
            }
            3 -> {
                if (circleCount == 1) {
                    fromXPos = finalDistance
                    fromYPos = finalDistance
                } else {
                    fromXPos = -1 * finalDistance
                    fromYPos = -1 * finalDistance
                }
            }
        }

        return TranslateAnimation(fromXPos, toXPos,
                fromYPos, toYPos).apply {
            duration = animDuration.toLong()
            fillAfter = true
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = 0
        }
    }

}