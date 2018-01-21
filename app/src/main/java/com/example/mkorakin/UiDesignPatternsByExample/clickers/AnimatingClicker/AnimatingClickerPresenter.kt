package com.example.mkorakin.UiDesignPatternsByExample.clickers.AnimatingClicker

import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvc.ClickerController
import io.reactivex.disposables.Disposable

class AnimatingClickerPresenter(private val view: AnimatingClickerView) : ClickerController {

    private val model = App.model

    private var subscription: Disposable?

    init {
        subscription = model.count.subscribe(view::displayCount)
    }

    override fun incrementCount() {
        if (!view.isAnimating) {
            model.incrementCount()
            view.animate()
        }
    }

    fun dispose() {
        subscription?.dispose()
    }
}