package com.igonris.repository.pokemon.remote

import com.igonris.common.types.ErrorType
import com.igonris.common.types.ResultType
import com.igonris.repository.pokemon.PokemonRepository
import com.igonris.repository.pokemon.bo.PokemonFullInfoBO
import com.igonris.repository.pokemon.bo.PokemonShortInfoBO
import com.igonris.repository.pokemon.dao.PokemonDAO
import com.igonris.repository.pokemon.dao.PokemonDirDAO
import com.igonris.repository.pokemon.dao.ResultDao
import retrofit2.Response

class PokemonAPIImpl(
    private val pokemonAPI: PokemonAPI
) : PokemonRepository {
    override suspend fun getPokemonList(limit: Int, offset: Int): ResultType<List<PokemonShortInfoBO>> {
        val result = pokemonAPI.getPokemonList(limit, offset)
        return if(result.isSuccessful && result.body() != null) {
            ResultType.Success(result.body()!!.results.map { it.map() })
        } else {
            ResultType.Error(ErrorType.APIError(result.message()))
        }

    }

    override suspend fun getPokemonInfo(id: Int): ResultType<PokemonFullInfoBO> {
        val result = pokemonAPI.getPokemonInfo(id)
        return if(result.isSuccessful && result.body() != null) {
            ResultType.Success(result.body()!!.mapFull())
        } else {
            ResultType.Error(ErrorType.APIError(result.message()))
        }
    }


}