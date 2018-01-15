package com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvvm

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.Model.Model
import com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvc.ClickerController
import io.reactivex.disposables.Disposable

/**
 * # MVVM
 *
 * |
 * |    +---------------------------------------------------------+
 * |    |                                                         |
 * |    |    View                                                 |
 * |    |                                                         |
 * |    +---------------------------------------------------------+
 * |         :                             |
 * |         : Observe [count]             | Modify [incrementCount]
 * |         V                             V
 * |    +---------------------------------------------------------+
 * |    |                                                         |
 * |    |    View Model                                           |
 * |    |                                                         |
 * |    +---------------------------------------------------------+
 * |         :                             |
 * |         : Observe                     | Modify
 * |         V                             V
 * |    +---------------------------------------------------------+
 * |    |                                                         |
 * |    |    Model                                                |
 * |    |                                                         |
 * |    +---------------------------------------------------------+
 * |
 * |    # Who Knows Who
 * |        - The View:
 * |            - Observes the View Model and reflects its state.
 * |            - Modifies the state of the View Model.
 * |        - The View Model
 * |            - Observes the state of the Model and modifies its own state accordingly.
 * |            - Modifies the state of the Model.
 *
 * Similarly to MVC, in MVVM the View reflects a model, only this time not the global Model.
 *
 * In this example the View Model supplies the View with a [count] property and an
 * [incrementCount] method to control the model.
 *
 * In this simplified implementation both work directly on the [Model]:
 * Calling [incrementCount] will directly increment the [Model], which
 * then will be reflected by [count].
 */
internal class SimpleClickerViewModel : ViewModel(), ClickerViewModel, ClickerController {

    private val model = App.model
    private var subscription: Disposable


    ////////////////////////////////////////////////////////////////////////
    // Exposed state - To be displayed by a bound view.
    //

    override val count: ObservableField<Int> = ObservableField()


    ////////////////////////////////////////////////////////////////////////
    // Exposed controls - Allowing a View to control the state of the model.
    //

    override fun incrementCount() {
        model.incrementCount()
    }


    ////////////////////////////////////////////////////////////////////////
    // Observing the Model's state and modifying the View Model's state
    // accordingly.
    //

    init {
        subscription = model.count().subscribe(count::set)
    }


    ////////////////////////////////////////////////////////////////////////
    // Clean ups.
    //

    override fun onCleared() {
        subscription.dispose()
        super.onCleared()
    }
}