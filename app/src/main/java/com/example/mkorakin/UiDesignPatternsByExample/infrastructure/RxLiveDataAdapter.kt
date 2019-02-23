package com.example.mkorakin.UiDesignPatternsByExample.infrastructure

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * An adapter from rx [Observable] to [LiveData].
 *
 * The [Observable] will be subscribed on the first call to [LiveData.onActive].
 * To dispose the subscription, call [dispose].
 */
class RxLiveDataAdapter<T>(val rx: Observable<T>) : LiveData<T>(), Disposable {

    private var rxSubscription: Disposable? = null

    override fun onActive() {
        super.onActive()
        if (rxSubscription == null)
            rxSubscription = rx.subscribe(this::postValue)
    }

    /**
     * Is the underlying rx subscription disposed.
     */
    override fun isDisposed(): Boolean {
        return rxSubscription?.isDisposed == true
    }

    /**
     * Disposes the underlying rx subscription.
     */
    override fun dispose() {
        rxSubscription?.dispose()
    }

    companion object {

        fun <T> Observable<T>.toLiveData(): LiveData<T> =
            this.to { RxLiveDataAdapter(it) }

        fun <T> Observable<T>.observeLiveData(lifecycleOwner: LifecycleOwner, onChanged: (T?) -> Unit) {
            this.toLiveData().observe(lifecycleOwner, Observer<T> { onChanged(it) })
        }
    }
}