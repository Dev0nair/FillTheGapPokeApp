package com.igonris.repository.pokemon.dao

import com.igonris.common.extensions.firstUpperThenLower
import com.igonris.repository.pokemon.bo.PokemonFullInfoBO
import com.igonris.repository.pokemon.bo.PokemonShortInfoBO

data class PokemonDAO(
    val id: Int,
    val abilities: List<PokemonAbilityDAO>,
    val types: List<PokemonTypeDAO>,
    val name: String,
    val sprites: PokemonSpriteDAO
) {

    fun mapFull() = PokemonFullInfoBO(
        id = id,
        name = name.firstUpperThenLower(),
        abilities = abilities.map { it.ability.name.firstUpperThenLower() },
        types = types.map { it.type.name.firstUpperThenLower() },
        image = sprites.front_default
    )
}
