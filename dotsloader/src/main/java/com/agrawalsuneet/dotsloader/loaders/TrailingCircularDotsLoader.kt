package com.agrawalsuneet.dotsloader.loaders

import android.content.Context
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewTreeObserver
import android.view.animation.*
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.basicviews.CircleView
import com.agrawalsuneet.dotsloader.basicviews.LoaderContract

class TrailingCircularDotsLoader : LinearLayout, LoaderContract {

    var dotsRadius: Int = 50
    var bigCircleRadius: Int = 200

    var circleColor: Int = ContextCompat.getColor(context, R.color.loader_selected)
    var noOfTrailingDots: Int = 6

    var animDuration: Int = 2000
    var animDelay: Int = animDuration / 10

    private var calWidthHeight: Int = 0
    private lateinit var mainCircle: CircleView
    private lateinit var relativeLayout: RelativeLayout
    private lateinit var trailingCirclesArray: Array<CircleView?>

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

    constructor(context: Context?, dotsRadius: Int, circleColor: Int, bigCircleRadius: Int, noOfTrailingDots: Int) : super(context) {
        this.dotsRadius = dotsRadius
        this.circleColor = circleColor
        this.bigCircleRadius = bigCircleRadius
        this.noOfTrailingDots = noOfTrailingDots
        initView()
    }


    override fun initAttributes(attrs: AttributeSet) {
        with(context.obtainStyledAttributes(attrs, R.styleable.TrailingCircularDotsLoader, 0, 0)) {
            dotsRadius = getDimensionPixelSize(R.styleable.TrailingCircularDotsLoader_trailingcircular_dotsRadius, 50)
            bigCircleRadius = getDimensionPixelSize(R.styleable.TrailingCircularDotsLoader_trailingcircular_bigCircleRadius, 200)

            circleColor = getColor(R.styleable.TrailingCircularDotsLoader_trailingcircular_dotsColor,
                    ContextCompat.getColor(context, R.color.loader_selected))

            noOfTrailingDots = getInt(R.styleable.TrailingCircularDotsLoader_trailingcircular_noOfTrailingDots, 6)


            animDuration = getInt(R.styleable.TrailingCircularDotsLoader_trailingcircular_animDuration, 2000)
            animDelay = getInt(R.styleable.TrailingCircularDotsLoader_trailingcircular_animDelay, animDuration / 10)

            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (calWidthHeight == 0) {
            calWidthHeight = (2 * bigCircleRadius) + (2 * dotsRadius)
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
            calWidthHeight = (2 * bigCircleRadius) + (2 * dotsRadius)
        }

        val relParam = RelativeLayout.LayoutParams(calWidthHeight, calWidthHeight)

        mainCircle = CircleView(context, dotsRadius, circleColor)
        relativeLayout.addView(mainCircle)

        this.addView(relativeLayout, relParam)

        trailingCirclesArray = arrayOfNulls(noOfTrailingDots)

        for (i in 0 until noOfTrailingDots) {
            val circle = CircleView(context, dotsRadius, circleColor)
            relativeLayout.addView(circle)
            trailingCirclesArray[i] = circle
        }

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

        val mainCircleAnim = getRotateAnimation()
        mainCircle.startAnimation(mainCircleAnim)

        for (i in 1..noOfTrailingDots) {
            val animSet = getTrailingAnim(i, ((animDuration * (2 + i)) / 20))
            trailingCirclesArray[i - 1]!!.startAnimation(animSet)

            if (i == noOfTrailingDots - 1) {
                animSet.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationEnd(p0: Animation?) {
                        Handler().postDelayed({
                            startLoading()
                        }, animDelay.toLong())
                    }

                    override fun onAnimationStart(p0: Animation?) {
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                    }
                })
            }
        }
    }

    private fun getRotateAnimation() = RotateAnimation(0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_PARENT, 0.5f).apply {
        duration = animDuration.toLong()
        fillAfter = true
        interpolator = AccelerateDecelerateInterpolator()
        startOffset = (animDuration / 10).toLong()
    }

    private fun getTrailingAnim(count: Int, delay: Int): AnimationSet {
        val animSet = AnimationSet(true)

        val scaleFactor: Float = 1.00f - (count.toFloat() / 20)

        val scaleAnim = ScaleAnimation(scaleFactor, scaleFactor, scaleFactor, scaleFactor,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)

        val rotateAnim = RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_PARENT, 0.5f)
        rotateAnim.duration = animDuration.toLong()

        return animSet.apply {
            addAnimation(scaleAnim)
            addAnimation(rotateAnim)
            duration = animDuration.toLong()
            fillAfter = false
            interpolator = AccelerateDecelerateInterpolator()
            startOffset = delay.toLong()
        }
    }
}