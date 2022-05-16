package com.igonris.features.usecase

import com.igonris.common.types.ResultType
import com.igonris.features.detail.domain.GetPokemonInfoUseCase
import com.igonris.features.detail.domain.IGetPokemonInfoUseCase
import com.igonris.repository.pokemon.PokemonRepository
import com.igonris.repository.pokemon.bo.PokemonFullInfoBO
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonDetailTest {

    //region Features
    private val pokemonRepository = mock<PokemonRepository>()
    private lateinit var getPokemonInfoUseCase: IGetPokemonInfoUseCase
    //endregion

    @Before
    fun setup() {
        this.getPokemonInfoUseCase = GetPokemonInfoUseCase(pokemonRepository)
    }

    @Test
    fun `fetch pokemon info`() = runTest {
        val id = 1
        val correctResult = ResultType.Success(
            PokemonFullInfoBO(
                id = 0,
                name = "",
                types = listOf(),
                abilities = listOf(),
                image = ""
            )
        )

        whenever(pokemonRepository.getPokemonInfo(id)) doReturn correctResult

        val result = getPokemonInfoUseCase.invoke(id).toList()

        verify(pokemonRepository).getPokemonInfo(id)

        result[0] shouldBeInstanceOf ResultType.Loading::class.java
        result[1] shouldBeInstanceOf ResultType.Success::class.java
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(pokemonRepository)
    }
}