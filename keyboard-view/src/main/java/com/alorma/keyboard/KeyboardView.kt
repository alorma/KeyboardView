package com.alorma.keyboard

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
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

    init {
        initBackground()

        val numbersAdapter = KeyboardNumbersAdapter()
        layoutManager = GridLayoutManager(context, numbersAdapter.itemCount / 2)
        adapter = numbersAdapter
    }

    private fun initBackground() {
        if (background == null || background is ColorDrawable) {
            val backgroundColor = MaterialColors.getColor(this, R.attr.colorSurface)

            val materialDrawable = MaterialShapeDrawable.createWithElevationOverlay(context)

            materialDrawable.shapeAppearanceModel = shapeAppearance
            materialDrawable.initializeElevationOverlay(context)
            materialDrawable.elevation = ViewCompat.getElevation(this)
            materialDrawable.fillColor = ColorStateList.valueOf(backgroundColor)

            background = materialDrawable
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