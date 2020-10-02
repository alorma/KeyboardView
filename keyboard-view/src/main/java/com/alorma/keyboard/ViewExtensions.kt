package com.alorma.keyboard

import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import com.google.android.material.color.MaterialColors
import kotlin.math.roundToInt

fun View.getMergedColor(
    @AttrRes backgroundColorAttributeResId: Int = R.attr.colorSurface,
    @ColorInt overlayColor: Int,
    @FloatRange(from = 0.0, to = 1.0) overlayAlpha: Float = 0.08f
): Int {
    val surfaceColor = MaterialColors.getColor(this, backgroundColorAttributeResId)
    val colorWithAlpha = MaterialColors.compositeARGBWithAlpha(
        overlayColor,
        (255 * overlayAlpha).roundToInt()
    )
    return MaterialColors.layer(surfaceColor, colorWithAlpha)
}