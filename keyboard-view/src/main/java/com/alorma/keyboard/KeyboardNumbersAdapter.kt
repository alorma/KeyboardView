package com.alorma.keyboard

import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KeyboardNumbersAdapter(
    private val numbers: List<Int>,
    var rippleColor: Int
) : RecyclerView.Adapter<KeyboardNumbersAdapter.NumberHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.number_layout,
                parent,
                false
            )
        return NumberHolder(view, rippleColor)
    }

    override fun onBindViewHolder(holder: NumberHolder, position: Int) {
        holder.bind(numbers[position])
    }

    override fun getItemCount(): Int = numbers.size

    class NumberHolder(itemView: View, rippleColor: Int) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.background = RippleDrawable(
                ColorStateList.valueOf(rippleColor),
                null,
                null
            )
        }

        fun bind(number: Int) {
            itemView.setOnClickListener {

            }
            (itemView as TextView).text = number.toString()
        }
    }

}