package com.example.mkorakin.UiDesignPatternsByExample.clickers.TwoThumbClicker

import android.databinding.ObservableField

interface ClickerViewModel {

    val localCount: ObservableField<Int>

    val globalCount: ObservableField<Int>

}