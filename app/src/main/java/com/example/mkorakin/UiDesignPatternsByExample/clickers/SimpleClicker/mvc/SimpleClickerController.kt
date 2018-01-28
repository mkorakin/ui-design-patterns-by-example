package com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvc

import com.example.mkorakin.UiDesignPatternsByExample.Model.App

/**
 * A Controller for incrementing the Model's count.
 */
internal class SimpleClickerController : ClickerController {

    private val model = App.model

    /**
     * Increment the Model's count.
     */
    override fun incrementCount() {
        model.incrementCount()
    }
}