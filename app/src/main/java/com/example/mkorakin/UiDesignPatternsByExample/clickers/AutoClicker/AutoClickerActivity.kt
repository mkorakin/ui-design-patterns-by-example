package com.example.mkorakin.UiDesignPatternsByExample.clickers.AutoClicker

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.example.mkorakin.UiDesignPatternsByExample.R
import com.example.mkorakin.UiDesignPatternsByExample.databinding.AutoClickerMvpBinding


class AutoClickerActivity : AppCompatActivity() {

    private val presenter = AutoClickerPresenter()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<AutoClickerMvpBinding>(this, R.layout.auto_clicker_mvp)

        binding.background.setOnTouchListener({ v, _ ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
            false})

        binding.count.setOnFocusChangeListener { _, hasFocus ->
            val input = binding.count.text.toString().toIntOrNull();
            if (!hasFocus && input != null) {
                presenter.setCount(input)
            }
        }

        presenter.bind(object : AutoClickerView {
            override fun isEditing(): Boolean {
                return binding.count.isFocused
            }

            override fun displayCount(count: Int) {
                binding.count.setText(count.toString(), TextView.BufferType.NORMAL)
            }
        })
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }
}