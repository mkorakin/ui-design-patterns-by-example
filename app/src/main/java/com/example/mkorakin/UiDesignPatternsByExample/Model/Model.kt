package com.example.mkorakin.UiDesignPatternsByExample.Model

import io.reactivex.Observable

interface Model {

    val count : Observable<Int>

    @Deprecated("Use setCount.")
    fun incrementCount()

    fun setCount(count: Int)

}