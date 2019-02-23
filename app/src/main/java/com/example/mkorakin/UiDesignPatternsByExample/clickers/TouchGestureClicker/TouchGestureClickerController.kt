package com.example.mkorakin.UiDesignPatternsByExample.clickers.TouchGestureClicker

import android.view.MotionEvent
import android.view.View
import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.Model.Model

class TouchGestureClickerController(
    private val model: Model = App.model
): View.OnTouchListener {

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            model.incrementCount()
            view.performClick()
        }
        return false
    }
}