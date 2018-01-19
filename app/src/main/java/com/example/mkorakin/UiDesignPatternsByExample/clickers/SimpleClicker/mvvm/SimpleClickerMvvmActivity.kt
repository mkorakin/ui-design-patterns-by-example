package com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvvm

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mkorakin.UiDesignPatternsByExample.R
import com.example.mkorakin.UiDesignPatternsByExample.databinding.SimpleClickerMvvmBinding

class SimpleClickerMvvmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<SimpleClickerMvvmBinding>(this, R.layout.simple_clicker_mvvm)
        binding.setLifecycleOwner(this)

        val vm = ViewModelProviders.of(this).get(SimpleClickerViewModel::class.java)

        binding.vm = vm
        binding.controller = vm
    }
}