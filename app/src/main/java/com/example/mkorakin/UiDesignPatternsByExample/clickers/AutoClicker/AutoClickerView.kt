package com.example.mkorakin.UiDesignPatternsByExample.clickers.AutoClicker

interface AutoClickerView {

    fun displayCount(count: Int)

    fun isEditing(): Boolean

}