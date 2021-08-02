package com.agrawalsuneet.loaders

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import com.agrawalsuneet.dotsloader.loaders.LazyLoader

class MainActivity : AppCompatActivity() {

    lateinit var containerLL: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_lineardotsloader)
        supportActionBar?.title = "initLazyLoader"

        containerLL = findViewById(R.id.container)

//        initLazyLoader()
    }

    private fun initLazyLoader() {
        val lazyLoader = LazyLoader(this, 15, 5,
                ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected))
                .apply {
                    animDuration = 250
                    firstDelayDuration = 100
                    secondDelayDuration = 200
                    interpolator = DecelerateInterpolator()
                }

        /*var lazyLoader = LazyLoader(this).apply{
            animDuration = 500
            firstDelayDuration = 100
            secondDelayDuration = 200
        }
        lazyLoader.dotsRadius = 60
        lazyLoader.dotsDist = 60*/

//        containerLL.addView(lazyLoader)
    }
}
