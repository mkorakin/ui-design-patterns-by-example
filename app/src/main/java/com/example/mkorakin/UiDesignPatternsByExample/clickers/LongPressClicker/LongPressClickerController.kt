package com.example.mkorakin.UiDesignPatternsByExample.clickers.LongPressClicker

import android.view.MotionEvent
import android.view.View
import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class LongPressClickerController : View.OnTouchListener {

    private val LONG_PRESS_INTERVAL_MILLIS = 500L

    private val model = App.model

    private var timerSubscription: Disposable? = null

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                timerSubscription = Observable
                        .timer(LONG_PRESS_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
                        .subscribe({ model.incrementCount() })
                view.performClick()
            }
            MotionEvent.ACTION_UP -> timerSubscription?.dispose()
        }
        return false
    }

    fun dispose() {
        timerSubscription?.dispose()
    }
}