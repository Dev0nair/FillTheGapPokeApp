package com.igonris.repository.pokemonrepository

import android.content.Context
import com.igonris.repository.RepoBuildType
import com.igonris.repository.RepositoryConfiguration
import com.igonris.repository.pokemonrepository.local.PokemonLocal
import com.igonris.repository.pokemonrepository.remote.PokemonAPI
import com.igonris.repository.pokemonrepository.remote.PokemonAPIImpl

object PokemonFactory {

    private const val API_V = "v2"
    const val BASE_URL = "https://pokeapi.co/api/${API_V}/"

    fun getRepository(
        buildType: RepoBuildType = RepositoryConfiguration.PokeRepo,
        pokemonAPI: PokemonAPI,
        context: Context
    ): PokemonRepository =
        when(buildType) {
            RepoBuildType.REMOTE -> PokemonAPIImpl(pokemonAPI)
            RepoBuildType.LOCAL -> PokemonLocal(context)
        }

}