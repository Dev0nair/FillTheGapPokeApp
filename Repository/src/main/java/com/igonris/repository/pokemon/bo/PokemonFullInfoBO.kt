package com.igonris.repository.pokemon.bo

data class PokemonFullInfoBO(
    val name: String,
    val image: String,
    val abilities: List<String>,
    val types: List<String>
) {
    fun map() = PokemonShortInfoBO(
        name, image
    )
}