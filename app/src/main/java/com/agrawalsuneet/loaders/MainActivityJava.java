package com.agrawalsuneet.loaders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.agrawalsuneet.dotsloader.ui.LazyLoader;

/**
 * Created by suneet on 9/22/17.
 */

public class MainActivityJava extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LazyLoader loader = new LazyLoader(this);
        loader.setDotsColor(ContextCompat.getColor(this,R.color.blue_selected));
        loader.setDotsDist(20);
        loader.setDotsRadius(30);
        loader.setAnimDuration(500);
        loader.setFirstDelayDuration(100);
        loader.setSecondDelayDuration(200);


    }
}
