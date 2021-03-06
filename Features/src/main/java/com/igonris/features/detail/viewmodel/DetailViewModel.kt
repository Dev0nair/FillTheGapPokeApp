package com.igonris.features.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.igonris.common.MyDispatchers
import com.igonris.common.base.BaseViewModel
import com.igonris.common.types.ResultType
import com.igonris.features.detail.domain.IGetPokemonInfoUseCase
import com.igonris.repository.pokemon.bo.PokemonFullInfoBO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    myDispatchers: MyDispatchers,
    private val getPokemonInfoUseCase: IGetPokemonInfoUseCase
) : BaseViewModel(myDispatchers) {

    private val _pokeInfo = MutableLiveData<PokemonFullInfoBO>()
    val pokeInfo: LiveData<PokemonFullInfoBO> = _pokeInfo

    fun loadInfo(id: Int) {
        launchOnIO {
            getPokemonInfoUseCase.invoke(id)
                .flowOn(myDispatchers.io)
                .collect { result ->
                    when (result) {
                        is ResultType.Loading -> {
                            onLoading(true)
                        }
                        is ResultType.Success -> {
                            onSuccess(result.data)
                        }
                        is ResultType.Error -> {
                            onError(result.error)
                        }
                    }
                }
        }
    }

    private suspend fun onSuccess(pokemonFullInfoBO: PokemonFullInfoBO) {
        onLoading(false)

        withContext(myDispatchers.main) {
            _pokeInfo.postValue(pokemonFullInfoBO)
        }
    }
}