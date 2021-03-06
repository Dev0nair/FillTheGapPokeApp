package com.igonris.features.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.igonris.common.MyDispatchers
import com.igonris.common.types.ResultType
import com.igonris.features.home.domain.IGetPokesUseCase
import com.igonris.features.home.viewmodel.HomeViewModel
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val dispatchers = MyDispatchers(
        io = testDispatcher,
        main = testDispatcher
    )

    // region ViewModel
    private lateinit var homeViewModel: HomeViewModel
    private val getPokesUseCase: IGetPokesUseCase = mock()
    // endregion

    @Before
    fun setup() {
        homeViewModel = HomeViewModel(
            myDispatchers = dispatchers,
            getPokesUseCase = getPokesUseCase
        )
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(getPokesUseCase)
    }

    @Test
    fun `needed data for home is fetched`() = runTest {
        whenever(getPokesUseCase(20, 0)) doReturn flowOf(
            ResultType.Success(emptyList())
        )

        homeViewModel.getListData(reset = true)

        verify(getPokesUseCase)(20, 0)
    }
}