package com.igonris.repository.di

import android.content.Context
import com.igonris.common.MyDispatchers
import com.igonris.repository.pokemonrepository.PokemonFactory
import com.igonris.repository.pokemonrepository.PokemonRepository
import com.igonris.repository.pokemonrepository.remote.PokemonAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun getDispatchers(): MyDispatchers = MyDispatchers()

    @Singleton
    @Provides
    fun getPokeRepository(
        pokemonAPI: PokemonAPI,
        dispatchers: MyDispatchers,
        @ApplicationContext context: Context,
    ): PokemonRepository =
        PokemonFactory
            .getRepository(
                pokemonAPI = pokemonAPI,
                context = context
            )

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return Retrofit.Builder()
            .baseUrl(PokemonFactory.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addNetworkInterceptor(httpLoggingInterceptor.apply {
                        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .build()
    }

    @Provides
    fun getRetrofitPoke(retrofit: Retrofit): PokemonAPI =
        retrofit.create(PokemonAPI::class.java)
}