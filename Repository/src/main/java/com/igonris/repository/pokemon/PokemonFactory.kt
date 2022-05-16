package com.igonris.repository.pokemon

import android.content.Context
import com.igonris.repository.RepoBuildType
import com.igonris.repository.RepositoryConfiguration
import com.igonris.repository.pokemon.local.PokemonLocal
import com.igonris.repository.pokemon.remote.PokemonAPI
import com.igonris.repository.pokemon.remote.PokemonRemote

object PokemonFactory {

    private const val API_V = "v2"
    const val BASE_URL = "https://pokeapi.co/api/${API_V}/"

    fun getRepository(
        buildType: RepoBuildType = RepositoryConfiguration.PokeRepo,
        pokemonAPI: PokemonAPI,
        context: Context
    ): PokemonRepository =
        when (buildType) {
            RepoBuildType.REMOTE -> PokemonRemote(pokemonAPI)
            RepoBuildType.LOCAL -> PokemonLocal(context)
        }

}