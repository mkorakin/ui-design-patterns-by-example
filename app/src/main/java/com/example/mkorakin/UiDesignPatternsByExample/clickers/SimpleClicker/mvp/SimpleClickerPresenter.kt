package com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvp

import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvc.ClickerController
import io.reactivex.disposables.Disposable

/**
 * A Presenter presenting the Model on a [ClickerView].
 */
internal class SimpleClickerPresenter : ClickerController {

    private val model = App.model
    private var subscription: Disposable? = null


    /**
     * Bind a view to be presented on.
     * Observes changes in the Model and presents them on the View.
     */
    fun bind(view: ClickerView) {
        unbind()
        subscription = model.count().subscribe(view::displayCount)
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
        subscription?.dispose()
    }
}