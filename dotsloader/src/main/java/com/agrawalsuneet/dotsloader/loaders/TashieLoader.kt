package com.agrawalsuneet.dotsloader.loaders

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.LinearLayout
import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.basicviews.AnimatingLinearLayout
import com.agrawalsuneet.dotsloader.basicviews.CircleView

/**
 * Created by suneet on 10/10/17.
 */
class TashieLoader : AnimatingLinearLayout {

    var noOfDots: Int = 8
    var animDelay: Int = 100

    private lateinit var dotsArray: Array<CircleView?>

    private var isDotsExpanding: Boolean = true

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
        with(context.obtainStyledAttributes(attrs, R.styleable.TashieLoader, 0, 0)) {
            dotsRadius = getDimensionPixelSize(R.styleable.TashieLoader_tashieloader_dotsRadius, 30)
            dotsDist = getDimensionPixelSize(R.styleable.TashieLoader_tashieloader_dotsDist, 15)
            dotsColor = getColor(R.styleable.TashieLoader_tashieloader_dotsColor,
                    ContextCompat.getColor(context, R.color.loader_selected))

            animDuration = getInt(R.styleable.TashieLoader_tashieloader_animDur, 500)

            interpolator = AnimationUtils.loadInterpolator(context,
                    getResourceId(R.styleable.TashieLoader_tashieloader_interpolator,
                            android.R.anim.linear_interpolator))

            noOfDots = getInt(R.styleable.TashieLoader_tashieloader_noOfDots, 8)
            animDelay = getInt(R.styleable.TashieLoader_tashieloader_animDelay, 100)

            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val calHeight = 2 * dotsRadius
        val calWidth = ((2 * noOfDots * dotsRadius) + ((noOfDots - 1) * dotsDist))

        setMeasuredDimension(calWidth, calHeight)
    }

    override fun initView() {
        removeAllViews()
        removeAllViewsInLayout()
        setVerticalGravity(Gravity.BOTTOM)

        dotsArray = arrayOfNulls<CircleView?>(noOfDots)

        for (iCount in 0 until noOfDots) {
            val circle = CircleView(context, dotsRadius, dotsColor)

            val params = LinearLayout.LayoutParams(2 * dotsRadius, 2 * dotsRadius)

            if (iCount != noOfDots - 1) {
                params.rightMargin = dotsDist
            }

            addView(circle, params)
            dotsArray[iCount] = circle
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
        for (iCount in 0 until noOfDots) {
            val anim = getScaleAnimation(isDotsExpanding, iCount)
            dotsArray[iCount]!!.startAnimation(anim)

            setAnimationListener(anim, iCount)
        }
        isDotsExpanding = !isDotsExpanding
    }

    private fun getScaleAnimation(isExpanding: Boolean, delay: Int): AnimationSet {
        val anim = AnimationSet(true)

        val scaleAnim: ScaleAnimation = when (isExpanding) {
            true -> {
                ScaleAnimation(0f, 1f, 0f, 1f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            }

            false -> {
                ScaleAnimation(1f, 0f, 1f, 0f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            }
        }.apply {
            duration = animDuration.toLong()
            fillAfter = true
            repeatCount = 0
            startOffset = (animDelay * delay).toLong()
        }

        anim.addAnimation(scaleAnim)
        anim.interpolator = interpolator

        return anim
    }

    private fun setAnimationListener(anim: AnimationSet, dotPosition: Int) {
        if (dotPosition == noOfDots - 1) {
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                }

                override fun onAnimationEnd(p0: Animation?) {
                    startLoading()
                }

                override fun onAnimationStart(p0: Animation?) {
                }

            })
        } else {
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                }

                override fun onAnimationEnd(p0: Animation?) {
                    when (!isDotsExpanding) {
                        true -> {
                            dotsArray[dotPosition]!!.visibility = View.VISIBLE
                        }

                        false -> {
                            dotsArray[dotPosition]!!.visibility = View.INVISIBLE
                        }
                    }

                }

                override fun onAnimationStart(p0: Animation?) {
                }
            })
        }
    }
}
