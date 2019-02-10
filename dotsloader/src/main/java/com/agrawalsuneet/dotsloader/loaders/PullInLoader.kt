package com.agrawalsuneet.dotsloader.loaders

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.*
import android.widget.LinearLayout
import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.basicviews.CircularLoaderBaseView
import com.agrawalsuneet.dotsloader.contracts.LoaderContract
import com.agrawalsuneet.dotsloader.utils.Helper

class PullInLoader : LinearLayout, LoaderContract {

    var dotsRadius: Int = 30
    var bigCircleRadius: Int = 90

    var useMultipleColors: Boolean = false

    var dotsColor: Int = resources.getColor(android.R.color.darker_gray)
    var dotsColorsArray = IntArray(8) { resources.getColor(android.R.color.darker_gray) }

    var animDuration: Int = 3000

    private lateinit var circularLoaderBaseView: CircularLoaderBaseView

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context, dotsRadius: Int, bigCircleRadius: Int, dotsColor: Int) : super(context) {
        this.dotsRadius = dotsRadius
        this.bigCircleRadius = bigCircleRadius
        this.dotsColor = dotsColor
        this.useMultipleColors = false
        initView()
    }


    constructor(context: Context, dotsRadius: Int, bigCircleRadius: Int, dotsColorsArray: IntArray) : super(context) {
        this.dotsRadius = dotsRadius
        this.bigCircleRadius = bigCircleRadius
        this.dotsColorsArray = dotsColorsArray
        this.useMultipleColors = true
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

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PullInLoader, 0, 0)

        dotsRadius = typedArray.getDimensionPixelSize(R.styleable.PullInLoader_pullin_dotsRadius, 30)

        useMultipleColors = typedArray.getBoolean(R.styleable.PullInLoader_pullin_useMultipleColors, false)

        if (useMultipleColors) {
            val dotsArrayId = typedArray.getResourceId(R.styleable.PullInLoader_pullin_colorsArray, 0)

            dotsColorsArray = validateColorsArray(dotsArrayId, resources.getColor(android.R.color.darker_gray))

        } else {
            dotsColor = typedArray.getColor(R.styleable.PullInLoader_pullin_dotsColor,
                    resources.getColor(android.R.color.darker_gray))
        }

        bigCircleRadius =
                typedArray.getDimensionPixelSize(R.styleable.PullInLoader_pullin_bigCircleRadius, 90)

        animDuration = typedArray.getInt(R.styleable.PullInLoader_pullin_animDur, 2000)

        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val calWidthHeight = 2 * this.bigCircleRadius + 2 * dotsRadius
        setMeasuredDimension(calWidthHeight, calWidthHeight)
    }

    private fun validateColorsArray(arrayId: Int, color: Int): IntArray {


        return if (arrayId != 0) {
            val colors = IntArray(8)
            val colorsArray = resources.getIntArray(arrayId)
            for (i in 0..7) {
                colors[i] = if (colorsArray.size > i) colorsArray[i] else color
            }

            colors
        } else {
            IntArray(8) { color }
        }
    }


    private fun initView() {
        removeAllViews()
        removeAllViewsInLayout()

        circularLoaderBaseView = if (useMultipleColors) {
            CircularLoaderBaseView(context, dotsRadius, bigCircleRadius, dotsColorsArray)
        } else {
            CircularLoaderBaseView(context, dotsRadius, bigCircleRadius, dotsColor)
        }

        addView(circularLoaderBaseView)

        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                startLoading()
                this@PullInLoader.viewTreeObserver.removeOnGlobalLayoutListener(this)
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

        circularLoaderBaseView.clearAnimation()

        val rotationAnim = getRotateAnimation()
        rotationAnim.setListener {
            val scaleAnimation = getScaleAnimation()
            scaleAnimation.setListener {
                startLoading()
            }

            circularLoaderBaseView.startAnimation(scaleAnimation)
        }
        circularLoaderBaseView.startAnimation(rotationAnim)
    }

    private fun Animation.setListener(onEnd: () -> Unit) {
        this.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                onEnd()
            }

            override fun onAnimationStart(animation: Animation?) {
            }

        })
    }

    private fun getRotateAnimation(): RotateAnimation {

        val transAnim = RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f)
        transAnim.duration = animDuration.toLong()
        transAnim.fillAfter = true
        transAnim.repeatCount = 0
        transAnim.interpolator = AccelerateDecelerateInterpolator()

        return transAnim
    }

    private fun getScaleAnimation(): AnimationSet {
        val scaleAnimation = ScaleAnimation(1.0f, 0.5f,
                1.0f, 0.5f,
                (circularLoaderBaseView.width / 2).toFloat(),
                (circularLoaderBaseView.height / 2).toFloat())

        scaleAnimation.repeatCount = 1
        scaleAnimation.repeatMode = Animation.REVERSE


        val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
        alphaAnimation.repeatCount = 1
        alphaAnimation.repeatMode = Animation.REVERSE

        val animSet = AnimationSet(true)
        animSet.addAnimation(scaleAnimation)
        animSet.addAnimation(alphaAnimation)
        animSet.repeatCount = 1
        animSet.repeatMode = Animation.REVERSE
        animSet.duration = if (animDuration > 0) (animDuration / 8).toLong() else 100
        animSet.interpolator = AccelerateInterpolator()

        return animSet

    }


}