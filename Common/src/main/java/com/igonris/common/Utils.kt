package com.igonris.common

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

object Utils {
    fun getJsonFromAssets(context: Context, fileName: String): String? {
        val jsonString: String = try {
            val asset: InputStream = context.assets.open(fileName)
            val size: Int = asset.available()
            val buffer = ByteArray(size)
            asset.read(buffer)
            asset.close()
            String(buffer, Charset.defaultCharset())
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getIdFromURL(urlPokemon: String): Int {
        return urlPokemon
            .dropLast(1) // remove last / in URL
            .split('/') // split by /
            .last() // get the number in last position
            .toIntOrNull() ?: 0
    }
}