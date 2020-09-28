package com.kishannareshpal.sample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val _bottomsheet = MutableLiveData<Boolean>()
    val bottomsheet: LiveData<Boolean>
        get() = _bottomsheet


    fun showBottomSheet() {
        _bottomsheet.value = true
    }
}