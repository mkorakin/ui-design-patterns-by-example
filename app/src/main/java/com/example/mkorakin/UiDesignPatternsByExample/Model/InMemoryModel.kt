package com.example.mkorakin.UiDesignPatternsByExample.Model

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

internal object InMemoryModel : Model {

    private val count: BehaviorSubject<Int> = BehaviorSubject.createDefault(0)

    override fun count() : Observable<Int> {
        return count;
    }

    @Synchronized override fun incrementCount() {
        count.onNext(count.value + 1)
    }

    @Synchronized override fun setCount(count: Int) {
        this.count.onNext(count)
    }
}