package com.example.mkorakin.UiDesignPatternsByExample.clickers.TwoThumbClicker

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvc.ClickerController
import io.reactivex.disposables.Disposable

/**
 * An example illustrating the View Model's own state, independent of
 * the global application Model's state.
 *
 * In this example, [localCount] supplies local count of the clicks, allowing
 * building a view with two buttons - each counting their own clicks.
 * [globalCount] is also supplied, directly reflecting the state of the view.
 *
 * Manipulating the ViewModel is done by [incrementCount] that manipulates both
 * the local model and the global one.
 */
class LocalAndGlobalClickerViewModel : ViewModel(), ClickerViewModel, ClickerController {

    private val model = App.model
    private var subscription: Disposable


    ////////////////////////////////////////////////////////
    // Exposed state - To be displayed by a bound view.
    //

    override val localCount = ObservableField(0)
    override val globalCount = ObservableField<Int>()


    ////////////////////////////////////////////////////////////////////////
    // Exposed controls - Allowing a view to control the state of the model.
    //

    /**
     * Increment both local and global counts.
     */
    override fun incrementCount() {
        model.incrementCount()
        localCount.set(localCount.get()?.plus(1))
    }


    ////////////////////////////////////////////////////////////////////////
    // Binding the application model to the displayed global count.
    //

    init {
        subscription = model.count().subscribe(globalCount::set)
    }

    override fun onCleared() {
        subscription.dispose()
        super.onCleared()
    }
}