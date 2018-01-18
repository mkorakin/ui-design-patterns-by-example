package com.example.mkorakin.UiDesignPatternsByExample.infrastructure

import android.arch.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * Adapts an Rx [Observable] to Android's [LiveData].
 *
 * The Rx [Observable] is subscribed to on [LiveData.onActive] and unsubscribed on [LiveData.onInactive]
 */
class RxToLiveDataAdapter<T>(val rx: Observable<T>) : LiveData<T>() {

    private var rxSubscription: Disposable? = null

    override fun onActive() {
        super.onActive()
        rxSubscription = rx.subscribe(this::postValue)
    }

    override fun onInactive() {
        rxSubscription?.dispose()
        super.onInactive()
    }
}