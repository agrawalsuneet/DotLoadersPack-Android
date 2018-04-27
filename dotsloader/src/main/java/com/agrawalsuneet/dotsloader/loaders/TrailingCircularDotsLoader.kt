package com.agrawalsuneet.dotsloader.loaders

import android.content.Context
import android.os.Handler
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

    var circleColor: Int = resources.getColor(R.color.loader_selected)

    var noOfTrailingDots: Int = 6

    var circleRadius: Int = 50
    var BigCircleRadius: Int = 200

    var animDuration: Int = 2000
    var animDelay: Int = animDuration / 5

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


    override fun initAttributes(attrs: AttributeSet) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (calWidthHeight == 0) {
            calWidthHeight = (2 * BigCircleRadius) + (2 * circleRadius)
        }

        setMeasuredDimension(calWidthHeight, calWidthHeight)
    }

    private fun initView() {
        removeAllViews()
        removeAllViewsInLayout()

        this.gravity = Gravity.CENTER_HORIZONTAL

        relativeLayout = RelativeLayout(context);
        relativeLayout.gravity = Gravity.CENTER_HORIZONTAL


        if (calWidthHeight == 0) {
            calWidthHeight = (2 * BigCircleRadius) + (2 * circleRadius)
        }

        var relParam = RelativeLayout.LayoutParams(calWidthHeight, calWidthHeight)

        mainCircle = CircleView(context, circleRadius, circleColor)
        relativeLayout.addView(mainCircle)

        this.addView(relativeLayout, relParam)


        trailingCirclesArray = arrayOfNulls(noOfTrailingDots)

        for (i in 0 until noOfTrailingDots) {
            /*var circleColor = 0
            when (i) {
                0, 3 -> circleColor = resources.getColor(android.R.color.holo_red_light)
                1, 4 -> circleColor = resources.getColor(android.R.color.holo_green_light)
                2, 5 -> circleColor = resources.getColor(android.R.color.holo_blue_light)
            }*/

            val circle = CircleView(context, circleRadius, circleColor)
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
            val animSet = getTrainlingAnim(i, (animDuration / 10) + ((animDuration * i) / 20))
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

    private fun getRotateAnimation(): RotateAnimation {

        val rotateAnim = RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_PARENT, 0.5f)
        rotateAnim.duration = animDuration.toLong()
        rotateAnim.fillAfter = true
        rotateAnim.interpolator = AccelerateDecelerateInterpolator()
        rotateAnim.startOffset = (animDuration / 10).toLong()

        return rotateAnim
    }

    private fun getTrainlingAnim(count: Int, delay: Int): AnimationSet {
        val animSet = AnimationSet(true)

        val scaleFactor: Float = 1.00f - (count.toFloat() / 20)

        val scaleAnim = ScaleAnimation(scaleFactor, scaleFactor, scaleFactor, scaleFactor,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        animSet.addAnimation(scaleAnim)


        val rotateAnim = RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_PARENT, 0.5f)
        rotateAnim.duration = animDuration.toLong()

        animSet.addAnimation(rotateAnim)
        animSet.duration = animDuration.toLong()
        animSet.fillAfter = false
        animSet.interpolator = AccelerateDecelerateInterpolator()
        animSet.startOffset = delay.toLong()

        return animSet
    }
}