package com.igonris.features.home.domain

import com.igonris.common.ResultType
import com.igonris.repository.pokemon.PokemonRepository
import com.igonris.repository.pokemon.bo.PokemonShortInfoBO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokesUseCase(
    pokemonRepository: PokemonRepository
) {

    suspend fun invoke(): ResultType<List<PokemonShortInfoBO>> =
        ResultType.Loading()
}