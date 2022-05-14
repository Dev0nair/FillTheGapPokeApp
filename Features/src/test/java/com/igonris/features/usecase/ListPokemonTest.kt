package com.igonris.features.usecase

import com.igonris.common.types.ResultType
import com.igonris.features.home.domain.GetPokesUseCase
import com.igonris.features.home.domain.IGetPokesUseCase
import com.igonris.repository.pokemon.PokemonRepository
import com.igonris.repository.pokemon.bo.PokemonShortInfoBO
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ListPokemonTest {

    //region Features
    private val pokemonRepository = mock<PokemonRepository>()
    private lateinit var getPokesUseCase: IGetPokesUseCase
    //endregion

    @Before
    fun doSetup() {
        getPokesUseCase = GetPokesUseCase(pokemonRepository)

    }

    @Test
    fun `fetch all pokemon test`() = runTest {
        val expected = ResultType.Success(emptyList<PokemonShortInfoBO>())
        whenever(pokemonRepository.getPokemonList(15, 0)) doReturn expected

        val result = getPokesUseCase.invoke(15, 0).toList()

        verify(pokemonRepository).getPokemonList(15, 0)

        result[0] shouldBeInstanceOf ResultType.Loading::class.java
        result[1] shouldBeInstanceOf ResultType.Success::class.java
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(pokemonRepository)
    }
}