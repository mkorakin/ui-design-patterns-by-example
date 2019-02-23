package com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvvm

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mkorakin.UiDesignPatternsByExample.R
import com.example.mkorakin.UiDesignPatternsByExample.databinding.SimpleClickerMvvmBinding
import com.example.mkorakin.UiDesignPatternsByExample.infrastructure.RxLiveDataAdapter.Companion.toLiveData

class SimpleClickerMvvmActivity : AppCompatActivity() {

    private val vm: SimpleClickerViewModel
        get() = ViewModelProviders.of(this).get(SimpleClickerViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<SimpleClickerMvvmBinding>(this, R.layout.simple_clicker_mvvm)
        binding.lifecycleOwner = this

        binding.clickerButton.setOnClickListener {
            vm.incrementCount()
        }

        binding.viewState = vm.count.toLiveData()
    }
}