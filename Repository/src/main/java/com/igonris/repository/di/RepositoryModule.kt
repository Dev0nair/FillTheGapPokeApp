package com.igonris.repository

import com.igonris.common.MyDispatchers
import com.igonris.repository.pokemon.PokemonFactory
import com.igonris.repository.pokemon.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeaturesModule {

    @Singleton
    @Provides
    fun getDispatchers(): MyDispatchers = MyDispatchers()

    @Singleton
    @Provides
    fun getPokeRepository(dispatchers: MyDispatchers): PokemonRepository = PokemonFactory.getRepository(dispatchers = dispatchers)
}