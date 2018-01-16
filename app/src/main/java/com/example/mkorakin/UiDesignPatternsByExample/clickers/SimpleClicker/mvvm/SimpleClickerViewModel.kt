package com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvvm

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvc.ClickerController
import io.reactivex.disposables.Disposable

/**
 * A View Model representing the state of a clicker.
 */
internal class SimpleClickerViewModel : ViewModel(), ClickerViewModel, ClickerController {

    private val model = App.model
    private var subscription: Disposable

    /**
     * Click count.
     */
    override val count: ObservableField<Int> = ObservableField()

    /**
     * Increment the click count.
     */
    override fun incrementCount() {
        model.incrementCount()
    }

    /**
     * Observe the [Model]'s state and modify the local state accordingly.
     */
    init {
        subscription = model.count().subscribe(count::set)
    }


    /**
     * Stop observing the Model.
     */
    override fun onCleared() {
        subscription.dispose()
        super.onCleared()
    }
}