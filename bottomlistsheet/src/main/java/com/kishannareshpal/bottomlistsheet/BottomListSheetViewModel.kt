package com.kishannareshpal.bottomlistsheet

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kishannareshpal.bottomlistsheet.models.Item

class BottomListSheetViewModel: ViewModel() {

    private val _title = MutableLiveData<String?>()
    val title: LiveData<String?>
        get() = _title

    private val _subtitle = MutableLiveData<String?>()
    val subtitle: LiveData<String?>
        get() = _subtitle

    private val _isMultipleSelection = MutableLiveData<Boolean>()
    val isMultipleSelection: LiveData<Boolean>
        get() = _isMultipleSelection

    private val _dismissOnConfirm = MutableLiveData<Boolean>()
    val dismissOnConfirm: LiveData<Boolean>
        get() = _dismissOnConfirm

    private val _onConfirmClickListener = MutableLiveData<((View, List<Int>) -> Unit)?>()
    val onConfirmClickListener: LiveData<((View, List<Int>) -> Unit)?>
        get() = _onConfirmClickListener

    private val _onCloseClickListener = MutableLiveData<((View, List<Int>) -> Unit)?>()
    val onCloseClickListener: LiveData<((View, List<Int>) -> Unit)?>
        get() = _onCloseClickListener

    private val _onItemSelectedListener = MutableLiveData<((Int) -> Unit)?>()
    val onItemSelectedListener: LiveData<((Int) -> Unit)?>
        get() = _onItemSelectedListener

    private val _itemsList = MutableLiveData<List<Item>?>()
    val itemsList: LiveData<List<Item>?>
        get() = _itemsList

    private val _preselectedItemsPositions = MutableLiveData<List<Int>?>()
    val preselectedItemsPositions: LiveData<List<Int>?>
        get() = _preselectedItemsPositions


    fun setTitle(title: String?) {
        this._title.value = title
    }

    fun setSubtitle(subtitle: String?) {
        this._subtitle.value = subtitle
    }

    fun setItemsList(itemsList: List<Item>?) {
        this._itemsList.value = itemsList
    }

    fun setPreselectedItemsPositions(itemsPositions: List<Int>) {
        this._preselectedItemsPositions.value = itemsPositions
    }

    fun setMultipleSelection(enabled: Boolean) {
        this._isMultipleSelection.value = enabled
    }

    fun setOnItemSelectedListener(itemSelectedListener: ((Int) -> Unit)?) {
        this._onItemSelectedListener.value = itemSelectedListener
    }

    fun setOnConfirmClickListener(clickListener: ((View, List<Int>) -> Unit)?) {
        this._onConfirmClickListener.value = clickListener
    }

    fun setOnCloseClickListener(clickListener: ((View, List<Int>) -> Unit)?) {
        this._onCloseClickListener.value = clickListener
    }

    fun setDismissOnConfirm(dismissOnConfirm: Boolean) {
        this._dismissOnConfirm.value = dismissOnConfirm
    }
}