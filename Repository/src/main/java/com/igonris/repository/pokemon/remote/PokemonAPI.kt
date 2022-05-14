package com.igonris.repository.pokemon.remote

import com.igonris.repository.pokemon.dao.PokemonDAO
import com.igonris.repository.pokemon.dao.ResultDao
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonAPI {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<ResultDao>

    @GET("pokemon/{id}")
    suspend fun getPokemonInfo(
        @Path("id") id: Int
    ): Response<PokemonDAO>
}