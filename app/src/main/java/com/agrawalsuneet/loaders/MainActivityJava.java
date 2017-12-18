package com.agrawalsuneet.loaders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.agrawalsuneet.dotsloader.loaders.SlidingLoader;
import com.agrawalsuneet.dotsloader.loaders.TashieLoader;

/**
 * Created by suneet on 9/22/17.
 */

public class MainActivityJava extends AppCompatActivity {

    LinearLayout containerLL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LazyLoader loader = new LazyLoader(this);
        loader.setDotsColor(ContextCompat.getColor(this, R.color.blue_selected));
        loader.setDotsDist(20);
        loader.setDotsRadius(30);
        loader.setAnimDuration(500);
        loader.setFirstDelayDuration(100);
        loader.setSecondDelayDuration(200);
        loader.setInterpolator(new LinearInterpolator());


        TashieLoader tashie = new TashieLoader(this);
        tashie.setNoOfDots(8);
        tashie.setDotsRadius(20);
        tashie.setDotsDist(10);
        tashie.setDotsColor(ContextCompat.getColor(this, R.color.blue_selected));
        tashie.setAnimDuration(500);
        tashie.setAnimDelay(100);
        tashie.setInterpolator(new LinearInterpolator());

        containerLL.addView(tashie);

        SlidingLoader sliding = new SlidingLoader(this, 40, 10,
                ContextCompat.getColor(this, R.color.red),
                ContextCompat.getColor(this, R.color.yellow),
                ContextCompat.getColor(this, R.color.green));
        sliding.setAnimDuration(1000);
        sliding.setDistanceToMove(12);

        containerLL.addView(sliding);
    }
}
