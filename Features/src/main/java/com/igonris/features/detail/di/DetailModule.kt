package com.igonris.features.detail.di

import com.igonris.features.detail.domain.GetPokemonInfoUseCase
import com.igonris.features.detail.domain.IGetPokemonInfoUseCase
import com.igonris.repository.pokemon.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DetailModule {

    @Provides
    fun getPokeInfoUseCase(pokemonRepository: PokemonRepository): IGetPokemonInfoUseCase =
        GetPokemonInfoUseCase(pokemonRepository)
}