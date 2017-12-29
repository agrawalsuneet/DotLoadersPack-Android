package com.agrawalsuneet.dotsloader.loaders

import android.content.Context
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.view.animation.*
import android.widget.LinearLayout
import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.basicviews.CircularLoaderBaseView
import com.agrawalsuneet.dotsloader.basicviews.LoaderContract

/**
 * Created by suneet on 12/29/17.
 */
class RotatingCircularDotsLoader : LinearLayout, LoaderContract {

    var dotsRadius: Int = 30
    var dotsColor: Int = resources.getColor(R.color.loader_selected)
    var bigCircleRadius: Int = 90

    var animDuration: Int = 5000
    var interpolator: Interpolator = LinearInterpolator()

    private lateinit var circularLoaderBaseView: CircularLoaderBaseView


    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context, dotsRadius: Int, dotsColor: Int, bigCircleRadius: Int) : super(context) {
        this.dotsRadius = dotsRadius
        this.dotsColor = dotsColor
        this.bigCircleRadius = bigCircleRadius
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

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RotatingCircularDotsLoader, 0, 0)

        this.dotsRadius = typedArray.getDimensionPixelSize(R.styleable.RotatingCircularDotsLoader_rotatingcircular_dotsRadius, 30)

        this.dotsColor = typedArray.getColor(R.styleable.RotatingCircularDotsLoader_rotatingcircular_dotsColor,
                resources.getColor(R.color.loader_selected))

        this.bigCircleRadius = typedArray.getDimensionPixelSize(R.styleable.RotatingCircularDotsLoader_rotatingcircular_bigCircleRadius, 90)

        this.animDuration = typedArray.getInt(R.styleable.RotatingCircularDotsLoader_rotatingcircular_animDur, 5000)

        this.interpolator = AnimationUtils.loadInterpolator(context,
                typedArray.getResourceId(R.styleable.RotatingCircularDotsLoader_rotatingcircular_interpolator,
                        android.R.anim.linear_interpolator))

        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val calWidth = 2 * this.bigCircleRadius + 2 * dotsRadius
        val calHeight = calWidth

        setMeasuredDimension(calWidth, calHeight)
    }

    private fun initView() {
        removeAllViews()
        removeAllViewsInLayout()

        circularLoaderBaseView = CircularLoaderBaseView(context, dotsRadius, dotsColor, bigCircleRadius)

        addView(circularLoaderBaseView)

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

        val rotationAnim = getRotateAnimation()
        circularLoaderBaseView.startAnimation(rotationAnim)
    }

    private fun getRotateAnimation(): RotateAnimation {

        val transAnim = RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f)
        transAnim.duration = animDuration.toLong()
        transAnim.fillAfter = true
        transAnim.repeatCount = Animation.INFINITE
        transAnim.repeatMode = Animation.RESTART
        transAnim.interpolator = interpolator

        return transAnim
    }


}