package com.unimapp.unimapp.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unimapp.common.extensions.SingleLiveEvent

abstract class BaseViewModel<T> : ViewModel() {

    val state = MutableLiveData<T>()

    val event = SingleLiveEvent<T>()

    fun postState(value: T) {
        value?.let {
            state.postValue(it)
        }
    }

    fun postEvent(value: T) {
        value?.let {
            event.postValue(it)
        }
    }

}