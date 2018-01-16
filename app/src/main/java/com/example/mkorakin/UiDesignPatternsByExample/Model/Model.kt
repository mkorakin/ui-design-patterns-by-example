package com.example.mkorakin.UiDesignPatternsByExample.Model

import io.reactivex.Observable

/**
 * An interface representing the Application Model.
 * It is implemented by a simple [InMemoryModel] that maintains its state in memory.
 * It can later be replaced with other implementations without modifying the rest of the app.
 */
interface Model {

    val count : Observable<Int>

    fun incrementCount()

    fun setCount(count: Int)

}