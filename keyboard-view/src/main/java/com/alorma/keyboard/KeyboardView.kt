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
import com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap

class KeyboardView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.keyboardViewStyle,
    @StyleRes defStyleRes: Int = R.style.Widget_Keyboard
) : RecyclerView(
    wrap(context, attributeSet, defStyleAttr, defStyleRes),
    attributeSet,
    defStyleAttr,
) {

    private var shapeAppearance: ShapeAppearanceModel = ShapeAppearanceModel.builder(
        context,
        attributeSet,
        defStyleAttr,
        defStyleRes
    ).build()
        set(value) {
            field = value
            initBackground()
        }

    var overlayColor: Int? = null
        set(value) {
            field = value
            initBackground()
        }
    var overlayAlpha: Float = 0.08f
        set(value) {
            field = value
            initBackground()
        }

    init {

        initAttributes(attributeSet, defStyleAttr, defStyleRes)
        initBackground()

        val numbersAdapter = KeyboardNumbersAdapter()
        layoutManager = GridLayoutManager(context, numbersAdapter.itemCount / 2)
        adapter = numbersAdapter
    }

    private fun initAttributes(
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

    private fun initBackground() {
        if (background == null || background is ColorDrawable) {
            val backgroundColor = obtainBackgroundColor()
            val materialDrawable = MaterialShapeDrawable(shapeAppearance)

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