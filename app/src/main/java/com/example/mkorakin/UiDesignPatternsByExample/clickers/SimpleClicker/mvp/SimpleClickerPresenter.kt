package com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvp

import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvc.ClickerController
import io.reactivex.disposables.Disposable

/**
 * # MVP
 *
 * |
 * |    +---------------------------------------------------------+
 * |    |                                                         |
 * |    |    View                                                 |
 * |    |                                                         |
 * |    +---------------------------------------------------------+
 * |       ▲                                          |
 * |       | Modify [ClickerView.displayCount]        | Modify [incrementCount]
 * |       |                                          ▼
 * |    +---------------------------------------------------------+
 * |    |                                                         |
 * |    |    Presenter                                            |
 * |    |                                                         |
 * |    +---------------------------------------------------------+
 * |        :                                        |
 * |        : Observe                                | Modify
 * |        ▽                                        ▼
 * |    +---------------------------------------------------------+
 * |    |                                                         |
 * |    |    Model                                                |
 * |    |                                                         |
 * |    +---------------------------------------------------------+
 * |
 * |    # Who Knows Who
 * |     - The View knows the Presenter and uses it to change the Model's state.
 * |     - The Presenter observes the Model and presents its state on the View.
 * |
 *
 */
internal class SimpleClickerPresenter : ClickerController {

    private val model = App.model
    private var subscription: Disposable? = null


    ////////////////////////////////////////////////////////////////////////
    // Observing the Model's state and presenting it on the View.
    //

    fun bind(view: ClickerView) {
        unbind()
        subscription = this.model.count().subscribe(view::displayCount)
    }

    ////////////////////////////////////////////////////////////////////////
    // Exposed controls - Allowing a View to control the state of the Model.
    //

    override fun incrementCount() {
        model.incrementCount()
    }

    ////////////////////////////////////////////////////////////////////////
    // Clean ups.
    //

    fun unbind() {
        subscription?.dispose()
    }
}