package com.igonris.features.home.di

import com.igonris.features.home.domain.GetPokemonInfoUseCase
import com.igonris.features.home.domain.GetPokesUseCase
import com.igonris.repository.pokemonrepository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

    @Provides
    fun getPokesUseCase(pokemonRepository: PokemonRepository): GetPokesUseCase = GetPokesUseCase(pokemonRepository)

    @Provides
    fun getPokeInfoUseCase(pokemonRepository: PokemonRepository): GetPokemonInfoUseCase = GetPokemonInfoUseCase(pokemonRepository)
}