# 🗳 BottomListSheet

[ ![Download](https://api.bintray.com/packages/kishannareshpal/maven/bottomlistsheet/images/download.svg?version=1.0-alpha02) ](https://bintray.com/kishannareshpal/maven/bottomlistsheet/1.0-alpha02/link)

A quick and easy way to implement a selectable list (single/multiple) on a bottom sheet.

## Instalation
Add it via gradle
```swift
    dependencies {
        // ...
        implementation 'com.kishannareshpal:bottomlistsheet:<version>'
        // ...
    }
```

## Usage
```kotlin

    // data list
    val dataList = listOf<Item>(
        Item("Maputo", R.drawable.maputo),
        Item("Inhambane", R.drawable.inhambane),
        Item("Beira", R.drawable.beira),
        Item("Nampula", R.drawable.nampula)
    ) 

    // the bottom sheet
    BottomListSheet()
            .title("Filter")
            .subtitle("By specific cities")
            .items(dataList)
            .onItemSelected { position ->
                Log.d(TAG, "You have clicked item at position: $position -> ${data[position]}")
            }
            .onConfirm { button, selectedItems ->
                Log.d(TAG, "Applied ${selectedItems.size} filters!")
            }
            .preselectItems(listOf(1, 2))
            .show(parentFragmentManager)

```
