package com.example.mkorakin.UiDesignPatternsByExample

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mkorakin.UiDesignPatternsByExample.databinding.MenuBinding

class MainActivity : AppCompatActivity(), MenuItem.Controller {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<MenuBinding>(this, R.layout.menu)

        binding.menuList.adapter = MenuAdapter(
                this,
                R.layout.menu_item,
                arrayOf(
                        MenuItem.SIMPLE_CLICKER_MVC,
                        MenuItem.SIMPLE_CLICKER_MVVM,
                        MenuItem.SIMPLE_CLICKER_MVP,
                        MenuItem.LONG_PRESS_CLICKER_MVC,
                        MenuItem.TWO_THUMBS_CLICKER_MVVM,
                        MenuItem.CLICKER_EDITOR_MVP
                ),
                this)

    }

    override fun select(item: MenuItem) {
        startActivity(Intent(this, item.activity))
    }
}