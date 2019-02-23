package com.example.mkorakin.UiDesignPatternsByExample.clickers.ToolbarClicker

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mkorakin.UiDesignPatternsByExample.R
import com.example.mkorakin.UiDesignPatternsByExample.databinding.SimpleClickerMvvmBinding
import com.example.mkorakin.UiDesignPatternsByExample.infrastructure.RxLiveDataAdapter

class ToolbarClickerActivity : AppCompatActivity() {

    val vm
        get() = ViewModelProviders.of(this).get(OnDemandClickerViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<SimpleClickerMvvmBinding>(this, R.layout.simple_clicker_mvvm)
        binding.setLifecycleOwner(this)

        binding.clickerButton.setOnClickListener {
            vm.incrementCount()
        }

        binding.viewState = vm.count.to { RxLiveDataAdapter(it) }

        // Bind to activity title
        vm.count.to { RxLiveDataAdapter(it) }.observe(this, Observer<Int> { count ->
            title = count?.toString()
        })
    }
}