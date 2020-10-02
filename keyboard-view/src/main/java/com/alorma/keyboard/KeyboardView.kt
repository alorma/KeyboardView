package com.alorma.keyboard

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.MaterialShapeUtils

class KeyboardView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.keyboardViewStyle,
    @StyleRes defStyleRes: Int = R.style.Widget_Keyboard
) : RecyclerView(context, attributeSet, defStyleAttr) {

    private var fillColor: Int = MaterialColors.getColor(this, R.attr.colorSurface)

    init {
        initAttributes(context, attributeSet, defStyleAttr, defStyleRes)
        initBackground(context)

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
            fillColor = getColor(R.styleable.KeyboardView_fillColor, fillColor)
        }
    }

    private fun initBackground(context: Context) {
        if (background == null || background is ColorDrawable) {

            fillColor = if (background != null) {
                (background as ColorDrawable).color
            } else {
                fillColor
            }
            
            fillColor = getMergedColor(overlayColor = fillColor)

            val materialDrawable = MaterialShapeDrawable()

            materialDrawable.initializeElevationOverlay(context)
            materialDrawable.elevation = ViewCompat.getElevation(this)
            materialDrawable.fillColor = ColorStateList.valueOf(fillColor)
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