package com.igonris.features.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igonris.common.MyDispatchers
import com.igonris.common.types.ErrorType
import com.igonris.common.types.ResultType
import com.igonris.features.home.domain.IGetPokesUseCase
import com.igonris.repository.pokemon.bo.PokemonShortInfoBO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val myDispatchers: MyDispatchers,
    private val getPokesUseCase: IGetPokesUseCase
) : ViewModel() {

    private val _listData = MutableLiveData<List<PokemonShortInfoBO>>()
    val listData: LiveData<List<PokemonShortInfoBO>> = _listData

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _errors = MutableLiveData<ErrorType>()
    val errors: LiveData<ErrorType> = _errors

    private var actualPage: Int = 0
    private var pageSize: Int = 15
    private var canLoadAnotherPage: Boolean = true

    fun getListData(reset: Boolean = false) {
        if (!canLoadAnotherPage && !reset) return

        viewModelScope.launch(myDispatchers.io) {
            if (reset) {
                canLoadAnotherPage = true
                actualPage = 0
            }

            val offset = actualPage * pageSize

            getPokesUseCase(pageSize, offset)
                .flowOn(myDispatchers.io)
                .collect { result ->
                    when (result) {
                        is ResultType.Success -> onSuccess(result.data)
                        is ResultType.Error -> onError(result.error)
                        is ResultType.Loading -> onLoading(true)
                    }
                }
        }

    }

    private suspend fun onLoading(loading: Boolean) {
        withContext(myDispatchers.main) {
            _loading.postValue(loading)
        }
    }

    private suspend fun onError(error: ErrorType) {
        onLoading(false)

        withContext(myDispatchers.main) {
            _errors.postValue(error)
        }
    }

    private suspend fun onSuccess(list: List<PokemonShortInfoBO>) {
        onLoading(false)
        withContext(myDispatchers.main) {
            actualPage++
            if (list.size < pageSize) {
                canLoadAnotherPage = false
            }

            val newList = ArrayList(_listData.value ?: emptyList())
            newList.addAll(list)

            _listData.postValue(newList)
        }
    }

}