package com.example.mkorakin.UiDesignPatternsByExample.presentation

import android.databinding.BindingAdapter
import android.widget.TextView

@BindingAdapter("clicker:count")
fun TextView.setCount(count: Int?) {
    text = count?.toString()
}