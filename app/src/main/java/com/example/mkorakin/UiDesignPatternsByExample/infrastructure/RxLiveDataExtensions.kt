package com.example.mkorakin.UiDesignPatternsByExample.infrastructure

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.Observer
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable

fun <T> Observable<T>.toLiveData(): LiveData<T> =
    LiveDataReactiveStreams.fromPublisher(this.toFlowable(BackpressureStrategy.LATEST))

fun <T> Observable<T>.observeLiveData(lifecycleOwner: LifecycleOwner, onChanged: (T?) -> Unit) {
    this.toLiveData().observe(lifecycleOwner, Observer<T> { onChanged(it) })
}