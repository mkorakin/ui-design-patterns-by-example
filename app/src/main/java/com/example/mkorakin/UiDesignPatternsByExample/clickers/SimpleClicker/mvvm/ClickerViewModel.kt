package com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvvm

import android.arch.lifecycle.LiveData

interface ClickerViewModel {

    val count: LiveData<Int>

    fun incrementCount()
}