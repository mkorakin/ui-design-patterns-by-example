package com.example.mkorakin.UiDesignPatternsByExample.clickers.AnimatingClicker

import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.Model.Model
import com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvc.ClickerController
import io.reactivex.disposables.Disposable

class AnimatingClickerPresenter(
    private val view: AnimatingClickerView,
    private val model: Model = App.model
) : ClickerController {

    private var subscription: Disposable?

    init {
        subscription = model.count.subscribe(view::displayCount)
    }

    override fun incrementCount() {
        if (!view.isAnimating) {
            view.animate()
        }
        model.incrementCount()
    }

    fun dispose() {
        subscription?.dispose()
    }
}