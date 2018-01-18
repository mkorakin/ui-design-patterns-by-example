package com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvp

import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvc.ClickerController
import io.reactivex.disposables.Disposable

/**
 * A Presenter displaying clicks on the attached [ClickerView].
 */
internal class SimpleClickerPresenter(view: ClickerView) : ClickerController {

    private val model = App.model
    private var subscription: Disposable

    init {
        // Observed changes in the Model count are presented on the View.
        subscription = model.count.subscribe(view::displayCount)
    }

    /**
     * Increment the click count.
     */
    override fun incrementCount() {
        model.incrementCount()
    }

    /**
     * Unbind the View from this Presenter.
     */
    fun unbind() {
        subscription.dispose()
    }
}