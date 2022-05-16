package com.igonris.features.home.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.igonris.common.MyDispatchers
import com.igonris.common.base.BaseViewModel
import com.igonris.common.base.Navigation
import com.igonris.common.types.ResultType
import com.igonris.features.R
import com.igonris.features.home.domain.IGetPokesUseCase
import com.igonris.repository.pokemon.bo.PokemonShortInfoBO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    myDispatchers: MyDispatchers,
    private val getPokesUseCase: IGetPokesUseCase
) : BaseViewModel(myDispatchers) {

    private val pokeList = ArrayList<PokemonShortInfoBO>()
    private var filtered = false

    private val _listData = MutableLiveData<List<PokemonShortInfoBO>>()
    val listData: LiveData<List<PokemonShortInfoBO>> = _listData

    private var actualPage: Int = 0
    private var pageSize: Int = 20
    private var canLoadAnotherPage: Boolean = true



    fun getListData(reset: Boolean = false) {
        if (!canLoadAnotherPage && !reset || filtered) return

        launchOnIO {
            if (reset) {
                canLoadAnotherPage = true
                actualPage = 0
            }

            val offset = actualPage * pageSize

            getPokesUseCase(pageSize, offset)
                .flowOn(myDispatchers.io)
                .collect { result ->
                    when (result) {
                        is ResultType.Loading -> onLoading(true)
                        is ResultType.Success -> onSuccess(result.data)
                        is ResultType.Error -> onError(result.error)
                    }
                }
        }
    }

    private suspend fun onSuccess(list: List<PokemonShortInfoBO>) {
        onLoading(false)

        actualPage++

        if (list.size < pageSize) {
            canLoadAnotherPage = false
        }

        pokeList.addAll(list)

        withContext(myDispatchers.main) {
            _listData.postValue(pokeList)
        }
    }

    fun onPokemonClick(id: Int, pokeImg: View, pokeName: View) {
        navigate(
            Navigation(
                dest = R.id.home_to_detail,
                args = listOf("pokeId" to id),
                sharedElements = listOf(
                    pokeImg to "poke_image_big",
                    pokeName to "poke_name_big"
                )
            )
        )
    }

    fun filterPokemon(query: String?) {
        if (query == null || query.isEmpty()) {
            filtered = false
            _listData.postValue(pokeList)
            return
        }

        filtered = true

        val filteredList = pokeList
            .filter { pokemon ->
                pokemon.id.toString().startsWith(query) ||
                        pokemon.name.contains(query)
            }
            .distinctBy { pokemon ->
                pokemon.id
            }

        _listData.postValue(filteredList)
    }

}