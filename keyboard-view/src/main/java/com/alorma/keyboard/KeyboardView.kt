package com.alorma.keyboard

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class KeyboardView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.keyboardViewStyle,
    @StyleRes defStyleRes: Int = R.style.Widget_Keyboard
) : RecyclerView(context, attributeSet, defStyleAttr) {

    init {
        val numbersAdapter = KeyboardNumbersAdapter()
        layoutManager = GridLayoutManager(context, numbersAdapter.itemCount / 2)
        adapter = numbersAdapter
    }

}