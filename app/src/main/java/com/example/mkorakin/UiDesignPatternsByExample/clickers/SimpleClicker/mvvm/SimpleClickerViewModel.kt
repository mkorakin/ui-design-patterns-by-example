package com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvvm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvc.ClickerController
import com.example.mkorakin.UiDesignPatternsByExample.infrastructure.RxToLiveDataAdapter

/**
 * A View Model representing the state of a clicker.
 */
internal class SimpleClickerViewModel : ViewModel(), ClickerViewModel, ClickerController {

    private val model = App.model

    /**
     * Click count.
     */
    override val count: LiveData<Int>

    init {
        // Bind the model directly to the exposed count.
        count = RxToLiveDataAdapter(model.count)
    }

    /**
     * Increment the click count.
     */
    override fun incrementCount() {
        model.incrementCount()
    }
}