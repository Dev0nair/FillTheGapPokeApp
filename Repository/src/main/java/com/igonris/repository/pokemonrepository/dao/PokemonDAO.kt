package com.igonris.repository.pokemonrepository.dao

import com.igonris.repository.pokemonrepository.bo.PokemonShortInfoBO

data class PokemonDAO(
    val abilities: List<PokemonAbilityDAO>,
    val types: List<PokemonTypeDAO>,
    val name: String,
    val sprites: PokemonSpriteDAO
) {
    fun map() = PokemonShortInfoBO(
        name = name,
        image = sprites.front_default
    )
}
