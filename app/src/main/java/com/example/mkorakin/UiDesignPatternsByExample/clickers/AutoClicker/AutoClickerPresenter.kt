package com.example.mkorakin.UiDesignPatternsByExample.clickers.AutoClicker

import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

/**
 * An example illustrating the Presenter ability to access the View.
 *
 * In this example, the Presenter inspects an [AutoClickerView.isEditing] state to
 * prevent changes to the View while editing.
 * [AutoClickerView.isEditing] state is maintained within the View.
 *
 * To simulate model changes while editing - the [AutoClickerPresenter] maintains
 * a timer to increment the model every second.
 */
internal class AutoClickerPresenter {

    private val model = App.model
    private var subscriptions: CompositeDisposable = CompositeDisposable()
    private var timer = Observable.interval(1, TimeUnit.SECONDS)

    ////////////////////////////////////////////////////////////////////////
    // Observing the Model's state and presenting it on the View.
    //

    fun bind(view: AutoClickerView) {
        unbind()

        // Querying the View to check if isEditing before presenting the count on it.
        subscriptions.add(
                this.model.count()
                        .filter({ !view.isEditing() })
                        .subscribe(view::displayCount))

        // Starting a timer that auto-"clicks" the Model every second.
        subscriptions.add(
                timer
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ model.incrementCount() })
        )
    }

    ////////////////////////////////////////////////////////////////////////
    // Exposed controls - Allowing a View to control the state of the model.
    //

    fun setCount(count: Int) {
        model.setCount(count)
    }

    ////////////////////////////////////////////////////////////////////////
    // Clean ups.
    //

    fun unbind() {
        subscriptions.dispose()
        subscriptions = CompositeDisposable()
    }
}