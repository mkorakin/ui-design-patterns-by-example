package com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvc

import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.Model.Model

/**
 * A Controller for incrementing the Model's count.
 */
internal class SimpleClickerController(
    private val model: Model = App.model
) : ClickerController {

    /**
     * Increment the Model's count.
     */
    override fun incrementCount() {
        model.incrementCount()
    }
}