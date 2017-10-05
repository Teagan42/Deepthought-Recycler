# Deepthought Recycler

## Purpose
Facilitate databinding with the Android RecyclerView through dynamic item inflation and binding.

## Architecture
### ItemBinders
Item binders are a mapping of layout to binding variable identifier. This allows the recycler view to automatically inflate and bind your view model for each item in the array.

`AbstractItemBinder` is the base to all implementations of item binders.

#### ItemBinder
A simple mapping of a layout to a binding variable identifier.

#### ConditionalItemBinder
Maps a layout to a binding variable identifier and implements logic to determine if the item should be handled by this item binder.

#### CompositeItemBinder
A collection of `ConditionalItemBinder` objects, allowing for different layouts to be inflated in a single recycler view based on the logic inside each `ConditionalItemBinder`.

### BindingRecyclerViewAdapter
The recycler view adapter that accepts any `AbstractItemBinder` and inflates the layout and binds your view model for you.

## Usage
### Project Setup
Your project must enable databinding, to enable databinding, add the following to your `build.gradle` file:

```
android {
    ...
    dataBinding {
        enabled = true;
    }
}
```

You will also need to add the `RecyclerView` dependency to your project:

```
dependencies {
    compile 'com.android.support:appcompat-v7:**.**.**'
    compile 'com.android.support:recyclerview-v7:**.**.**'
}
```

**NOTE** The support library versions will vary depending on your target SDK version.

### Layout
Add a `RecyclerView` and `xmlns:app` namespace to your layout, and set a binding `data` variable:

```
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>
        <variable
            name="vm"
            type="your.package.viewmodel.ListViewModel />
    </data>
    
    ...
    
    <android.support.v7.widget.RecyclerView
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         app:adapter="@{vm.adapter}"
         app:layoutManager="@{vm.layoutManager}" />
    
    ...
    
</RelativeLayout>
```

The two attributes that are required for the binding to work are `app:adapter` and `app:layoutManager`.

The `app:adapter` must be bound to a `BindingRecyclerViewAdapter`.
The `app:layoutManager` must be bound to a `RecyclerView.LayoutManager`.

### RecyclerView Event Hooks

#### itemClickHandler
Invoked when an item in the `RecyclerView` is clicked, the bound object will be passed into this handler

#### itemLongClickHandler
Invoked when an item in the `RecyclerView` is long pressed, the bound object will be passed into this handler.

#### itemDoubleTapHandler
Invoked when an item in the `RecyclerView` is double tapped, the bound object will be passed into this handler.

#### viewAttachedHandler
Hooks into the `RecyclerView.onViewAttachedFroWindow` event hook, the `View` that was attached, along with it's bound object will be passed to this handler.

#### viewDetachedHandler
Hooks into the `RecyclerView.onViewDetachedFromWindow` event hook, the `View` that was detached, along with it's bound object will be passed to this handler.

#### viewRecycledHandler
Hooks into the `RecyclerView.onViewRecycled` event hook, the `View` that was recycled, along with it's bound object will be passed to this handler.

### Item Interfaces
Included are interfaces your items can implement to hook into some of the `RecyclerView` events.

#### AttachedToWindowListener
Items that implement `AttachedToWindowListener` will be invoked when their associated view is being attached to the window. See <> for more information.

#### DetachedFromWindowListener
Items that implement `DetachedFromWindowListener` will be invoked when their associated view is being detached from the window. See <> for more information.

#### RecycledListener
Items that implement `RecycledListener` will be invoked when their associated views are recycled. See <> for more information.
