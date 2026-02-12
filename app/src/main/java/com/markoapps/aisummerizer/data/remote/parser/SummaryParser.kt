package com.markoapps.aisummerizer.data.remote.parser

import com.markoapps.aisummerizer.data.remote.model.NoteSummaryJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class SummaryParser {

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val adapter = moshi.adapter(NoteSummaryJson::class.java)

    fun parse(json: String): NoteSummaryJson {
        return adapter.fromJson(json)
            ?: throw IllegalStateException("Invalid JSON from AI")
    }
}