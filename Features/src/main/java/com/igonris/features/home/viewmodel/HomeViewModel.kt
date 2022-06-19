package com.igonris.features.home.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.igonris.common.MyDispatchers
import com.igonris.common.base.BaseViewModel
import com.igonris.common.base.Navigation
import com.igonris.common.types.ResultType
import com.igonris.features.R
import com.igonris.features.home.domain.IGetPokesUseCase
import com.igonris.repository.pokemon.bo.PokemonShortInfoBO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    myDispatchers: MyDispatchers,
    private val getPokesUseCase: IGetPokesUseCase
) : BaseViewModel(myDispatchers) {

    private var pokeList = emptyList<PokemonShortInfoBO>()
    private var filtered = false

    private val _listData = MutableLiveData<List<PokemonShortInfoBO>>()
    val listData: LiveData<List<PokemonShortInfoBO>> = _listData

    private var actualPage: Int = 0
    private var pageSize: Int = 20
    private var canLoadAnotherPage: Boolean = true

    init {
        getListData(true)
    }

    fun onScroll(canScroll: Boolean) {
        if(!canScroll) {
            getListData()
        }
    }

    fun getListData(reset: Boolean = false) {
        if (!canLoadAnotherPage && !reset || filtered || isLoading()) return

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

        val actualList: List<PokemonShortInfoBO> = if (_listData.value != null) _listData.value!! else emptyList()
        val newList = actualList.plus(list)

        withContext(myDispatchers.main) {
            _listData.postValue(newList)
            pokeList = newList
        }
    }

    fun onPokemonClick(id: Int, pokeImg: View, pokeName: View, cardView: View) {
        navigate(
            Navigation(
                dest = R.id.home_to_detail,
                args = listOf("pokeId" to id),
                sharedElements = listOf(
                    pokeImg to "poke_image_big",
                    pokeName to "poke_name_big",
                    cardView to "poke_view_big"
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
                        pokemon.name.lowercase().contains(query.lowercase())
            }
            .distinctBy { pokemon ->
                pokemon.id
            }

        _listData.postValue(filteredList)
    }

}