package com.igonris.common

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

object Utils {
    fun getJsonFromAssets(context: Context, fileName: String): String? {
        val jsonString: String = try {
            val asset: InputStream = context.getAssets().open(fileName)
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
}