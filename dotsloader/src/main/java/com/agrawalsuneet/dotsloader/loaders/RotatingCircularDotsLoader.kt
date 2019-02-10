package com.agrawalsuneet.dotsloader.loaders

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.LinearLayout
import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.basicviews.CircularLoaderBaseView
import com.agrawalsuneet.dotsloader.contracts.LoaderContract

/**
 * Created by suneet on 12/29/17.
 */
class RotatingCircularDotsLoader : LinearLayout, LoaderContract {

    var dotsRadius: Int = 30
    var dotsColor: Int = resources.getColor(R.color.loader_selected)
    var bigCircleRadius: Int = 90

    var animDuration: Int = 5000

    private lateinit var circularLoaderBaseView: CircularLoaderBaseView


    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context, dotsRadius: Int, bigCircleRadius: Int, dotsColor: Int) : super(context) {
        this.dotsRadius = dotsRadius
        this.bigCircleRadius = bigCircleRadius
        this.dotsColor = dotsColor
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

        circularLoaderBaseView = CircularLoaderBaseView(context, dotsRadius, bigCircleRadius, dotsColor)

        addView(circularLoaderBaseView)

        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                startLoading()
                this@RotatingCircularDotsLoader.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)

        if (visibility != View.VISIBLE) {
            initView()
        } else {
            circularLoaderBaseView.clearAnimation()
        }
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
        transAnim.interpolator = LinearInterpolator()

        return transAnim
    }


}