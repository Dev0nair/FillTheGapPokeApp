package com.igonris.repository.pokemon

import com.igonris.common.types.ResultType
import com.igonris.repository.pokemon.bo.PokemonFullInfoBO
import com.igonris.repository.pokemon.bo.PokemonShortInfoBO
import com.igonris.repository.pokemon.dao.PokemonDAO
import com.igonris.repository.pokemon.dao.PokemonDirDAO
import com.igonris.repository.pokemon.dao.ResultDao
import retrofit2.Response

interface PokemonRepository {

    suspend fun getPokemonList(
        limit: Int,
        offset: Int
    ): ResultType<List<PokemonShortInfoBO>>

    suspend fun getPokemonInfo(id: Int): ResultType<PokemonFullInfoBO>
}