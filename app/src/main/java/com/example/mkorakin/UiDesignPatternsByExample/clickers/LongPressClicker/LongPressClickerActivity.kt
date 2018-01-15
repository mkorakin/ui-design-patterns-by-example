package com.example.mkorakin.UiDesignPatternsByExample.clickers.LongPressClicker

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.R
import com.example.mkorakin.UiDesignPatternsByExample.databinding.LongPressClickerMvcBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class LongPressClickerActivity : AppCompatActivity() {

    private lateinit var subscription: Disposable

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<LongPressClickerMvcBinding>(this, R.layout.long_press_clicker_mvc)

        val controller = LongPressClickerController()
        binding.controller = controller

        binding.clickerButton.setOnTouchListener({ _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> controller.onPress()
                MotionEvent.ACTION_UP -> controller.onRelease()
            }
            false
        })

        subscription = App.model
                .count()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ count -> binding.clickerButton.text = count.toString() })
    }

    override fun onDestroy() {
        subscription.dispose()
        super.onDestroy()
    }
}