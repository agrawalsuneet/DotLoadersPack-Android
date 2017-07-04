package com.agrawalsuneet.dotsloader.ui;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by ballu on 04/07/17.
 */

public class CircularDotsLoader extends DotsLoader {

    public CircularDotsLoader(Context context) {
        super(context);
        initValues();
    }

    public CircularDotsLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(attrs);
        initValues();
    }

    public CircularDotsLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(attrs);
        initValues();
    }

    @Override
    protected void initValues() {

    }
}
