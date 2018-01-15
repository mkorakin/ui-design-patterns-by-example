package com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvp

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mkorakin.UiDesignPatternsByExample.R
import com.example.mkorakin.UiDesignPatternsByExample.databinding.SimpleClickerMvpBinding

class SimpleClickerMvpActivity : AppCompatActivity() {

    private var presenter: SimpleClickerPresenter = SimpleClickerPresenter()

    private lateinit var binding: SimpleClickerMvpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.simple_clicker_mvp)

        presenter.bind(object : ClickerView {

            override fun displayCount(count: Int) {
                binding.simpleClickerButton.text = count.toString()
            }

        })

        binding.controller = presenter
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }
}