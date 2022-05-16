package com.igonris.repository.pokemon.dao

import com.igonris.common.Utils
import com.igonris.repository.pokemon.bo.PokemonShortInfoBO

data class PokemonDirDAO(
    val name: String,
    val url: String
) {
    fun map() =
        PokemonShortInfoBO(
            id = Utils.getIdFromURL(url),
            name = name,
            urlInfo = url
        )
}