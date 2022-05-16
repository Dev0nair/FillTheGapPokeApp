package com.igonris.repository.pokemon.bo

data class PokemonShortInfoBO(
    val id: Int,
    val name: String,
    val image: String = "",
    val urlInfo: String = ""
)