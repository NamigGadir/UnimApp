package com.ingress.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unimapp.domain.base.BaseUseCase
import com.unimapp.domain.base.RemoteResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.lang.Exception

abstract class BaseViewModel<State, Event> : ViewModel() {

    val state = MutableLiveData<State>()

    val event = SingleLiveEvent<Event>()

    val errorHandler = MutableLiveData<Int>()

    val handler by lazy {
        CoroutineExceptionHandler { _, exception ->
            handleError(exception, 0)
        }
    }

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

    fun <T, R> invokeRequest(params: T, useCase: BaseUseCase<T, R>, result: (result: R) -> Unit) {
        viewModelScope.launch(handler) {
            when (val callResult = useCase.invoke(params)) {
                is RemoteResponse.Success -> result(callResult.result)
                is RemoteResponse.NetworkError -> handleError(callResult.exception, callResult.errorCode)
            }
        }
    }

    private fun handleError(exception: Throwable, errorCode: Int) {
        errorHandler.postValue(errorCode)
    }
}