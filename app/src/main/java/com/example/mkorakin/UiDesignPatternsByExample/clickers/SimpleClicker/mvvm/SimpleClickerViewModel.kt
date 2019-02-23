package com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvvm

import android.arch.lifecycle.ViewModel
import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

/**
 * A View Model representing the state of a clicker.
 */
internal class SimpleClickerViewModel : ViewModel(), ClickerViewModel {

    private val model = App.model
    private var subscription: Disposable

    /**
     * Click count.
     */
    override val count: Observable<Int> = BehaviorSubject.create()

    /**
     * Observe the [Model]'s state and modify the local state accordingly.
     */
    init {
        subscription = model.count.subscribe { (count as BehaviorSubject).onNext(it) } // MKOR!!!
    }

    /**
     * Increment the click count.
     */
    override fun incrementCount() {
        model.incrementCount()
    }

    /**
     * Stop observing the Model.
     */
    override fun onCleared() {
        subscription.dispose()
        super.onCleared()
    }
}