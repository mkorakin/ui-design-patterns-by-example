package com.example.mkorakin.UiDesignPatternsByExample.clickers.ClickerEditor

import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

/**
 * A Presenter presenting changes in the clicker count on a bound [ClickerEditorView].
 */
internal class ClickerEditorPresenter {

    private val model = App.model
    private var subscriptions: CompositeDisposable = CompositeDisposable()
    private var timer = Observable.interval(1, TimeUnit.SECONDS)

    /**
     * Bind a view to be presented on.
     * Changes in the model won't be presented while [ClickerEditorView.isEditing]
     * is true.
     */
    fun bind(view: ClickerEditorView) {
        unbind()

        subscriptions.add(
                this.model.count()
                        // Querying the View to check if isEditing before presenting the count on it.
                        .filter({ !view.isEditing() })
                        .subscribe(view::displayCount))

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
        subscriptions = CompositeDisposable()
    }
}