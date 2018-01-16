package com.example.mkorakin.UiDesignPatternsByExample.Model

import io.reactivex.subjects.BehaviorSubject

internal object InMemoryModel : Model {

    override val count = BehaviorSubject.createDefault(0)

    @Synchronized override fun incrementCount() {
        count.onNext(count.value + 1)
    }

    @Synchronized override fun setCount(count: Int) {
        this.count.onNext(count)
    }
}