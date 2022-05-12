package com.igonris.repository.pokemonrepository.local

import android.content.Context
import com.google.gson.Gson
import com.igonris.common.Utils
import com.igonris.repository.pokemonrepository.PokemonRepository
import com.igonris.repository.pokemonrepository.dao.PokemonDAO
import com.igonris.repository.pokemonrepository.dao.ResultDao
import retrofit2.Response

class PokemonLocal(val context: Context): PokemonRepository {
    override suspend fun getPokemonList(limit: Int, offset: Int): Response<ResultDao> {
        val file = Utils.getJsonFromAssets(context,"PokemonDirs.JSON") ?: return Response.success(null)

        val data = Gson().fromJson<ResultDao>(file, ResultDao::class.java)
        data.results = data.results.filterIndexed { index, pokemonDirDAO ->
            index > offset && index < offset+limit
        }
        return Response.success(data)

    }

    override suspend fun getPokemonInfo(id: Int): Response<PokemonDAO> {
        val file = Utils.getJsonFromAssets(context, "PokemonInfo.JSON") ?: return Response.success(null)

        val data = Gson().fromJson<PokemonDAO>(file, PokemonDAO::class.java)
        return Response.success(data)
    }
}