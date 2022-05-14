package com.igonris.pokeapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igonris.common.MyDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val dispatchers: MyDispatchers
): ViewModel() {

    private val _goHome = MutableLiveData(false)
    val goHome: LiveData<Boolean> = _goHome

    fun countDown() {
        viewModelScope.launch(dispatchers.io) {
            delay(2000)

            withContext(dispatchers.main) {
                _goHome.postValue(true)
            }
        }
    }

}