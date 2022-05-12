package com.igonris.features.home.domain

import com.igonris.common.ErrorType
import com.igonris.common.ResultType
import com.igonris.repository.pokemonrepository.PokemonRepository
import com.igonris.repository.pokemonrepository.bo.PokemonShortInfoBO
import com.igonris.repository.pokemonrepository.dao.ResultDao
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class GetPokesUseCase(
    val pokemonRepository: PokemonRepository
) {

    suspend fun invoke(size: Int, offset: Int): Flow<ResultType<List<PokemonShortInfoBO>>> =
        flow {
            coroutineScope {
                emit(ResultType.Loading())

                parseResult(
                    response = pokemonRepository.getPokemonList(size, offset),
                    onError = { error -> emit(ResultType.Error(ErrorType.APIError(error))) },
                    onSuccess = { list -> emit(ResultType.Success(parsePokemonData(list))) }
                )
            }
        }

    private suspend fun parsePokemonData(list: List<PokemonShortInfoBO>): List<PokemonShortInfoBO> {
        return list.mapNotNull { pokemon ->
            pokemonRepository.getPokemonInfo(
                pokemon.image
                    .dropLast(1)
                    .split('/')
                    .last()
                    .toIntOrNull() ?: 0
            ).body()?.map()
        }
    }

    private suspend fun parseResult(
        response: Response<ResultDao>,
        onError: suspend (String) -> Unit,
        onSuccess: suspend (List<PokemonShortInfoBO>) -> Unit
    ) {

        if (response.isSuccessful && response.body() != null) {
            onSuccess(response.body()!!.results.map { it.map() })
        } else {
            onError(
                response.message()
            )
        }

    }

}