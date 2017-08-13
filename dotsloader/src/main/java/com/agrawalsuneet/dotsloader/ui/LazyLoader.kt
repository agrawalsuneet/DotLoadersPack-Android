package com.agrawalsuneet.dotsloader.ui

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import com.agrawalsuneet.dotsloader.ui.basicviews.CircleView


/**
 * Created by ballu on 13/08/17.
 */
class LazyLoader : LinearLayout {

    var dotsRadius: Int = 30

    var dotsDist: Int = 15

    var dotsColor: Int = 0

    var animDuration: Int = 500

    var startLoadingDefault: Boolean = true

    lateinit var firstCircle: CircleView
    lateinit var secondCircle: CircleView
    lateinit var thirdCircle: CircleView

    constructor(context: Context, dotsRadius: Int, dotsDist: Int, dotsColor: Int) : super(context) {
        this.dotsRadius = dotsRadius
        this.dotsDist = dotsDist
        this.dotsColor = dotsColor
        initView()
    }

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var calWidth = (6 * dotsRadius) + (2 * dotsDist)
        var calHeight = 6 * dotsRadius

        setMeasuredDimension(calWidth, calHeight)
    }

    private fun initView() {
        removeAllViews()
        removeAllViewsInLayout()

        firstCircle = CircleView(context, dotsRadius, dotsColor)
        secondCircle = CircleView(context, dotsRadius, dotsColor)
        thirdCircle = CircleView(context, dotsRadius, dotsColor)

        var params = LinearLayout.LayoutParams((2 * dotsRadius), 2 * dotsRadius)
        params.leftMargin = dotsDist

        setVerticalGravity(Gravity.BOTTOM)

        addView(firstCircle)
        addView(secondCircle, params)
        addView(thirdCircle, params)


        if (startLoadingDefault) {
            val loaderView = this

            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    startLoading()

                    val vto = loaderView.viewTreeObserver
                    vto.removeOnGlobalLayoutListener(this)
                }
            })
            startLoadingDefault = false
        }
    }

    private fun startLoading() {
        var trans1Anim = TranslateAnimation(0f, 0f, 0f, (-(4 * dotsRadius).toFloat()))
        trans1Anim.duration = animDuration.toLong()
        trans1Anim.fillAfter = true
        trans1Anim.repeatCount = 1
        trans1Anim.repeatMode = Animation.REVERSE

        firstCircle.startAnimation(trans1Anim)

        var trans2Anim = TranslateAnimation(0f, 0f, 0f, (-(4 * dotsRadius).toFloat()))
        trans2Anim.duration = animDuration.toLong()
        trans2Anim.fillAfter = true
        trans2Anim.repeatCount = 1
        trans2Anim.repeatMode = Animation.REVERSE
        trans2Anim.startOffset = (animDuration.toLong() / 6)
        secondCircle.startAnimation(trans2Anim)

        var trans3Anim = TranslateAnimation(0f, 0f, 0f, (-(4 * dotsRadius).toFloat()))
        trans3Anim.duration = animDuration.toLong()
        trans3Anim.fillAfter = true
        trans3Anim.repeatCount = 1
        trans3Anim.repeatMode = Animation.REVERSE
        trans3Anim.startOffset = (animDuration.toLong() / 3)
        thirdCircle.startAnimation(trans3Anim)

        trans3Anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                startLoading()
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
    }
}