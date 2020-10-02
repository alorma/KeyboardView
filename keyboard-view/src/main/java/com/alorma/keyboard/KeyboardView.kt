package com.alorma.keyboard

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.withStyledAttributes
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.MaterialShapeUtils
import com.google.android.material.shape.ShapeAppearanceModel

class KeyboardView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.keyboardViewStyle,
    @StyleRes defStyleRes: Int = R.style.Widget_Keyboard
) : RecyclerView(context, attributeSet, defStyleAttr) {

    private var overlayColor: Int? = null
    private var overlayAlpha: Float = 0.08f

    init {

        initAttributes(context, attributeSet, defStyleAttr, defStyleRes)
        initBackground(context, attributeSet, defStyleAttr, defStyleRes)

        val numbersAdapter = KeyboardNumbersAdapter()
        layoutManager = GridLayoutManager(context, numbersAdapter.itemCount / 2)
        adapter = numbersAdapter
    }

    private fun initAttributes(
        context: Context,
        attributeSet: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        context.withStyledAttributes(
            set = attributeSet,
            attrs = R.styleable.KeyboardView,
            defStyleAttr = defStyleAttr,
            defStyleRes = defStyleRes
        ) {
            overlayColor = try {
                getColorOrThrow(R.styleable.KeyboardView_overlayColor)
            } catch (t: Throwable) {
                null
            }
            val overlayAlpha = getFloat(R.styleable.KeyboardView_overlayAlpha, overlayAlpha)
            when {
                overlayAlpha < 0f -> this@KeyboardView.overlayAlpha = 0f
                overlayAlpha > 1f -> this@KeyboardView.overlayAlpha = 1f
                else -> this@KeyboardView.overlayAlpha = overlayAlpha
            }
        }
    }

    private fun initBackground(
        context: Context,
        attributeSet: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        if (background == null || background is ColorDrawable) {

            val backgroundColor = obtainBackgroundColor()

            val shape = ShapeAppearanceModel.builder(
                context,
                attributeSet,
                defStyleAttr,
                defStyleRes
            ).build()

            val materialDrawable = MaterialShapeDrawable(shape)

            materialDrawable.initializeElevationOverlay(context)
            materialDrawable.elevation = ViewCompat.getElevation(this)
            materialDrawable.fillColor = ColorStateList.valueOf(backgroundColor)
            background = materialDrawable
        }
    }

    private fun obtainBackgroundColor(): Int {
        val finalOverlayColor = overlayColor
        return when {
            background != null -> (background as ColorDrawable).color
            finalOverlayColor != null -> getMergedColor(
                overlayColor = finalOverlayColor,
                overlayAlpha = overlayAlpha
            )
            else -> MaterialColors.getColor(this, R.attr.colorSurface)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        MaterialShapeUtils.setParentAbsoluteElevation(this)
    }

    override fun setElevation(elevation: Float) {
        super.setElevation(elevation)
        MaterialShapeUtils.setElevation(this, elevation)
    }

}