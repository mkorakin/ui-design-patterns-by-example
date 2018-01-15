package com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvc

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.R
import com.example.mkorakin.UiDesignPatternsByExample.databinding.SimpleClickerMvcBinding
import io.reactivex.disposables.Disposable

class SimpleClickerMvcActivity : AppCompatActivity() {

    private lateinit var subscription: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<SimpleClickerMvcBinding>(this, R.layout.simple_clicker_mvc)

        binding.controller = SimpleClickerController()
        subscription = App.model
                .count()
                .subscribe({ count -> binding.simpleClickerButton.text = count.toString() })
    }

    override fun onDestroy() {
        subscription.dispose()
        super.onDestroy()
    }
}