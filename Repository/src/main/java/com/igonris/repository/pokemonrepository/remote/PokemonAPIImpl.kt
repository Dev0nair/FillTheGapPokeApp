package com.igonris.repository.pokemonrepository.remote

import com.igonris.repository.pokemonrepository.PokemonRepository
import com.igonris.repository.pokemonrepository.dao.PokemonDAO
import com.igonris.repository.pokemonrepository.dao.ResultDao
import retrofit2.Response

class PokemonAPIImpl(
    val pokemonAPI: PokemonAPI
) : PokemonRepository {
    override suspend fun getPokemonList(
        limit: Int,
        offset: Int
    ): Response<ResultDao> = pokemonAPI.getPokemonList(limit, offset)

    override suspend fun getPokemonInfo(id: Int): Response<PokemonDAO> =
        pokemonAPI.getPokemonInfo(id)
}