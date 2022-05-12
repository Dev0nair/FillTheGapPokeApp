package com.igonris.repository.pokemonrepository

import com.igonris.repository.pokemonrepository.dao.PokemonDAO
import com.igonris.repository.pokemonrepository.dao.ResultDao
import retrofit2.Response

interface PokemonRepository {

    suspend fun getPokemonList(
        limit: Int,
        offset: Int
    ): Response<ResultDao>

    suspend fun getPokemonInfo(id: Int): Response<PokemonDAO>
}