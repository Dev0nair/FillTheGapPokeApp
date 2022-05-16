package com.igonris.features.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.igonris.common.MyDispatchers
import com.igonris.common.types.ResultType
import com.igonris.features.detail.domain.IGetPokemonInfoUseCase
import com.igonris.features.detail.ui.DetailViewModel
import com.igonris.features.home.domain.IGetPokesUseCase
import com.igonris.features.home.ui.HomeViewModel
import com.igonris.repository.pokemon.bo.PokemonFullInfoBO
import com.igonris.repository.pokemon.bo.PokemonShortInfoBO
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBeEqualTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.math.exp

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val dispatchers = MyDispatchers(
        io = testDispatcher,
        main = testDispatcher
    )

    // region ViewModel
    private lateinit var detailViewModel: DetailViewModel
    private val getPokemonInfoUseCase: IGetPokemonInfoUseCase = mock()
    // endregion

    @Before
    fun setup() {
        detailViewModel = DetailViewModel(
            myDispatchers = dispatchers,
            getPokemonInfoUseCase = getPokemonInfoUseCase
        )
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(getPokemonInfoUseCase)
    }

    @Test
    fun `needed data for home is fetched`() = runTest {
        val expected = flowOf(
            ResultType.Loading(),
            ResultType.Success(
                PokemonFullInfoBO(
                    id = 0,
                    name = "",
                    image = "",
                    abilities = emptyList(),
                    types = emptyList()
                )
            )
        )

        whenever(getPokemonInfoUseCase(id = 0)) doReturn expected

        detailViewModel.loadInfo(id = 0)

        verify(getPokemonInfoUseCase)(id = 0)
    }
}