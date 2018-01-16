package com.example.mkorakin.UiDesignPatternsByExample.clickers.SimpleClicker.mvc

import com.example.mkorakin.UiDesignPatternsByExample.Model.App
import com.example.mkorakin.UiDesignPatternsByExample.Model.Model

/**
 * │
 * │                        MVC
 * │
 * │
 * │    ┌───────────────────────────────────────────────┐
 * │    │                                               │
 * │    │  View                                         │
 * │    │                                               │
 * │    └───────────────────────────────────────────────┘
 * │        ┋                              │
 * │        ┋                              │
 * │        ┋ Observe [Model.count]        │ Modify [incrementCount]
 * │        ┋                              ▼
 * │        ┋                   ┌───────────────────────┐
 * │        ┋                   │                       │
 * │        ┋                   │      Controller       │
 * │        ┋                   │                       │
 * │        ┋                   └───────────────────────┘
 * │        ┋                              │
 * │        ┋                              │ Modify
 * │        ▽                              ▼
 * │    ┌───────────────────────────────────────────────┐
 * │    │                                               │
 * │    │  Model                                        │
 * │    │                                               │
 * │    └───────────────────────────────────────────────┘
 * │
 * │    # Who Knows Who
 * │        - The View:
 * │            - Knows the Model and reflects its state.
 * │            - Knows the Controller and uses it to modify the Model's state.
 * │        - The Controller knows the Model and modifies its state.
 * │
 *
 * In this example the Controller supplies the view with the [incrementCount] interface.
 * The state of the View is a direct reflection of [Model.count].
 */
internal class SimpleClickerController : ClickerController {

    val model = App.model

    ////////////////////////////////////////////////////////////////////////
    // Exposed controls - Allowing a View to control the state of the model.
    //

    override fun incrementCount() {
        model.incrementCount()
    }
}