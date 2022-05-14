package com.igonris.features.home.domain

import com.igonris.common.types.ErrorType
import com.igonris.common.types.ResultType
import com.igonris.repository.pokemon.PokemonRepository
import com.igonris.repository.pokemon.bo.PokemonShortInfoBO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface IGetPokesUseCase {
    suspend operator fun invoke(size: Int, offset: Int): Flow<ResultType<List<PokemonShortInfoBO>>>
}

class GetPokesUseCase(
    private val pokemonRepository: PokemonRepository
): IGetPokesUseCase {

    override suspend operator fun invoke(size: Int, offset: Int): Flow<ResultType<List<PokemonShortInfoBO>>> =
        flow {
            coroutineScope {
                emit(ResultType.Loading())

                val result = pokemonRepository.getPokemonList(limit = size, offset = offset)

                when(result) {
                    is ResultType.Success -> emit(
                        ResultType.Success(
                            parsePokemonData(
                                list = result.data,
                                onError = { error -> emit(ResultType.Error(ErrorType.APIError(error))) }
                            )
                        )
                    )
                    else -> emit(result)
                }
            }
        }

    private suspend fun parsePokemonData(
        list: List<PokemonShortInfoBO>,
        onError: suspend (String) -> Unit
    ): List<PokemonShortInfoBO> {
        return list.mapNotNull { pokemon ->
            pokemonRepository.getPokemonInfo(
                pokemon.urlInfo
                    .dropLast(1)
                    .split('/')
                    .last()
                    .toIntOrNull() ?: 0
            ).let { pokemonResult ->
                if(pokemonResult is ResultType.Success) pokemonResult.data.map()
                else {
                    onError("")
                    null
                }
            }
        }
    }
}