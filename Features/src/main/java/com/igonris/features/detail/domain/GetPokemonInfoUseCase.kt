package com.igonris.features.detail.domain

import com.igonris.common.types.ResultType
import com.igonris.repository.pokemon.PokemonRepository
import com.igonris.repository.pokemon.bo.PokemonFullInfoBO
import kotlinx.coroutines.coroutineScope

interface IGetPokemonInfoUseCase {
    suspend operator fun invoke(id: Int): ResultType<PokemonFullInfoBO>
}

class GetPokemonInfoUseCase(
    private val pokemonRepository: PokemonRepository
): IGetPokemonInfoUseCase {
    override suspend operator fun invoke(id: Int): ResultType<PokemonFullInfoBO> =
        coroutineScope {
            pokemonRepository.getPokemonInfo(id)
        }
}