package com.igonris.repository.pokemon.bo

data class PokemonFullInfoBO(
    val id: Int,
    val name: String,
    val image: String,
    val abilities: List<String>,
    val types: List<String>
) {
    fun map() = PokemonShortInfoBO(id = id, name = name, image = image)
}