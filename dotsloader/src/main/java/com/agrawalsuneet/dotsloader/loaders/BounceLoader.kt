package com.agrawalsuneet.dotsloader.loaders

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.*
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.basicviews.CircleView
import com.agrawalsuneet.dotsloader.contracts.LoaderContract

/**
 * Created by agrawalsuneet on 4/13/19.
 */

class BounceLoader : LinearLayout, LoaderContract {

    var ballRadius: Int = 60
    var ballColor: Int = resources.getColor(android.R.color.holo_red_dark)
    var shadowColor: Int = resources.getColor(android.R.color.black)

    var showShadow: Boolean = true
    var animDuration: Int = 1500

    private var relativeLayout: RelativeLayout? = null

    private var ballCircleView: CircleView? = null
    private var ballShadowView: CircleView? = null

    private var calWidth: Int = 0
    private var calHeight: Int = 0

    private val STATE_GOINGDOWN: Int = 0
    private val STATE_SQUEEZING: Int = 1
    private val STATE_RESIZING: Int = 2
    private val STATE_COMINGUP: Int = 3

    private var state: Int = STATE_GOINGDOWN

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs) {
        initAttributes(attrs)
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttributes(attrs)
        initView()
    }

    override fun initAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BounceLoader, 0, 0)

        this.ballRadius = typedArray.getDimensionPixelSize(R.styleable.BounceLoader_bounce_ballRadius, 60)
        this.ballColor = typedArray.getColor(R.styleable.BounceLoader_bounce_ballColor,
                resources.getColor(android.R.color.holo_red_dark))

        this.shadowColor = typedArray.getColor(R.styleable.BounceLoader_bounce_shadowColor,
                resources.getColor(android.R.color.black))

        this.showShadow = typedArray.getBoolean(R.styleable.BounceLoader_bounce_showShadow, true)
        this.animDuration = typedArray.getInt(R.styleable.BounceLoader_bounce_animDuration, 1500)

        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (calWidth == 0 || calHeight == 0) {
            calWidth = 5 * ballRadius
            calHeight = 8 * ballRadius
        }

        setMeasuredDimension(calWidth, calHeight)
    }

    private fun initView() {
        removeAllViews()
        removeAllViewsInLayout()

        if (calWidth == 0 || calHeight == 0) {
            calWidth = 5 * ballRadius
            calHeight = 8 * ballRadius
        }

        relativeLayout = RelativeLayout(context)

        if (showShadow) {
            ballShadowView = CircleView(context = context,
                    circleRadius = ballRadius,
                    circleColor = shadowColor,
                    isAntiAlias = false)

            val shadowParam = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            shadowParam.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)
            shadowParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            relativeLayout?.addView(ballShadowView, shadowParam)
        }

        ballCircleView = CircleView(context = context,
                circleRadius = ballRadius,
                circleColor = ballColor)

        val ballParam = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        ballParam.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)
        ballParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        relativeLayout?.addView(ballCircleView, ballParam)

        val relParam = RelativeLayout.LayoutParams(calWidth, calHeight)
        this.addView(relativeLayout, relParam)

        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                startLoading()
                this@BounceLoader.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

    }

    private fun startLoading() {
        val ballAnim = getBallAnimation()

        ballAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(anim: Animation?) {
                state = (state + 1) % 4
                startLoading()
            }


            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        })

        if (showShadow) {
            if (state == STATE_SQUEEZING || state == STATE_RESIZING) {
                ballShadowView?.clearAnimation()
                ballShadowView?.visibility = View.GONE
            } else {
                ballShadowView?.visibility = View.VISIBLE
                val shadowAnim = getShadowAnimation()
                ballShadowView?.startAnimation(shadowAnim)
            }
        }

        ballCircleView?.startAnimation(ballAnim)
    }

    private fun getBallAnimation(): Animation {
        return when (state) {
            STATE_GOINGDOWN -> {
                TranslateAnimation(0.0f, 0.0f,
                        (-6 * ballRadius).toFloat(), 0.0f)
                        .apply {
                            duration = animDuration.toLong()
                            interpolator = AccelerateInterpolator()
                        }
            }

            STATE_SQUEEZING -> {
                ScaleAnimation(1.0f, 1.0f, 1.0f, 0.85f
                        , ballRadius.toFloat(), (2 * ballRadius).toFloat())
                        .apply {
                            duration = (animDuration / 10).toLong()
                            interpolator = AccelerateInterpolator()
                        }
            }

            STATE_RESIZING -> {
                ScaleAnimation(1.0f, 1.0f, 0.85f, 1.0f
                        , ballRadius.toFloat(), (2 * ballRadius).toFloat())
                        .apply {
                            duration = (animDuration / 10).toLong()
                            interpolator = DecelerateInterpolator()
                        }
            }

            else -> {
                TranslateAnimation(0.0f, 0.0f,
                        0.0f, (-6 * ballRadius).toFloat())
                        .apply {
                            duration = animDuration.toLong()
                            interpolator = DecelerateInterpolator()
                        }
            }
        }.apply {
            fillAfter = true
            repeatCount = 0
        }
    }

    private fun getShadowAnimation(): AnimationSet {

        val transAnim: Animation
        val scaleAnim: Animation
        val alphaAnim: AlphaAnimation

        val set = AnimationSet(true)

        when (state) {
            STATE_COMINGUP -> {
                transAnim = TranslateAnimation(0.0f, (-4 * ballRadius).toFloat(),
                        0.0f, (-3 * ballRadius).toFloat())

                scaleAnim = ScaleAnimation(0.9f, 0.5f, 0.9f, 0.5f,
                        ballRadius.toFloat(), ballRadius.toFloat())

                alphaAnim = AlphaAnimation(0.6f, 0.2f)

                set.interpolator = DecelerateInterpolator()
            }
            else -> {
                transAnim = TranslateAnimation((-4 * ballRadius).toFloat(), 0.0f,
                        (-3 * ballRadius).toFloat(), 0.0f)

                scaleAnim = ScaleAnimation(0.5f, 0.9f, 0.5f, 0.9f,
                        ballRadius.toFloat(), ballRadius.toFloat())

                alphaAnim = AlphaAnimation(0.2f, 0.6f)

                set.interpolator = AccelerateInterpolator()
            }
        }

        set.addAnimation(transAnim)
        set.addAnimation(scaleAnim)
        set.addAnimation(alphaAnim)

        set.apply {
            duration = animDuration.toLong()
            fillAfter = true
            repeatCount = 0
        }

        return set
    }
}