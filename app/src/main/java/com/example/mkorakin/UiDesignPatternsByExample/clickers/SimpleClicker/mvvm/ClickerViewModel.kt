package com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvvm

import io.reactivex.Observable

interface ClickerViewModel {

    val count: Observable<Int>

    fun incrementCount()
}