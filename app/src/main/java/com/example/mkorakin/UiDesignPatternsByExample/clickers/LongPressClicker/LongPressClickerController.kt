package com.example.mkorakin.UiDesignPatternsByExample.clickers.LongPressClicker

import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * A Controller for a clicker that can be long pressed to automatically generate clicks.
 * The longer pressed, the faster clicks will be generated.
 */
internal class LongPressClickerController {

    private val AUTO_INCREMENT_TURN_ON_DELAY_MS = 200L
    private val AUTO_INCREMENT_ACCELERATION_INTERVAL_MS = 1000L
    private val BASE_AUTO_INCREMENT_INTERVAL_MS = 500L
    private val MAX_ACCELERATION_STEPS = 7L

    val model = App.model

    private var timerSubscription: Disposable? = null
    private val accelerationTimer = Observable.intervalRange(
            0,
            MAX_ACCELERATION_STEPS,
            AUTO_INCREMENT_TURN_ON_DELAY_MS,
            AUTO_INCREMENT_ACCELERATION_INTERVAL_MS,
            TimeUnit.MILLISECONDS)

    private var autoIncrementing = false
        @Synchronized get
        @Synchronized set


    /**
     * Start auto incrementing the count.
     */
    fun onPress() {
        timerSubscription = accelerationTimer
                .doOnNext({ autoIncrementing = true })
                .switchMap({ accelerationStep ->
                    Observable.interval(
                            (BASE_AUTO_INCREMENT_INTERVAL_MS / (Math.pow(2.0, accelerationStep.toDouble()))).toLong(),
                            TimeUnit.MILLISECONDS)
                })
                .subscribe({ model.incrementCount() })
    }

    /**
     * Stop auto incrementing the count.
     */
    fun onRelease() {
        timerSubscription?.dispose()
        timerSubscription = null

        if (autoIncrementing) {
            autoIncrementing = false
        } else {
            model.incrementCount()
        }
    }
}