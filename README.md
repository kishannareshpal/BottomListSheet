# 🗳 BottomListSheet

[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14) [![Download](https://api.bintray.com/packages/kishannareshpal/maven/bottomlistsheet/images/download.svg?version=1.0-alpha02)](https://bintray.com/kishannareshpal/maven/bottomlistsheet/1.0-beta01/link)

A quick and easy way to implement a selectable list (single/multiple) via [Bottom Sheet Dialog Fragment](https://material.io/components/sheets-bottom).

## Instalation
Add it via gradle
```gradle
    dependencies {
        // ...
        implementation 'com.kishannareshpal:bottomlistsheet:<version>'
        // ...
    }
```

## Using it
```kotlin

    /* 
        List of items you want to show
        The items should be:
            - Item(text: String, iconRes: Int? = null) 
    */
    val dataList = listOf<Item>(
        Item("Maputo", R.drawable.maputo),
        Item("Inhambane", R.drawable.inhambane),
        Item("Beira", R.drawable.beira),
        Item("Nampula", R.drawable.nampula)
    ) 


    BottomListSheet()
        .title("Filter")
        .subtitle("By specific cities")
        .items(dataList)
        .onItemSelected { position ->
            Log.d(TAG, "You have clicked item at position: $position -> ${data[position]}")
        }
        .onConfirm { button, selectedItems ->
            Log.d(TAG, "You have selected ${selectedItems.size} items!")
        }
        .preselectItems(listOf(1, 2));
        .show(supportFragmentManager) // or if in a fragment, use activity?.supportfragmentManager

        
        // .dismiss() // use to dismiss the sheet at any time
        
        // Available properties:
        .isMultipleSelection // to get or set multiple selection mode
        .selectedItemsPositions // to get the list of selected items positions

```



## Available Methods
<table style="width:100%">
    <tr>
        <th></th>
        <th>Method</th>
        <th>Default value</th>
        <th>Description</th>
    </tr>
    <tr>
        <td><b>Title</b></td>
        <td><code>title</code></td>
        <td><code>null</code></td>
        <td>Set the title</td>
    </tr>
    <tr>
        <td><b>Subtitle</b></td>
        <td><code>subtitle</code></td>
        <td><code>null</code></td>
        <td>Set the subtitle</td>
    </tr>
    <tr>
        <td><b>Selectable items</b></td>
        <td><code>items</code></td>
        <td><code>[]</code></td>
        <td>An array list of items <code>Item(string, int)</code></td>
    </tr>
    <tr>
        <td><b>Show as dark mode</b></td>
        <td><code>isNightMode</code></td>
        <td><code>false</code></td>
        <td>Use this option to show the bottom sheet as dark mode</td>
    </tr>
    <tr>
        <td><b>On item clicked</b></td>
        <td><code>onItemSelected</code></td>
        <td><code>null</code></td>
        <td>The block of code to run whenever a single item in the list is tapped. Providing you with the <code>position</code></td>
    </tr>
    <tr>
        <td><b>On confirm button clicked</b></td>
        <td><code>onConfirm</code></td>
        <td><code>null</code></td>
        <td>The block of code to run when the confirm button is tapped. Providing you with the <code>selectedItemsList</code></td>
    </tr>
    <tr>
        <td><b>On close button clicked</b></td>
        <td><code>onClose</code></td>
        <td><code>null</code></td>
        <td>The block of code to run when the close button is tapped.</td>
    </tr>
    <tr>
        <td><b>Preselected items</b></td>
        <td><code>preselectedItems</code></td>
        <td><code>null</code></td>
        <td>The list of the items position, which you want to be selected by default when the sheet is set to multiple selection.</td>
    </tr>
    <tr>
        <td><b>Clear all selected items</b></td>
        <td><code>clearAllSelection</code></td>
        <td><code>n/a</code></td>
        <td>Use to clear all selected items when the sheet is set to be multiple selected.</td>
    </tr>
</table>


## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.


## License
```
Copyright 2020 Kishan Jadav

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
