package com.agrawalsuneet.dotsloader.utils;

import android.graphics.Color;

/**
 * Created by suneet on 17/7/17.
 */

public class Helper {

    public static int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
}
