package com.igonris.features.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igonris.common.ErrorType
import com.igonris.common.MyDispatchers
import com.igonris.common.ResultType
import com.igonris.features.home.domain.GetPokesUseCase
import com.igonris.repository.pokemon.bo.PokemonShortInfoBO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val myDispatchers: MyDispatchers,
    private val getPokesUseCase: GetPokesUseCase
): ViewModel() {

    private val _listData = MutableLiveData<List<PokemonShortInfoBO>>()
    val listData: LiveData<List<PokemonShortInfoBO>> = _listData

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    private val _errors = MutableLiveData<ErrorType>()
    val errors: LiveData<ErrorType> = _errors

    fun getListData() {
        viewModelScope.launch(myDispatchers.io) {
            val result = getPokesUseCase.invoke()

            when(result) {
                is ResultType.Success -> onSuccess(result.data)
                is ResultType.Error -> onError(result.error)
                is ResultType.Loading -> onLoading(true)
            }

        }
    }

    private fun onLoading(loading: Boolean) {
        viewModelScope.launch(myDispatchers.main) {
            _loading.postValue(true)
        }
    }

    private fun onError(error: ErrorType) {
        onLoading(false)
        viewModelScope.launch(myDispatchers.main) {
            _errors.postValue(error)
        }
    }

    private fun onSuccess(list: List<PokemonShortInfoBO>) {
        onLoading(false)
        viewModelScope.launch(myDispatchers.main) {
            _listData.postValue(list)
        }
    }

}