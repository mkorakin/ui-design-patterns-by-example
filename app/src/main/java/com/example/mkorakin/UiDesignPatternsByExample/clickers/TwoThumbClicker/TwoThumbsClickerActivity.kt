package com.example.mkorakin.UiDesignPatternsByExample.clickers.TwoThumbClicker

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mkorakin.UiDesignPatternsByExample.R
import com.example.mkorakin.UiDesignPatternsByExample.databinding.TwoThumbsClickerMvvmBinding

/**
 * A screen with 2 clicker buttons.
 * Each button shows both how many times it was clicked, and how many times any button was clicked.
 */
class TwoThumbsClickerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<TwoThumbsClickerMvvmBinding>(this, R.layout.two_thumbs_clicker_mvvm)

        binding.vmA = ViewModelProviders.of(this).get("clickerA", LocalAndGlobalClickerViewModel::class.java)
        binding.vmB = ViewModelProviders.of(this).get("clickerB", LocalAndGlobalClickerViewModel::class.java)
    }

}