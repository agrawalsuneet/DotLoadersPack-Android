package com.agrawalsuneet.dotsloader.ui

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.ui.basicviews.CircleView
import com.agrawalsuneet.dotsloader.ui.basicviews.LoaderContract


/**
 * Created by ballu on 13/08/17.
 */
class LazyLoader : LinearLayout, LoaderContract {

    var animDuration: Int = 500
    var firstDelayDuration: Int = 100
    var secondDelayDuration: Int = 200

    var startLoadingDefault: Boolean = true

    private lateinit var firstCircle: CircleView
    private lateinit var secondCircle: CircleView
    private lateinit var thirdCircle: CircleView

    constructor(context: Context, dotsRadius: Int, dotsDist: Int, dotsColor: Int) : super(context) {
        this.dotsRadius = dotsRadius
        this.dotsDist = dotsDist
        this.dotsColor = dotsColor
        initView()
    }

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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LazyLoader, 0, 0)

        this.dotsRadius = typedArray.getDimensionPixelSize(R.styleable.LazyLoader_lazyloader_dotsRadius, 30)
        this.dotsDist = typedArray.getDimensionPixelSize(R.styleable.LazyLoader_lazyloader_dotsDist, 15)
        this.dotsColor = typedArray.getColor(R.styleable.LazyLoader_lazyloader_dotsColor,
                resources.getColor(R.color.loader_defalut))

        this.animDuration = typedArray.getInt(R.styleable.LazyLoader_lazyloader_animDur, 500)
        this.firstDelayDuration = typedArray.getInt(R.styleable.LazyLoader_lazyloader_firstDelayDur, 100)
        this.secondDelayDuration = typedArray.getInt(R.styleable.LazyLoader_lazyloader_secondDelayDur, 200)

        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val calWidth = (6 * dotsRadius) + (2 * dotsDist)
        val calHeight = 6 * dotsRadius

        setMeasuredDimension(calWidth, calHeight)
    }

    private fun initView() {
        removeAllViews()
        removeAllViewsInLayout()

        firstCircle = CircleView(context, dotsRadius, dotsColor)
        secondCircle = CircleView(context, dotsRadius, dotsColor)
        thirdCircle = CircleView(context, dotsRadius, dotsColor)

        val params = LinearLayout.LayoutParams((2 * dotsRadius), 2 * dotsRadius)
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

        val trans1Anim = getTranslateAnim()
        firstCircle.startAnimation(trans1Anim)

        val trans2Anim = getTranslateAnim()

        Handler().postDelayed({
            secondCircle.startAnimation(trans2Anim)
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

    private fun getTranslateAnim(): TranslateAnimation {
        val transAnim = TranslateAnimation(0f, 0f, 0f, (-(4 * dotsRadius).toFloat()))
        transAnim.duration = animDuration.toLong()
        transAnim.fillAfter = true
        transAnim.repeatCount = 1
        transAnim.repeatMode = Animation.REVERSE

        return transAnim
    }

    var dotsRadius: Int = 30
        get() = field
        set(value) {
            field = value
            initView()
        }

    var dotsDist: Int = 15
        get() = field
        set(value) {
            field = value
            initView()
        }

    var dotsColor: Int = resources.getColor(R.color.loader_defalut, null)
        get() = field
        set(value) {
            field = value
            initView()
        }
}