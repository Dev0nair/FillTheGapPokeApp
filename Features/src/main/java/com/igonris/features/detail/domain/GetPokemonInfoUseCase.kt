package com.igonris.features.detail.domain

import com.igonris.common.types.ResultType
import com.igonris.repository.pokemon.PokemonRepository
import com.igonris.repository.pokemon.bo.PokemonFullInfoBO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface IGetPokemonInfoUseCase {
    suspend operator fun invoke(id: Int): Flow<ResultType<PokemonFullInfoBO>>
}

class GetPokemonInfoUseCase(
    private val pokemonRepository: PokemonRepository
) : IGetPokemonInfoUseCase {
    override suspend operator fun invoke(id: Int): Flow<ResultType<PokemonFullInfoBO>> =
        flow {
            coroutineScope {
                emit(ResultType.Loading())
                emit(pokemonRepository.getPokemonInfo(id))
            }
        }
}