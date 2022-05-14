package com.igonris.repository.pokemon.dao

import com.igonris.repository.pokemon.bo.PokemonShortInfoBO

data class PokemonDirDAO(
    val name: String,
    val url: String
) {
    fun map() =
        PokemonShortInfoBO(
            name = name,
            urlInfo = url
        )
}