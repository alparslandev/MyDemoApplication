package com.alp.myapplication.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser

object GsonUtils {
    private var gson: Gson = GsonBuilder().create()
    private var jsonParser: JsonParser = JsonParser()

    fun getGson() = gson

    fun getJsonParser() = jsonParser

    fun <T> parseTo(json: String, classOfT: Class<T>): T? {
        return try {
            getGson().fromJson(json, classOfT)
        } catch (ex: Exception) {
            null
        }

    }
}
