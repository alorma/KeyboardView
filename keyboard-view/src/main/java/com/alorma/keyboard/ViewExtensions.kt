package com.alorma.keyboard

import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import com.google.android.material.color.MaterialColors
import kotlin.math.roundToInt

fun getMergedColor(
    @ColorInt backgroundColor: Int,
    @ColorInt overlayColor: Int,
    @FloatRange(from = 0.0, to = 1.0) overlayAlpha: Float = 0.08f
): Int {
    val colorWithAlpha = MaterialColors.compositeARGBWithAlpha(
        overlayColor,
        (255 * overlayAlpha).roundToInt()
    )
    return MaterialColors.layer(backgroundColor, colorWithAlpha)
}