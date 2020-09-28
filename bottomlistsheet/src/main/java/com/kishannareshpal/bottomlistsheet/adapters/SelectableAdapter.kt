package com.kishannareshpal.bottomlistsheet.adapters

import android.util.SparseBooleanArray
import androidx.core.util.contains
import androidx.core.util.forEach
import androidx.recyclerview.widget.RecyclerView

abstract class SelectableAdapter<VH: RecyclerView.ViewHolder>: RecyclerView.Adapter<VH>() {

    private var selectedItems: SparseBooleanArray = SparseBooleanArray()

    /**
     * Indicates if the item at position position is selected
     * @param position Position of the item to check
     * @return true if the item is selected, false otherwise
     */
    fun isPositionSelected(position: Int): Boolean {
        return selectedItems.contains(position)
    }

    /**
     * Toggle the selection status of the item at a given position
     * @param position Position of the item to toggle the selection status for
     */
    fun toggleSelection(position: Int) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    /**
     * Clear the selection status for all items
     */
    fun clearSelection() {
        val totalCount = getSelectedItemsCount()
        selectedItems.clear()
        notifyItemRangeChanged(0, totalCount)
    }

    fun getSelectedItemsCount(): Int {
        return selectedItems.size()
    }

    fun getSelectedItems(): List<Int> {
        val listOfSelectedItems = mutableListOf<Int>()
        selectedItems.forEach { position, _ ->
            listOfSelectedItems += position
        }
        return listOfSelectedItems
    }
}