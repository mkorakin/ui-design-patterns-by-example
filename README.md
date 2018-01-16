# What's here

**The same Simple Clicker is implemented in 3 design patterns: MVC, MVVM, and MVP.**

**Then more features are added to highlight traits of each pattern.**

- [The Features](#the-features)
  * [Simple Clicker](#simple-clicker)
  * [Long-Press Clicker](#long-press-clicker)
  * [Two Thumbs Clicker](#two-thumbs-clicker)
  * [Clicker Editor](#clicker-editor)
- [The Model](#the-model)
- [Examples](#examples)
  * [Simple Clicker - MVC, MVVM, MVP](#simple-clicker---mvc-mvvm-mvp)
  * [Two Thumbs Clicker - MVVM](#two-thumbs-clicker---mvvm)
  * [Long-Press Clicker - MVC](#long-press-clicker---mvc)
  * [Clicker Editor - MVP](#clicker-editor---mvp)

# The Features

### Simple Clicker
A button that displays a count of the total clicks.

### Long-Press Clicker
A button than can be long pressed to automatically generate clicks. The longer pressed, the faster clicks will be generated.

### Two Thumbs Clicker
Two buttons, each showing both the global count and the count each was clicked.

### Clicker Editor
A clicker that uses the same text box to allow editing of the global counter value, and to show the count.
The count is auto-incremented every second, and the UI shouldn\`t update while the count is being edited.

# The Model

The Model represents the state of the Application. 

In the clicker examples, the same [Model](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/Model/Model.kt) is used by 
all the examples.
The Model provides an interface for observing and for modifying its state:

```kotlin
fun count() : Observable<Int>
fun incrementCount()
fun setCount(count: Int)
```

At first we may want to implement the Model using a simple [InMemoryModel](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/Model/InMemoryModel.kt).
This can later be modified without changes to the rest of the code.
For example - adding persistency to remember the count when the app is reopened, adding server support to allow changing the same count from different devices, and so on.

# Examples

## Simple Clicker - MVC, MVVM, MVP

The exact same Simple Clicker is implemented in 3 design patterns:
 - [**SimpleClickerController**](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvc/SimpleClickerController.kt)
 - [**SimpleClickerViewModel**](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvvm/SimpleClickerViewModel.kt)
 - [**SimpleClickerPresenter**](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvp/SimpleClickerPresenter.kt)

While this illustrates the structure of each pattern, all implementations exhibit the same basic behavior: The View continuously represents the state of the Model.

## Two Thumbs Clicker - MVVM
A View Model allows us to maintain a view state that is decoupled from the Model. 

In Two Thumbs Clicker each [View Model](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/TwoThumbClicker/LocalAndGlobalClickerViewModel.kt) holds a separate count. This count can then be displayed and controlled by 2 different buttons.

## Long-Press Clicker - MVC
A Controller can encapsulate control logic, separating it from the code that is responsible for reflecting the model state on the View.

In Long-Press Clicker we want the count to auto-increment (and accelerate) as long as the clicker is pressed. For this the [Controller](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/LongPressClicker/LongPressClickerController.kt) will maintain a state using timers for auto-incrementing.

## Clicker Editor - MVP
As a Presenter holds a reference to the View, it can access its state.

In [ClickerEditorPresenter](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/ClickerEditor/ClickerEditorPresenter.kt) we inspect the 
[View.isEditing()](/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/ClickerEditor/ClickerEditorView.kt) state to prevent presenting Model.count updates while the user is editing.

# Code Walk-Through
## Simple Clicker - MVC
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
In MVC The Controller provides the View with the api to modfiy the Model.

In this example, the [Controller]((/app/src/main/java/com/example/mkorakin/UiDesignPatternsByExample/clickers/SimpleClicker/mvc/SimpleClickerController.kt)) exposes:
```kotlin
fun incrementCount()
```

To reflect the Model's state, the View binds to it directly.
