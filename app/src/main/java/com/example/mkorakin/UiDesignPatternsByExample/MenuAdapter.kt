package com.example.mkorakin.UiDesignPatternsByExample

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.mkorakin.UiDesignPatternsByExample.databinding.MenuItemBinding

class MenuAdapter(context: Context?, layout: Int, items: Array<out MenuItem>?, private var controller: MenuItem.Controller) :
        ArrayAdapter<MenuItem>(context, layout, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = super.getView(position, convertView, parent)
        val binding = DataBindingUtil.bind<MenuItemBinding>(view)

        binding?.controller = controller
        binding?.item = getItem(position)

        return view
    }
}