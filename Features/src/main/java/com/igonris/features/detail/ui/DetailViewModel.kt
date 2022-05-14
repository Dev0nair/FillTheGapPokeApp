package com.igonris.features.detail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igonris.common.MyDispatchers
import com.igonris.common.types.ErrorType
import com.igonris.common.types.ResultType
import com.igonris.features.detail.domain.GetPokemonInfoUseCase
import com.igonris.features.detail.domain.IGetPokemonInfoUseCase
import com.igonris.repository.pokemon.bo.PokemonFullInfoBO
import com.igonris.repository.pokemon.bo.PokemonShortInfoBO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor (
    private val myDispatchers: MyDispatchers,
    private val getPokemonInfoUseCase: IGetPokemonInfoUseCase
) : ViewModel() {

    private val _pokeInfo = MutableLiveData<PokemonFullInfoBO>()
    val pokeInfo: LiveData<PokemonFullInfoBO> = _pokeInfo

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _errors = MutableLiveData<ErrorType>()
    val errors: LiveData<ErrorType> = _errors

    fun loadInfo(id: Int) {
        viewModelScope.launch(myDispatchers.io) {
            val result = getPokemonInfoUseCase.invoke(id)

            when (result) {
                is ResultType.Loading -> {
                    withContext(myDispatchers.main) {
                        _loading.postValue(true)
                    }
                }
                is ResultType.Success -> {
                    withContext(myDispatchers.main) {
                        _pokeInfo.postValue(result.data!!)
                    }
                }
                is ResultType.Error -> {
                    withContext(myDispatchers.main) {
                        _errors.postValue(result.error)
                    }
                }
            }

        }
    }

}