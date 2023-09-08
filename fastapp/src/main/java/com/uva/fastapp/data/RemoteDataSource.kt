package com.uva.fastapp.data

import android.util.Log
import com.google.gson.Gson
import com.uva.fastapp.ext.fromJson
import com.uva.fastapp.model.CatDto
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class RemoteDataSource() {

    suspend fun uploadCats(limit: Int = 10): List<CatDto> {
        val url: URL
        var urlConnection: HttpURLConnection? = null
        try {
            url = URL("https://api.thecatapi.com/v1/images/search?limit=$limit")
            urlConnection = url
                .openConnection() as HttpURLConnection
            urlConnection.setRequestProperty(
                "x-api-key",
                "live_G5LgZXmEAKb6u3JY4oDQ6gI8HgqHX0WHPv27Mg54UiL9mtyKo8VXDNbjIMASyRAN"
            )
            val `in` = urlConnection.inputStream
            val isw = InputStreamReader(`in`)
            val data = isw.readLines().firstOrNull().orEmpty()
            Log.i("quick", data)
            return Gson().fromJson(data)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            urlConnection?.disconnect()
        }
        return emptyList()
    }

    fun uploadCat() {


    }
}

