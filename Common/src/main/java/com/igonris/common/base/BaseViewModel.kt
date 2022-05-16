package com.igonris.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igonris.common.MyDispatchers
import com.igonris.common.types.ErrorType
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel(val myDispatchers: MyDispatchers) : ViewModel() {

    private val _errors = MutableLiveData<ManagedItem<ErrorType>>()
    val errors: LiveData<ManagedItem<ErrorType>> = _errors

    private val _loading = MutableLiveData<ManagedItem<Boolean>>()
    val loading: LiveData<ManagedItem<Boolean>> = _loading

    private val _navigation = MutableLiveData<ManagedItem<Navigation>>()
    val navigation: LiveData<ManagedItem<Navigation>> = _navigation

    private val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { _, throwable ->
            _errors.postValue(ManagedItem(ErrorType.Other(throwable.message ?: "")))
        }
    }

    fun launchOnIO(action: suspend () -> Unit) {
        viewModelScope.launch(myDispatchers.io + coroutineExceptionHandler) {
            action()
        }
    }

    protected suspend fun onLoading(loading: Boolean) {
        withContext(myDispatchers.main) {
            _loading.postValue(ManagedItem(loading))
        }
    }

    protected suspend fun onError(error: ErrorType) {
        onLoading(false)

        withContext(myDispatchers.main) {
            _errors.postValue(ManagedItem(error))
        }
    }

    protected fun navigate(navigation: Navigation) {
        _navigation.postValue(ManagedItem(navigation))
    }
}