package com.example.mkorakin.UiDesignPatternsByExample.Model

import io.reactivex.Observable

interface Model {

    val count : Observable<Int>

    fun incrementCount()

    fun setCount(count: Int)

}