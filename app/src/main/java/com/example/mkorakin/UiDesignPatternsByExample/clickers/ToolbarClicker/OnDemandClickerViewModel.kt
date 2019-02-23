package com.example.mkorakin.UiDesignPatternsByExample.clickers.ToolbarClicker

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvvm.ClickerViewModel
import io.reactivex.Observable

class OnDemandClickerViewModel : ViewModel(), ClickerViewModel { // MKOR!!! remove.

    private val LOG_TAG = "OnDemandClickerVm" // TODO: OnDemandClickerViewModel::class.simpleName
    private val model = App.model

    override val count: Observable<Int>

    init {
        count = model.count

                .doOnSubscribe({Log.i(LOG_TAG, "Subscribing to model.count")})
                .doOnDispose({Log.i(LOG_TAG, "Disposing subscription to model.count")})

                .replay(1).refCount()
    }

    override fun incrementCount() {
        model.incrementCount()
    }
}