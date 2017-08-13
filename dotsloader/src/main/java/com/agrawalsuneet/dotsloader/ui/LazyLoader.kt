package com.agrawalsuneet.dotsloader.ui

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import com.agrawalsuneet.dotsloader.ui.basicviews.CircleView



/**
 * Created by ballu on 13/08/17.
 */
class LazyLoader : LinearLayout {

    var dotsRadius: Int = 30

    var dotsDist: Int = 15

    var dotsColor: Int = 0

    var startLoadingDefault :Boolean = true

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

    }
}