package com.example.mkorakin.UiDesignPatternsByExample.clickers.TouchGestureClicker

import android.view.MotionEvent
import android.view.View
import com.example.mkorakin.UiDesignPatternsByExample.Model.App

class TouchGestureClickerController : View.OnTouchListener {

    private val model = App.model

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            model.incrementCount()
            view.performClick()
        }
        return false
    }
}