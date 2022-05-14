package com.igonris.repository.pokemon.local

import android.content.Context
import com.google.gson.Gson
import com.igonris.common.Utils
import com.igonris.common.types.ErrorType
import com.igonris.common.types.ResultType
import com.igonris.repository.pokemon.PokemonRepository
import com.igonris.repository.pokemon.bo.PokemonFullInfoBO
import com.igonris.repository.pokemon.bo.PokemonShortInfoBO
import com.igonris.repository.pokemon.dao.PokemonDAO
import com.igonris.repository.pokemon.dao.ResultDao
import retrofit2.Response

class PokemonLocal(val context: Context): PokemonRepository {
    override suspend fun getPokemonList(limit: Int, offset: Int): ResultType<List<PokemonShortInfoBO>> {
        val file = Utils.getJsonFromAssets(context,"PokemonDirs.JSON") ?: return ResultType.Error(ErrorType.APIError(""))

        val data = Gson().fromJson<ResultDao>(file, ResultDao::class.java)
        data.results = data.results.filterIndexed { index, pokemonDirDAO ->
            index >= offset && index < offset+limit
        }
        return ResultType.Success(data.results.map { it.map() })

    }

    override suspend fun getPokemonInfo(id: Int): ResultType<PokemonFullInfoBO> {
        val file = Utils.getJsonFromAssets(context, "PokemonInfo.JSON") ?: return ResultType.Error(ErrorType.APIError(""))

        val data = Gson().fromJson<PokemonDAO>(file, PokemonDAO::class.java)
        return ResultType.Success(data.mapFull())
    }
}