package com.igonris.repository.pokemon.dao

import com.igonris.repository.pokemon.bo.PokemonFullInfoBO
import com.igonris.repository.pokemon.bo.PokemonShortInfoBO

data class PokemonDAO(
    val abilities: List<PokemonAbilityDAO>,
    val types: List<PokemonTypeDAO>,
    val name: String,
    val sprites: PokemonSpriteDAO
) {

    fun mapFull() = PokemonFullInfoBO(
        name = name,
        abilities = abilities.map { it.ability.name },
        types = types.map { it.type.name },
        image = sprites.front_default
    )
}
