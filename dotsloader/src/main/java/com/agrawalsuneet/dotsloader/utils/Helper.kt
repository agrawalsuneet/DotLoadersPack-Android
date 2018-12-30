package com.agrawalsuneet.dotsloader.utils

import android.graphics.Color
import java.util.*

/**
 * Created by suneet on 17/7/17.
 */

object Helper {

    fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * factor)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }
}

fun ClosedFloatingPointRange<Float>.random() =
        (Random().nextFloat() * (endInclusive - start)) + start

fun ClosedRange<Int>.random() =
        Random().nextInt((endInclusive + 1) - start) + start

