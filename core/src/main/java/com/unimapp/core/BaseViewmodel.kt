package com.unimapp.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unimapp.domain.base.BaseUseCase
import com.unimapp.domain.base.RemoteResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

abstract class BaseViewModel<State, Event> : ViewModel() {


    val state = MutableLiveData<State>()

    val event = SingleLiveEvent<Event>()

    val errorHandler = SingleLiveEvent<Int>()

    val handleProgressBar = MutableLiveData<Boolean>()

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

    fun <T, R> invokeRequest(
        params: T, useCase: BaseUseCase<T, R>,
        onStart: (() -> Unit)? = null,
        onFinish: (() -> Unit)? = null,
        onHandleLoading: (Boolean) -> Unit = ::showLoading,
        onError: ((exception: Exception, errorCode: Int) -> Unit)? = null,
        onSuccess: ((result: R) -> Unit)? = null,
    ) {
        viewModelScope.launch(handler) {
            onStart?.invoke()
            onHandleLoading(true)
            when (val callResult = useCase.invoke(params)) {
                is RemoteResponse.Success -> onSuccess?.let {
                    onSuccess(callResult.result)
                }
                is RemoteResponse.NetworkError ->
                    onError?.let {  //if error handler connected disable global error handler
                        onError(callResult.exception, callResult.errorCode)
                    } ?: handleError(callResult.exception, callResult.errorCode)
            }
            onFinish?.invoke()
            onHandleLoading(false)
        }
    }

    private fun showLoading(isShowing: Boolean) {
        handleProgressBar.postValue(isShowing)
    }

    private fun handleError(exception: Throwable, errorCode: Int) {
        errorHandler.postValue(errorCode)
    }

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(handler) {
            block()
        }
    }


    suspend fun <T> launchWithAsync(
        loadingHandle: (Boolean) -> Unit = ::showLoading,
        errorHandle: (exception: Throwable, errorCode: Int) -> Unit = ::handleError,
        block: () -> List<Deferred<T>>
    ) {
        try {
            loadingHandle(true)
            block().map { it.await() }
        } catch (t: Throwable) {
            errorHandle(t, 0)
        } finally {
            loadingHandle(false)
        }
    }
}