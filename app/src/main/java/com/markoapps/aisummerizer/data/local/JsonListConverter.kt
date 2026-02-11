package com.markoapps.aisummerizer.data.local

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class JsonListConverter {

    private val moshi = Moshi.Builder().build()
    private val adapter: JsonAdapter<List<String>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, String::class.java))

    fun toJson(list: List<String>): String {
        return adapter.toJson(list)
    }

    fun fromJson(json: String): List<String> {
        return adapter.fromJson(json) ?: emptyList()
    }
}