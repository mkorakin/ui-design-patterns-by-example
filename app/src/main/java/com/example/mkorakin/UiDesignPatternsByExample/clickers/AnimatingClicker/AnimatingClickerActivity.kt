package com.example.mkorakin.UiDesignPatternsByExample.clickers.AnimatingClicker

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mkorakin.UiDesignPatternsByExample.R
import com.example.mkorakin.UiDesignPatternsByExample.databinding.SimpleClickerMvpBinding

class AnimatingClickerActivity : AppCompatActivity() {

    private lateinit var presenter: AnimatingClickerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<SimpleClickerMvpBinding>(this, R.layout.simple_clicker_mvp)

        presenter = AnimatingClickerPresenter(object : AnimatingClickerView {
            override fun animate() {
                binding.simpleClickerButton.animate()
                        .withStartAction({binding.simpleClickerButton.isEnabled = false})
                        .withEndAction({binding.simpleClickerButton.isEnabled = true})
                        .rotationBy(360f)
            }

            override fun displayCount(count: Int) {
                binding.simpleClickerButton.text = count.toString()
            }
        })

        binding.controller = presenter
    }

    override fun onDestroy() {
        presenter.dispose()
        super.onDestroy()
    }
}