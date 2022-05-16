package com.igonris.repository.pokemon

import com.igonris.common.types.ResultType
import com.igonris.repository.pokemon.bo.PokemonFullInfoBO
import com.igonris.repository.pokemon.bo.PokemonShortInfoBO

interface PokemonRepository {

    suspend fun getPokemonList(
        limit: Int,
        offset: Int
    ): ResultType<List<PokemonShortInfoBO>>

    suspend fun getPokemonInfo(id: Int): ResultType<PokemonFullInfoBO>
}