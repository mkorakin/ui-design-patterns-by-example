package com.example.mkorakin.UiDesignPatternsByExample

import com.example.mkorakin.UiDesignPatternsByExample.clickers.AnimatingClicker.AnimatingClickerActivity
import com.example.mkorakin.UiDesignPatternsByExample.clickers.ClickerEditor.ClickerEditorActivity
import com.example.mkorakin.UiDesignPatternsByExample.clickers.LongPressClicker.LongPressClickerActivity
import com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvc.SimpleClickerMvcActivity
import com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvp.SimpleClickerMvpActivity
import com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvvm.SimpleClickerMvvmActivity
import com.example.mkorakin.UiDesignPatternsByExample.clickers.ToolbarClicker.ToolbarClickerActivity
import com.example.mkorakin.UiDesignPatternsByExample.clickers.TouchGestureClicker.TouchGestureClickerActivity
import com.example.mkorakin.UiDesignPatternsByExample.clickers.TwoThumbClicker.TwoThumbsClickerActivity

enum class MenuItem(val label: Int, val activity: Class<*>) {

    SIMPLE_CLICKER_MVC(R.string.menu_clicker_mvc, SimpleClickerMvcActivity::class.java as Class<*>),
    SIMPLE_CLICKER_MVVM(R.string.menu_clicker_mvvm, SimpleClickerMvvmActivity::class.java as Class<*>),
    SIMPLE_CLICKER_MVP(R.string.menu_clicker_mvp, SimpleClickerMvpActivity::class.java as Class<*>),
    GESTURE_CLICKER_MVC(R.string.menu_gesture_clicker_mvc, TouchGestureClickerActivity::class.java as Class<*>),
    LONG_PRESS_CLICKER_MVC(R.string.menu_long_press_clicker_mvc, LongPressClickerActivity::class.java as Class<*>),
    TWO_THUMBS_CLICKER_MVVM(R.string.menu_two_thumbs_clicker_mvvm, TwoThumbsClickerActivity::class.java as Class<*>),
    TOOLBAR_CLICKER_MVVM(R.string.menu_toolbar_clicker_mvvm, ToolbarClickerActivity::class.java as Class<*>),
    CLICKER_EDITOR_MVP(R.string.menu_clicker_editor_mvp, ClickerEditorActivity::class.java as Class<*>),
    ANIMATING_CLICKER_MVP(R.string.menu_animating_clicker_mvp, AnimatingClickerActivity::class.java as Class<*>);


    interface Controller {
        fun select(item: MenuItem)
    }
}