# What's here

**The same Simple Clicker is implemented in 3 design patterns: MVC, MVVM, and MVP.**

**Then more features are added to highlight traits of each pattern.**  
  
See brief descriptions below and full implementation in the [repo](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers).
  
- [The Model](#the-model)
- [Examples](#examples)
  * [Simple Clicker](#simple-clicker---mvc-mvvm-mvp)
    + [**MVC**](#simple-clicker---mvc)
    + [**MVVM**](#simple-clicker---mvvm)
    + [**MVP**](#simple-clicker---mvp)  
  * [Touch Gesture Clicker - **MVC**](#touch-gesture-clicker---mvc): Indirect model modification
  * [Long-Press Clicker - **MVC**](#long-press-clicker---mvc): Controller state
  * [Two Thumbs Clicker - **MVVM**](#two-thumbs-clicker---mvvm): View Model state
  * [Toolbar Clicker - **MVVM**](#toolbar-clicker---mvvm): View Model sharing
  * [Animating Clicker - **MVP**](#animating-clicker---mvp): View state
  
Any suggestions for more examples, or different implementations/interpretations are most welcome.

# The Model

The Model represents the state of the application. 

In the clicker examples we want to implement views that count clicks. The application [Model](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/Model/Model.kt) 
provides an interface for observing and for modifying the global click state that is shared by all the screens in the app:

```kotlin
val count : Observable<Int>
  
fun incrementCount()
fun setCount(count: Int)
```

The same [InMemoryModel](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/Model/InMemoryModel.kt)
implementation is shared in all the examples. Later we may want to add features to the model such as persistency to database, server support and so on.

# Examples

## Simple Clicker - MVC, MVVM, MVP

The exact same Simple Clicker (a button that displays a count of the total clicks) is implemented in 3 design patterns:
 - [Simple Clicker - MVC](#simple-clicker---mvc)
 - [Simple Clicker - MVVM](#simple-clicker---mvvm)
 - [Simple Clicker - MVP](#simple-clicker---mvp)

All examples follow the same behavior:
- User clicks end up modifying the global Model.
- The Model is observed. Changes in the Model end up changing the state of the displayed count.
```
┌───────────────────────────────────────────────┐
│                                               │
│  View                                         │
│                                               │
└───────────────────────────────────────────────┘


                  ?  ?  ?            

                                   
    |                               ┋
    | Modify                        ┋ Observe
    ▼                               ▽
┌───────────────────────────────────────────────┐
│                                               │
│  Model                                        │
│                                               │
└───────────────────────────────────────────────┘
```

### Simple Clicker - MVC
```
┌───────────────────────────────────────────────┐
│                                               │
│  View                                         │
│                                               │
└───────────────────────────────────────────────┘
    │                               ┋
    │ OnClick                       ┋ Observe Model.count 
    ▼                               ┋
┌───────────────────────┐           ┋
│                       │           ┋
│      Controller       │           ┋
│                       │           ┋
└───────────────────────┘           ┋
    │                               ┋
    │  Modify                       ┋ 
    ▼                               ▽
┌───────────────────────────────────────────────┐
│                                               │
│  Model                                        │
│                                               │
└───────────────────────────────────────────────┘
```
In MVC the View reflects the Model directly, and uses the Controller for modifying it.

In the clicker example, the [Controller](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvc/SimpleClickerController.kt) 
provides incrementCount to control the Model:
```kotlin
class SimpleClickerController {

    fun onClick() {
        model.incrementCount()
    }
    
}
```
![Android](https://developer.android.com/images/robot-tiny.png) 
To reflect the Model's state, the [View](/app/src/main/res/layout/simple_clicker_mvc.xml) binds to it directly.
This is done in the [Activity](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvc/SimpleClickerMvcActivity.kt):
```kotlin
val binding = DataBindingUtil.setContentView<SimpleClickerMvcBinding>(this, R.layout.simple_clicker_mvc)
  
// Bind Model -> View
App.model.count.subscribe({ count -> 
        binding.simpleClickerButton.text = count.toString() 
    })
  
// Bind View -> Controller
binding.controller = SimpleClickerController()
```

### Simple Clicker - MVVM
```
┌───────────────────────────────────────────────┐
│                                               │
│  View                                         │
│                                               │
└───────────────────────────────────────────────┘
    |                               ┋
    | incrementCount                ┋ Observe count
    ▼                               ▽
┌───────────────────────────────────────────────┐
│                                               │
│  View Model                                   │
│                                               │
└───────────────────────────────────────────────┘
    |                               ┋
    | Modify                        ┋ Observe
    ▼                               ▽
┌───────────────────────────────────────────────┐
│                                               │
│  Model                                        │
│                                               │
└───────────────────────────────────────────────┘
```
In MVVM the View refelects the state of the View Model.

In the clicker example, the View Model exposes a count state and the controls for modifying it. To manage its state [SimpleClickerViewModel](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvvm/SimpleClickerViewModel.kt)  increments the Model's count and observes changes in the Model:  
```kotlin
class SimpleClickerViewModel {  

    val viewModelCount: MutableLiveData<Int>

    fun incrementCount() {
        model.incrementCount()
    }
    
    init {
        model.count.subscribe(viewModelCount::set)
    }
}
```
![Android](https://developer.android.com/images/robot-tiny.png) 
In the [Activity](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvvm/SimpleClickerMvvmActivity.kt) 
we bind the [View](/app/src/main/res/layout/simple_clicker_mvvm.xml) to the [View Model](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvvm/SimpleClickerViewModel.kt):
```kotlin
val binding = DataBindingUtil.setContentView<SimpleClickerMvvmBinding>(this, R.layout.simple_clicker_mvvm)
  
binding.vm = ViewModelProviders.of(this).get(SimpleClickerViewModel::class.java)
```
### Simple Clicker - MVP
```
┌───────────────────────────────────────────────┐
│                                               │
│  View                                         │
│                                               │
└───────────────────────────────────────────────┘
   |                                ▲
   | incrementCount                 | displayCount
   ▼                                |
┌───────────────────────────────────────────────┐
│                                               │
│  Presenter                                    │
│                                               │
└───────────────────────────────────────────────┘
    |                               ┋
    | Modify                        ┋ Observe
    ▼                               ▽
┌───────────────────────────────────────────────┐
│                                               │
│  Model                                        │
│                                               │
└───────────────────────────────────────────────┘
```
In MVP the Presenter is aware of the View, and presents on it a reflection of the Model.

In the clicker example, the [Presenter](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvp/SimpleClickerPresenter.kt) 
is constructed with a [ClickerView](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvp/ClickerView.kt). The Model is observed for presenting it on the View:
```kotlin
interface ClickerView {
    fun displayCount(count: Int)
}
```

```kotlin
class SimpleClickerPresenter {

    constructor(view: ClickerView) {
        model.count.subscribe(view::displayCount)
    }
    
    fun incrementCount() {
        model.incrementCount()
    }
}
```
![Android](https://developer.android.com/images/robot-tiny.png) 
In the [Activity](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvp/SimpleClickerMvpActivity.kt) 
we implement the [ClickerView](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvp/ClickerView.kt)
interface:
```kotlin
binding = DataBindingUtil.setContentView(this, R.layout.simple_clicker_mvp)

presenter = SimpleClickerPresenter(object : ClickerView {

    override fun displayCount(count: Int) {
        binding.simpleClickerButton.text = count.toString()
    }
})
```
and bind the [View](/app/src/main/res/layout/simple_clicker_mvp.xml) to the controls implemented by the [Presenter](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvp/SimpleClickerPresenter.kt):
```kotlin
binding.controller = presenter
```
## Touch Gesture Clicker - MVC
**Indirect model modification**  
In MVC the Controller modifies the model in reposnse to UI events.  
  
In Touch Gesture Clicker we want a button that clicks when pressed (as opposed to when released).
  
To implement this, the [Controller](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/TouchGestureClicker/TouchGestureClickerController.kt) 
receives touch events from the view and decides when to call incrementCount:
```kotlin
fun onTouch(event: MotionEvent) {
    if (event.action == MotionEvent.ACTION_DOWN) {
        model.incrementCount()
    }
}
```
## Long-Press Clicker - MVC
**Controller state**  
A Controller can maintain a control state.  
  
In Long-Press Clicker we want a button that only clicks when long pressed.

To implement this, the [Controller](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/LongPressClicker/LongPressClickerController.kt) will maintain a state using a timer:
```kotlin
fun onTouch(event: MotionEvent) {
    when (event.action) {
        MotionEvent.ACTION_DOWN -> {
            timer = Observable
                    .timer(LONG_PRESS_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
                    .subscribe({ model.incrementCount() })
        }
        MotionEvent.ACTION_UP -> timer?.dispose()
    }
}
```
## Two Thumbs Clicker - MVVM
**View Model state**  
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

init {
    model.count.subscribe(globalCount::set)
}
```
![Android](https://developer.android.com/images/robot-tiny.png) 
Two View Models are created, each bound to a button in the [Activity](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/TwoThumbClicker/TwoThumbsClickerActivity.kt):
```kotlin
val binding = DataBindingUtil.setContentView<TwoThumbsClickerMvvmBinding>(this, R.layout.two_thumbs_clicker_mvvm)
  
binding.vmA = ViewModelProviders.of(this).get("clickerA", StatefulClickerViewModel::class.java)
binding.vmB = ViewModelProviders.of(this).get("clickerB", StatefulClickerViewModel::class.java)
```
## Toolbar Clicker - MVVM
**View Model sharing**  
Multiple views can share the same View Model instance.

In toolbar clicker we want the clicks to be displayed both on a button and on the screen's toolbar.
  
![Android](https://developer.android.com/images/robot-tiny.png) 
To implement this, in the [Activity](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/ToolbarClicker/ToolbarClickerActivity.kt)
we bind the same [View Model](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvvm/ClickerViewModel.kt)
both to the button and to the activity's title:
```kotlin
// Bind to button view
binding.vm = vm
  
// Bind to activity title
vm.count.observe(this, Observer<Int> {
    count ->  title = count?.toString()
})
```
As multiple Views can share the same View Model, some may suscribe to only a subset of the model (e.g. only to localCount). We may want to make sure that any heavy work (db / network etc.) is done only when needed.
For this in [OnDemandClickerViewModel](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/ToolbarClicker/OnDemandClickerViewModel.kt)
we’ll use [rx sharing](http://reactivex.io/documentation/operators.html#connectable) 
and [RxLiveDataAdapter](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/infrastructure/RxLiveDataAdapter.kt):
```kotlin
viewModelCount = model.count
  
        // Cache and share the observable with all subscribers.
        .replay(1).refCount()
  
        // [RxLiveDataAdapter] will subscribe to the rx [Observable] only
        // when [LiveData] is subscribed to by the Views.
        .to({ rx -> RxLiveDataAdapter(rx) })

```
## Animating Clicker - MVP
**View state**  
As a Presenter holds a reference to the View, it can access its state.
  
In Animating Clicker we want a clicker that animates on each click. Clicks on an animating view should not trigger more animations. 
  
To implement this we’ll construct a [Presenter](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/AnimatingClicker/AnimatingClickerPresenter.kt) 
with an [AnimatingClickerView](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/AnimatingClicker/AnimatingClickerView.kt):
```kotlin
interface AnimatingClickerView {
    fun animate()
    val isAnimating: Boolean
    
    fun displayCount(count: Int)
}
```
and animate it on clicks. While animating, clicks will increment the Models count, but won't trigger more animations:
```kotlin
fun incrementCount() {
    if (!view.isAnimating) {
        view.animate()
    }
    model.incrementCount()
}
```

Animation implementation will be done in the [View](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/AnimatingClicker/AnimatingClickerActivity.kt):
```kotlin
override fun animate() {
    binding.simpleClickerButton.animate().rotationBy(360f)
}
```
