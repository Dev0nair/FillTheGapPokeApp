package com.igonris.features.home.domain

import com.igonris.common.ErrorType
import com.igonris.common.ResultType
import com.igonris.repository.pokemonrepository.PokemonRepository
import com.igonris.repository.pokemonrepository.bo.PokemonShortInfoBO
import kotlinx.coroutines.coroutineScope

class GetPokemonInfoUseCase(
    val pokemonRepository: PokemonRepository
) {
    suspend fun invoke(id: Int): ResultType<PokemonShortInfoBO> =
        coroutineScope {
            val result = pokemonRepository.getPokemonInfo(id)

            result.errorBody()?.run {
                ResultType.Error<PokemonShortInfoBO>(ErrorType.APIError(this.toString() + result.message() + " ${result.code()}"))
            }

            result.body()?.run {
                ResultType.Success(this.map())
            }

            ResultType.Error(ErrorType.APIError(result.message() + " ${result.code()}"))
        }
}