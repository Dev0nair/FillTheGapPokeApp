package com.igonris.features.home.di

import com.igonris.features.detail.domain.GetPokemonInfoUseCase
import com.igonris.features.home.domain.GetPokesUseCase
import com.igonris.features.home.domain.IGetPokesUseCase
import com.igonris.repository.pokemon.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

    @Provides
    fun getPokesUseCase(pokemonRepository: PokemonRepository): IGetPokesUseCase = GetPokesUseCase(pokemonRepository)
}