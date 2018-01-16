package com.example.mkorakin.UiDesignPatternsByExample.clickers.TwoThumbClicker

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvc.ClickerController
import io.reactivex.disposables.Disposable

/**
 * A View Model that provides both the count of the local clicks, and the total
 * number of clicks in the app.
 */
class StatefulClickerViewModel : ViewModel(), ClickerViewModel, ClickerController {

    private val model = App.model
    private var subscription: Disposable


    /**
     * The count of clicks on this clicker.
     */
    override val localCount = ObservableField(0)

    /**
     * The total count of clicks in the app.
     */
    override val globalCount = ObservableField<Int>()

    /**
     * Increment both local and global counts.
     */
    override fun incrementCount() {
        model.incrementCount()
        localCount.set(localCount.get()?.plus(1))
    }

    /**
     * Binding the application model to the displayed global count.
     */
    init {
        subscription = model.count.subscribe(globalCount::set)
    }

    /**
     * Stop observing the Model.
     */
    override fun onCleared() {
        subscription.dispose()
        super.onCleared()
    }
}