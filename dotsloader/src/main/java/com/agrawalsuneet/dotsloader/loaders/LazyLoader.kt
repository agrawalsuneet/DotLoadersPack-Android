package com.agrawalsuneet.dotsloader.loaders

import android.content.Context
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.basicviews.CircleView
import com.agrawalsuneet.dotsloader.basicviews.ThreeDotsBaseView


/**
 * Created by ballu on 13/08/17.
 */
class LazyLoader : ThreeDotsBaseView {

    var firstDelayDuration: Int = 100
    var secondDelayDuration: Int = 200

    constructor(context: Context, dotsRadius: Int, dotsDist: Int,
                firstDotColor: Int, secondDotColor: Int, thirdDotColor: Int)
            : super(context, dotsRadius, dotsDist, firstDotColor, secondDotColor, thirdDotColor)

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
        with(context.obtainStyledAttributes(attrs, R.styleable.LazyLoader, 0, 0)) {
            dotsRadius = getDimensionPixelSize(R.styleable.LazyLoader_lazyloader_dotsRadius, 30)
            dotsDist = getDimensionPixelSize(R.styleable.LazyLoader_lazyloader_dotsDist, 15)
            firstDotColor = getColor(R.styleable.LazyLoader_lazyloader_firstDotColor,
                    ContextCompat.getColor(context, R.color.loader_selected))
            secondDotColor = getColor(R.styleable.LazyLoader_lazyloader_secondDotColor,
                    ContextCompat.getColor(context, R.color.loader_selected))
            thirdDotColor = getColor(R.styleable.LazyLoader_lazyloader_thirdDotColor,
                    ContextCompat.getColor(context, R.color.loader_selected))
            animDuration = getInt(R.styleable.LazyLoader_lazyloader_animDur, 500)
            interpolator = AnimationUtils.loadInterpolator(context,
                    getResourceId(R.styleable.LazyLoader_lazyloader_interpolator,
                            android.R.anim.linear_interpolator))
            firstDelayDuration = getInt(R.styleable.LazyLoader_lazyloader_firstDelayDur, 100)
            secondDelayDuration = getInt(R.styleable.LazyLoader_lazyloader_secondDelayDur, 200)
            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val calWidth = (6 * dotsRadius) + (2 * dotsDist)
        val calHeight = 6 * dotsRadius

        setMeasuredDimension(calWidth, calHeight)
    }

    override fun initView() {
        removeAllViews()
        removeAllViewsInLayout()

        firstCircle = CircleView(context, dotsRadius, firstDotColor)
        secondCircle = CircleView(context, dotsRadius, secondDotColor)
        thirdCircle = CircleView(context, dotsRadius, thirdDotColor)

        val params = LinearLayout.LayoutParams((2 * dotsRadius), 2 * dotsRadius).apply {
            leftMargin = dotsDist
        }

        setVerticalGravity(Gravity.BOTTOM)

        addView(firstCircle)
        addView(secondCircle, params)
        addView(thirdCircle, params)

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
        firstCircle.startAnimation(getTranslateAnim())

        Handler().postDelayed({
            secondCircle.startAnimation(getTranslateAnim())
        }, firstDelayDuration.toLong())

        val trans3Anim = getTranslateAnim()

        Handler().postDelayed({
            thirdCircle.startAnimation(trans3Anim)
        }, secondDelayDuration.toLong())

        trans3Anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                startLoading()
            }

            override fun onAnimationStart(animation: Animation) {
            }
        })
    }

    private fun getTranslateAnim() =
            TranslateAnimation(0f, 0f, 0f, (-(4 * dotsRadius).toFloat())).apply {
                duration = animDuration.toLong()
                fillAfter = true
                repeatCount = 1
                repeatMode = Animation.REVERSE
                interpolator = interpolator
            }
}