package com.example.mkorakin.UiDesignPatternsByExample.clickers.ToolbarClicker

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mkorakin.UiDesignPatternsByExample.R
import com.example.mkorakin.UiDesignPatternsByExample.databinding.SimpleClickerMvvmBinding

class ToolbarClickerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<SimpleClickerMvvmBinding>(this, R.layout.simple_clicker_mvvm)
        binding.setLifecycleOwner(this)
        val vm = ViewModelProviders.of(this).get(OnDemandClickerViewModel::class.java)

        // Bind to button view
        binding.vm = vm
        binding.controller = vm

        // Bind to activity title
        vm.count.observe(this, Observer<Int> {
            count ->  title = count?.toString()
        })
    }
}