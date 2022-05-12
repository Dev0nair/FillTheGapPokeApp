package com.igonris.repository.pokemonrepository.dao

import com.igonris.repository.pokemonrepository.bo.PokemonShortInfoBO

data class PokemonDirDAO(
    val name: String,
    val url: String
) {
    fun map() =
        PokemonShortInfoBO(
            name = name,
            image = url
        )
}