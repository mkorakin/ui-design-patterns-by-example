package com.example.mkorakin.UiDesignPatternsByExample.clickers.ClickerEditor

import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.Model.Model
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

/**
 * A Presenter presenting changes in the clicker count on a bound [ClickerEditorView].
 * Changes won't be presented while the View is in editing mode.
 */
internal class ClickerEditorPresenter(
    view: ClickerEditorView,
    private val model: Model = App.model
) {

    private var subscriptions: CompositeDisposable = CompositeDisposable()
    private var timer = Observable.interval(1, TimeUnit.SECONDS)

    init {
        subscriptions.add(
            model.count
                // Querying the View to check if isEditing before presenting the count on it.
                .filter({ !view.isEditing() })
                .subscribe(view::displayCount)
        )

        // For simulating external changes in the count:
        // Starting a timer that auto-"clicks" the Model every second.
        subscriptions.add(
            timer
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ model.incrementCount() })
        )
    }

    /**
     * Set the global count of the app.
     */
    fun setCount(count: Int) {
        model.setCount(count)
    }

    /**
     * Unbind the View from this Presenter.
     */
    fun unbind() {
        subscriptions.dispose()
    }
}