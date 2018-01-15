package com.example.mkorakin.UiDesignPatternsByExample.clickers.LongPressClicker

import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.Model.Model
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * An example illustrating a stateful Controller logic.
 *
 * In this example, [LongPressClickerController] maintains timers that
 * accelerate the speed of automatic clicks while the clicker is pressed.
 *
 * The state of the View is still a direct reflection of the [Model].
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


    ////////////////////////////////////////////////////////////////////////
    // Exposed controls - Allowing a View to control the state of the model.
    //

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