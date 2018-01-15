package com.example.mkorakin.UiDesignPatternsByExample.clickers.ClickerEditor

interface ClickerEditorView {

    fun displayCount(count: Int)

    fun isEditing(): Boolean

}