package com.example.mkorakin.UiDesignPatternsByExample.clickers.LongPressClicker

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.R
import com.example.mkorakin.UiDesignPatternsByExample.databinding.GestureClickerMvcBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class LongPressClickerActivity : AppCompatActivity() {

    private lateinit var modelSubscription: Disposable

    private val controller = LongPressClickerController()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<GestureClickerMvcBinding>(this, R.layout.gesture_clicker_mvc)

        binding.clickerButton.setOnTouchListener(controller)

        modelSubscription = App.model
                .count
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ count -> binding.clickerButton.text = count.toString() })
    }

    override fun onDestroy() {
        modelSubscription.dispose()
        controller.dispose()
        super.onDestroy()
    }
}