package com.unimapp.unimapp.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unimapp.common.extensions.SingleLiveEvent

abstract class BaseViewModel<State, Event> : ViewModel() {

    val state = MutableLiveData<State>()

    val event = SingleLiveEvent<Event>()

    fun postState(value: State) {
        value?.let {
            state.postValue(it)
        }
    }

    fun postEvent(value: Event) {
        value?.let {
            event.postValue(it)
        }
    }

}