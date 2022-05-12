package com.igonris.repository.pokemon

import com.igonris.common.MyDispatchers
import com.igonris.repository.configuration.RepoBuildType
import com.igonris.repository.configuration.RepositoryConfiguration

object PokemonFactory {

    fun getRepository(
        buildType: RepoBuildType = RepositoryConfiguration.PokeRepo,
        dispatchers: MyDispatchers
    ): PokemonRepository =
        when(buildType) {
            RepoBuildType.LOCAL -> object: PokemonRepository{}
            RepoBuildType.REMOTE -> object: PokemonRepository{}
        }

}