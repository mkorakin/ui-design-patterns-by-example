package com.example.mkorakin.UiDesignPatternsByExample.clickers.AnimatingClicker

interface AnimatingClickerView {

    fun animate()

    val isAnimating: Boolean

    fun displayCount(count: Int)
}