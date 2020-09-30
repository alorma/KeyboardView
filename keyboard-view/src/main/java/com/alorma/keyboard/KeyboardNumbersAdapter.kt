package com.alorma.keyboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KeyboardNumbersAdapter : RecyclerView.Adapter<KeyboardNumbersAdapter.NumberHolder>() {

    private val numbers: List<Int> = (0..9).toList().shuffled()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.number_layout,
                parent,
                false
            )
        return NumberHolder(view)
    }

    override fun onBindViewHolder(holder: NumberHolder, position: Int) {
        holder.bind(numbers[position])
    }

    override fun getItemCount(): Int = numbers.size

    class NumberHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(number: Int) {
            (itemView as TextView).text = number.toString()
        }
    }

}