# What's here

**The same Simple Clicker is implemented in 3 design patterns: MVC, MVVM, and MVP.**

**Then more features are added to highlight traits of each pattern.**
  
- [The Model](#the-model)
- [Examples](#examples)
  * [Simple Clicker](#simple-clicker---mvc-mvvm-mvp)
    + [MVC](#simple-clicker---mvc)
    + [MVVM](#simple-clicker---mvvm)
    + [MVP](#simple-clicker---mvp)
  * [Two Thumbs Clicker - MVVM](#two-thumbs-clicker---mvvm)
  * [Long-Press Clicker - MVC](#long-press-clicker---mvc)
  * [Clicker Editor - MVP](#clicker-editor---mvp)
  
Any suggestions for more examples, or different implementations/interpretations are most welcome.

# The Model

The Model represents a global state of the application. 

For the clicker [Model](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/Model/Model.kt) we provide an interface for observing and for modifying the global click state:

```kotlin
val count : Observable<Int>
  
fun incrementCount()
fun setCount(count: Int)
```

The same [InMemoryModel](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/Model/InMemoryModel.kt)
implementation is shared in all the examples. Later we may consider adding features to the model such as persistency to remember the count when the app is reopened, adding server support to allow changing the same count from different devices, and so on.

# Examples

## Simple Clicker - MVC, MVVM, MVP

The exact same Simple Clicker (a button that displays a count of the total clicks) is implemented in 3 design patterns:
 - [Simple Clicker - MVC](#simple-clicker---mvc)
 - [Simple Clicker - MVVM](#simple-clicker---mvvm)
 - [Simple Clicker - MVP](#simple-clicker---mvp)

### Simple Clicker - MVC
```
┌───────────────────────────────────────────────┐
│                                               │
│  View                                         │
│                                               │
└───────────────────────────────────────────────┘
    ┋                              │
    ┋                              │
    ┋ Observe [Model.count]        │ Modify [incrementCount]
    ┋                              ▼
    ┋                   ┌───────────────────────┐
    ┋                   │                       │
    ┋                   │      Controller       │
    ┋                   │                       │
    ┋                   └───────────────────────┘
    ┋                              │
    ┋                              │ Modify
    ▽                              ▼
┌───────────────────────────────────────────────┐
│                                               │
│  Model                                        │
│                                               │
└───────────────────────────────────────────────┘
```
In MVC The Controller provides the View with the api to modify the Model.

In the clicker example, the [Controller](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvc/SimpleClickerController.kt) exposes:
```kotlin
fun incrementCount() {
    model.incrementCount()
}
```

To reflect the Model's state, the [View](/app/src/main/res/layout/simple_clicker_mvc.xml) binds to it directly.
This is done in the [Activity](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvc/SimpleClickerMvcActivity.kt):
```kotlin
val binding = DataBindingUtil.setContentView<SimpleClickerMvcBinding>(this, R.layout.simple_clicker_mvc)
  
binding.controller = SimpleClickerController()
App.model.count.subscribe({ count -> 
        binding.simpleClickerButton.text = count.toString() 
    })
```

### Simple Clicker - MVVM
```
┌───────────────────────────────────────────────┐
│                                               │
│  View                                         │
│                                               │
└───────────────────────────────────────────────┘
    ┋                             │
    ┋ Observe [count]             │ Modify [incrementCount]
    ▽                             ▼
┌───────────────────────────────────────────────┐
│                                               │
│  View Model                                   │
│                                               │
└───────────────────────────────────────────────┘
    ┋                             |
    ┋ Observe                     | Modify
    ▽                             ▼
┌───────────────────────────────────────────────┐
│                                               │
│  Model                                        │
│                                               │
└───────────────────────────────────────────────┘
```
In MVVM the View refelects the state of the View Model.

In the clicker example, the [View Model](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvvm/SimpleClickerViewModel.kt) exposes a count state and the controls for modifying it:  
```kotlin
val viewModelCount: ObservableField<Int>

fun incrementCount() {
    model.incrementCount()
}
```
The View Model observes the Model and modifies its state accordingly:
```kotlin
model.count.subscribe(viewModelCount::set)
```
In the [Activity](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvvm/SimpleClickerMvvmActivity.kt) 
we bind the [View](/app/src/main/res/layout/simple_clicker_mvvm.xml) to the [View Model](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvvm/SimpleClickerViewModel.kt):
```kotlin
val binding = DataBindingUtil.setContentView<SimpleClickerMvvmBinding>(this, R.layout.simple_clicker_mvvm)
var vm = ViewModelProviders.of(this).get(SimpleClickerViewModel::class.java)

binding.vm = vm
binding.controller = vm
```
### Simple Clicker - MVP
```
┌───────────────────────────────────────────────┐
│                                               │
│  View                                         │
│                                               │
└───────────────────────────────────────────────┘
   ▲                                        |
   | Modify [ClickerView.displayCount]      | Modify [incrementCount]
   |                                        ▼
┌───────────────────────────────────────────────┐
│                                               │
│  Presenter                                    │
│                                               │
└───────────────────────────────────────────────┘
    ┋                                       |
    ┋ Observe                               | Modify
    ▽                                       ▼
┌───────────────────────────────────────────────┐
│                                               │
│  Model                                        │
│                                               │
└───────────────────────────────────────────────┘
```
In MVP the Presenter is aware of the View, and presents on it a reflection of the Model.

In the clicker example, the [Presenter](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvp/SimpleClickerPresenter.kt) 
can be bound to a [ClickerView](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvp/ClickerView.kt)
. The Model is observed for presenting it on the View:
```kotlin
fun bind(view: ClickerView) {
    model.count.subscribe(view::displayCount)
}
```
In the [Activity](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvp/SimpleClickerMvpActivity.kt) 
we implent the [ClickerView](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvp/ClickerView.kt)
interface:
```kotlin
binding = DataBindingUtil.setContentView(this, R.layout.simple_clicker_mvp)

presenter.bind(object : ClickerView {
    override fun displayCount(count: Int) {
        binding.simpleClickerButton.text = count.toString()
    }
})
```
and bind the [View](/app/src/main/res/layout/simple_clicker_mvp.xml) to the controls implemented by the [Presenter](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvp/SimpleClickerPresenter.kt):
```kotlin
binding.controller = presenter
```
## Two Thumbs Clicker - MVVM
A View Model allows us to maintain a view state that is decoupled from the Model. 

In Two Thumbs Clicker we want to display two buttons, each counting its own clicks.  
  
To implement this, we use a [View Model](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/TwoThumbClicker/StatefulClickerViewModel.kt) 
that maintains a local count in addition to observing the model's:
```kotlin
val localCount = ObservableField(0)
val globalCount = ObservableField<Int>()
  
fun incrementCount() {
    model.incrementCount()
    localCount.set(localCount.get()?.plus(1))
}
```
Two View Models are created, each bound to a button in the [Activity](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/TwoThumbClicker/TwoThumbsClickerActivity.kt):
```kotlin
val binding = DataBindingUtil.setContentView<TwoThumbsClickerMvvmBinding>(this, R.layout.two_thumbs_clicker_mvvm)
  
binding.vmA = ViewModelProviders.of(this).get("clickerA", StatefulClickerViewModel::class.java)
binding.vmB = ViewModelProviders.of(this).get("clickerB", StatefulClickerViewModel::class.java)
```

## Long-Press Clicker - MVC
A Controller can encapsulate control logic, separating it from presentation logic.

In Long-Press Clicker we want a button that can be long pressed to automatically generate clicks. The longer pressed, the faster clicks will be generated.

To implement this, the [Controller](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/LongPressClicker/LongPressClickerController.kt) will maintain a state using timers for auto-incrementing:
```kotlin
fun onPress() {
    accelerationTimer
            .doOnNext({ autoIncrementing = true })
            .switchMap({ accelerationStep ->
                Observable.interval(
                        (BASE_AUTO_INCREMENT_INTERVAL_MILLIS / (Math.pow(2.0, accelerationStep.toDouble()))).toLong(),
                        TimeUnit.MILLISECONDS)
            })
            .subscribe({ model.incrementCount() })
}
```

## Clicker Editor - MVP
As a Presenter holds a reference to the View, it can access its state.

In Clicker Editor we want a clicker that uses the same text box to allow editing of the global counter value, and to show the count. The count is auto-incremented every second, and the UI shouldn't update while it is being edited.

To implement this,in [ClickerEditorPresenter](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/ClickerEditor/ClickerEditorPresenter.kt) we inspect the 
[View.isEditing()](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/ClickerEditor/ClickerEditorView.kt) state to prevent presenting Model.count updates while the user is editing:
```kotlin
model.count
    .filter({ !view.isEditing() })
    .subscribe(view::displayCount))
```
