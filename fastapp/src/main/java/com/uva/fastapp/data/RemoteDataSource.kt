package com.uva.fastapp.data

import android.util.JsonReader
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.uva.fastapp.model.CatDto
import java.io.InputStreamReader
import java.io.StringReader
import java.net.HttpURLConnection
import java.net.URL

class RemoteDataSource() {

    fun uploadCats(): List<CatDto> {
        val url: URL
        var urlConnection: HttpURLConnection? = null
        try {
            url = URL("https://api.thecatapi.com/v1/images/search?limit=10")
            urlConnection = url
                .openConnection() as HttpURLConnection
            urlConnection.setRequestProperty(
                "x-api-key",
                "live_G5LgZXmEAKb6u3JY4oDQ6gI8HgqHX0WHPv27Mg54UiL9mtyKo8VXDNbjIMASyRAN"
            )
            val `in` = urlConnection!!.inputStream
            val isw = InputStreamReader(`in`)
            val data = isw.readLines().firstOrNull().orEmpty()
            Log.i("quick", data)
            val itemList: List<CatDto> = Gson().fromJson(data)
            return itemList
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            urlConnection?.disconnect()
        }
        return emptyList()
    }
}


internal inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)
